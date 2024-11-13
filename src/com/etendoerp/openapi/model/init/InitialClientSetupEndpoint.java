package com.etendoerp.openapi.model.init;

import com.etendoerp.openapi.model.OpenAPIEndpoint;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class InitialClientSetupEndpoint implements OpenAPIEndpoint {

  private static final String BASE_PATH = "/etendo/ad_forms/InitialClientSetup.html?stateless=true";

  @Override
  public void add(OpenAPI openAPI) {
    // Definir los esquemas y ejemplos para la acción InitialClientSetup
    Schema<?> initialClientSetupRequestSchema = defineInitialClientSetupRequestSchema();
    String initialClientSetupRequestExample = "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"Command\"\r\n\r\n" +
        "OK\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpLastFieldChanged\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpClient\"\r\n\r\n" +
        "Cliente\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpPassword\"\r\n\r\n" +
        "admin\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpClientUser\"\r\n\r\n" +
        "ClienteAdmin\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpConfirmPassword\"\r\n\r\n" +
        "admin\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpCurrency\"\r\n\r\n" +
        "102\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpFile\"; filename=\"\"\r\n" +
        "Content-Type: application/octet-stream\r\n\r\n\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpTreeClass\"\r\n\r\n" +
        "org.openbravo.erpCommon.modules.ModuleReferenceDataClientTree\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpNodeId\"\r\n\r\n" +
        "0\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpLevel\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r\r\n" +
        "Content-Disposition: form-data; name=\"inpNodes\"\r\n\r\n" +
        "0\r\n" +
        "------WebKitFormBoundaryESnGV3KpzjPoQw1r--\r\n";

    Schema<?> initialClientSetupResponseSchema = defineInitialClientSetupResponseSchema();
    String initialClientSetupResponseExample = "<html>Configuración Inicial del Cliente</html>";

    List<Parameter> commonHeaders = Arrays.asList(
        createHeaderParameter("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7", "string", true, "Specifies the media types that are acceptable for the response."),
        createHeaderParameter("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryESnGV3KpzjPoQw1r", "string", true, "Indicates the media type of the resource."),
        createHeaderParameter("Origin", "http://localhost:8080", "string", false, "The origin of the request."),
        createHeaderParameter("Referer", "http://localhost:8080/etendo/ad_forms/InitialClientSetup.html?noprefs=true&hideMenu=true&Command=DEFAULT", "string", false, "The address of the previous web page from which a link to the currently requested page was followed."),
        createHeaderParameter("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36", "string", false, "The user agent string of the user agent.")
    );

    List<String> initialClientSetupTags = Arrays.asList("Initial Setup");

    createInitialClientSetupEndpoint(openAPI,
        "InitialClientSetup",
        "Handles the initial client setup form submission",
        "This endpoint processes the initial client setup form submission, including client credentials and configuration parameters.",
        initialClientSetupRequestSchema,
        initialClientSetupRequestExample,
        initialClientSetupResponseSchema,
        initialClientSetupResponseExample,
        commonHeaders,
        initialClientSetupTags,
        "POST");
  }

  /**
   * Método para crear el endpoint relacionado con Initial Client Setup
   */
  private void createInitialClientSetupEndpoint(OpenAPI openAPI, String actionName, String summary, String description,
      Schema<?> requestSchema, String requestExample,
      Schema<?> responseSchema, String responseExample,
      List<Parameter> headers, List<String> tags, String httpMethod) {

    List<Parameter> queryParameters = Collections.emptyList();

    RequestBody requestBody = new RequestBody()
        .description("Payload for executing the Initial Client Setup action.")
        .content(new Content().addMediaType("multipart/form-data",
            new MediaType().schema(requestSchema).example(requestExample)))
        .required(true);

    ApiResponses apiResponses = new ApiResponses()
        .addApiResponse("200", createApiResponse("Successful response.", responseSchema, responseExample))
        .addApiResponse("400", new ApiResponse().description("Bad Request."))
        .addApiResponse("500", new ApiResponse().description("Internal Server Error."));

    Operation operation = new Operation()
        .summary(summary)
        .description(description)
        .addTagsItem(tags.get(0)); // Asignar el primer tag de la lista

    for (Parameter param : queryParameters) {
      operation.addParametersItem(param);
    }

    for (Parameter header : headers) {
      operation.addParametersItem(header);
    }

    operation.setRequestBody(requestBody);

    operation.setResponses(apiResponses);

    PathItem pathItem = new PathItem();
    if ("GET".equalsIgnoreCase(httpMethod)) {
      pathItem.get(operation);
    } else if ("POST".equalsIgnoreCase(httpMethod)) {
      pathItem.post(operation);
    }
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }
    openAPI.getPaths().addPathItem(BASE_PATH, pathItem);

    addSchema(openAPI, actionName + "Response", responseSchema);

    if (openAPI.getTags() == null) {
      openAPI.setTags(new ArrayList<>());
    }
    for (String tag : tags) {
      if (openAPI.getTags().stream().noneMatch(t -> t.getName().equals(tag))) {
        String tagDescription = "";
        if ("Initial Client Setup".equals(tag)) {
          tagDescription = "Endpoints related to the initial client setup process.";
        }
        openAPI.addTagsItem(new Tag().name(tag).description(tagDescription));
      }
    }
  }

  /**
   * Método para crear parámetros de consulta (query parameters)
   */
  private Parameter createQueryParameter(String name, String example, String type, boolean required, String description) {
    return new Parameter()
        .in("query")
        .name(name)
        .required(required)
        .schema(new Schema<String>().type(type).example(example))
        .description(description);
  }

  /**
   * Método para crear parámetros de cabecera (header parameters)
   */
  private Parameter createHeaderParameter(String name, String example, String type, boolean required, String description) {
    return new Parameter()
        .in("header")
        .name(name)
        .required(required)
        .schema(new Schema<String>().type(type).example(example))
        .description(description);
  }

  /**
   * Método para crear respuestas de API
   */
  private ApiResponse createApiResponse(String description, Schema<?> schema, String example) {
    // Determinar el tipo de contenido basado en el esquema
    String mediaType = "text/html";
    // Si la respuesta fuera JSON, podrías usar "application/json"

    return new ApiResponse()
        .description(description)
        .content(new Content().addMediaType(mediaType,
            new MediaType().schema(schema).example(example)));
  }

  /**
   * Método para añadir esquemas al componente de OpenAPI
   */
  private void addSchema(OpenAPI openAPI, String key, Schema<?> schema) {
    if (openAPI.getComponents() == null) {
      openAPI.setComponents(new io.swagger.v3.oas.models.Components());
    }
    if (openAPI.getComponents().getSchemas() == null) {
      openAPI.getComponents().setSchemas(new HashMap<>());
    }
    if (!openAPI.getComponents().getSchemas().containsKey(key)) {
      openAPI.getComponents().addSchemas(key, schema);
    }
  }

  /**
   * Define el esquema de la solicitud para InitialClientSetup (multipart/form-data)
   */
  private Schema<?> defineInitialClientSetupRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");
    schema.description("Multipart form data for Initial Client Setup.");

    schema.addProperty("Command", new Schema<>().type("string").example("OK"));
    schema.addProperty("inpLastFieldChanged", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpClient", new Schema<>().type("string").example("Cliente"));
    schema.addProperty("inpPassword", new Schema<>().type("string").example("admin"));
    schema.addProperty("inpClientUser", new Schema<>().type("string").example("ClienteAdmin"));
    schema.addProperty("inpConfirmPassword", new Schema<>().type("string").example("admin"));
    schema.addProperty("inpCurrency", new Schema<>().type("string").example("102"));
    schema.addProperty("inpFile", new Schema<>().type("string").format("binary").nullable(true).example(""));
    schema.addProperty("inpTreeClass", new Schema<>().type("string").example("org.openbravo.erpCommon.modules.ModuleReferenceDataClientTree"));
    schema.addProperty("inpNodeId", new Schema<>().type("string").example("0"));
    schema.addProperty("inpLevel", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpNodes", new Schema<>().type("string").example("0"));

    // Definir campos requeridos
    schema.required(Arrays.asList(
        "Command",
        "inpClient",
        "inpPassword",
        "inpClientUser",
        "inpConfirmPassword",
        "inpCurrency",
        "inpTreeClass",
        "inpNodeId",
        "inpNodes"
    ));

    return schema;
  }

  /**
   * Define el esquema de la respuesta para InitialClientSetup (HTML)
   */
  private Schema<?> defineInitialClientSetupResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");
    schema.description("HTML response for Initial Client Setup.");

    return schema;
  }
}