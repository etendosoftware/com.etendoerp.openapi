package com.etendoerp.openapi;

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
import org.openbravo.base.weld.WeldUtils;
import org.openbravo.service.web.WebService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OpenAPIController implements WebService {

  private static final String BASE_URL = "http://localhost:8080";
  private static final String RESOURCE_PACKAGE = "com.etendoerp.openapi";

  @Override
  public void doGet(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      OpenAPI openAPI = initializeOpenAPI();
      configureSecurity(openAPI);
      openAPI = applyEndpoints(openAPI);
      String openApiJson = serializeOpenAPI(openAPI);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(openApiJson);
    } catch (Exception e) {
      throw new ServletException("Error initializing OpenAPI", e);
    }
  }

  private OpenAPI initializeOpenAPI() {
    return new OpenAPI().info(new Info().title("Etendo API Headless")
            .description("OpenAPI definition for Etendo API Headless")
            .version("1.0.0")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation().description("GitHub repository")
            .url("https://github.com/etendosoftware/etendo_core"))
        .servers(Collections.singletonList(new Server().url(BASE_URL).description("Local Server")));
  }

  private void configureSecurity(OpenAPI openAPI) throws IOException {
    Components components = new Components().addSecuritySchemes("basicAuth",
        new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .scheme("basic")
            .description("Basic authentication with username and password"));
    openAPI.components(components);

    SecurityScheme bearerAuthScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("Bearer token authentication using token from <a href=\"http://localhost:8080/etendo/web/com.smf.securewebservices/doc/#/Login/post_sws_login\" target=\"_blank\">Login</a> endpoint");
    openAPI.components(components.addSecuritySchemes("bearerAuth", bearerAuthScheme));

    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
    openAPI.addSecurityItem(securityRequirement);

    openAPI.setSecurity(
        Collections.singletonList(securityRequirement)
    );
  }

  private OpenAPI applyEndpoints(OpenAPI openAPI) throws OpenApiConfigurationException {
    Set<String> resourcePackages = new HashSet<>();
    resourcePackages.add(RESOURCE_PACKAGE);

    SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(openAPI)
        .resourcePackages(resourcePackages);

    OpenApiContext ctx = new GenericOpenApiContext<>().openApiConfiguration(oasConfig).init();

    OpenAPI updatedOpenAPI = ctx.read();

    for (OpenAPIEndpoint endpoint : WeldUtils.getInstances(OpenAPIEndpoint.class)) {
      endpoint.add(updatedOpenAPI);
    }
    return updatedOpenAPI;
  }

  private String serializeOpenAPI(OpenAPI openAPI) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    String output =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(openAPI);
    output = output.replaceAll("\"type\" : \"HTTP\"", "\"type\" : \"http\"");
    return output;
  }

  @Override
  public void doPost(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }

  @Override
  public void doDelete(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }

  @Override
  public void doPut(String path, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Not implemented
  }
}
