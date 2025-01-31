package com.etendoerp.openapi.model.jobs;

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
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class JobsAndActionsEndpoint implements OpenAPIEndpoint {

  private static final String JOBS_ACTION_HANDLER = "com.smf.jobs.defaults";
  private static final String BASE_PATH = "/org.openbravo.client.kernel";
  private static final List<String> tags = List.of("Jobs and Actions");

  @Override
  public boolean isValid(String tag) {
    if(tag == null) {
      return true;
    }
    return tags.contains(tag);
  }

  @Override
  public void add(OpenAPI openAPI) {
    Schema<?> processOrdersDefaultsRequestSchema = defineProcessOrdersDefaultsRequestSchema();
    String processOrdersDefaultsRequestExample = "{\r    \"documentStatuses\":[\r        \"CO\"\r    ], \r    \"isProcessing\":\"\", \r    \"tabId\":\"294\"\r}";

    Schema<?> processOrdersDefaultsResponseSchema = defineProcessOrdersDefaultsResponseSchema();
    String processOrdersDefaultsResponseExample = "{\"actions\":[\"CL\",\"RE\"]}";

    Schema<?> processOrdersRequestSchema = defineProcessOrdersRequestSchema();
    String processOrdersRequestExample = "{\r    \"recordIds\":[\r        \"0AC230C0DDA4435A949B40602A183F45\"\r    ], \r    \"_buttonValue\":\"DONE\", \r    \"_params\":{\r        \"DocAction\":\"CL\"\r    }, \r    \"_entityName\":\"Order\"\r}";

    Schema<?> processOrdersResponseSchema = defineProcessOrdersResponseSchema();
    String processOrdersResponseExample = "{\n" + "    \"responseActions\": [\n" + "        {\n" + "            \"showMsgInProcessView\": {\n" + "                \"msgType\": \"success\",\n" + "                \"msgTitle\": \"Success\",\n" + "                \"msgText\": \"1000000: Process completed successfully\\n\"\n" + "            }\n" + "        }\n" + "    ],\n" + "    \"refreshParent\": true\n" + "}";

    List<Parameter> commonHeaders = new ArrayList<>();

    createJobsAndActionsEndpoint(openAPI, "ProcessOrdersDefaults",
        "Initializes job processing with default settings",
        "This endpoint initializes job processing with default settings based on the provided document statuses and tab ID.",
        processOrdersDefaultsRequestSchema, processOrdersDefaultsRequestExample,
        processOrdersDefaultsResponseSchema, processOrdersDefaultsResponseExample, commonHeaders);

    createJobsAndActionsEndpoint(openAPI, "ProcessOrders",
        "Executes job processing for specified orders",
        "This endpoint executes job processing for the specified orders, performing actions like closing the document.",
        processOrdersRequestSchema, processOrdersRequestExample, processOrdersResponseSchema,
        processOrdersResponseExample, commonHeaders);
  }

  private void createJobsAndActionsEndpoint(OpenAPI openAPI, String actionName, String summary,
      String description, Schema<?> requestSchema, String requestExample, Schema<?> responseSchema,
      String responseExample, List<Parameter> headers) {

    Parameter queryParameter = new Parameter().in("query")
        .name("_action")
        .required(true)
        .schema(new Schema<String>().type("string").example(JOBS_ACTION_HANDLER + "." + actionName))
        .description("Specifies the action to execute.");

    List<Parameter> queryParameters = new ArrayList<>();
    queryParameters.add(queryParameter);
    if(StringUtils.equals(actionName, "ProcessOrders")) {
      Parameter processId = new Parameter().in("query")
          .name("processId")
          .required(true)
          .schema(new Schema<String>().type("string").example("8DF818E471394C01A6546A4AB7F5E529")
          .description("Specifies the id of the process to execute."));
      queryParameters.add(processId);
      Parameter reportId = new Parameter().in("query")
          .name("reportId")
          .required(true)
          .schema(new Schema<String>().type("string").example("null")
              .description("Report ID to be used for the process."));
      queryParameters.add(reportId);
      Parameter windowId = new Parameter().in("query")
          .name("windowId")
          .required(true)
          .schema(new Schema<String>().type("string").example("18")
              .description("Window ID to be used for the process."));
      queryParameters.add(windowId);
    }

    RequestBody requestBody = new RequestBody().description(
            "Payload for executing the " + actionName + " action.")
        .content(new Content().addMediaType("application/json",
            new MediaType().schema(requestSchema).example(requestExample)))
        .required(true);

    ApiResponses apiResponses = new ApiResponses().addApiResponse("200",
            createApiResponse(responseSchema, responseExample))
        .addApiResponse("400", new ApiResponse().description("Bad Request."))
        .addApiResponse("500", new ApiResponse().description("Internal Server Error."));

    Operation operation = new Operation().summary(summary)
        .description(description);
    tags.forEach(operation::addTagsItem);

    for (Parameter param : queryParameters) {
      operation.addParametersItem(param);
    }

    for (Parameter header : headers) {
      operation.addParametersItem(header);
    }

    operation.setRequestBody(requestBody);

    operation.setResponses(apiResponses);

    PathItem pathItem = new PathItem();
    pathItem.post(operation);
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }

    openAPI.getPaths().addPathItem(BASE_PATH + "?_action=" + JOBS_ACTION_HANDLER + "." + actionName, pathItem);

    addSchema(openAPI, actionName + "Response", responseSchema);
    if (openAPI.getTags() == null) {
      openAPI.setTags(new ArrayList<>());
    }
    for (String tag : tags) {
      if (openAPI.getTags().stream().noneMatch(t -> t.getName().equals(tag))) {
        String tagDescription = "";
        if ("Jobs and Actions".equals(tag)) {
          tagDescription = "Endpoints related to Jobs and Actions processing.";
        }
        openAPI.addTagsItem(new Tag().name(tag).description(tagDescription));
      }
    }
  }

  private Parameter createHeaderParameter(String name, String example, boolean required,
      String description) {
    return new Parameter().in("header")
        .name(name)
        .required(required)
        .schema(new Schema<String>().type("string").example(example))
        .description(description);
  }

  private ApiResponse createApiResponse(Schema<?> schema, String example) {
    String mediaType = "application/json";
    if (schema.getFormat() != null && schema.getFormat().equalsIgnoreCase("html")) {
      mediaType = "text/html";
    }

    return new ApiResponse().description("Successful response.")
        .content(
            new Content().addMediaType(mediaType, new MediaType().schema(schema).example(example)));
  }

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

  private Schema<?> defineProcessOrdersDefaultsRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    Schema<?> items = new Schema<>().type("array");
    items.setAdditionalProperties(new Schema<>().type("string").example("CO"));
    schema.addProperties("documentStatuses", items);
    schema.addProperties("isProcessing", new Schema<>().type("string").example(""));
    schema.addProperties("tabId", new Schema<>().type("string").example("294"));

    schema.required(Arrays.asList("documentStatuses", "tabId"));

    return schema;
  }

  private Schema<?> defineProcessOrdersDefaultsResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    Schema<?> items = new Schema<>().type("array");
    items.setAdditionalProperties(new Schema<>().type("string").example("CL"));
    schema.addProperties("actions", items);

    schema.required(List.of("actions"));

    return schema;
  }

  private Schema<?> defineProcessOrdersRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    Schema<?> items = new Schema<>().type("array");
    items.setAdditionalProperties(new Schema<>().type("string").example("0AC230C0DDA4435A949B40602A183F45"));

    schema.addProperties("recordIds", items);
    schema.addProperties("_buttonValue", new Schema<>().type("string").example("DONE"));
    schema.addProperties("_params", new Schema<>().type("object")
        .addProperties("DocAction", new Schema<>().type("string").example("CL")));
    schema.addProperties("_entityName", new Schema<>().type("string").example("Order"));

    schema.required(Arrays.asList("recordIds", "_buttonValue", "_params", "_entityName"));

    return schema;
  }

  private Schema<?> defineProcessOrdersResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    Schema<Object> responseActionsSchema = new Schema<>();
    responseActionsSchema.type("array");
    Schema<Object> responseActionItemSchema = new Schema<>();
    responseActionItemSchema.type("object");

    Schema<Object> showMsgInProcessViewSchema = new Schema<>();
    showMsgInProcessViewSchema.type("object");
    showMsgInProcessViewSchema.addProperties("msgType",
        new Schema<>().type("string").example("success"));
    showMsgInProcessViewSchema.addProperties("msgTitle",
        new Schema<>().type("string").example("Success"));
    showMsgInProcessViewSchema.addProperties("msgText",
        new Schema<>().type("string").example("1000000: Process completed successfully\n"));

    responseActionItemSchema.addProperties("showMsgInProcessView", showMsgInProcessViewSchema);
    responseActionsSchema.setAdditionalProperties(responseActionItemSchema);

    schema.addProperties("responseActions", responseActionsSchema);
    schema.addProperties("refreshParent", new Schema<>().type("boolean").example(true));

    schema.required(Arrays.asList("responseActions", "refreshParent"));

    return schema;
  }
}
