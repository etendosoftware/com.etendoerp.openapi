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
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class WindowSettingEndpoint implements OpenAPIEndpoint {

  private static final String WINDOW_SETTINGS_ACTION = "org.openbravo.client.application.WindowSettingsActionHandler";
  private static final String FORM_INIT_ACTION = "org.openbravo.client.application.window.FormInitializationComponent";
  private static final String BASE_PATH = "/etendo/org.openbravo.client.kernel?_action=";
  private static final List<String> tags = List.of("Window Settings");

  @Override
  public boolean isValid(String tag) {
    if(tag == null) {
      return true;
    }
    return tags.contains(tag);
  }

  @Override
  public void add(OpenAPI openAPI) {
    Schema<?> windowSettingsResponseSchema = defineWindowSettingsResponseSchema();
    String windowSettingsExample = "{\"uiPattern\":{\"293\":\"STD\",\"294\":\"STD\",\"295\":\"RO\",\"800220\":\"STD\",\"800221\":\"RO\",\"1011100001\":\"STD\",\"ADBD1719BFA14C97A32C7B6E8452D14F\":\"RO\",\"4CA5324BD037430B8E682B41C1DAA8CC\":\"RO\",\"64B971D786A646DC9656534AABB13FA9\":\"RO\",\"FA57D75B1BBB4583B4A777008A29BF54\":\"RO\",\"6707D7449A8D45DB851F608BA88329C8\":\"RO\"},\"autoSave\":true,\"personalization\":{\"forms\":{},\"views\":[],\"formData\":{\"clients\":{\"23C59575B9CF467C9620760EB255B389\":\"F&B International Group\"},\"orgs\":{\"E443A31992CB4635AFCAEABE7183CE85\":\"F&B España - Región Norte\",\"B843C30461EA4501935CB1D125C9C25A\":\"F&B España, S.A\",\"BAE22373FEBE4CCCA24517E23F0C8A48\":\"F&B US West Coast\",\"DC206C91AA6A4897B44DA897936E0EC3\":\"F&B España - Región Sur\",\"19404EAD144C49A0AF37D54377CF452D\":\"F&B International Group\",\"2E60544D37534C0B89E765FE29BC0B43\":\"F&B US, Inc.\",\"7BABA5FF80494CAFA54DEBD22EC46F01\":\"F&B US East Coast\"},\"roles\":{\"0\":\"System Administrator\",\"42D0EEB1C66F497A90DD526DC597E6F0\":\"F&B International Group Admin\"}}},\"showAutoSaveConfirmation\":false,\"tabs\":[],\"notAccessibleProcesses\":[],\"extraSettings\":{},\"extraCallbacks\":[]}";

    List<Parameter> windowSettingsParams = Arrays.asList(
        createParameter("_action", true, "string", WINDOW_SETTINGS_ACTION, "Acción a ejecutar."),
        createParameter("windowId", true, "string", "181",
            "ID de la ventana del formulario a consultar."));

    createEndpoint(openAPI, WINDOW_SETTINGS_ACTION,
        "Obtains the initial configuration of Window Settings for a specific window.",
        "This endpoint is used to obtain the configuration of Window Settings for a specific window.",
        windowSettingsResponseSchema, windowSettingsExample, "WindowSettingResponse",
        windowSettingsParams, null, // No hay requestBody para GET
        null, "GET");

    // Form init
    Schema<?> formInitResponseSchema = defineFormInitResponseSchema();
    String formInitResponseExample = "{\"hiddenInputs\":{\"inpiscashvat\":\"N\"},\"calloutMessages\":[],\"columnValues\":{\"AD_Org_ID\":{\"value\":\"E443A31992CB4635AFCAEABE7183CE85\",\"classicValue\":\"E443A31992CB4635AFCAEABE7183CE85\",\"identifier\":\"F&B España - Región Norte\"},\"C_DocTypeTarget_ID\":{\"value\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"classicValue\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"identifier\":\"Standard Order\"},\"C_Return_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DocumentNo\":{\"value\":\"<1000454>\",\"classicValue\":\"<1000454>\"},\"DateOrdered\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"C_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"RM_PickFromShipment\":{\"classicValue\":\"N\",\"value\":false},\"C_BPartner_Location_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"M_PriceList_ID\":{\"value\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"classicValue\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"identifier\":\"Tarifa de ventas\"},\"DatePromised\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"RM_ReceiveMaterials\":{\"classicValue\":\"N\",\"value\":false},\"RM_CreateInvoice\":{\"classicValue\":\"N\",\"value\":false},\"FIN_Paymentmethod_ID\":{},\"C_PaymentTerm_ID\":{\"value\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"classicValue\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"identifier\":\"120 days\"},\"M_Warehouse_ID\":{\"value\":\"B2D40D8A5D644DD89E329DC297309055\",\"classicValue\":\"B2D40D8A5D644DD89E329DC297309055\",\"identifier\":\"España Región Norte\"},\"InvoiceRule\":{\"value\":\"D\",\"classicValue\":\"D\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"After Delivery\"},{\"id\":\"O\",\"_identifier\":\"After Order Delivered\"},{\"id\":\"S\",\"_identifier\":\"Customer Schedule After Delivery\"},{\"id\":\"N\",\"_identifier\":\"Do Not Invoice\"},{\"id\":\"I\",\"_identifier\":\"Immediate\"}]},\"DocStatus\":{\"value\":\"DR\",\"classicValue\":\"DR\",\"entries\":[{\"id\":\"AE\",\"_identifier\":\"Automatic Evaluation\"},{\"id\":\"CO\",\"_identifier\":\"Booked\"},{\"id\":\"CL\",\"_identifier\":\"Closed\"},{\"id\":\"CA\",\"_identifier\":\"Closed - Order Created\"},{\"id\":\"CJ\",\"_identifier\":\"Closed - Rejected\"},{\"id\":\"DR\",\"_identifier\":\"Draft\"},{\"id\":\"ME\",\"_identifier\":\"Manual Evaluation\"},{\"id\":\"NA\",\"_identifier\":\"Not Accepted\"},{\"id\":\"NC\",\"_identifier\":\"Not Confirmed\"},{\"id\":\"WP\",\"_identifier\":\"Not Paid\"},{\"id\":\"RE\",\"_identifier\":\"Re-Opened\"},{\"id\":\"TMP\",\"_identifier\":\"Temporal\"},{\"id\":\"UE\",\"_identifier\":\"Under Evaluation\"},{\"id\":\"IP\",\"_identifier\":\"Under Way\"},{\"id\":\"??\",\"_identifier\":\"Unknown\"},{\"id\":\"VO\",\"_identifier\":\"Voided\"}]},\"GrandTotal\":{\"value\":0,\"classicValue\":0},\"TotalLines\":{\"value\":0,\"classicValue\":0},\"C_Currency_ID\":{\"value\":\"102\",\"classicValue\":\"102\",\"identifier\":\"EUR\"},\"AD_User_ID\":{\"value\":\"100\",\"classicValue\":\"100\"},\"C_DocType_ID\":{\"value\":\"0\",\"classicValue\":\"0\",\"identifier\":\"** New **\"},\"SO_Res_Status\":{\"value\":\"\",\"classicValue\":\"\",\"entries\":[{},{\"id\":\"CR\",\"_identifier\":\"Completely Reserved\"},{\"id\":\"NR\",\"_identifier\":\"Not Reserved\"},{\"id\":\"PR\",\"_identifier\":\"Partially Reserved\"}]},\"POReference\":{\"classicValue\":\"\"},\"SalesRep_ID\":{},\"Description\":{\"classicValue\":\"\"},\"BillTo_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"Delivery_Location_ID\":{},\"EM_APRM_AddPayment\":{\"classicValue\":\"N\",\"value\":false},\"DocAction\":{\"value\":\"CO\",\"classicValue\":\"CO\",\"identifier\":\"Book\"},\"CopyFrom\":{\"classicValue\":\"N\",\"value\":false},\"CopyFromPO\":{\"classicValue\":\"N\",\"value\":false},\"DeliveryViaRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"Delivery\"},{\"id\":\"P\",\"_identifier\":\"Pickup\"},{\"id\":\"S\",\"_identifier\":\"Shipper\"}]},\"M_Shipper_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryRule\":{\"value\":\"A\",\"classicValue\":\"A\",\"entries\":[{\"id\":\"R\",\"_identifier\":\"After Receipt\"},{\"id\":\"A\",\"_identifier\":\"Availability\"},{\"id\":\"L\",\"_identifier\":\"Complete Line\"},{\"id\":\"O\",\"_identifier\":\"Complete Order\"}]},\"FreightCostRule\":{\"value\":\"I\",\"classicValue\":\"I\",\"entries\":[{\"id\":\"C\",\"_identifier\":\"Calculated\"},{\"id\":\"I\",\"_identifier\":\"Freight included\"}]},\"FreightAmt\":{\"value\":0,\"classicValue\":0},\"IsDiscountPrinted\":{\"classicValue\":\"N\",\"value\":false},\"PriorityRule\":{\"value\":\"5\",\"classicValue\":\"5\",\"entries\":[{\"id\":\"3\",\"_identifier\":\"High\"},{\"id\":\"7\",\"_identifier\":\"Low\"},{\"id\":\"5\",\"_identifier\":\"Medium\"}]},\"C_Campaign_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"ChargeAmt\":{\"value\":0,\"classicValue\":\"0.00\"},\"C_Charge_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Activity_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"AD_OrgTrx_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Quotation_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryStatus\":{\"classicValue\":\"\"},\"InvoiceStatus\":{\"classicValue\":\"\"},\"Calculate_Promotions\":{\"classicValue\":\"N\",\"value\":false},\"RM_AddOrphanLine\":{\"classicValue\":\"N\",\"value\":false},\"Convertquotation\":{\"classicValue\":\"N\",\"value\":false},\"C_Reject_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"validuntil\":{\"classicValue\":\"\"},\"Cancelledorder_id\":{},\"Replacedorder_id\":{},\"Replacementorder_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Iscancelled\":{\"value\":false,\"classicValue\":\"N\"},\"BPartner_ExtRef\":{\"classicValue\":\"\"},\"C_Project_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Costcenter_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"A_Asset_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User1_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User2_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Cancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"PaymentStatus\":{\"classicValue\":\"\"},\"Confirmcancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"C_Order_ID\":{\"classicValue\":\"\"},\"PaymentRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"5\",\"_identifier\":\"Bank Deposit\"},{\"id\":\"R\",\"_identifier\":\"Bank Remittance\"},{\"id\":\"B\",\"_identifier\":\"Cash\"},{\"id\":\"C\",\"_identifier\":\"Cash on Delivery\"},{\"id\":\"2\",\"_identifier\":\"Check\"},{\"id\":\"K\",\"_identifier\":\"Credit Card\"},{\"id\":\"4\",\"_identifier\":\"Money Order\"},{\"id\":\"P\",\"_identifier\":\"On Credit\"},{\"id\":\"3\",\"_identifier\":\"Promissory Note\"},{\"id\":\"1\",\"_identifier\":\"Wire Transfer\"},{\"id\":\"W\",\"_identifier\":\"Withholding\"}]},\"Posted\":{\"value\":\"N\",\"classicValue\":\"N\",\"identifier\":\"Post\"},\"IsTaxIncluded\":{\"classicValue\":\"N\",\"value\":false},\"IsSelected\":{\"classicValue\":\"N\",\"value\":false},\"DropShip_User_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DropShip_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DatePrinted\":{\"classicValue\":\"\"},\"DropShip_Location_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"IsSelfService\":{\"classicValue\":\"N\",\"value\":false},\"Generatetemplate\":{\"classicValue\":\"N\",\"value\":false},\"Processed\":{\"classicValue\":\"N\",\"value\":false},\"Deliverynotes\":{\"classicValue\":\"\"},\"C_Incoterms_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Incotermsdescription\":{\"classicValue\":\"\"},\"Processing\":{\"classicValue\":\"N\",\"value\":false},\"DateAcct\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"IsPrinted\":{\"classicValue\":\"N\",\"value\":false},\"IsInvoiced\":{\"classicValue\":\"N\",\"value\":false},\"IsDelivered\":{\"classicValue\":\"N\",\"value\":false},\"IsActive\":{\"value\":true,\"classicValue\":\"Y\"},\"AD_Client_ID\":{\"value\":\"23C59575B9CF467C9620760EB255B389\",\"classicValue\":\"23C59575B9CF467C9620760EB255B389\",\"identifier\":\"F&B International Group\"},\"IsSOTrx\":{\"value\":true,\"classicValue\":\"Y\"}},\"auxiliaryInputValues\":{\"ORDERTYPE\":{\"value\":\"SO\",\"classicValue\":\"SO\"},\"DOCBASETYPE\":{\"value\":\"SOO\",\"classicValue\":\"SOO\"},\"showAddPayment\":{},\"PromotionsDefined\":{\"value\":\"N\",\"classicValue\":\"N\"},\"APRM_OrderIsPaid\":{}},\"overwrittenAuxiliaryInputs\":[\"ORDERTYPE\"],\"sessionAttributes\":{\"$Element_U2_SOO_H\":\"N\",\"$Element_U2_ARR_H\":\"N\",\"EnableCanceAndReplace\":\"\",\"$Element_U2_APP_H\":\"N\",\"StockReservations\":\"\",\"Replacementorder_ID\":\"\",\"$Element_OO\":\"Y\",\"$Element_CC\":\"\",\"$Element_OO_POO_H\":\"Y\",\"$Element_U1_POO_H\":\"N\",\"$Element_PJ_SOO_H\":\"Y\",\"$Element_CC_APP_H\":\"\",\"$Element_CC_ARR_H\":\"\",\"$Element_BP_POO_H\":\"Y\",\"$Element_AS\":\"\",\"$Element_BP_SOO_H\":\"Y\",\"$Element_CC_SOO_H\":\"\",\"$Element_U1_SOO_H\":\"N\",\"$IsAcctDimCentrally\":\"Y\",\"ACCT_DIMENSION_DISPLAY\":\"\",\"HASRELATEDSERVICE\":\"N\",\"$Element_OO_ARR_H\":\"Y\",\"$Element_U2_POO_H\":\"N\",\"$Element_OO_APP_H\":\"Y\",\"$Element_U2\":\"\",\"$Element_BP_APP_H\":\"Y\",\"$Element_U1\":\"\",\"$Element_U1_ARR_H\":\"N\",\"$Element_BP_ARR_H\":\"Y\",\"$Element_PJ\":\"Y\",\"$Element_CC_POO_H\":\"\",\"$Element_U1_APP_H\":\"N\",\"PRODUCTTYPE\":\"I\",\"$Element_PJ_POO_H\":\"Y\",\"$Element_PJ_ARR_H\":\"Y\",\"$Element_OO_SOO_H\":\"Y\",\"$Element_BP\":\"Y\",\"$Element_PJ_APP_H\":\"Y\"},\"dynamicCols\":[\"inpadOrgId\",\"inpissotrx\",\"inpcBpartnerId\",\"inpcDoctypetargetId\",\"inpdropshipBpartnerId\",\"inpdateordered\",\"inpcBpartnerLocationId\",\"inpmPricelistId\",\"inpdatepromised\",\"inpcChargeId\",\"inpcProjectId\"],\"attachmentCount\":0,\"attachmentExists\":false}";

    Schema<?> formInitRequestSchema = defineFormInitRequestSchema();
    String formInitRequestExample = "{\"inpgeneratetemplate\":\"N\",\"inpcReturnReasonId\":null,\"inprmPickfromshipment\":\"N\",\"inprmReceivematerials\":\"N\",\"inprmCreateinvoice\":\"N\",\"inptotallines\":\"0.00\",\"inpadUserId\":\"100\",\"inpsalesrepId\":\"100\",\"inpcIncotermsId\":null,\"inpincotermsdescription\":null,\"inpisdiscountprinted\":\"N\",\"inpcDoctypeId\":\"0\",\"inpfreightamt\":\"0.00\",\"inpdeliveryviarule\":\"P\",\"inpcCampaignId\":null,\"inpmShipperId\":null,\"inppriorityrule\":\"5\",\"inpchargeamt\":\"0.00\",\"inpcChargeId\":null,\"inpcActivityId\":null,\"inpfreightcostrule\":\"I\",\"inpadOrgtrxId\":null,\"inpemAprmAddpayment\":\"N\",\"inpdocaction\":\"CO\",\"inpcopyfrom\":\"N\",\"inpcopyfrompo\":\"N\",\"inprmAddorphanline\":\"N\",\"inpconvertquotation\":\"N\",\"inpcRejectReasonId\":null,\"inpvaliduntil\":null,\"inpcalculatePromotions\":\"N\",\"inpquotationId\":null,\"inpcreatePolines\":\"N\",\"inpcOrderId\":null,\"inpadClientId\":\"23C59575B9CF467C9620760EB255B389\",\"inpisactive\":\"Y\",\"inpisdelivered\":\"N\",\"inpisinvoiced\":\"N\",\"inpisprinted\":\"N\",\"inpdateacct\":\"07-11-2024\",\"inpinvoicerule\":\"D\",\"inpprocessing\":\"N\",\"inpprocessed\":\"N\",\"inpdateprinted\":null,\"inpdeliveryrule\":\"A\",\"inpissotrx\":\"N\",\"inppaymentrule\":\"P\",\"inpposted\":\"N\",\"inpisselected\":\"N\",\"inpdropshipUserId\":null,\"inpdropshipBpartnerId\":null,\"inpdropshipLocationId\":null,\"inpisselfservice\":\"N\",\"inpdeliveryLocationId\":null,\"inpsoResStatus\":null,\"C_Order_ID\":null,\"inpadOrgId\":\"E443A31992CB4635AFCAEABE7183CE85\",\"inpcDoctypetargetId\":\"AB22CE8FFA5E4AF29F2AC90FCDD400D8\",\"inpdocumentno\":\"<1000376>\",\"inpdateordered\":\"07-11-2024\",\"inpcBpartnerId\":\"858B90C7AF0A4533863EEC65437382BF\",\"inpcBpartnerLocationId\":null,\"inpmWarehouseId\":\"B2D40D8A5D644DD89E329DC297309055\",\"inpdatepromised\":\"07-11-2024\",\"inpfinPaymentmethodId\":null,\"inpcPaymenttermId\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"inpmPricelistId\":\"CBC16D5792744C669D388FC4F66B85FD\",\"inpdeliverynotes\":null,\"inpdescription\":null,\"inpporeference\":null,\"inpbilltoId\":null,\"inpistaxincluded\":\"N\",\"inpcProjectId\":null,\"inpcCostcenterId\":null,\"inpaAssetId\":null,\"inpuser1Id\":null,\"inpuser2Id\":null,\"inpdocstatus\":\"DR\",\"inpgrandtotal\":\"0.00\",\"inpcCurrencyId\":\"102\",\"inpTabId\":\"294\",\"inpwindowId\":\"181\",\"inpTableId\":\"259\",\"inpkeyColumnId\":\"C_Order_ID\",\"keyProperty\":\"id\",\"inpKeyName\":\"inpcOrderId\",\"keyColumnName\":\"C_Order_ID\",\"keyPropertyType\":\"_id_13\",\"ORDERTYPE\":\"--\",\"DOCBASETYPE\":\"POO\",\"showAddPayment\":\"\",\"APRM_OrderIsPaid\":\"\",\"$Element_U2_SOO_H\":\"N\",\"$Element_U2_ARR_H\":\"N\",\"$Element_U2_APP_H\":\"N\",\"StockReservations\":\"\",\"$Element_OO\":\"Y\",\"$Element_CC\":\"\",\"$Element_OO_POO_H\":\"Y\",\"$Element_U1_POO_H\":\"N\",\"$Element_PJ_SOO_H\":\"Y\",\"$Element_CC_APP_H\":\"\",\"$Element_CC_ARR_H\":\"\",\"$Element_BP_POO_H\":\"Y\",\"$Element_AS\":\"\",\"$Element_BP_SOO_H\":\"Y\",\"$Element_CC_SOO_H\":\"\",\"$Element_U1_SOO_H\":\"N\",\"$IsAcctDimCentrally\":\"Y\",\"ACCT_DIMENSION_DISPLAY\":\"\",\"$Element_OO_ARR_H\":\"Y\",\"$Element_U2_POO_H\":\"N\",\"$Element_OO_APP_H\":\"Y\",\"$Element_U2\":\"\",\"$Element_BP_APP_H\":\"Y\",\"$Element_U1\":\"\",\"$Element_U1_ARR_H\":\"N\",\"$Element_BP_ARR_H\":\"Y\",\"$Element_PJ\":\"Y\",\"$Element_CC_POO_H\":\"\",\"$Element_U1_APP_H\":\"N\",\"$Element_PJ_POO_H\":\"Y\",\"$Element_PJ_ARR_H\":\"Y\",\"$Element_OO_SOO_H\":\"Y\",\"$Element_BP\":\"Y\",\"$Element_PJ_APP_H\":\"Y\",\"_entityName\":\"Order\",\"overwrittenAuxiliaryInputs\":[\"ORDERTYPE\"]}";

    List<Parameter> formInitParams = Arrays.asList(
        createParameter("_action", true, "string", FORM_INIT_ACTION, "Action to execute."),
        createParameter("MODE", true, "string", "NEW", "Initial form mode."),
        createParameter("PARENT_ID", false, "string", "null",
            "Parent ID of the form initialization."),
        createParameter("TAB_ID", false, "string", "294", "Tab ID of the form initialization."),
        createParameter("ROW_ID", false, "string", "null", "Row ID of the form initialization."));

    createEndpoint(openAPI, FORM_INIT_ACTION + "&MODE=NEW",
        "Initializes a form with default values",
        "This endpoint is used to initialize a form with default values.", formInitResponseSchema,
        formInitResponseExample, "FormInitResponse", formInitParams, formInitRequestSchema,
        formInitRequestExample, "POST");

    // Callout
    Schema<?> formChangeResponseSchema = defineFormInitResponseSchema();
    String formChangeResponseExample = "{\"hiddenInputs\":{\"inpiscashvat\":\"N\"},\"calloutMessages\":[],\"columnValues\":{\"AD_Org_ID\":{\"value\":\"E443A31992CB4635AFCAEABE7183CE85\",\"classicValue\":\"E443A31992CB4635AFCAEABE7183CE85\",\"identifier\":\"F&B España - Región Norte\"},\"C_DocTypeTarget_ID\":{\"value\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"classicValue\":\"466AF4B0136A4A3F9F84129711DA8BD3\",\"identifier\":\"Standard Order\"},\"C_Return_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DocumentNo\":{\"value\":\"<1000454>\",\"classicValue\":\"<1000454>\"},\"DateOrdered\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"C_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"RM_PickFromShipment\":{\"classicValue\":\"N\",\"value\":false},\"C_BPartner_Location_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"M_PriceList_ID\":{\"value\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"classicValue\":\"AEE66281A08F42B6BC509B8A80A33C29\",\"identifier\":\"Tarifa de ventas\"},\"DatePromised\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"RM_ReceiveMaterials\":{\"classicValue\":\"N\",\"value\":false},\"RM_CreateInvoice\":{\"classicValue\":\"N\",\"value\":false},\"FIN_Paymentmethod_ID\":{},\"C_PaymentTerm_ID\":{\"value\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"classicValue\":\"B62EDD9166D146539E9A19C05BCF85E5\",\"identifier\":\"120 days\"},\"M_Warehouse_ID\":{\"value\":\"B2D40D8A5D644DD89E329DC297309055\",\"classicValue\":\"B2D40D8A5D644DD89E329DC297309055\",\"identifier\":\"España Región Norte\"},\"InvoiceRule\":{\"value\":\"D\",\"classicValue\":\"D\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"After Delivery\"},{\"id\":\"O\",\"_identifier\":\"After Order Delivered\"},{\"id\":\"S\",\"_identifier\":\"Customer Schedule After Delivery\"},{\"id\":\"N\",\"_identifier\":\"Do Not Invoice\"},{\"id\":\"I\",\"_identifier\":\"Immediate\"}]},\"DocStatus\":{\"value\":\"DR\",\"classicValue\":\"DR\",\"entries\":[{\"id\":\"AE\",\"_identifier\":\"Automatic Evaluation\"},{\"id\":\"CO\",\"_identifier\":\"Booked\"},{\"id\":\"CL\",\"_identifier\":\"Closed\"},{\"id\":\"CA\",\"_identifier\":\"Closed - Order Created\"},{\"id\":\"CJ\",\"_identifier\":\"Closed - Rejected\"},{\"id\":\"DR\",\"_identifier\":\"Draft\"},{\"id\":\"ME\",\"_identifier\":\"Manual Evaluation\"},{\"id\":\"NA\",\"_identifier\":\"Not Accepted\"},{\"id\":\"NC\",\"_identifier\":\"Not Confirmed\"},{\"id\":\"WP\",\"_identifier\":\"Not Paid\"},{\"id\":\"RE\",\"_identifier\":\"Re-Opened\"},{\"id\":\"TMP\",\"_identifier\":\"Temporal\"},{\"id\":\"UE\",\"_identifier\":\"Under Evaluation\"},{\"id\":\"IP\",\"_identifier\":\"Under Way\"},{\"id\":\"??\",\"_identifier\":\"Unknown\"},{\"id\":\"VO\",\"_identifier\":\"Voided\"}]},\"GrandTotal\":{\"value\":0,\"classicValue\":0},\"TotalLines\":{\"value\":0,\"classicValue\":0},\"C_Currency_ID\":{\"value\":\"102\",\"classicValue\":\"102\",\"identifier\":\"EUR\"},\"AD_User_ID\":{\"value\":\"100\",\"classicValue\":\"100\"},\"C_DocType_ID\":{\"value\":\"0\",\"classicValue\":\"0\",\"identifier\":\"** New **\"},\"SO_Res_Status\":{\"value\":\"\",\"classicValue\":\"\",\"entries\":[{},{\"id\":\"CR\",\"_identifier\":\"Completely Reserved\"},{\"id\":\"NR\",\"_identifier\":\"Not Reserved\"},{\"id\":\"PR\",\"_identifier\":\"Partially Reserved\"}]},\"POReference\":{\"classicValue\":\"\"},\"SalesRep_ID\":{},\"Description\":{\"classicValue\":\"\"},\"BillTo_ID\":{\"value\":\"\",\"classicValue\":\"\",\"identifier\":\"\"},\"Delivery_Location_ID\":{},\"EM_APRM_AddPayment\":{\"classicValue\":\"N\",\"value\":false},\"DocAction\":{\"value\":\"CO\",\"classicValue\":\"CO\",\"identifier\":\"Book\"},\"CopyFrom\":{\"classicValue\":\"N\",\"value\":false},\"CopyFromPO\":{\"classicValue\":\"N\",\"value\":false},\"DeliveryViaRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"D\",\"_identifier\":\"Delivery\"},{\"id\":\"P\",\"_identifier\":\"Pickup\"},{\"id\":\"S\",\"_identifier\":\"Shipper\"}]},\"M_Shipper_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryRule\":{\"value\":\"A\",\"classicValue\":\"A\",\"entries\":[{\"id\":\"R\",\"_identifier\":\"After Receipt\"},{\"id\":\"A\",\"_identifier\":\"Availability\"},{\"id\":\"L\",\"_identifier\":\"Complete Line\"},{\"id\":\"O\",\"_identifier\":\"Complete Order\"}]},\"FreightCostRule\":{\"value\":\"I\",\"classicValue\":\"I\",\"entries\":[{\"id\":\"C\",\"_identifier\":\"Calculated\"},{\"id\":\"I\",\"_identifier\":\"Freight included\"}]},\"FreightAmt\":{\"value\":0,\"classicValue\":0},\"IsDiscountPrinted\":{\"classicValue\":\"N\",\"value\":false},\"PriorityRule\":{\"value\":\"5\",\"classicValue\":\"5\",\"entries\":[{\"id\":\"3\",\"_identifier\":\"High\"},{\"id\":\"7\",\"_identifier\":\"Low\"},{\"id\":\"5\",\"_identifier\":\"Medium\"}]},\"C_Campaign_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"ChargeAmt\":{\"value\":0,\"classicValue\":\"0.00\"},\"C_Charge_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Activity_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"AD_OrgTrx_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Quotation_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DeliveryStatus\":{\"classicValue\":\"\"},\"InvoiceStatus\":{\"classicValue\":\"\"},\"Calculate_Promotions\":{\"classicValue\":\"N\",\"value\":false},\"RM_AddOrphanLine\":{\"classicValue\":\"N\",\"value\":false},\"Convertquotation\":{\"classicValue\":\"N\",\"value\":false},\"C_Reject_Reason_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"validuntil\":{\"classicValue\":\"\"},\"Cancelledorder_id\":{},\"Replacedorder_id\":{},\"Replacementorder_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Iscancelled\":{\"value\":false,\"classicValue\":\"N\"},\"BPartner_ExtRef\":{\"classicValue\":\"\"},\"C_Project_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"C_Costcenter_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"A_Asset_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User1_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"User2_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Cancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"PaymentStatus\":{\"classicValue\":\"\"},\"Confirmcancelandreplace\":{\"classicValue\":\"N\",\"value\":false},\"C_Order_ID\":{\"classicValue\":\"\"},\"PaymentRule\":{\"value\":\"P\",\"classicValue\":\"P\",\"entries\":[{\"id\":\"5\",\"_identifier\":\"Bank Deposit\"},{\"id\":\"R\",\"_identifier\":\"Bank Remittance\"},{\"id\":\"B\",\"_identifier\":\"Cash\"},{\"id\":\"C\",\"_identifier\":\"Cash on Delivery\"},{\"id\":\"2\",\"_identifier\":\"Check\"},{\"id\":\"K\",\"_identifier\":\"Credit Card\"},{\"id\":\"4\",\"_identifier\":\"Money Order\"},{\"id\":\"P\",\"_identifier\":\"On Credit\"},{\"id\":\"3\",\"_identifier\":\"Promissory Note\"},{\"id\":\"1\",\"_identifier\":\"Wire Transfer\"},{\"id\":\"W\",\"_identifier\":\"Withholding\"}]},\"Posted\":{\"value\":\"N\",\"classicValue\":\"N\",\"identifier\":\"Post\"},\"IsTaxIncluded\":{\"classicValue\":\"N\",\"value\":false},\"IsSelected\":{\"classicValue\":\"N\",\"value\":false},\"DropShip_User_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DropShip_BPartner_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"DatePrinted\":{\"classicValue\":\"\"},\"DropShip_Location_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"IsSelfService\":{\"classicValue\":\"N\",\"value\":false},\"Generatetemplate\":{\"classicValue\":\"N\",\"value\":false},\"Processed\":{\"classicValue\":\"N\",\"value\":false},\"Deliverynotes\":{\"classicValue\":\"\"},\"C_Incoterms_ID\":{\"value\":\"\",\"classicValue\":\"\"},\"Incotermsdescription\":{\"classicValue\":\"\"},\"Processing\":{\"classicValue\":\"N\",\"value\":false},\"DateAcct\":{\"value\":\"2024-11-04\",\"classicValue\":\"04-11-2024\",\"hasDateDefault\":true},\"IsPrinted\":{\"classicValue\":\"N\",\"value\":false},\"IsInvoiced\":{\"classicValue\":\"N\",\"value\":false},\"IsDelivered\":{\"classicValue\":\"N\",\"value\":false},\"IsActive\":{\"value\":true,\"classicValue\":\"Y\"},\"AD_Client_ID\":{\"value\":\"23C59575B9CF467C9620760EB255B389\",\"classicValue\":\"23C59575B9CF467C9620760EB255B389\",\"identifier\":\"F&B International Group\"},\"IsSOTrx\":{\"value\":true,\"classicValue\":\"Y\"}},\"auxiliaryInputValues\":{\"ORDERTYPE\":{\"value\":\"SO\",\"classicValue\":\"SO\"},\"DOCBASETYPE\":{\"value\":\"SOO\",\"classicValue\":\"SOO\"},\"showAddPayment\":{},\"PromotionsDefined\":{\"value\":\"N\",\"classicValue\":\"N\"},\"APRM_OrderIsPaid\":{}},\"overwrittenAuxiliaryInputs\":[\"ORDERTYPE\"],\"sessionAttributes\":{\"$Element_U2_SOO_H\":\"N\",\"$Element_U2_ARR_H\":\"N\",\"EnableCanceAndReplace\":\"\",\"$Element_U2_APP_H\":\"N\",\"StockReservations\":\"\",\"Replacementorder_ID\":\"\",\"$Element_OO\":\"Y\",\"$Element_CC\":\"\",\"$Element_OO_POO_H\":\"Y\",\"$Element_U1_POO_H\":\"N\",\"$Element_PJ_SOO_H\":\"Y\",\"$Element_CC_APP_H\":\"\",\"$Element_CC_ARR_H\":\"\",\"$Element_BP_POO_H\":\"Y\",\"$Element_AS\":\"\",\"$Element_BP_SOO_H\":\"Y\",\"$Element_CC_SOO_H\":\"\",\"$Element_U1_SOO_H\":\"N\",\"$IsAcctDimCentrally\":\"Y\",\"ACCT_DIMENSION_DISPLAY\":\"\",\"HASRELATEDSERVICE\":\"N\",\"$Element_OO_ARR_H\":\"Y\",\"$Element_U2_POO_H\":\"N\",\"$Element_OO_APP_H\":\"Y\",\"$Element_U2\":\"\",\"$Element_BP_APP_H\":\"Y\",\"$Element_U1\":\"\",\"$Element_U1_ARR_H\":\"N\",\"$Element_BP_ARR_H\":\"Y\",\"$Element_PJ\":\"Y\",\"$Element_CC_POO_H\":\"\",\"$Element_U1_APP_H\":\"N\",\"PRODUCTTYPE\":\"I\",\"$Element_PJ_POO_H\":\"Y\",\"$Element_PJ_ARR_H\":\"Y\",\"$Element_OO_SOO_H\":\"Y\",\"$Element_BP\":\"Y\",\"$Element_PJ_APP_H\":\"Y\"},\"dynamicCols\":[\"inpadOrgId\",\"inpissotrx\",\"inpcBpartnerId\",\"inpcDoctypetargetId\",\"inpdropshipBpartnerId\",\"inpdateordered\",\"inpcBpartnerLocationId\",\"inpmPricelistId\",\"inpdatepromised\",\"inpcChargeId\",\"inpcProjectId\"],\"attachmentCount\":0,\"attachmentExists\":false}";

    List<Parameter> formChangeParams = Arrays.asList(
        createParameter("_action", true, "string", FORM_INIT_ACTION, "Action to execute."),
        createParameter("MODE", true, "string", "CHANGE", "Initial form mode."),
        createParameter("PARENT_ID", false, "string", "null",
            "Parent ID of the form initialization."),
        createParameter("TAB_ID", false, "string", "294", "Tab ID of the form initialization."),
        createParameter("ROW_ID", false, "string", "null", "Row ID of the form initialization."),
        createParameter("CHANGED_COLUMN", false, "string", "inpcBpartnerId",
            "Row ID of the form initialization."));

    createEndpoint(openAPI, FORM_INIT_ACTION + "&MODE=CHANGE",
        "(CALLOUTS) Triggers field changes in a form",
        "This endpoint is used to trigger field changes in a form.", formChangeResponseSchema,
        formChangeResponseExample, "FormChangeResponse", formChangeParams, formInitRequestSchema,
        formInitRequestExample, "POST");

  }

  private void createEndpoint(OpenAPI openAPI, String actionValue, String summary,
      String description, Schema<?> responseSchema, String responseExample, String schemaKey,
      List<Parameter> parameters, Schema<?> requestBodySchema, String requestBodyExample,
      String httpMethod) {

    ApiResponses apiResponses = new ApiResponses().addApiResponse("200",
            createApiResponse("Successful response.", responseSchema, responseExample))
        .addApiResponse("400", new ApiResponse().description("Unsuccessful request."))
        .addApiResponse("500", new ApiResponse().description("Internal server error."));

    Operation operation = new Operation().summary(summary)
        .description(description);
    if(operation.getTags() == null) {
      operation.setTags(new ArrayList<>());
    }
    tags.forEach(it -> operation.getTags().add(it));

    for (Parameter parameter : parameters) {
      operation.addParametersItem(parameter);
    }

    if (requestBodySchema != null) {
      RequestBody requestBody = new RequestBody().description(
              "Request body for request " + actionValue)
          .content(new Content().addMediaType("application/json",
              new MediaType().schema(requestBodySchema).example(requestBodyExample)))
          .required(true);
      operation.setRequestBody(requestBody);
    }

    operation.responses(apiResponses);

    PathItem pathItem = httpMethod.equalsIgnoreCase("GET") ?
        new PathItem().get(operation) :
        new PathItem().post(operation);

    if (openAPI.getPaths() == null) {
      openAPI.setPaths(new Paths());
    }

    String path = BASE_PATH + actionValue;
    openAPI.getPaths().addPathItem(path, pathItem);

    addSchema(openAPI, schemaKey, responseSchema);
  }

  private ApiResponse createApiResponse(String description, Schema<?> schema, String example) {
    return new ApiResponse().description(description)
        .content(new Content().addMediaType("application/json",
            new MediaType().schema(schema).example(example)));
  }

  private Parameter createParameter(String name, boolean required, String type, String example,
      String description) {
    return new Parameter().in("query")
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
    Schema<?> items = new Schema<>().type("array");
    items.setAdditionalProperties(new Schema<>().type("string"));

    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperties("uiPattern",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("autoSave", new Schema<>().type("boolean"));

    Schema<Object> personalizationSchema = new Schema<>();
    personalizationSchema.type("object");
    personalizationSchema.addProperties("forms",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));

    personalizationSchema.addProperties("views", items);

    Schema<Object> formDataSchema = new Schema<>();
    formDataSchema.type("object");
    formDataSchema.addProperties("clients",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperties("orgs",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperties("roles",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));

    personalizationSchema.addProperties("formData", formDataSchema);
    schema.addProperties("personalization", personalizationSchema);

    schema.addProperties("showAutoSaveConfirmation", new Schema<>().type("boolean"));
    schema.addProperties("tabs", items);
    schema.addProperties("notAccessibleProcesses", items);
    schema.addProperties("extraSettings",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("extraCallbacks", items);

    // Define required fields
    schema.required(
        Arrays.asList("uiPattern", "autoSave", "personalization", "showAutoSaveConfirmation",
            "tabs", "notAccessibleProcesses", "extraSettings", "extraCallbacks"));

    return schema;
  }

  private Schema<?> defineFormInitResponseSchema() {
    Schema<Object> schema = new Schema<>();
    Schema<?> items = new Schema<>().type("array");
    items.setAdditionalProperties(new Schema<>().type("string"));

    schema.type("object");

    schema.addProperties("hiddenInputs",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("calloutMessages", items);
    schema.addProperties("columnValues",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("auxiliaryInputValues",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("overwrittenAuxiliaryInputs", items);
    schema.addProperties("sessionAttributes",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperties("dynamicCols", items);
    schema.addProperties("attachmentCount", new Schema<>().type("integer").example(0));
    schema.addProperties("attachmentExists", new Schema<>().type("boolean").example(false));

    // Define required fields
    schema.required(
        Arrays.asList("hiddenInputs", "calloutMessages", "columnValues", "auxiliaryInputValues",
            "overwrittenAuxiliaryInputs", "sessionAttributes", "dynamicCols", "attachmentCount",
            "attachmentExists"));

    return schema;
  }

  private Schema<?> defineFormInitRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    // Propiedades del esquema
    schema.addProperties("inpgeneratetemplate", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcReturnReasonId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inprmPickfromshipment", new Schema<>().type("string").example("N"));
    schema.addProperties("inprmReceivematerials", new Schema<>().type("string").example("N"));
    schema.addProperties("inprmCreateinvoice", new Schema<>().type("string").example("N"));
    schema.addProperties("inptotallines",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperties("inpadUserId", new Schema<>().type("string").example("100"));
    schema.addProperties("inpsalesrepId", new Schema<>().type("string").example("100"));
    schema.addProperties("inpcIncotermsId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpincotermsdescription",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpisdiscountprinted", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcDoctypeId", new Schema<>().type("string").example("0"));
    schema.addProperties("inpfreightamt",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperties("inpdeliveryviarule", new Schema<>().type("string").example("P"));
    schema.addProperties("inpcCampaignId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpmShipperId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inppriorityrule", new Schema<>().type("string").example("5"));
    schema.addProperties("inpchargeamt", new Schema<>().type("number").format("float").example(0.00));
    schema.addProperties("inpcChargeId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpcActivityId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpfreightcostrule", new Schema<>().type("string").example("I"));
    schema.addProperties("inpadOrgtrxId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpemAprmAddpayment", new Schema<>().type("string").example("N"));
    schema.addProperties("inpdocaction", new Schema<>().type("string").example("CO"));
    schema.addProperties("inpcopyfrom", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcopyfrompo", new Schema<>().type("string").example("N"));
    schema.addProperties("inprmAddorphanline", new Schema<>().type("string").example("N"));
    schema.addProperties("inpconvertquotation", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcRejectReasonId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpvaliduntil", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpcalculatePromotions", new Schema<>().type("string").example("N"));
    schema.addProperties("inpquotationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpcreatePolines", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcOrderId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpadClientId",
        new Schema<>().type("string").example("23C59575B9CF467C9620760EB255B389"));
    schema.addProperties("inpisactive", new Schema<>().type("string").example("Y"));
    schema.addProperties("inpisdelivered", new Schema<>().type("string").example("N"));
    schema.addProperties("inpisinvoiced", new Schema<>().type("string").example("N"));
    schema.addProperties("inpisprinted", new Schema<>().type("string").example("N"));
    schema.addProperties("inpdateacct", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperties("inpinvoicerule", new Schema<>().type("string").example("D"));
    schema.addProperties("inpprocessing", new Schema<>().type("string").example("N"));
    schema.addProperties("inpprocessed", new Schema<>().type("string").example("N"));
    schema.addProperties("inpdateprinted",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpdeliveryrule", new Schema<>().type("string").example("A"));
    schema.addProperties("inpissotrx", new Schema<>().type("string").example("N"));
    schema.addProperties("inppaymentrule", new Schema<>().type("string").example("P"));
    schema.addProperties("inpposted", new Schema<>().type("string").example("N"));
    schema.addProperties("inpisselected", new Schema<>().type("string").example("N"));
    schema.addProperties("inpdropshipUserId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpdropshipBpartnerId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpdropshipLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpisselfservice", new Schema<>().type("string").example("N"));
    schema.addProperties("inpdeliveryLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpsoResStatus",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("C_Order_ID", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpadOrgId",
        new Schema<>().type("string").example("E443A31992CB4635AFCAEABE7183CE85"));
    schema.addProperties("inpcDoctypetargetId",
        new Schema<>().type("string").example("AB22CE8FFA5E4AF29F2AC90FCDD400D8"));
    schema.addProperties("inpdocumentno", new Schema<>().type("string").example("<1000376>"));
    schema.addProperties("inpdateordered", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperties("inpcBpartnerId",
        new Schema<>().type("string").example("858B90C7AF0A4533863EEC65437382BF"));
    schema.addProperties("inpcBpartnerLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpmWarehouseId",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    schema.addProperties("inpdatepromised", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperties("inpfinPaymentmethodId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpcPaymenttermId",
        new Schema<>().type("string").example("B62EDD9166D146539E9A19C05BCF85E5"));
    schema.addProperties("inpmPricelistId",
        new Schema<>().type("string").example("CBC16D5792744C669D388FC4F66B85FD"));
    schema.addProperties("inpdeliverynotes",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpdescription",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpporeference",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpbilltoId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpistaxincluded", new Schema<>().type("string").example("N"));
    schema.addProperties("inpcProjectId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpcCostcenterId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpaAssetId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpuser1Id", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpuser2Id", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperties("inpdocstatus", new Schema<>().type("string").example("DR"));
    schema.addProperties("inpgrandtotal",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperties("inpcCurrencyId", new Schema<>().type("string").example("102"));
    schema.addProperties("inpTabId", new Schema<>().type("string").example("294"));
    schema.addProperties("inpwindowId", new Schema<>().type("string").example("181"));
    schema.addProperties("inpTableId", new Schema<>().type("string").example("259"));
    schema.addProperties("inpkeyColumnId", new Schema<>().type("string").example("C_Order_ID"));
    schema.addProperties("keyProperty", new Schema<>().type("string").example("id"));
    schema.addProperties("inpKeyName", new Schema<>().type("string").example("inpcOrderId"));
    schema.addProperties("keyColumnName", new Schema<>().type("string").example("C_Order_ID"));
    schema.addProperties("keyPropertyType", new Schema<>().type("string").example("_id_13"));
    schema.addProperties("ORDERTYPE", new Schema<>().type("string").example("--"));
    schema.addProperties("DOCBASETYPE", new Schema<>().type("string").example("POO"));
    schema.addProperties("showAddPayment", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperties("APRM_OrderIsPaid",
        new Schema<>().type("string").nullable(true).example(""));

    // Propiedades con prefijo "$Element_"
    schema.addProperties("$Element_U2_SOO_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_U2_ARR_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_U2_APP_H", new Schema<>().type("string").example("N"));
    schema.addProperties("StockReservations", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_OO", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_CC", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_OO_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_U1_POO_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_PJ_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_CC_APP_H", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_CC_ARR_H", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_BP_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_AS", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_BP_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_CC_SOO_H", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_U1_SOO_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$IsAcctDimCentrally", new Schema<>().type("string").example("Y"));
    schema.addProperties("ACCT_DIMENSION_DISPLAY", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_OO_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_U2_POO_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_OO_APP_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_U2", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_BP_APP_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_U1", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_U1_ARR_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_BP_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_PJ", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_CC_POO_H", new Schema<>().type("string").example(""));
    schema.addProperties("$Element_U1_APP_H", new Schema<>().type("string").example("N"));
    schema.addProperties("$Element_PJ_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_PJ_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_OO_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_BP", new Schema<>().type("string").example("Y"));
    schema.addProperties("$Element_PJ_APP_H", new Schema<>().type("string").example("Y"));

    // Propiedades adicionales
    schema.addProperties("_entityName", new Schema<>().type("string").example("Order"));

    // Propiedad de arreglo "overwrittenAuxiliaryInputs"
    Schema<?> overwrittenAuxiliaryInputsSchema = new Schema<>().type("array");
    overwrittenAuxiliaryInputsSchema.setAdditionalProperties(new Schema<>().type("string").example("ORDERTYPE"));
    schema.addProperties("overwrittenAuxiliaryInputs", overwrittenAuxiliaryInputsSchema);

    // Definir campos requeridos
    schema.required(
        Arrays.asList("inpgeneratetemplate", "inprmPickfromshipment", "inprmReceivematerials",
            "inprmCreateinvoice", "inptotallines", "inpadUserId", "inpsalesrepId",
            "inpisdiscountprinted", "inpcDoctypeId", "inpfreightamt", "inpdeliveryviarule",
            "inppriorityrule", "inpchargeamt", "inpfreightcostrule", "inpemAprmAddpayment",
            "inpdocaction", "inpcopyfrom", "inpcopyfrompo", "inprmAddorphanline",
            "inpconvertquotation", "inpcalculatePromotions", "inpcreatePolines", "inpadClientId",
            "inpisactive", "inpisdelivered", "inpisinvoiced", "inpisprinted", "inpdateacct",
            "inpinvoicerule", "inpprocessing", "inpprocessed", "inpdeliveryrule", "inpissotrx",
            "inppaymentrule", "inpposted", "inpisselected", "inpisselfservice", "inpTabId",
            "inpwindowId", "inpTableId", "inpkeyColumnId", "keyProperty", "inpKeyName",
            "keyColumnName", "keyPropertyType", "ORDERTYPE", "DOCBASETYPE", "$Element_U2_SOO_H",
            "$Element_U2_ARR_H", "$Element_U2_APP_H", "StockReservations", "$Element_OO",
            "$Element_CC", "$Element_OO_POO_H", "$Element_U1_POO_H", "$Element_PJ_SOO_H",
            "$Element_CC_APP_H", "$Element_CC_ARR_H", "$Element_BP_POO_H", "$Element_AS",
            "$Element_BP_SOO_H", "$Element_CC_SOO_H", "$Element_U1_SOO_H", "$IsAcctDimCentrally",
            "ACCT_DIMENSION_DISPLAY", "$Element_OO_ARR_H", "$Element_U2_POO_H", "$Element_OO_APP_H",
            "$Element_U2", "$Element_BP_APP_H", "$Element_U1", "$Element_U1_ARR_H",
            "$Element_BP_ARR_H", "$Element_PJ", "$Element_CC_POO_H", "$Element_U1_APP_H",
            "$Element_PJ_POO_H", "$Element_PJ_ARR_H", "$Element_OO_SOO_H", "$Element_BP",
            "$Element_PJ_APP_H", "_entityName", "overwrittenAuxiliaryInputs"));

    return schema;
  }

  private Schema<?> defineFormChangeRequestSchema() {
    Schema<Object> schema = new Schema<>();
    schema.type("object");

    Schema<Object> hiddenInputsSchema = new Schema<>();
    hiddenInputsSchema.type("object");
    hiddenInputsSchema.addProperties("inpiscashvat", new Schema<>().type("string").example("N"));
    hiddenInputsSchema.addProperties("inpcProjectId_R", new Schema<>().type("string").example(""));
    schema.addProperties("hiddenInputs", hiddenInputsSchema);

    Schema<Object> calloutMessageSchema = new Schema<>();
    calloutMessageSchema.type("object");
    calloutMessageSchema.addProperties("text", new Schema<>().type("string").example(""));
    calloutMessageSchema.addProperties("severity",
        new Schema<>().type("string").example("TYPE_INFO"));

    Schema<Object> calloutMessagesSchema = new Schema<>();
    calloutMessagesSchema.type("array");
    calloutMessagesSchema.setAdditionalProperties(calloutMessageSchema);
    schema.addProperties("calloutMessages", calloutMessagesSchema);

    Schema<Object> columnValuesSchema = new Schema<>();
    columnValuesSchema.type("object");

    Schema<Object> cBPartnerLocationIdSchema = new Schema<>();
    cBPartnerLocationIdSchema.type("object");
    cBPartnerLocationIdSchema.addProperties("value",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    cBPartnerLocationIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    cBPartnerLocationIdSchema.addProperties("identifier",
        new Schema<>().type("string").example(".Barcelona, C\\Mayor, 23"));
    columnValuesSchema.addProperties("C_BPartner_Location_ID", cBPartnerLocationIdSchema);

    Schema<Object> mWarehouseIdSchema = new Schema<>();
    mWarehouseIdSchema.type("object");
    mWarehouseIdSchema.addProperties("value",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    mWarehouseIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));

    Schema<Object> mWarehouseEntriesSchema = new Schema<>();
    mWarehouseEntriesSchema.type("array");
    Schema<Object> mWarehouseEntryItemSchema = new Schema<>();
    mWarehouseEntryItemSchema.type("object");
    mWarehouseEntryItemSchema.addProperties("id",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    mWarehouseEntryItemSchema.addProperties("_identifier",
        new Schema<>().type("string").example("España Región Norte"));
    mWarehouseEntriesSchema.setAdditionalProperties(mWarehouseEntryItemSchema);
    mWarehouseIdSchema.addProperties("entries", mWarehouseEntriesSchema);

    columnValuesSchema.addProperties("M_Warehouse_ID", mWarehouseIdSchema);

    Schema<Object> finPaymentmethodIdSchema = new Schema<>();
    finPaymentmethodIdSchema.type("object");
    finPaymentmethodIdSchema.addProperties("value",
        new Schema<>().type("string").example("A97CFD2AFC234B59BB0A72189BD8FC2A"));
    finPaymentmethodIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("A97CFD2AFC234B59BB0A72189BD8FC2A"));
    finPaymentmethodIdSchema.addProperties("identifier",
        new Schema<>().type("string").example("Transferencia"));
    columnValuesSchema.addProperties("FIN_Paymentmethod_ID", finPaymentmethodIdSchema);

    Schema<Object> cPaymentTermIdSchema = new Schema<>();
    cPaymentTermIdSchema.type("object");
    cPaymentTermIdSchema.addProperties("value",
        new Schema<>().type("string").example("66BA1164A7394344BB9CD1A6ECEED05D"));
    cPaymentTermIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("66BA1164A7394344BB9CD1A6ECEED05D"));
    cPaymentTermIdSchema.addProperties("identifier",
        new Schema<>().type("string").example("30 days"));
    columnValuesSchema.addProperties("C_PaymentTerm_ID", cPaymentTermIdSchema);

    Schema<Object> mPriceListIdSchema = new Schema<>();
    mPriceListIdSchema.type("object");
    mPriceListIdSchema.addProperties("value",
        new Schema<>().type("string").example("91AE1E96A30844209CD996917E193BE1"));
    mPriceListIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("91AE1E96A30844209CD996917E193BE1"));
    mPriceListIdSchema.addProperties("identifier",
        new Schema<>().type("string").example("Tarifa Bebidas Alegres"));
    columnValuesSchema.addProperties("M_PriceList_ID", mPriceListIdSchema);

    Schema<Object> adUserIdSchema = new Schema<>();
    adUserIdSchema.type("object");
    adUserIdSchema.addProperties("value",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));
    adUserIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));

    Schema<Object> adUserEntriesSchema = new Schema<>();
    adUserEntriesSchema.type("array");
    Schema<Object> adUserEntryItemSchema = new Schema<>();
    adUserEntryItemSchema.type("object");
    adUserEntryItemSchema.addProperties("id",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));
    adUserEntryItemSchema.addProperties("_identifier",
        new Schema<>().type("string").example("Pablo Ramos"));
    adUserEntriesSchema.setAdditionalProperties(adUserEntryItemSchema);
    adUserIdSchema.addProperties("entries", adUserEntriesSchema);

    columnValuesSchema.addProperties("AD_User_ID", adUserIdSchema);

    Schema<Object> salesRepIdSchema = new Schema<>();
    salesRepIdSchema.type("object");

    Schema<Object> salesRepEntriesSchema = new Schema<>();
    salesRepEntriesSchema.type("array");

    Schema<Object> emptySalesRepEntry = new Schema<>();
    emptySalesRepEntry.type("object");

    Schema<Object> salesRepEntry1 = new Schema<>();
    salesRepEntry1.type("object");
    salesRepEntry1.addProperties("id",
        new Schema<>().type("string").example("C3503BEFB3CB4848A674284A656163B9"));
    salesRepEntry1.addProperties("_identifier",
        new Schema<>().type("string").example("Javier Martín"));

    Schema<Object> salesRepEntry2 = new Schema<>();
    salesRepEntry2.type("object");
    salesRepEntry2.addProperties("id",
        new Schema<>().type("string").example("8537B1F5669E423ABA79F1F57B1E4222"));
    salesRepEntry2.addProperties("_identifier", new Schema<>().type("string").example("John Smith"));

    Schema<Object> salesRepEntry3 = new Schema<>();
    salesRepEntry3.type("object");
    salesRepEntry3.addProperties("id",
        new Schema<>().type("string").example("CADCDC3549FB4201B5F24E4C03AD2349"));
    salesRepEntry3.addProperties("_identifier", new Schema<>().type("string").example("Juan López"));

    salesRepEntriesSchema.setAdditionalProperties(emptySalesRepEntry);
    salesRepEntriesSchema.setAdditionalProperties(salesRepEntry1);
    salesRepEntriesSchema.setAdditionalProperties(salesRepEntry2);
    salesRepEntriesSchema.setAdditionalProperties(salesRepEntry3);

    salesRepIdSchema.addProperties("entries", salesRepEntriesSchema);
    columnValuesSchema.addProperties("SalesRep_ID", salesRepIdSchema);

    Schema<Object> billToIdSchema = new Schema<>();
    billToIdSchema.type("object");
    billToIdSchema.addProperties("value",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    billToIdSchema.addProperties("classicValue",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    billToIdSchema.addProperties("identifier",
        new Schema<>().type("string").example(".Barcelona, C\\Mayor, 23"));
    columnValuesSchema.addProperties("BillTo_ID", billToIdSchema);

    Schema<Object> cProjectIdSchema = new Schema<>();
    cProjectIdSchema.type("object");
    cProjectIdSchema.addProperties("value", new Schema<>().type("string").example(""));
    cProjectIdSchema.addProperties("classicValue", new Schema<>().type("string").example(""));
    columnValuesSchema.addProperties("C_Project_ID", cProjectIdSchema);

    Schema<Object> invoiceRuleSchema = new Schema<>();
    invoiceRuleSchema.type("object");
    invoiceRuleSchema.addProperties("value", new Schema<>().type("string").example("I"));
    invoiceRuleSchema.addProperties("classicValue", new Schema<>().type("string").example("I"));

    Schema<Object> invoiceRuleEntriesSchema = new Schema<>();
    invoiceRuleEntriesSchema.type("array");

    Schema<Object> invoiceRuleEntry1 = new Schema<>();
    invoiceRuleEntry1.type("object");
    invoiceRuleEntry1.addProperties("id", new Schema<>().type("string").example("D"));
    invoiceRuleEntry1.addProperties("_identifier",
        new Schema<>().type("string").example("After Delivery"));

    Schema<Object> invoiceRuleEntry2 = new Schema<>();
    invoiceRuleEntry2.type("object");
    invoiceRuleEntry2.addProperties("id", new Schema<>().type("string").example("O"));
    invoiceRuleEntry2.addProperties("_identifier",
        new Schema<>().type("string").example("After Order Delivered"));

    Schema<Object> invoiceRuleEntry3 = new Schema<>();
    invoiceRuleEntry3.type("object");
    invoiceRuleEntry3.addProperties("id", new Schema<>().type("string").example("S"));
    invoiceRuleEntry3.addProperties("_identifier",
        new Schema<>().type("string").example("Customer Schedule After Delivery"));

    Schema<Object> invoiceRuleEntry4 = new Schema<>();
    invoiceRuleEntry4.type("object");
    invoiceRuleEntry4.addProperties("id", new Schema<>().type("string").example("N"));
    invoiceRuleEntry4.addProperties("_identifier",
        new Schema<>().type("string").example("Do Not Invoice"));

    Schema<Object> invoiceRuleEntry5 = new Schema<>();
    invoiceRuleEntry5.type("object");
    invoiceRuleEntry5.addProperties("id", new Schema<>().type("string").example("I"));
    invoiceRuleEntry5.addProperties("_identifier",
        new Schema<>().type("string").example("Immediate"));

    invoiceRuleEntriesSchema.setAdditionalProperties(invoiceRuleEntry1);
    invoiceRuleEntriesSchema.setAdditionalProperties(invoiceRuleEntry2);
    invoiceRuleEntriesSchema.setAdditionalProperties(invoiceRuleEntry3);
    invoiceRuleEntriesSchema.setAdditionalProperties(invoiceRuleEntry4);
    invoiceRuleEntriesSchema.setAdditionalProperties(invoiceRuleEntry5);

    invoiceRuleSchema.addProperties("entries", invoiceRuleEntriesSchema);
    columnValuesSchema.addProperties("InvoiceRule", invoiceRuleSchema);

    schema.addProperties("columnValues", columnValuesSchema);

    Schema<Object> auxiliaryInputValuesSchema = new Schema<>();
    auxiliaryInputValuesSchema.type("object");

    Schema<Object> docbasetypeSchema = new Schema<>();
    docbasetypeSchema.type("object");
    docbasetypeSchema.addProperties("value", new Schema<>().type("string").example("POO"));
    docbasetypeSchema.addProperties("classicValue", new Schema<>().type("string").example("POO"));
    auxiliaryInputValuesSchema.addProperties("DOCBASETYPE", docbasetypeSchema);

    Schema<Object> showAddPaymentSchema = new Schema<>();
    showAddPaymentSchema.type("object");
    auxiliaryInputValuesSchema.addProperties("showAddPayment", showAddPaymentSchema);

    Schema<Object> aprmOrderIsPaidSchema = new Schema<>();
    aprmOrderIsPaidSchema.type("object");
    auxiliaryInputValuesSchema.addProperties("APRM_OrderIsPaid", aprmOrderIsPaidSchema);

    schema.addProperties("auxiliaryInputValues", auxiliaryInputValuesSchema);

    Schema<Object> overwrittenAuxiliaryInputsSchema = new Schema<>();
    overwrittenAuxiliaryInputsSchema.type("array");
    overwrittenAuxiliaryInputsSchema.setAdditionalProperties(new Schema<>().type("string").example("ORDERTYPE"));
    schema.addProperties("overwrittenAuxiliaryInputs", overwrittenAuxiliaryInputsSchema);

    schema.addProperties("noteCount", new Schema<>().type("integer").example(0));

    schema.addProperties("attachmentCount", new Schema<>().type("integer").example(0));

    schema.addProperties("attachmentExists", new Schema<>().type("boolean").example(false));

    schema.required(
        Arrays.asList("hiddenInputs", "calloutMessages", "columnValues", "auxiliaryInputValues",
            "overwrittenAuxiliaryInputs", "noteCount", "attachmentCount", "attachmentExists"));

    return schema;
  }
}
