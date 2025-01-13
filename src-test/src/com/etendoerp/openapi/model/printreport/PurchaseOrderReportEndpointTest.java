package com.etendoerp.openapi.model.printreport;

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
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Purchase order report endpoint test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderReportEndpointTest {

    @InjectMocks
    private PurchaseOrderReportEndpoint purchaseOrderReportEndpoint;

    @Mock
    private OpenAPI mockOpenAPI;

    @Mock
    private Paths mockPaths;

    private OpenAPI openAPI;

    private static final String JOBS_AND_ACTIONS = "Jobs and Actions";
    private static final String OBJECT = "object";

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        openAPI = new OpenAPI();
    }

    /**
     * Test is valid with null tag returns true.
     */
    @Test
    public void testIsValidWithNullTagReturnsTrue() {
        assertTrue(purchaseOrderReportEndpoint.isValid(null));
    }

    /**
     * Test is valid with valid tag returns true.
     */
    @Test
    public void testIsValidWithValidTagReturnsTrue() {
        assertTrue(purchaseOrderReportEndpoint.isValid(JOBS_AND_ACTIONS));
    }

    /**
     * Test is valid with invalid tag returns false.
     */
    @Test
    public void testIsValidWithInvalidTagReturnsFalse() {
        assertFalse(purchaseOrderReportEndpoint.isValid("Invalid Tag"));
    }

    /**
     * Test add adds correct endpoints.
     */
    @Test
    public void testAddCorrectEndpoints() {
        // Given
        openAPI.setPaths(new Paths());

        // When
        purchaseOrderReportEndpoint.add(openAPI);

        // Then
        // Verify BaseReportActionHandler endpoint
        String baseReportPath = "/etendo/org.openbravo.client.kernel?_action=BaseReportActionHandler";
        PathItem baseReportPathItem = openAPI.getPaths().get(baseReportPath);
        assertNotNull(baseReportPathItem);
        verifyEndpoint(baseReportPathItem);

        // Verify BaseReportActionHandler&mode=DOWNLOAD endpoint
        String downloadPath = "/etendo/org.openbravo.client.kernel?_action=BaseReportActionHandler&mode=DOWNLOAD";
        PathItem downloadPathItem = openAPI.getPaths().get(downloadPath);
        assertNotNull(downloadPathItem);
        verifyEndpoint(downloadPathItem);
    }

    private void verifyEndpoint(PathItem pathItem) {
        assertNotNull(pathItem.getPost());
        Operation operation = pathItem.getPost();

        // Verify operation details
        assertEquals("Generates reports based on provided parameters", operation.getSummary());
        assertTrue(operation.getTags().contains(JOBS_AND_ACTIONS));

        // Verify parameters
        List<Parameter> parameters = operation.getParameters();
        assertNotNull(parameters);
        assertFalse(parameters.isEmpty());

        // Verify request body
        RequestBody requestBody = operation.getRequestBody();
        assertNotNull(requestBody);
        assertTrue(requestBody.getRequired());
        Content content = requestBody.getContent();
        assertNotNull(content);
        MediaType mediaType = content.get("application/json");
        assertNotNull(mediaType);

        // Verify responses
        Map<String, ApiResponse> responses = operation.getResponses();
        assertNotNull(responses);
        assertTrue(responses.containsKey("200"));
        assertTrue(responses.containsKey("400"));
        assertTrue(responses.containsKey("500"));
    }

    /**
     * Test add adds correct tags.
     */
    @Test
    public void testAddCorrectTags() {
        // When
        purchaseOrderReportEndpoint.add(openAPI);

        // Then
        List<Tag> tags = openAPI.getTags();
        assertNotNull(tags);
        assertFalse(tags.isEmpty());
        
        boolean foundJobsAndActionsTag = false;
        for (Tag tag : tags) {
            if (StringUtils.equals(JOBS_AND_ACTIONS, tag.getName())) {
                foundJobsAndActionsTag = true;
                assertEquals("Endpoints related to jobs and actions.", tag.getDescription());
                break;
            }
        }
        assertTrue("Jobs and Actions tag should be present", foundJobsAndActionsTag);
    }

    /**
     * Test add adds correct schemas.
     */
    @Test
    public void testAddCorrectSchemas() {
        // When
        purchaseOrderReportEndpoint.add(openAPI);

        // Then
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSchemas());
        
        // Verify BaseReportActionHandlerResponse schema
        assertTrue(openAPI.getComponents().getSchemas().containsKey("BaseReportActionHandlerResponse"));
        Schema<?> baseReportSchema = openAPI.getComponents().getSchemas().get("BaseReportActionHandlerResponse");
        assertEquals(OBJECT, baseReportSchema.getType());
        
        // Verify BaseReportActionHandler&mode=DOWNLOADResponse schema
        assertTrue(openAPI.getComponents().getSchemas().containsKey("BaseReportActionHandler&mode=DOWNLOADResponse"));
        Schema<?> downloadSchema = openAPI.getComponents().getSchemas().get("BaseReportActionHandler&mode=DOWNLOADResponse");
        assertEquals(OBJECT, downloadSchema.getType());
    }

    /**
     * Test add request schema has correct structure.
     */
    @Test
    public void testAddRequestSchemaHasCorrectStructure() {
        // Given
        openAPI.setPaths(new Paths());

        // When
        purchaseOrderReportEndpoint.add(openAPI);

        // Then
        PathItem pathItem = openAPI.getPaths().get("/etendo/org.openbravo.client.kernel?_action=BaseReportActionHandler");
        assertNotNull(pathItem);
        
        Operation operation = pathItem.getPost();
        RequestBody requestBody = operation.getRequestBody();
        MediaType mediaType = requestBody.getContent().get("application/json");
        Schema<?> schema = mediaType.getSchema();
        
        // Verify schema structure
        assertEquals(OBJECT, schema.getType());
        assertNotNull(schema.getProperties().get("_buttonValue"));
        assertNotNull(schema.getProperties().get("_params"));
        
        // Verify _params structure
        Schema<?> paramsSchema = (Schema<?>) schema.getProperties().get("_params");
        assertEquals(OBJECT, paramsSchema.getType());
        assertNotNull(paramsSchema.getProperties().get("AD_Org_ID"));
        assertNotNull(paramsSchema.getProperties().get("C_BPartner_ID"));
        assertNotNull(paramsSchema.getProperties().get("C_Currency_ID"));
        assertNotNull(paramsSchema.getProperties().get("DateFrom"));
        assertNotNull(paramsSchema.getProperties().get("DateTo"));
        assertNotNull(paramsSchema.getProperties().get("C_Project_ID"));
        assertNotNull(paramsSchema.getProperties().get("M_Warehouse_ID"));
        assertNotNull(paramsSchema.getProperties().get("Status"));
    }
}