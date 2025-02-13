package com.etendoerp.openapi.model.jobs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.tags.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Jobs and actions endpoint test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsAndActionsEndpointTest {

    @InjectMocks
    private JobsAndActionsEndpoint jobsAndActionsEndpoint;

    private OpenAPI openAPI;

    private static final String PROCESS_ORDER_ENDPOINT = "/sws/com.smf.securewebservices.kernel/org.openbravo.client.kernel?_action=com.smf.jobs.defaults.ProcessOrders";
    private static final String PROCESS_ORDER_DEFAULTS_ENDPOINT = "/sws/com.smf.securewebservices.kernel/org.openbravo.client.kernel?_action=com.smf.jobs.defaults.ProcessOrdersDefaults";
    private static final String ACTION = "_action";

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
        // When
        boolean result = jobsAndActionsEndpoint.isValid(null);

        // Then
        assertTrue("Should return true for null tag", result);
    }

    /**
     * Test is valid with valid tag returns true.
     */
    @Test
    public void testIsValidWithValidTagReturnsTrue() {
        // When
        boolean result = jobsAndActionsEndpoint.isValid("Jobs and Actions");

        // Then
        assertTrue("Should return true for valid tag", result);
    }

    /**
     * Test is valid with invalid tag returns false.
     */
    @Test
    public void testIsValidWithInvalidTagReturnsFalse() {
        // When
        boolean result = jobsAndActionsEndpoint.isValid("Invalid Tag");

        // Then
        assertFalse("Should return false for invalid tag", result);
    }

    /**
     * Test add creates valid open api specification.
     */
    @Test
    public void testAddCreatesValidOpenAPISpecification() {
        // When
        jobsAndActionsEndpoint.add(openAPI);

        // Then
        // Verify paths are created
        assertNotNull("Paths should not be null", openAPI.getPaths());
        assertTrue("Should contain ProcessOrdersDefaults path", 
            openAPI.getPaths().containsKey(PROCESS_ORDER_DEFAULTS_ENDPOINT));
        assertTrue("Should contain ProcessOrders path", 
            openAPI.getPaths().containsKey(PROCESS_ORDER_ENDPOINT));

        // Verify tags are created
        assertNotNull("Tags should not be null", openAPI.getTags());
        List<Tag> tags = openAPI.getTags();
        boolean hasJobsAndActionsTag = tags.stream()
            .anyMatch(tag -> tag.getName().equals("Jobs and Actions"));
        assertTrue("Should contain Jobs and Actions tag", hasJobsAndActionsTag);

        // Verify ProcessOrdersDefaults endpoint
        PathItem processOrdersDefaultsPath = openAPI.getPaths()
            .get(PROCESS_ORDER_DEFAULTS_ENDPOINT);
        Operation processOrdersDefaultsOperation = processOrdersDefaultsPath.getPost();
        assertNotNull("ProcessOrdersDefaults operation should not be null", processOrdersDefaultsOperation);
        
        // Verify required parameters
        List<Parameter> parameters = processOrdersDefaultsOperation.getParameters();
        assertTrue("Should have _action parameter", 
            parameters.stream().anyMatch(p -> p.getName().equals(ACTION)));

        // Verify responses
        ApiResponse successResponse = processOrdersDefaultsOperation.getResponses().get("200");
        assertNotNull("Should have 200 response", successResponse);
        ApiResponse badRequestResponse = processOrdersDefaultsOperation.getResponses().get("400");
        assertNotNull("Should have 400 response", badRequestResponse);
        ApiResponse serverErrorResponse = processOrdersDefaultsOperation.getResponses().get("500");
        assertNotNull("Should have 500 response", serverErrorResponse);
    }

    /**
     * Test add creates valid process orders request schema.
     */
    @Test
    public void testAddCreatesValidProcessOrdersRequestSchema() {
        // When
        jobsAndActionsEndpoint.add(openAPI);

        // Then
        PathItem processOrdersPath = openAPI.getPaths()
            .get(PROCESS_ORDER_ENDPOINT);
        Operation processOrdersOperation = processOrdersPath.getPost();
        
        // Verify request body schema
        Schema<?> requestSchema = processOrdersOperation.getRequestBody()
            .getContent()
            .get("application/json")
            .getSchema();
        
        assertNotNull("Request schema should not be null", requestSchema);
        assertEquals("Schema type should be object", "object", requestSchema.getType());
        
        // Verify required fields
        List<String> required = requestSchema.getRequired();
        assertTrue("recordIds should be required", required.contains("recordIds"));
        assertTrue("_buttonValue should be required", required.contains("_buttonValue"));
        assertTrue("_params should be required", required.contains("_params"));
        assertTrue("_entityName should be required", required.contains("_entityName"));
    }

    /**
     * Test add creates valid process orders response schema.
     */
    @Test
    public void testAddCreatesValidProcessOrdersResponseSchema() {
        // When
        jobsAndActionsEndpoint.add(openAPI);

        // Then
        PathItem processOrdersPath = openAPI.getPaths()
            .get(PROCESS_ORDER_ENDPOINT);
        Operation processOrdersOperation = processOrdersPath.getPost();
        
        // Verify response schema
        Schema<?> responseSchema = processOrdersOperation.getResponses()
            .get("200")
            .getContent()
            .get("application/json")
            .getSchema();
        
        assertNotNull("Response schema should not be null", responseSchema);
        assertEquals("Schema type should be object", "object", responseSchema.getType());
        
        // Verify required fields
        List<String> required = responseSchema.getRequired();
        assertTrue("responseActions should be required", required.contains("responseActions"));
        assertTrue("refreshParent should be required", required.contains("refreshParent"));
    }

    /**
     * Test add process orders includes required parameters.
     */
    @Test
    public void testAddProcessOrdersIncludesRequiredParameters() {
        // When
        jobsAndActionsEndpoint.add(openAPI);

        // Then
        PathItem processOrdersPath = openAPI.getPaths()
            .get(PROCESS_ORDER_ENDPOINT);
        Operation processOrdersOperation = processOrdersPath.getPost();
        
        List<Parameter> parameters = processOrdersOperation.getParameters();
        
        // Verify all required parameters are present
        assertTrue("Should have _action parameter", 
            parameters.stream().anyMatch(p -> p.getName().equals(ACTION)));
        assertTrue("Should have processId parameter", 
            parameters.stream().anyMatch(p -> p.getName().equals("processId")));
        assertTrue("Should have reportId parameter", 
            parameters.stream().anyMatch(p -> p.getName().equals("reportId")));
        assertTrue("Should have windowId parameter", 
            parameters.stream().anyMatch(p -> p.getName().equals("windowId")));
        
        // Verify parameters are required
        assertTrue("_action should be required", 
            parameters.stream().filter(p -> p.getName().equals(ACTION))
                .findFirst()
                .map(Parameter::getRequired)
                .orElse(false));
        assertTrue("processId should be required", 
            parameters.stream().filter(p -> p.getName().equals("processId"))
                .findFirst()
                .map(Parameter::getRequired)
                .orElse(false));
    }
}