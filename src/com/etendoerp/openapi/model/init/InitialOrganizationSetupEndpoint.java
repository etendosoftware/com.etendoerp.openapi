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
public class InitialOrganizationSetupEndpoint implements OpenAPIEndpoint {

  private static final String BASE_PATH = "/etendo/ad_forms/InitialOrgSetup.html?stateless=true";
  private static final List<String> tags = Arrays.asList("Initial Setup");

  @Override
  public List<String> getTags() {
    return tags;
  }

  @Override
  public void add(OpenAPI openAPI) {
    Schema<?> initialOrgSetupRequestSchema = defineInitialOrgSetupRequestSchema();
    String initialOrgSetupRequestExample = "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"Command\"\r\n\r\n" +
        "OK\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpLastFieldChanged\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpOrganization\"\r\n\r\n" +
        "Org\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpOrgUser\"\r\n\r\n" +
        "Cliente\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpPassword\"\r\n\r\n" +
        "admin\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpConfirmPassword\"\r\n\r\n" +
        "admin\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpOrgType\"\r\n\r\n" +
        "3\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpParentOrg\"\r\n\r\n" +
        "0\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpcLocationId\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpcLocationId_R\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpCurrency\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpTreeClass\"\r\n\r\n" +
        "org.openbravo.erpCommon.modules.ModuleReferenceDataOrgTree\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpNodeId\"\r\n\r\n" +
        "7BFA8FF057AB46CAAB2FAAED8B870E32\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7\r\n" +
        "Content-Disposition: form-data; name=\"inpLevel\"\r\n\r\n\r\n" +
        "------WebKitFormBoundaryGFUPc5UZTjrBzWc7--\r\n";

    Schema<?> initialOrgSetupResponseSchema = defineInitialOrgSetupResponseSchema();
    String initialOrgSetupResponseExample = "<html>Configuración Inicial de la Organización</html>";

    List<Parameter> commonHeaders = Arrays.asList(
        createHeaderParameter("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryGFUPc5UZTjrBzWc7", "string", true, "Indicates the media type of the resource."),
        createHeaderParameter("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36", "string", false, "The user agent string of the user agent.")
    );


    // Crear endpoint para InitialOrgSetup - POST
    createInitialOrgSetupEndpoint(openAPI,
        "InitialOrgSetup",
        "Handles the initial organization setup form submission",
        "This endpoint processes the initial organization setup form submission, including organization credentials and configuration parameters.",
        initialOrgSetupRequestSchema,
        initialOrgSetupRequestExample,
        initialOrgSetupResponseSchema,
        initialOrgSetupResponseExample,
        commonHeaders, tags,
        "POST");
  }

  /**
   * Método para crear el endpoint relacionado con Initial Org Setup
   */
  private void createInitialOrgSetupEndpoint(OpenAPI openAPI, String actionName, String summary, String description,
      Schema<?> requestSchema, String requestExample,
      Schema<?> responseSchema, String responseExample,
      List<Parameter> headers, List<String> tags, String httpMethod) {

    // Definir los parámetros de consulta (query parameters) si los hay
    // En este caso, no hay parámetros de consulta en la solicitud proporcionada

    List<Parameter> queryParameters = Collections.emptyList();

    // Definir el cuerpo de la solicitud (request body)
    RequestBody requestBody = new RequestBody()
        .description("Payload for executing the Initial Organization Setup action.")
        .content(new Content().addMediaType("multipart/form-data",
            new MediaType().schema(requestSchema).example(requestExample)))
        .required(true);

    // Definir las respuestas de la API
    ApiResponses apiResponses = new ApiResponses()
        .addApiResponse("200", createApiResponse("Successful response.", responseSchema, responseExample))
        .addApiResponse("400", new ApiResponse().description("Bad Request."))
        .addApiResponse("500", new ApiResponse().description("Internal Server Error."));

    // Crear la operación
    Operation operation = new Operation()
        .summary(summary)
        .description(description)
        .addTagsItem(tags.get(0)); // Asignar el primer tag de la lista

    // Añadir parámetros de consulta si existen
    for (Parameter param : queryParameters) {
      operation.addParametersItem(param);
    }

    // Añadir encabezados
    for (Parameter header : headers) {
      operation.addParametersItem(header);
    }

    // Asignar el cuerpo de la solicitud
    operation.setRequestBody(requestBody);

    // Asignar las respuestas
    operation.setResponses(apiResponses);

    // Crear el PathItem
    PathItem pathItem = new PathItem();
    if ("GET".equalsIgnoreCase(httpMethod)) {
      pathItem.get(operation);
    } else if ("POST".equalsIgnoreCase(httpMethod)) {
      pathItem.post(operation);
    }
    // Puedes añadir otros métodos HTTP si es necesario

    // Añadir el PathItem al OpenAPI
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }

    openAPI.getPaths().addPathItem(BASE_PATH, pathItem);

    // Añadir el esquema al componente de OpenAPI
    addSchema(openAPI, actionName + "Response", responseSchema);

    // Añadir tags al OpenAPI para evitar duplicados y proporcionar descripciones
    if (openAPI.getTags() == null) {
      openAPI.setTags(new ArrayList<>());
    }
    for (String tag : tags) {
      if (openAPI.getTags().stream().noneMatch(t -> t.getName().equals(tag))) {
        String tagDescription = "";
        if ("Initial Org Setup".equals(tag)) {
          tagDescription = "Endpoints related to the initial organization setup process.";
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
   * Define el esquema de la solicitud para InitialOrgSetup (multipart/form-data)
   */
  private Schema<?> defineInitialOrgSetupRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");
    schema.description("Multipart form data for Initial Organization Setup.");

    schema.addProperty("Command", new Schema<>().type("string").example("OK"));
    schema.addProperty("inpLastFieldChanged", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpOrganization", new Schema<>().type("string").example("Org"));
    schema.addProperty("inpOrgUser", new Schema<>().type("string").example("Cliente"));
    schema.addProperty("inpPassword", new Schema<>().type("string").example("admin"));
    schema.addProperty("inpConfirmPassword", new Schema<>().type("string").example("admin"));
    schema.addProperty("inpOrgType", new Schema<>().type("string").example("3"));
    schema.addProperty("inpParentOrg", new Schema<>().type("string").example("0"));
    schema.addProperty("inpcLocationId", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpcLocationId_R", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpCurrency", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpTreeClass", new Schema<>().type("string").example("org.openbravo.erpCommon.modules.ModuleReferenceDataOrgTree"));
    schema.addProperty("inpNodeId", new Schema<>().type("string").example("7BFA8FF057AB46CAAB2FAAED8B870E32"));
    schema.addProperty("inpLevel", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("inpNodes", new Schema<>().type("string").example("7BFA8FF057AB46CAAB2FAAED8B870E32"));

    // Definir campos requeridos
    schema.required(Arrays.asList(
        "Command",
        "inpOrganization",
        "inpOrgUser",
        "inpPassword",
        "inpConfirmPassword",
        "inpOrgType",
        "inpParentOrg",
        "inpTreeClass",
        "inpNodeId",
        "inpNodes"
    ));

    return schema;
  }

  /**
   * Define el esquema de la respuesta para InitialOrgSetup (HTML)
   */
  private Schema<?> defineInitialOrgSetupResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");
    schema.description("HTML response for Initial Organization Setup.");

    // Si la respuesta es HTML, podrías no definir un esquema detallado.
    // Sin embargo, si quieres documentar partes específicas, podrías hacerlo aquí.

    return schema;
  }
}
