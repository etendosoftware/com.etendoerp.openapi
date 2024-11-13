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
import io.swagger.v3.oas.models.tags.Tag;

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
  public List<String> getTags() {
    return tags;
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
    items.items(new Schema<>().type("string"));

    Schema<Object> schema = new Schema<>();
    schema.type("object");

    schema.addProperty("uiPattern",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("autoSave", new Schema<>().type("boolean"));

    Schema<Object> personalizationSchema = new Schema<>();
    personalizationSchema.type("object");
    personalizationSchema.addProperty("forms",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));

    personalizationSchema.addProperty("views", items);

    Schema<Object> formDataSchema = new Schema<>();
    formDataSchema.type("object");
    formDataSchema.addProperty("clients",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperty("orgs",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    formDataSchema.addProperty("roles",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));

    personalizationSchema.addProperty("formData", formDataSchema);
    schema.addProperty("personalization", personalizationSchema);

    schema.addProperty("showAutoSaveConfirmation", new Schema<>().type("boolean"));
    schema.addProperty("tabs", items);
    schema.addProperty("notAccessibleProcesses", items);
    schema.addProperty("extraSettings",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("extraCallbacks", items);

    // Define required fields
    schema.required(
        Arrays.asList("uiPattern", "autoSave", "personalization", "showAutoSaveConfirmation",
            "tabs", "notAccessibleProcesses", "extraSettings", "extraCallbacks"));

    return schema;
  }

  private Schema<?> defineFormInitResponseSchema() {
    Schema<Object> schema = new Schema<>();
    Schema<?> items = new Schema<>().type("array");
    items.items(new Schema<>().type("string"));

    schema.type("object");

    schema.addProperty("hiddenInputs",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("calloutMessages", items);
    schema.addProperty("columnValues",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("auxiliaryInputValues",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("overwrittenAuxiliaryInputs", items);
    schema.addProperty("sessionAttributes",
        new Schema<>().type("object").additionalProperties(new Schema<>().type("string")));
    schema.addProperty("dynamicCols", items);
    schema.addProperty("attachmentCount", new Schema<>().type("integer").example(0));
    schema.addProperty("attachmentExists", new Schema<>().type("boolean").example(false));

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
    schema.addProperty("inpgeneratetemplate", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcReturnReasonId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inprmPickfromshipment", new Schema<>().type("string").example("N"));
    schema.addProperty("inprmReceivematerials", new Schema<>().type("string").example("N"));
    schema.addProperty("inprmCreateinvoice", new Schema<>().type("string").example("N"));
    schema.addProperty("inptotallines",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperty("inpadUserId", new Schema<>().type("string").example("100"));
    schema.addProperty("inpsalesrepId", new Schema<>().type("string").example("100"));
    schema.addProperty("inpcIncotermsId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpincotermsdescription",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpisdiscountprinted", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcDoctypeId", new Schema<>().type("string").example("0"));
    schema.addProperty("inpfreightamt",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperty("inpdeliveryviarule", new Schema<>().type("string").example("P"));
    schema.addProperty("inpcCampaignId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpmShipperId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inppriorityrule", new Schema<>().type("string").example("5"));
    schema.addProperty("inpchargeamt", new Schema<>().type("number").format("float").example(0.00));
    schema.addProperty("inpcChargeId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpcActivityId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpfreightcostrule", new Schema<>().type("string").example("I"));
    schema.addProperty("inpadOrgtrxId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpemAprmAddpayment", new Schema<>().type("string").example("N"));
    schema.addProperty("inpdocaction", new Schema<>().type("string").example("CO"));
    schema.addProperty("inpcopyfrom", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcopyfrompo", new Schema<>().type("string").example("N"));
    schema.addProperty("inprmAddorphanline", new Schema<>().type("string").example("N"));
    schema.addProperty("inpconvertquotation", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcRejectReasonId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpvaliduntil", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpcalculatePromotions", new Schema<>().type("string").example("N"));
    schema.addProperty("inpquotationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpcreatePolines", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcOrderId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpadClientId",
        new Schema<>().type("string").example("23C59575B9CF467C9620760EB255B389"));
    schema.addProperty("inpisactive", new Schema<>().type("string").example("Y"));
    schema.addProperty("inpisdelivered", new Schema<>().type("string").example("N"));
    schema.addProperty("inpisinvoiced", new Schema<>().type("string").example("N"));
    schema.addProperty("inpisprinted", new Schema<>().type("string").example("N"));
    schema.addProperty("inpdateacct", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperty("inpinvoicerule", new Schema<>().type("string").example("D"));
    schema.addProperty("inpprocessing", new Schema<>().type("string").example("N"));
    schema.addProperty("inpprocessed", new Schema<>().type("string").example("N"));
    schema.addProperty("inpdateprinted",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpdeliveryrule", new Schema<>().type("string").example("A"));
    schema.addProperty("inpissotrx", new Schema<>().type("string").example("N"));
    schema.addProperty("inppaymentrule", new Schema<>().type("string").example("P"));
    schema.addProperty("inpposted", new Schema<>().type("string").example("N"));
    schema.addProperty("inpisselected", new Schema<>().type("string").example("N"));
    schema.addProperty("inpdropshipUserId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpdropshipBpartnerId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpdropshipLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpisselfservice", new Schema<>().type("string").example("N"));
    schema.addProperty("inpdeliveryLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpsoResStatus",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("C_Order_ID", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpadOrgId",
        new Schema<>().type("string").example("E443A31992CB4635AFCAEABE7183CE85"));
    schema.addProperty("inpcDoctypetargetId",
        new Schema<>().type("string").example("AB22CE8FFA5E4AF29F2AC90FCDD400D8"));
    schema.addProperty("inpdocumentno", new Schema<>().type("string").example("<1000376>"));
    schema.addProperty("inpdateordered", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperty("inpcBpartnerId",
        new Schema<>().type("string").example("858B90C7AF0A4533863EEC65437382BF"));
    schema.addProperty("inpcBpartnerLocationId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpmWarehouseId",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    schema.addProperty("inpdatepromised", new Schema<>().type("string").example("07-11-2024"));
    schema.addProperty("inpfinPaymentmethodId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpcPaymenttermId",
        new Schema<>().type("string").example("B62EDD9166D146539E9A19C05BCF85E5"));
    schema.addProperty("inpmPricelistId",
        new Schema<>().type("string").example("CBC16D5792744C669D388FC4F66B85FD"));
    schema.addProperty("inpdeliverynotes",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpdescription",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpporeference",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpbilltoId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpistaxincluded", new Schema<>().type("string").example("N"));
    schema.addProperty("inpcProjectId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpcCostcenterId",
        new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpaAssetId", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpuser1Id", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpuser2Id", new Schema<>().type("string").nullable(true).example(null));
    schema.addProperty("inpdocstatus", new Schema<>().type("string").example("DR"));
    schema.addProperty("inpgrandtotal",
        new Schema<>().type("number").format("float").example(0.00));
    schema.addProperty("inpcCurrencyId", new Schema<>().type("string").example("102"));
    schema.addProperty("inpTabId", new Schema<>().type("string").example("294"));
    schema.addProperty("inpwindowId", new Schema<>().type("string").example("181"));
    schema.addProperty("inpTableId", new Schema<>().type("string").example("259"));
    schema.addProperty("inpkeyColumnId", new Schema<>().type("string").example("C_Order_ID"));
    schema.addProperty("keyProperty", new Schema<>().type("string").example("id"));
    schema.addProperty("inpKeyName", new Schema<>().type("string").example("inpcOrderId"));
    schema.addProperty("keyColumnName", new Schema<>().type("string").example("C_Order_ID"));
    schema.addProperty("keyPropertyType", new Schema<>().type("string").example("_id_13"));
    schema.addProperty("ORDERTYPE", new Schema<>().type("string").example("--"));
    schema.addProperty("DOCBASETYPE", new Schema<>().type("string").example("POO"));
    schema.addProperty("showAddPayment", new Schema<>().type("string").nullable(true).example(""));
    schema.addProperty("APRM_OrderIsPaid",
        new Schema<>().type("string").nullable(true).example(""));

    // Propiedades con prefijo "$Element_"
    schema.addProperty("$Element_U2_SOO_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_U2_ARR_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_U2_APP_H", new Schema<>().type("string").example("N"));
    schema.addProperty("StockReservations", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_OO", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_CC", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_OO_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_U1_POO_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_PJ_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_CC_APP_H", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_CC_ARR_H", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_BP_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_AS", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_BP_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_CC_SOO_H", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_U1_SOO_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$IsAcctDimCentrally", new Schema<>().type("string").example("Y"));
    schema.addProperty("ACCT_DIMENSION_DISPLAY", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_OO_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_U2_POO_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_OO_APP_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_U2", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_BP_APP_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_U1", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_U1_ARR_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_BP_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_PJ", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_CC_POO_H", new Schema<>().type("string").example(""));
    schema.addProperty("$Element_U1_APP_H", new Schema<>().type("string").example("N"));
    schema.addProperty("$Element_PJ_POO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_PJ_ARR_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_OO_SOO_H", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_BP", new Schema<>().type("string").example("Y"));
    schema.addProperty("$Element_PJ_APP_H", new Schema<>().type("string").example("Y"));

    // Propiedades adicionales
    schema.addProperty("_entityName", new Schema<>().type("string").example("Order"));

    // Propiedad de arreglo "overwrittenAuxiliaryInputs"
    Schema<?> overwrittenAuxiliaryInputsSchema = new Schema<>().type("array");
    overwrittenAuxiliaryInputsSchema.items(new Schema<>().type("string").example("ORDERTYPE"));
    schema.addProperty("overwrittenAuxiliaryInputs", overwrittenAuxiliaryInputsSchema);

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
    hiddenInputsSchema.addProperty("inpiscashvat", new Schema<>().type("string").example("N"));
    hiddenInputsSchema.addProperty("inpcProjectId_R", new Schema<>().type("string").example(""));
    schema.addProperty("hiddenInputs", hiddenInputsSchema);

    Schema<Object> calloutMessageSchema = new Schema<>();
    calloutMessageSchema.type("object");
    calloutMessageSchema.addProperty("text", new Schema<>().type("string").example(""));
    calloutMessageSchema.addProperty("severity",
        new Schema<>().type("string").example("TYPE_INFO"));

    Schema<Object> calloutMessagesSchema = new Schema<>();
    calloutMessagesSchema.type("array");
    calloutMessagesSchema.items(calloutMessageSchema);
    schema.addProperty("calloutMessages", calloutMessagesSchema);

    Schema<Object> columnValuesSchema = new Schema<>();
    columnValuesSchema.type("object");

    Schema<Object> cBPartnerLocationIdSchema = new Schema<>();
    cBPartnerLocationIdSchema.type("object");
    cBPartnerLocationIdSchema.addProperty("value",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    cBPartnerLocationIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    cBPartnerLocationIdSchema.addProperty("identifier",
        new Schema<>().type("string").example(".Barcelona, C\\Mayor, 23"));
    columnValuesSchema.addProperty("C_BPartner_Location_ID", cBPartnerLocationIdSchema);

    Schema<Object> mWarehouseIdSchema = new Schema<>();
    mWarehouseIdSchema.type("object");
    mWarehouseIdSchema.addProperty("value",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    mWarehouseIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));

    Schema<Object> mWarehouseEntriesSchema = new Schema<>();
    mWarehouseEntriesSchema.type("array");
    Schema<Object> mWarehouseEntryItemSchema = new Schema<>();
    mWarehouseEntryItemSchema.type("object");
    mWarehouseEntryItemSchema.addProperty("id",
        new Schema<>().type("string").example("B2D40D8A5D644DD89E329DC297309055"));
    mWarehouseEntryItemSchema.addProperty("_identifier",
        new Schema<>().type("string").example("España Región Norte"));
    mWarehouseEntriesSchema.items(mWarehouseEntryItemSchema);
    mWarehouseIdSchema.addProperty("entries", mWarehouseEntriesSchema);

    columnValuesSchema.addProperty("M_Warehouse_ID", mWarehouseIdSchema);

    Schema<Object> finPaymentmethodIdSchema = new Schema<>();
    finPaymentmethodIdSchema.type("object");
    finPaymentmethodIdSchema.addProperty("value",
        new Schema<>().type("string").example("A97CFD2AFC234B59BB0A72189BD8FC2A"));
    finPaymentmethodIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("A97CFD2AFC234B59BB0A72189BD8FC2A"));
    finPaymentmethodIdSchema.addProperty("identifier",
        new Schema<>().type("string").example("Transferencia"));
    columnValuesSchema.addProperty("FIN_Paymentmethod_ID", finPaymentmethodIdSchema);

    Schema<Object> cPaymentTermIdSchema = new Schema<>();
    cPaymentTermIdSchema.type("object");
    cPaymentTermIdSchema.addProperty("value",
        new Schema<>().type("string").example("66BA1164A7394344BB9CD1A6ECEED05D"));
    cPaymentTermIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("66BA1164A7394344BB9CD1A6ECEED05D"));
    cPaymentTermIdSchema.addProperty("identifier",
        new Schema<>().type("string").example("30 days"));
    columnValuesSchema.addProperty("C_PaymentTerm_ID", cPaymentTermIdSchema);

    Schema<Object> mPriceListIdSchema = new Schema<>();
    mPriceListIdSchema.type("object");
    mPriceListIdSchema.addProperty("value",
        new Schema<>().type("string").example("91AE1E96A30844209CD996917E193BE1"));
    mPriceListIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("91AE1E96A30844209CD996917E193BE1"));
    mPriceListIdSchema.addProperty("identifier",
        new Schema<>().type("string").example("Tarifa Bebidas Alegres"));
    columnValuesSchema.addProperty("M_PriceList_ID", mPriceListIdSchema);

    Schema<Object> adUserIdSchema = new Schema<>();
    adUserIdSchema.type("object");
    adUserIdSchema.addProperty("value",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));
    adUserIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));

    Schema<Object> adUserEntriesSchema = new Schema<>();
    adUserEntriesSchema.type("array");
    Schema<Object> adUserEntryItemSchema = new Schema<>();
    adUserEntryItemSchema.type("object");
    adUserEntryItemSchema.addProperty("id",
        new Schema<>().type("string").example("33FE57CFE5BE4774B9B9EDFD8E27BCAE"));
    adUserEntryItemSchema.addProperty("_identifier",
        new Schema<>().type("string").example("Pablo Ramos"));
    adUserEntriesSchema.items(adUserEntryItemSchema);
    adUserIdSchema.addProperty("entries", adUserEntriesSchema);

    columnValuesSchema.addProperty("AD_User_ID", adUserIdSchema);

    Schema<Object> salesRepIdSchema = new Schema<>();
    salesRepIdSchema.type("object");

    Schema<Object> salesRepEntriesSchema = new Schema<>();
    salesRepEntriesSchema.type("array");

    Schema<Object> emptySalesRepEntry = new Schema<>();
    emptySalesRepEntry.type("object");

    Schema<Object> salesRepEntry1 = new Schema<>();
    salesRepEntry1.type("object");
    salesRepEntry1.addProperty("id",
        new Schema<>().type("string").example("C3503BEFB3CB4848A674284A656163B9"));
    salesRepEntry1.addProperty("_identifier",
        new Schema<>().type("string").example("Javier Martín"));

    Schema<Object> salesRepEntry2 = new Schema<>();
    salesRepEntry2.type("object");
    salesRepEntry2.addProperty("id",
        new Schema<>().type("string").example("8537B1F5669E423ABA79F1F57B1E4222"));
    salesRepEntry2.addProperty("_identifier", new Schema<>().type("string").example("John Smith"));

    Schema<Object> salesRepEntry3 = new Schema<>();
    salesRepEntry3.type("object");
    salesRepEntry3.addProperty("id",
        new Schema<>().type("string").example("CADCDC3549FB4201B5F24E4C03AD2349"));
    salesRepEntry3.addProperty("_identifier", new Schema<>().type("string").example("Juan López"));

    salesRepEntriesSchema.addOneOfItem(emptySalesRepEntry);
    salesRepEntriesSchema.addOneOfItem(salesRepEntry1);
    salesRepEntriesSchema.addOneOfItem(salesRepEntry2);
    salesRepEntriesSchema.addOneOfItem(salesRepEntry3);

    salesRepIdSchema.addProperty("entries", salesRepEntriesSchema);
    columnValuesSchema.addProperty("SalesRep_ID", salesRepIdSchema);

    Schema<Object> billToIdSchema = new Schema<>();
    billToIdSchema.type("object");
    billToIdSchema.addProperty("value",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    billToIdSchema.addProperty("classicValue",
        new Schema<>().type("string").example("1F38687D74244B369F0B16B3858CD256"));
    billToIdSchema.addProperty("identifier",
        new Schema<>().type("string").example(".Barcelona, C\\Mayor, 23"));
    columnValuesSchema.addProperty("BillTo_ID", billToIdSchema);

    Schema<Object> cProjectIdSchema = new Schema<>();
    cProjectIdSchema.type("object");
    cProjectIdSchema.addProperty("value", new Schema<>().type("string").example(""));
    cProjectIdSchema.addProperty("classicValue", new Schema<>().type("string").example(""));
    columnValuesSchema.addProperty("C_Project_ID", cProjectIdSchema);

    Schema<Object> invoiceRuleSchema = new Schema<>();
    invoiceRuleSchema.type("object");
    invoiceRuleSchema.addProperty("value", new Schema<>().type("string").example("I"));
    invoiceRuleSchema.addProperty("classicValue", new Schema<>().type("string").example("I"));

    Schema<Object> invoiceRuleEntriesSchema = new Schema<>();
    invoiceRuleEntriesSchema.type("array");

    Schema<Object> invoiceRuleEntry1 = new Schema<>();
    invoiceRuleEntry1.type("object");
    invoiceRuleEntry1.addProperty("id", new Schema<>().type("string").example("D"));
    invoiceRuleEntry1.addProperty("_identifier",
        new Schema<>().type("string").example("After Delivery"));

    Schema<Object> invoiceRuleEntry2 = new Schema<>();
    invoiceRuleEntry2.type("object");
    invoiceRuleEntry2.addProperty("id", new Schema<>().type("string").example("O"));
    invoiceRuleEntry2.addProperty("_identifier",
        new Schema<>().type("string").example("After Order Delivered"));

    Schema<Object> invoiceRuleEntry3 = new Schema<>();
    invoiceRuleEntry3.type("object");
    invoiceRuleEntry3.addProperty("id", new Schema<>().type("string").example("S"));
    invoiceRuleEntry3.addProperty("_identifier",
        new Schema<>().type("string").example("Customer Schedule After Delivery"));

    Schema<Object> invoiceRuleEntry4 = new Schema<>();
    invoiceRuleEntry4.type("object");
    invoiceRuleEntry4.addProperty("id", new Schema<>().type("string").example("N"));
    invoiceRuleEntry4.addProperty("_identifier",
        new Schema<>().type("string").example("Do Not Invoice"));

    Schema<Object> invoiceRuleEntry5 = new Schema<>();
    invoiceRuleEntry5.type("object");
    invoiceRuleEntry5.addProperty("id", new Schema<>().type("string").example("I"));
    invoiceRuleEntry5.addProperty("_identifier",
        new Schema<>().type("string").example("Immediate"));

    invoiceRuleEntriesSchema.addOneOfItem(invoiceRuleEntry1);
    invoiceRuleEntriesSchema.addOneOfItem(invoiceRuleEntry2);
    invoiceRuleEntriesSchema.addOneOfItem(invoiceRuleEntry3);
    invoiceRuleEntriesSchema.addOneOfItem(invoiceRuleEntry4);
    invoiceRuleEntriesSchema.addOneOfItem(invoiceRuleEntry5);

    invoiceRuleSchema.addProperty("entries", invoiceRuleEntriesSchema);
    columnValuesSchema.addProperty("InvoiceRule", invoiceRuleSchema);

    schema.addProperty("columnValues", columnValuesSchema);

    Schema<Object> auxiliaryInputValuesSchema = new Schema<>();
    auxiliaryInputValuesSchema.type("object");

    Schema<Object> docbasetypeSchema = new Schema<>();
    docbasetypeSchema.type("object");
    docbasetypeSchema.addProperty("value", new Schema<>().type("string").example("POO"));
    docbasetypeSchema.addProperty("classicValue", new Schema<>().type("string").example("POO"));
    auxiliaryInputValuesSchema.addProperty("DOCBASETYPE", docbasetypeSchema);

    Schema<Object> showAddPaymentSchema = new Schema<>();
    showAddPaymentSchema.type("object");
    auxiliaryInputValuesSchema.addProperty("showAddPayment", showAddPaymentSchema);

    Schema<Object> aprmOrderIsPaidSchema = new Schema<>();
    aprmOrderIsPaidSchema.type("object");
    auxiliaryInputValuesSchema.addProperty("APRM_OrderIsPaid", aprmOrderIsPaidSchema);

    schema.addProperty("auxiliaryInputValues", auxiliaryInputValuesSchema);

    Schema<Object> overwrittenAuxiliaryInputsSchema = new Schema<>();
    overwrittenAuxiliaryInputsSchema.type("array");
    overwrittenAuxiliaryInputsSchema.items(new Schema<>().type("string").example("ORDERTYPE"));
    schema.addProperty("overwrittenAuxiliaryInputs", overwrittenAuxiliaryInputsSchema);

    schema.addProperty("noteCount", new Schema<>().type("integer").example(0));

    schema.addProperty("attachmentCount", new Schema<>().type("integer").example(0));

    schema.addProperty("attachmentExists", new Schema<>().type("boolean").example(false));

    schema.required(
        Arrays.asList("hiddenInputs", "calloutMessages", "columnValues", "auxiliaryInputValues",
            "overwrittenAuxiliaryInputs", "noteCount", "attachmentCount", "attachmentExists"));

    return schema;
  }
}
