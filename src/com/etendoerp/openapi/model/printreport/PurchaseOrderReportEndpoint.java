package com.etendoerp.openapi.model.printreport;

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
public class PurchaseOrderReportEndpoint implements OpenAPIEndpoint {

  private static final String JOBS_ACTION_HANDLER = "com.smf.jobs.defaults";
  private static final String DEFAULTS_PROCESS_ACTION_HANDLER = "org.openbravo.client.application.process.DefaultsProcessActionHandler";
  private static final String BASE_REPORT_ACTION_HANDLER = "org.openbravo.client.application.report.BaseReportActionHandler";
  private static final String BASE_PATH = "/etendo/org.openbravo.client.kernel";

  @Override
  public void add(OpenAPI openAPI) {

    Schema<?> baseReportActionRequestSchema = defineBaseReportActionRequestSchema();
    String baseReportActionRequestExample = "{\r    \"_buttonValue\":\"HTML\", \r    \"_params\":{\r        \"AD_Org_ID\":null, \r        \"C_BPartner_ID\":null, \r        \"C_Currency_ID\":\"102\", \r        \"DateFrom\":\"2024-11-07\", \r        \"DateTo\":null, \r        \"C_Project_ID\":null, \r        \"M_Warehouse_ID\":null, \r        \"Status\":null\r    }\r}";

    Schema<?> baseReportActionResponseSchema = defineBaseReportActionResponseSchema();
    String baseReportActionResponseExample = "{\n" + "    \"retryExecution\": true,\n" + "    \"showResultsInProcessView\": true,\n" + "    \"refreshParent\": true,\n" + "    \"responseActions\": [\n" + "        {\n" + "            \"OBUIAPP_browseReport\": {\n" + "                \"processParameters\": {\n" + "                    \"AD_Org_ID\": null,\n" + "                    \"C_BPartner_ID\": null,\n" + "                    \"C_Currency_ID\": \"102\",\n" + "                    \"DateFrom\": \"2024-11-07\",\n" + "                    \"DateTo\": null,\n" + "                    \"C_Project_ID\": null,\n" + "                    \"M_Warehouse_ID\": null,\n" + "                    \"Status\": null,\n" + "                    \"processId\": \"4BDE0AF5E8C44B6C9575E388AAECDF69\",\n" + "                    \"reportId\": \"95A65133A5314CAABEE405B1EB2A639D\",\n" + "                    \"actionHandler\": \"org.openbravo.client.application.report.BaseReportActionHandler\"\n" + "                },\n" + "                \"tmpfileName\": \"3d5c89c9-c563-4395-8ce9-04be8e7b0ea5.html\",\n" + "                \"fileName\": \"Purchase Order Report-07-11-2024 14_01_38.html\",\n" + "                \"tabTitle\": \"Purchase Order Report\"\n" + "            }\n" + "        }\n" + "    ]\n" + "}";

    // Definir tags
    List<String> jobsActionsTags = List.of("Jobs and Actions");

    createJobsAndActionsEndpoint(openAPI, "BaseReportActionHandler",
        "Generates reports based on provided parameters",
        "This endpoint generates reports based on the provided parameters and returns the report details.",
        baseReportActionRequestSchema, baseReportActionRequestExample,
        baseReportActionResponseSchema, baseReportActionResponseExample,
        jobsActionsTags);

    createJobsAndActionsEndpoint(openAPI, "BaseReportActionHandler&mode=DOWNLOAD",
        "Generates reports based on provided parameters",
        "This endpoint generates reports based on the provided parameters and returns the report details.",
        baseReportActionRequestSchema, baseReportActionRequestExample,
        baseReportActionResponseSchema, baseReportActionResponseExample, jobsActionsTags);
  }

