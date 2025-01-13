package com.etendoerp.openapi.model.jobs;

import com.ctc.wstx.util.StringUtil;
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


@RunWith(MockitoJUnitRunner.class)
public class JobsAndActionsEndpointTest {

    @InjectMocks
    private JobsAndActionsEndpoint jobsAndActionsEndpoint;

    private OpenAPI openAPI;

    private final String PROCESS_ORDER_ENDPOINT = "/etendo/org.openbravo.client.kernel?_action=com.smf.jobs.defaults.ProcessOrders";
    private final String ACTION = "_action";

    @Before
    public void setUp() {
        openAPI = new OpenAPI();
    }

    @Test
    public void test_isValid_withNullTag_returnsTrue() {
        // When
        boolean result = jobsAndActionsEndpoint.isValid(null);

        // Then
        assertTrue("Should return true for null tag", result);
    }

    @Test
    public void test_isValid_withValidTag_returnsTrue() {
        // When
        boolean result = jobsAndActionsEndpoint.isValid("Jobs and Actions");

        // Then
        assertTrue("Should return true for valid tag", result);
    }

    @Test
    public void test_isValid_withInvalidTag_returnsFalse() {
        // When
        boolean result = jobsAndActionsEndpoint.isValid("Invalid Tag");

        // Then
        assertFalse("Should return false for invalid tag", result);
    }

    @Test
    public void test_add_createsValidOpenAPISpecification() {
        // When
        jobsAndActionsEndpoint.add(openAPI);

        // Then
        // Verify paths are created
        assertNotNull("Paths should not be null", openAPI.getPaths());
        assertTrue("Should contain ProcessOrdersDefaults path", 
            openAPI.getPaths().containsKey("/etendo/org.openbravo.client.kernel?_action=com.smf.jobs.defaults.ProcessOrdersDefaults"));
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
            .get("/etendo/org.openbravo.client.kernel?_action=com.smf.jobs.defaults.ProcessOrdersDefaults");
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

    @Test
    public void test_add_createsValidProcessOrdersRequestSchema() {
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

    @Test
    public void test_add_createsValidProcessOrdersResponseSchema() {
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

    @Test
    public void test_add_processOrdersIncludesRequiredParameters() {
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