package com.etendoerp.openapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbravo.base.HttpBaseUtils;
import org.openbravo.base.session.OBPropertiesProvider;
import org.openbravo.base.weld.WeldUtils;
import org.openbravo.service.web.WebService;

import com.etendoerp.openapi.model.OpenAPIEndpoint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Controller class for handling OpenAPI requests.
 */
public class OpenAPIController implements WebService {

  public static final String BEARER_TOKEN_DESCRIPTION = "Bearer token authentication using token from <a href=\"%s/web/com.smf.securewebservices/doc/#/Login/post_sws_login\" target=\"_blank\">Login</a> endpoint.";
  public static final String BASIC_AUTH_DESCRIPTION = "Basic authentication with username and password";
  private static final String DEFAULT_BASE_URL = "%s/%s";
  private static final String RESOURCE_PACKAGE = "com.etendoerp.openapi";
  private static final Logger log = LogManager.getLogger(OpenAPIController.class);


  private static final Schema<?> STRING_SCHEMA = new Schema<>()
      .type("string");
  public static final String APPLICATION_JSON = "application/json";

  /**
   * Handles HTTP GET requests to generate OpenAPI documentation.
   *
   * @param path
   *     The request path.
   * @param request
   *     The HttpServletRequest object.
   * @param response
   *     The HttpServletResponse object.
   * @throws Exception
   *     If an error occurs during the request handling.
   */
  @Override
  public void doGet(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      String hostAddress = HttpBaseUtils.getLocalHostAddress(request, true);
      String openApiJson = getOpenAPIJson(hostAddress, request.getParameter("tag"), request.getParameter("host"));
      response.setContentType(APPLICATION_JSON);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(openApiJson);
    } catch (Exception e) {
      throw new ServletException("Error initializing OpenAPI", e);
    }
  }

  /**
   * Generates the OpenAPI specification JSON for the specified flow and base URL.
   * <p>
   * This method provides backward compatibility by calling the overloaded method with excludeLogin set to false.
   *
   * @param hostAddress
   *     the host address for the OpenAPI specification
   * @param tag
   *     the tag used to filter endpoints
   * @param baseUrl
   *     the base URL for the OpenAPI specification; if null, a default base URL is used
   * @return the OpenAPI specification in JSON format
   * @throws OpenApiConfigurationException
   *     if an error occurs during OpenAPI configuration
   * @throws IOException
   *     if an error occurs during serialization
   */
  public String getOpenAPIJson(String hostAddress, String tag,
      String baseUrl) throws OpenApiConfigurationException, IOException {
    return getOpenAPIJson(hostAddress, tag, baseUrl, false);
  }

  /**
   * Generates the OpenAPI specification JSON for the specified flow and base URL.
   * <p>
   * This method initializes the OpenAPI object with the given base URL, configures security settings,
   * applies endpoints based on the provided tag, and serializes the OpenAPI object to JSON format.
   * The login endpoint can be optionally excluded from the generated specification.
   *
   * @param hostAddress
   *     the host address for the OpenAPI specification
   * @param tag
   *     the tag used to filter endpoints
   * @param baseUrl
   *     the base URL for the OpenAPI specification; if null, a default base URL is used
   * @param excludeLogin
   *     if true, the login endpoint will be excluded from the OpenAPI specification
   * @return the OpenAPI specification in JSON format
   * @throws OpenApiConfigurationException
   *     if an error occurs during OpenAPI configuration
   * @throws IOException
   *     if an error occurs during serialization
   */
  public String getOpenAPIJson(String hostAddress, String tag,
      String baseUrl, boolean excludeLogin) throws OpenApiConfigurationException, IOException {

    if (baseUrl == null) {
      baseUrl = String.format(DEFAULT_BASE_URL, hostAddress, getContextName());
    }
    OpenAPI openAPI = initializeOpenAPI(baseUrl);
    configureSecurity(openAPI, baseUrl);
    openAPI = applyEndpoints(openAPI, tag);
    if (!excludeLogin) {
      addLoginEndpoint(openAPI);
    }
    String openApiJson = serializeOpenAPI(openAPI);
    return openApiJson;
  }

  private void addLoginEndpoint(OpenAPI openAPI) {
    // Create login endpoint path
    PathItem loginPath = new PathItem();

    // Create POST operation for login
    Operation loginOperation = new Operation()
        .summary("Obtain authentication token")
        .description(
            "Obtains an authentication token (JWT) associated with an Etendo context (User, Role, Org, Warehouse) and the list of usable roles, organizations and warehouses.\nBoth the lists of roles, organizations and warehouses can be hidden to simplify the request result.")
        .addTagsItem("Login");

    // Add query parameters
    loginOperation.addParametersItem(new Parameter()
        .name("showRoles")
        .in("query")
        .description("Show role list.")
        .required(false)
        .schema(getBooleanSchemaTrue()));

    loginOperation.addParametersItem(new Parameter()
        .name("showOrgs")
        .in("query")
        .description("Show Organization lists.")
        .required(false)
        .schema(getBooleanSchemaTrue()));

    loginOperation.addParametersItem(new Parameter()
        .name("showWarehouses")
        .in("query")
        .description("Show Warehouse lists.")
        .required(false)
        .schema(getBooleanSchemaTrue()));

    // Create request body schema
    Schema<Object> loginRequestSchema = new Schema<Object>()
        .type("object")
        .required(List.of("username", "password"))
        .addProperties("username", new Schema<String>().type("string").example("admin"))
        .addProperties("password", new Schema<String>().type("string").example("admin"))
        .addProperties("role", new Schema<String>().type("string").example("0"))
        .addProperties("organization", new Schema<String>().type("string").example("0"))
        .addProperties("warehouse", new Schema<String>().type("string").example("0"));

    // Set request body
    RequestBody requestBody = new RequestBody()
        .description("Username, password, role id, organization id, storage id. Only the first two are required.")
        .required(true)
        .content(new Content()
            .addMediaType(APPLICATION_JSON,
                new MediaType().schema(loginRequestSchema)));

    loginOperation.setRequestBody(requestBody);

    // Create response schemas
    addLoginResponseSchemas(openAPI);

    // Add responses
    ApiResponses responses = new ApiResponses();
    responses.addApiResponse("200", new ApiResponse()
        .description("Ok.")
        .content(new Content()
            .addMediaType(APPLICATION_JSON,
                new MediaType().schema(new Schema<Object>().$ref("#/components/schemas/LogResp")))));

    responses.addApiResponse("401", new ApiResponse()
        .description("No auth."));

    loginOperation.setResponses(responses);

    // Set the POST operation on the path
    loginPath.setPost(loginOperation);

    // Add the path to OpenAPI
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }
    openAPI.getPaths().addPathItem("/sws/login", loginPath);

    // Add Login tag
    if (openAPI.getTags() == null) {
      openAPI.setTags(new ArrayList<>());
    }
    boolean hasLoginTag = openAPI.getTags().stream()
        .anyMatch(tag -> "Login".equals(tag.getName()));
    if (!hasLoginTag) {
      openAPI.getTags().add(new Tag().name("Login").description("Authentication endpoints"));
    }
  }

  /**
   * Creates a boolean schema with default value and example set to true.
   * <p>
   * This is a utility method for creating consistent boolean schema objects
   * used in OpenAPI parameter definitions.
   *
   * @return a Schema object configured as boolean type with default and example values set to true
   */
  private Schema getBooleanSchemaTrue() {
    Schema<Boolean> booleanSchema = new Schema<>();
    booleanSchema.type("boolean");
    booleanSchema.setDefault(true);
    booleanSchema.setExample(true);
    return booleanSchema;
  }

  /**
   * Adds login response schemas to the OpenAPI specification.
   * <p>
   * This method defines the schema components for login responses, including:
   * - Warehouse schema with id and name properties
   * - Organization schema with id, name, and warehouseList properties
   * - Role schema with id, name, and orgList properties
   * - Main LogResp schema containing status, token, and roleList properties
   *
   * @param openAPI
   *     the OpenAPI object to add the schemas to
   */
  private void addLoginResponseSchemas(OpenAPI openAPI) {
    if (openAPI.getComponents() == null) {
      openAPI.setComponents(new Components());
    }
    if (openAPI.getComponents().getSchemas() == null) {
      openAPI.getComponents().setSchemas(new HashMap<>());
    }

    // Warehouse schema
    Schema<Object> warehouseSchema = new Schema<>();
    warehouseSchema.type("object");
    warehouseSchema.addProperties("id", new Schema<String>().type("string").example("0"));
    warehouseSchema.addProperties("name", new Schema<String>().type("string").example("*"));

    // Organization schema
    Schema<Object> orgSchema = new Schema<>();
    orgSchema.type("object");
    orgSchema.addProperties("id", new Schema<String>().type("string").example("0"));
    orgSchema.addProperties("name", new Schema<String>().type("string").example("*"));
    orgSchema.addProperties("warehouseList", new ArraySchema().items(warehouseSchema));

    // Role schema
    Schema<Object> roleSchema = new Schema<>();
    roleSchema.type("object");
    roleSchema.addProperties("id", new Schema<String>().type("string").example("0"));
    roleSchema.addProperties("name", getStringSchema("System Administrator"));
    roleSchema.addProperties("orgList", new ArraySchema().items(orgSchema));

    // Main LogResp schema
    Schema<Object> logRespSchema = new Schema<>();
    logRespSchema.type("object");
    logRespSchema.title("Login response");
    logRespSchema.addProperties("status", new Schema<String>().type("string").example("success"));
    logRespSchema.addProperties("token", new Schema<String>().type("string").example(
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJzd3MiLCJyb2xlIjoiMCIsIm9yZ2FuaXphdGlvbiI6IjAiLCJpc3MiOiJzd3MiLCJleHAiOjE1ODE1MjMxNTcsInVzZXIiOiIxMDAiLCJpYXQiOjE1ODE1MTU3Nzd9.ZyAwUz7B1xOuMJzrtt6LJo0O7UNi133W15Uv_RfW3IM"));
    logRespSchema.addProperties("roleList", new ArraySchema().items(roleSchema));

    // Add schemas to components
    openAPI.getComponents().addSchemas("LogResp", logRespSchema);
  }

  /**
   * Creates a string schema with the specified example value.
   * <p>
   * This is a utility method for creating consistent string schema objects
   * used in OpenAPI response definitions.
   *
   * @param example
   *     the example value to set for the string schema
   * @return a Schema object configured as string type with the provided example value
   */
  private static Schema getStringSchema(String example) {
    Schema<String> stringSchema = new Schema<>();
    stringSchema.type("string");
    stringSchema.setExample(example);
    return stringSchema;
  }

  /**
   * Initializes the OpenAPI configuration for the Etendo API Headless.
   *
   * @param baseUrl
   *     the base URL for the API server
   * @return an instance of {@link OpenAPI} configured with the API information, license, external documentation, and server details
   */
  private OpenAPI initializeOpenAPI(String baseUrl) {
    return new OpenAPI().info(new Info().title("Etendo API Headless")
            .description("OpenAPI definition for Etendo API Headless")
            .version("1.0.0")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation().description("GitHub repository")
            .url("https://github.com/etendosoftware/etendo_core"))
        .servers(Collections.singletonList(new Server().url(baseUrl).description(
            (StringUtils.containsIgnoreCase(baseUrl, "localhost") ? "Etendo Classic Server" : "Local Server"))));
  }

  /**
   * Configures the security settings for the OpenAPI specification.
   * <p>
   * This method adds basic and bearer authentication schemes to the OpenAPI components.
   * It also sets the security requirements for the OpenAPI specification.
   *
   * @param openAPI
   *     the OpenAPI object to configure
   * @param baseUrl
   *     the base URL for the OpenAPI specification
   */
  private void configureSecurity(OpenAPI openAPI, String baseUrl) {
    Components components = new Components().addSecuritySchemes("basicAuth",
        createSecuritySchema("basic", null, BASIC_AUTH_DESCRIPTION));
    openAPI.components(components);

    SecurityScheme bearerAuthScheme = createSecuritySchema("bearer", "JWT",
        String.format(BEARER_TOKEN_DESCRIPTION, baseUrl));
    openAPI.components(components.addSecuritySchemes("bearerAuth", bearerAuthScheme));

    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
    openAPI.addSecurityItem(securityRequirement);

    openAPI.setSecurity(
        Collections.singletonList(securityRequirement)
    );
  }


  /**
   * Creates a SecurityScheme object with the specified scheme, bearer format, and description.
   * <p>
   * This method initializes a SecurityScheme object of type HTTP and sets its scheme.
   * If a bearer format is provided, it is set on the SecurityScheme object.
   * If a description is provided, it is set on the SecurityScheme object.
   *
   * @param scheme
   *     the security scheme (e.g., "basic", "bearer")
   * @param bearerFormat
   *     the format of the bearer token (e.g., "JWT"), can be null or empty
   * @param description
   *     a description of the security scheme, can be null or empty
   * @return a SecurityScheme object configured with the provided parameters
   */
  private SecurityScheme createSecuritySchema(String scheme, String bearerFormat, String description) {
    SecurityScheme result = new SecurityScheme().type(SecurityScheme.Type.HTTP);
    result.scheme(scheme);
    if (bearerFormat != null && !bearerFormat.isEmpty()) {
      result.bearerFormat(bearerFormat);
    }
    if (description != null && !description.isEmpty()) {
      result.description(description);
    }
    return result;
  }

  private OpenAPI applyEndpoints(OpenAPI openAPI, String tag) throws OpenApiConfigurationException {
    Set<String> resourcePackages = new HashSet<>();
    resourcePackages.add(RESOURCE_PACKAGE);

    SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(openAPI)
        .resourcePackages(resourcePackages);

    OpenApiContext ctx = new GenericOpenApiContext<>().openApiConfiguration(oasConfig).init();
    OpenAPI updatedOpenAPI = ctx.read();

    for (OpenAPIEndpoint endpoint : WeldUtils.getInstances(OpenAPIEndpoint.class)) {
      if (tag == null || endpoint.isValid(tag)) {
        endpoint.add(updatedOpenAPI);
      }
    }
    return updatedOpenAPI;
  }

  /**
   * Serializes the OpenAPI object to a JSON string.
   *
   * @param openAPI
   *     The OpenAPI object to serialize.
   * @return The serialized JSON string.
   * @throws IOException
   *     If an I/O error occurs during serialization.
   */
  private String serializeOpenAPI(OpenAPI openAPI) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    String output = mapper.writeValueAsString(openAPI);
    output = output.replaceAll("\"type\":\"HTTP\"", "\"type\":\"http\"");
    return output;
  }

  private String getContextName() {
    return OBPropertiesProvider.getInstance().getOpenbravoProperties().getProperty("context.name");
  }

  /**
   * Handles HTTP POST requests. Not implemented.
   *
   * @param path
   *     The request path.
   * @param request
   *     The HttpServletRequest object.
   * @param response
   *     The HttpServletResponse object.
   * @throws Exception
   *     If an error occurs during the request handling.
   */
  @Override
  public void doPost(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }

  /**
   * Handles HTTP DELETE requests. Not implemented.
   *
   * @param path
   *     The request path.
   * @param request
   *     The HttpServletRequest object.
   * @param response
   *     The HttpServletResponse object.
   * @throws Exception
   *     If an error occurs during the request handling.
   */
  @Override
  public void doDelete(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }

  /**
   * Handles HTTP PUT requests. Not implemented.
   *
   * @param path
   *     The request path.
   * @param request
   *     The HttpServletRequest object.
   * @param response
   *     The HttpServletResponse object.
   * @throws Exception
   *     If an error occurs during the request handling.
   */
  @Override
  public void doPut(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }
}