  private void createJobsAndActionsEndpoint(OpenAPI openAPI, String actionName, String summary,
      String description, Schema<?> requestSchema, String requestExample, Schema<?> responseSchema,
      String responseExample, List<String> tags) {

    List<Parameter> queryParameters = new ArrayList<>();
    String actionHandler;
    switch (actionName) {
      case "DefaultsProcessActionHandler":
        actionHandler = DEFAULTS_PROCESS_ACTION_HANDLER;
        break;
      case "BaseReportActionHandler":
        actionHandler = BASE_REPORT_ACTION_HANDLER;
        break;
      default:
        String[] actionNameParts = actionName.split("&");
        actionHandler = BASE_REPORT_ACTION_HANDLER + "&" + actionNameParts[1];
    }

    String ah = actionHandler;
    if (StringUtils.contains(actionHandler, "&")) {
      String[] actionHandlerParts = actionHandler.split("&");
      ah = actionHandlerParts[0];
    }

    queryParameters.add(
        createQueryParameter("_action", ah, true, "Specifies the action to execute."));

    queryParameters.addAll(
      Arrays.asList(
          createQueryParameter("processId", "8DF818E471394C01A6546A4AB7F5E529", false,
              "ID of process to execute."),
          createQueryParameter("reportId", "95A65133A5314CAABEE405B1EB2A639D", false, "ID of report, if apply."),
          createQueryParameter("windowId", "181", false, "ID of related window.")
      )
    );
    if (StringUtils.contains(actionName, "DOWNLOAD")) {
      queryParameters.add(createQueryParameter("tmpfileName", "29e84cf8-b9f6-44ac-b918-543d91a6b22d.pdf", false, "Specifies the mode of the action."));
      queryParameters.add(createQueryParameter("fileName", "Purchase Order Report-07-11-2024 14_56_08.pdf", false, "Specifies the mode of the action."));
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
        .description(description)
        .addTagsItem(tags.get(0));

    for (Parameter param : queryParameters) {
      operation.addParametersItem(param);
    }

    operation.setRequestBody(requestBody);

    operation.setResponses(apiResponses);

    PathItem pathItem = new PathItem();
    pathItem.post(operation);
    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }

    openAPI.getPaths().addPathItem(BASE_PATH + "?_action=" + actionName, pathItem);

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

  private Parameter createQueryParameter(String name, String example, boolean required,
      String description) {
    return new Parameter().in("query")
        .name(name)
        .required(required)
        .schema(new Schema<String>().type("string").example(example))
        .description(description);
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

  private Schema<?> defineBaseReportActionRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperty("_buttonValue", new Schema<>().type("string").example("HTML"));
    Schema<Object> paramsSchema = new Schema<>();
    paramsSchema.type("object");
    paramsSchema.addProperty("AD_Org_ID",
        new Schema<>().type("string").nullable(true).example(null));
    paramsSchema.addProperty("C_BPartner_ID",
        new Schema<>().type("string").nullable(true).example(null));
    paramsSchema.addProperty("C_Currency_ID", new Schema<>().type("string").example("102"));
    paramsSchema.addProperty("DateFrom", new Schema<>().type("string").example("2024-11-07"));
    paramsSchema.addProperty("DateTo", new Schema<>().type("string").nullable(true).example(null));
    paramsSchema.addProperty("C_Project_ID",
        new Schema<>().type("string").nullable(true).example(null));
    paramsSchema.addProperty("M_Warehouse_ID",
        new Schema<>().type("string").nullable(true).example(null));
    paramsSchema.addProperty("Status", new Schema<>().type("string").nullable(true).example(null));

    schema.addProperty("_params", paramsSchema);

    schema.required(Arrays.asList("_buttonValue", "_params"));

    return schema;
  }

  private Schema<?> defineBaseReportActionResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperty("retryExecution", new Schema<>().type("boolean").example(true));
    schema.addProperty("showResultsInProcessView", new Schema<>().type("boolean").example(true));
    schema.addProperty("refreshParent", new Schema<>().type("boolean").example(true));

    Schema<Object> responseActionsSchema = new Schema<>();
    responseActionsSchema.type("array");
    Schema<Object> responseActionItemSchema = new Schema<>();
    responseActionItemSchema.type("object");

    Schema<Object> browseReportSchema = new Schema<>();
    browseReportSchema.type("object");

    Schema<Object> processParametersSchema = new Schema<>();
    processParametersSchema.type("object");
    processParametersSchema.addProperty("AD_Org_ID",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("C_BPartner_ID",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("C_Currency_ID",
        new Schema<>().type("string").example("102"));
    processParametersSchema.addProperty("DateFrom",
        new Schema<>().type("string").example("2024-11-07"));
    processParametersSchema.addProperty("DateTo",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("C_Project_ID",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("M_Warehouse_ID",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("Status",
        new Schema<>().type("string").nullable(true).example(null));
    processParametersSchema.addProperty("processId",
        new Schema<>().type("string").example("4BDE0AF5E8C44B6C9575E388AAECDF69"));
    processParametersSchema.addProperty("reportId",
        new Schema<>().type("string").example("95A65133A5314CAABEE405B1EB2A639D"));
    processParametersSchema.addProperty("actionHandler", new Schema<>().type("string")
        .example("org.openbravo.client.application.report.BaseReportActionHandler"));

    browseReportSchema.addProperty("processParameters", processParametersSchema);
    browseReportSchema.addProperty("tmpfileName",
        new Schema<>().type("string").example("3d5c89c9-c563-4395-8ce9-04be8e7b0ea5.html"));
    browseReportSchema.addProperty("fileName",
        new Schema<>().type("string").example("Purchase Order Report-07-11-2024 14_01_38.html"));
    browseReportSchema.addProperty("tabTitle",
        new Schema<>().type("string").example("Purchase Order Report"));

    responseActionItemSchema.addProperty("OBUIAPP_browseReport", browseReportSchema);
    responseActionsSchema.addOneOfItem(responseActionItemSchema);

    schema.addProperty("responseActions", responseActionsSchema);

    schema.required(Arrays.asList("retryExecution", "showResultsInProcessView", "refreshParent",
        "responseActions"));

    return schema;
  }
}
