package com.etendoerp.openapi;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
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
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Controller class for handling OpenAPI requests.
 */
public class OpenAPIController implements WebService {

  public static final String BEARER_TOKEN_DESCRIPTION = "Bearer token authentication using token from <a href=\"%s/web/com.smf.securewebservices/doc/#/Login/post_sws_login\" target=\"_blank\">Login</a> endpoint.";
  public static final String BASIC_AUTH_DESCRIPTION = "Basic authentication with username and password";
  private static final String DEFAULT_BASE_URL = "%s/%s";
  private static final String RESOURCE_PACKAGE = "com.etendoerp.openapi";
  private static final Logger log = LogManager.getLogger(OpenAPIController.class);

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
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(openApiJson);
    } catch (Exception e) {
      throw new ServletException("Error initializing OpenAPI", e);
    }
  }

  /**
   * Generates the OpenAPI specification JSON for the specified flow and base URL.
   * <p>
   * This method initializes the OpenAPI object with the given base URL, configures security settings,
   * applies endpoints based on the provided tag, and serializes the OpenAPI object to JSON format.
   *
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
  public String getOpenAPIJson(String hostAddress, String tag, String baseUrl) throws OpenApiConfigurationException, IOException {
    if (baseUrl == null) {
      baseUrl = String.format(DEFAULT_BASE_URL, hostAddress, getContextName());
    }
    OpenAPI openAPI = initializeOpenAPI(baseUrl);
    configureSecurity(openAPI, baseUrl);
    openAPI = applyEndpoints(openAPI, tag);
    String openApiJson = serializeOpenAPI(openAPI);
    return openApiJson;
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
