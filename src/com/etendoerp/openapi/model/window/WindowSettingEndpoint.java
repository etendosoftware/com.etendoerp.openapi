package com.etendoerp.openapi.model.window;

import com.etendoerp.openapi.model.OpenAPIEndpoint;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class WindowSettingEndpoint implements OpenAPIEndpoint {

  private static final String WINDOW_SETTINGS_ACTION = "org.openbravo.client.application.WindowSettingsActionHandler";
  private static final String FORM_INIT_ACTION = "org.openbravo.client.application.window.FormInitializationComponent";
  private static final String BASE_PATH = "/etendo/org.openbravo.client.kernel?_action=";

  @Override
  public void add(OpenAPI openAPI) {
    createEndpoint(openAPI, WINDOW_SETTINGS_ACTION, "Get the initial Window Settings configuration of a window",
        "This endpoint is used to get the Window Settings configuration of a specific window.",
        defineWindowSettingsResponseSchema(),
         "{\"uiPattern\":{\"293\":\"STD\",\"294\":\"STD\",\"295\":\"RO\",\"800220\":\"STD\",\"800221\":\"RO\",\"1011100001\":\"STD\",\"ADBD1719BFA14C97A32C7B6E8452D14F\":\"RO\",\"4CA5324BD037430B8E682B41C1DAA8CC\":\"RO\",\"64B971D786A646DC9656534AABB13FA9\":\"RO\",\"FA57D75B1BBB4583B4A777008A29BF54\":\"RO\",\"6707D7449A8D45DB851F608BA88329C8\":\"RO\"},\"autoSave\":true,\"personalization\":{\"forms\":{},\"views\":[],\"formData\":{\"clients\":{\"23C59575B9CF467C9620760EB255B389\":\"F&B International Group\"},\"orgs\":{\"E443A31992CB4635AFCAEABE7183CE85\":\"F&B España - Región Norte\",\"B843C30461EA4501935CB1D125C9C25A\":\"F&B España, S.A\",\"BAE22373FEBE4CCCA24517E23F0C8A48\":\"F&B US West Coast\",\"DC206C91AA6A4897B44DA897936E0EC3\":\"F&B España - Región Sur\",\"19404EAD144C49A0AF37D54377CF452D\":\"F&B International Group\",\"2E60544D37534C0B89E765FE29BC0B43\":\"F&B US, Inc.\",\"7BABA5FF80494CAFA54DEBD22EC46F01\":\"F&B US East Coast\"},\"roles\":{\"0\":\"System Administrator\",\"42D0EEB1C66F497A90DD526DC597E6F0\":\"F&B International Group Admin\"}}},\"showAutoSaveConfirmation\":false,\"tabs\":[],\"notAccessibleProcesses\":[],\"extraSettings\":{},\"extraCallbacks\":[]}",
        "WindowSettingResponse",
        Arrays.asList(
            createParameter("_action", true, "string", WINDOW_SETTINGS_ACTION, "Action to execute."),
            createParameter("windowId", true, "string", "181", "Window ID of the form to query.")
        ),
        "GET"
    );

    createEndpoint(openAPI, FORM_INIT_ACTION, "Initialize a form with default values",
        "This endpoint is used to initialize a form with default values.",
        defineFormInitResponseSchema(),
        "{\"hiddenInputs\":{\"inpiscashvat\":\"N\"},\"calloutMessages\":[],\"columnValues\":{\"AD_Org_ID\":{\"value\":\"E443A31992CB4635AFCAEABE7183CE85\",\"classicValue\":\"E443A31992CB4635AFCAEABE7183CE85\",\"identifier\":\"F&B España - Región Norte\"},\"C_DocTypeTarget_ID\":{\"value\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"classicValue\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"identifier\":\"Standard Order\"},\"C_Return_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DocumentNo\":{\"value\":\"<1000454>\",\"classicValue\":\"<1000454>\"},\"DateOrdered\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"C_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"RM_PickFromShipment\":{\"classicValue\":\"N\",\"value\":false},\"C_BPartner_Location_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"M_PriceList_ID\":{\"value\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"classicValue\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"identifier\":\"Tarifa de ventas\"},\"DatePromised\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"RM_ReceiveMaterials\":{\"classicValue\":\"N\",\"value\":false},\"RM_CreateInvoice\":{\"classicValue\":\"N\",\"value\":false},\"FIN_Paymentmethod_ID\":{},\"C_PaymentTerm_ID\":{\"value\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"classicValue\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"identifier\":\"120 days\"},\"M_Warehouse_ID\":{\"value\":\"B2D40D8A5D644DD89E329DC297309055\",\"classicValue\":\"B2D40D8A5D644DD89E329DC297309055\",\"identifier\":\"España Región Norte\"},\"InvoiceRule\":{\"value\":\"D\",\"classicValue\":\"D\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"After Delivery\"},{\"id\":\"O\",\"_identifier\":\"After Order Delivered\"},{\"id\":\"S\",\"_identifier\":\"Customer Schedule After Delivery\"},{\"id\":\"N\",\"_identifier\":\"Do Not Invoice\"},{\"id\":\"I\",\"_identifier\":\"Immediate\"}]},\"DocStatus\":{\"value\":\"DR\",\"classicValue\":\"DR\",\"entries\":[{\"id\":\"AE\",\"_identifier\":\"Automatic Evaluation\"},{\"id\":\"CO\",\"_identifier\":\"Booked\"},{\"id\":\"CL\",\"_identifier\":\"Closed\"},{\"id\":\"CA\",\"_identifier\":\"Closed - Order Created\"},{\"id\":\"CJ\",\"_identifier\":\"Closed - Rejected\"},{\"id\":\"DR\",\"_identifier\":\"Draft\"},{\"id\":\"ME\",\"_identifier\":\"Manual Evaluation\"},{\"id\":\"NA\",\"_identifier\":\"Not Accepted\"},{\"id\":\"NC\",\"_identifier\":\"Not Confirmed\"},{\"id\":\"WP\",\"_identifier\":\"Not Paid\"},{\"id\":\"RE\",\"_identifier\":\"Re-Opened\"},{\"id\":\"TMP\",\"_identifier\":\"Temporal\"},{\"id\":\"UE\",\"_identifier\":\"Under Evaluation\"},{\"id\":\"IP\",\"_identifier\":\"Under Way\"},{\"id\":\"??\",\"_identifier\":\"Unknown\"},{\"id\":\"VO\",\"_identifier\":\"Voided\"}]},\"GrandTotal\":{\"value\":0,\"classicValue\":0},\"TotalLines\":{\"value\":0,\"classicValue\":0},\"C_Currency_ID\":{\"value\":\"102\",\"classicValue\":\"102\",\"identifier\":\"EUR\"},\"AD_User_ID\":{\"value\":\"100\",\"classicValue\":\"100\"},\"C_DocType_ID\":{\"value\":\"0\",\"classicValue\":\"0\",\"identifier\":\"** New **\"},\"SO_Res_Status\":{\"value\":\"\",\"classicValue\":\"\",\"entries\":[{},{\"id\":\"CR\",\"_identifier\":\"Completely Reserved\"},{\"id\":\"NR\",\"_identifier\":\"Not Reserved\"},{\"id\":\"PR\",\"_identifier\":\"Partially Reserved\"}]},\"POReference\":{\"classicValue\":\"\"},\"SalesRep_ID\":{},\"Description\":{\"classicValue\":\"\"},\"BillTo_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"Delivery_Location_ID\":{},\"EM_APRM_AddPayment\":{\"classicValue\":\"N\",\"value\":false},\"DocAction\":{\"value\":\"CO\",\"classicValue\":\"CO\",\"identifier\":\"Book\"},\"CopyFrom\":{\"classicValue\":\"N\",\"value\":false},\"CopyFromPO\":{\"classicValue\":\"N\",\"value\":false},\"DeliveryViaRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"Delivery\"},{\"id\":\"P\",\"_identifier\":\"Pickup\"},{\"id\":\"S\",\"_identifier\":\"Shipper\"}]},\"M_Shipper_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryRule\":{\"value\":\"A\",\"classicValue\":\"A\",\"entries\":[{\"id\":\"R\",\"_identifier\":\"After Receipt\"},{\"id\":\"A\",\"_identifier\":\"Availability\"},{\"id\":\"L\",\"_identifier\":\"Complete Line\"},{\"id\":\"O\",\"_identifier\":\"Complete Order\"}]},\"FreightCostRule\":{\"value\":\"I\",\"classicValue\":\"I\",\"entries\":[{\"id\":\"C\",\"_identifier\":\"Calculated\"},{\"id\":\"I\",\"_identifier\":\"Freight included\"}]},\"FreightAmt\":{\"value\":0,\"classicValue\":0},\"IsDiscountPrinted\":{\"classicValue\":\"N\",\"value\":false},\"PriorityRule\":{\"value\":\"5\",\"classicValue\":\"5\",\"entries\":[{\"id\":\"3\",\"_identifier\":\"High\"},{\"id\":\"7\",\"_identifier\":\"Low\"},{\"id\":\"5\",\"_identifier\":\"Medium\"}]},\"C_Campaign_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"ChargeAmt\":{\"value\":0,\"classicValue\":\"0.00\"},\"C_Charge_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Activity_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"AD_OrgTrx_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Quotation_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryStatus\":{\"classicValue\":\"\"},\"InvoiceStatus\":{\"classicValue\":\"\"},\"Calculate_Promotions\":{\"classicValue\":\"N\",\"value\":false},\"RM_AddOrphanLine\":{\"classicValue\":\"N\",\"value\":false},\"Convertquotation\":{\"classicValue\":\"N\",\"value\":false},\"C_Reject_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"validuntil\":{\"classicValue\":\"\"},\"Cancelledorder_id\":{},\"Replacedorder_id\":{},\"Replacementorder_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Iscancelled\":{\"value\":false,\"classicValue\":\"N\"},\"BPartner_ExtRef\":{\"classicValue\":\"\"},\"C_Project_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Costcenter_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"A_Asset_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User1_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User2_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Cancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"PaymentStatus\":{\"classicValue\":\"\"},\"Confirmcancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"C_Order_ID\":{\"classicValue\":\"\"},\"PaymentRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"5\",\"_identifier\":\"Bank Deposit\"},{\"id\":\"R\",\"_identifier\":\"Bank Remittance\"},{\"id\":\"B\",\"_identifier\":\"Cash\"},{\"id\":\"C\",\"_identifier\":\"Cash on Delivery\"},{\"id\":\"2\",\"_identifier\":\"Check\"},{\"id\":\"K\",\"_identifier\":\"Credit Card\"},{\"id\":\"4\",\"_identifier\":\"Money Order\"},{\"id\":\"P\",\"_identifier\":\"On Credit\"},{\"id\":\"3\",\"_identifier\":\"Promissory Note\"},{\"id\":\"1\",\"_identifier\":\"Wire Transfer\"},{\"id\":\"W\",\"_identifier\":\"Withholding\"}]},\"Posted\":{\"value\":\"N\",\"classicValue\":\"N\",\"identifier\":\"Post\"},\"IsTaxIncluded\":{\"classicValue\":\"N\",\"value\":false},\"IsSelected\":{\"classicValue\":\"N\",\"value\":false},\"DropShip_User_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DropShip_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DatePrinted\":{\"classicValue\":\"\"},\"DropShip_Location_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"IsSelfService\":{\"classicValue\":\"N\",\"value\":false},\"Generatetemplate\":{\"classicValue\":\"N\",\"value\":false},\"Processed\":{\"classicValue\":\"N\",\"value\":false},\"Deliverynotes\":{\"classicValue\":\"\"},\"C_Incoterms_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Incotermsdescription\":{\"classicValue\":\"\"},\"Processing\":{\"classicValue\":\"N\",\"value\":false},\"DateAcct\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"IsPrinted\":{\"classicValue\":\"N\",\"value\":false},\"IsInvoiced\":{\"classicValue\":\"N\",\"value\":false},\"IsDelivered\":{\"classicValue\":\"N\",\"value\":false},\"IsActive\":{\"value\":true,\"classicValue\":\"Y\"},\"AD_Client_ID\":{\"value\":\"23C59575B9CF467C9620760EB255B389\",\"classicValue\":\"23C59575B9CF467C9620760EB255B389\",\"identifier\":\"F&B International Group\"},\"IsSOTrx\":{\"value\":true,\"classicValue\":\"Y\"}},\"auxiliaryInputValues\":{\"ORDERTYPE\":{\"value\":\"SO\",\"classicValue\":\"SO\"},\"DOCBASETYPE\":{\"value\":\"SOO\",\"classicValue\":\"SOO\"},\"showAddPayment\":{},\"PromotionsDefined\":{\"value\":\"N\",\"classicValue\":\"N\"},\"APRM_OrderIsPaid\":{}},\"overwrittenAuxiliaryInputs\":[\"ORDERTYPE\"],\"sessionAttributes\":{\"$Element_U2_SOO_H\":\"N\",\"$Element_U2_ARR_H\":\"N\",\"EnableCanceAndReplace\":\"\",\"$Element_U2_APP_H\":\"N\",\"StockReservations\":\"\",\"Replacementorder_ID\":\"\",\"$Element_OO\":\"Y\",\"$Element_CC\":\"\",\"$Element_OO_POO_H\":\"Y\",\"$Element_U1_POO_H\":\"N\",\"$Element_PJ_SOO_H\":\"Y\",\"$Element_CC_APP_H\":\"\",\"$Element_CC_ARR_H\":\"\",\"$Element_BP_POO_H\":\"Y\",\"$Element_AS\":\"\",\"$Element_BP_SOO_H\":\"Y\",\"$Element_CC_SOO_H\":\"\",\"$Element_U1_SOO_H\":\"N\",\"$IsAcctDimCentrally\":\"Y\",\"ACCT_DIMENSION_DISPLAY\":\"\",\"HASRELATEDSERVICE\":\"N\",\"$Element_OO_ARR_H\":\"Y\",\"$Element_U2_POO_H\":\"N\",\"$Element_OO_APP_H\":\"Y\",\"$Element_U2\":\"\",\"$Element_BP_APP_H\":\"Y\",\"$Element_U1\":\"\",\"$Element_U1_ARR_H\":\"N\",\"$Element_BP_ARR_H\":\"Y\",\"$Element_PJ\":\"Y\",\"$Element_CC_POO_H\":\"\",\"$Element_U1_APP_H\":\"N\",\"PRODUCTTYPE\":\"I\",\"$Element_PJ_POO_H\":\"Y\",\"$Element_PJ_ARR_H\":\"Y\",\"$Element_OO_SOO_H\":\"Y\",\"$Element_BP\":\"Y\",\"$Element_PJ_APP_H\":\"Y\"},\"dynamicCols\":[\"inpadOrgId\",\"inpissotrx\",\"inpcBpartnerId\",\"inpcDoctypetargetId\",\"inpdropshipBpartnerId\",\"inpdateordered\",\"inpcBpartnerLocationId\",\"inpmPricelistId\",\"inpdatepromised\",\"inpcChargeId\",\"inpcProjectId\"],\"attachmentCount\":0,\"attachmentExists\":false}",
        "FormInitResponse",
        Arrays.asList(
            createParameter("_action", true, "string", FORM_INIT_ACTION, "Action to execute."),
            createParameter("MODE", true, "string", "NEW", "Mode of the form initialization."),
            createParameter("PARENT_ID", false, "string", "null", "Parent ID of the form initialization."),
            createParameter("TAB_ID", false, "string", "294", "Tab ID of the form initialization."),
            createParameter("ROW_ID", false, "string", "null", "Row ID of the form initialization.")
        ),
        "POST"
    );
  }

  private void createEndpoint(OpenAPI openAPI, String actionValue, String summary, String description,
      Schema<?> schema, String example, String schemaKey, List<Parameter> parameters,
      String httpMethod) {

    ApiResponses apiResponses = new ApiResponses()
        .addApiResponse("200", createApiResponse("Successful response.", schema, example))
        .addApiResponse("400", new ApiResponse().description("Unsuccessful request."))
        .addApiResponse("500", new ApiResponse().description("Internal server error."));

    Operation operation = new Operation()
        .summary(summary)
        .description(description);
    for (Parameter parameter : parameters) {
      operation.addParametersItem(parameter);
    }
    operation.responses(apiResponses);

    PathItem pathItem = httpMethod.equalsIgnoreCase("GET") ? new PathItem().get(operation) :
        new PathItem().post(operation);

    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }

    String path = BASE_PATH + actionValue.replace('.', '/');
    openAPI.getPaths().addPathItem(path, pathItem);

    addSchema(openAPI, schemaKey, schema);
  }

  private ApiResponse createApiResponse(String description, Schema<?> schema, String example) {
    return new ApiResponse().description(description)
        .content(new Content().addMediaType("application/json",
            new MediaType().schema(schema).example(example)));
  }

  private Parameter createParameter(String name, boolean required, String type, String example, String description) {
    return new Parameter()
        .in("query")
        .name(name)
        .required(required)
        .schema(new Schema<String>().type(type).example(example))
        .description(description);
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

  private Schema<?> defineWindowSettingsResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperties("uiPattern", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("autoSave", new Schema<>().type("boolean"));

    Schema<Object> personalizationSchema = new Schema<>();
    personalizationSchema.type("object");
    personalizationSchema.addProperties("forms", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    personalizationSchema.addProperties("views", new Schema<>().type("array")
        .items(new Schema<>().type("string")));

    Schema<Object> formDataSchema = new Schema<>();
    formDataSchema.type("object");
    formDataSchema.addProperties("clients", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperties("orgs", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperties("roles", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));

    personalizationSchema.addProperties("formData", formDataSchema);
    schema.addProperties("personalization", personalizationSchema);

    schema.addProperties("showAutoSaveConfirmation", new Schema<>().type("boolean"));
    schema.addProperties("tabs", new Schema<>().type("array")
        .items(new Schema<>().type("string")));
    schema.addProperties("notAccessibleProcesses", new Schema<>().type("array")
        .items(new Schema<>().type("string")));
    schema.addProperties("extraSettings", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("extraCallbacks", new Schema<>().type("array")
        .items(new Schema<>().type("string")));

    // Define required fields
    schema.required(Arrays.asList(
        "uiPattern",
        "autoSave",
        "personalization",
        "showAutoSaveConfirmation",
        "tabs",
        "notAccessibleProcesses",
        "extraSettings",
        "extraCallbacks"
    ));

    return schema;
  }

  private Schema<?> defineFormInitResponseSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperties("hiddenInputs", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("calloutMessages", new Schema<>().type("array")
        .items(new Schema<>().type("string")));
    schema.addProperties("columnValues", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("auxiliaryInputValues", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("overwrittenAuxiliaryInputs", new Schema<>().type("array")
        .items(new Schema<>().type("string")));
    schema.addProperties("sessionAttributes", new Schema<>().type("object")
        .additionalProperties(new Schema<>().type("string")));
    schema.addProperties("dynamicCols", new Schema<>().type("array")
        .items(new Schema<>().type("string")));
    schema.addProperties("attachmentCount", new Schema<>().type("integer").example(0));
    schema.addProperties("attachmentExists", new Schema<>().type("boolean").example(false));

    // Define required fields
    schema.required(Arrays.asList(
        "hiddenInputs",
        "calloutMessages",
        "columnValues",
        "auxiliaryInputValues",
        "overwrittenAuxiliaryInputs",
        "sessionAttributes",
        "dynamicCols",
        "attachmentCount",
        "attachmentExists"
    ));

    return schema;
  }
}
