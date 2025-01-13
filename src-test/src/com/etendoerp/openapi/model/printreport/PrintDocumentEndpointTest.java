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
 * Print document endpoint test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrintDocumentEndpointTest {

    @InjectMocks
    private PrintDocumentEndpoint printDocumentEndpoint;

    @Mock
    private OpenAPI mockOpenAPI;

    @Mock
    private Paths mockPaths;

    private OpenAPI openAPI;

    private final String PRINT_REPORT = "Print Report";

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
    public void testIsValid_withNullTag_returnsTrue() {
        assertTrue(printDocumentEndpoint.isValid(null));
    }

    /**
     * Test is valid with valid tag returns true.
     */
    @Test
    public void testIsValid_withValidTag_returnsTrue() {
        assertTrue(printDocumentEndpoint.isValid(PRINT_REPORT));
    }

    /**
     * Test is valid with invalid tag returns false.
     */
    @Test
    public void testIsValid_withInvalidTag_returnsFalse() {
        assertFalse(printDocumentEndpoint.isValid("Invalid Tag"));
    }

    /**
     * Test add adds correct path item.
     */
    @Test
    public void testAdd_addsCorrectPathItem() {
        // Given
        openAPI.setPaths(new Paths());

        // When
        printDocumentEndpoint.add(openAPI);

        // Then
        PathItem pathItem = openAPI.getPaths().get("/etendo/orders/PrintOptions.html?stateless=true");
        assertNotNull(pathItem);
        assertNotNull(pathItem.getPost());

        Operation operation = pathItem.getPost();
        assertEquals("(Require changes in backend) Initializes print options for an order", operation.getSummary());
        assertTrue(operation.getTags().contains(PRINT_REPORT));

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
        MediaType mediaType = content.get("application/x-www-form-urlencoded");
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
    public void testAdd_addsCorrectTags() {
        // When
        printDocumentEndpoint.add(openAPI);

        // Then
        List<Tag> tags = openAPI.getTags();
        assertNotNull(tags);
        assertFalse(tags.isEmpty());
        
        boolean foundPrintReportTag = false;
        for (Tag tag : tags) {
            if (StringUtils.equals(PRINT_REPORT,tag.getName())) {
                foundPrintReportTag = true;
                assertEquals("Endpoints related to printing reports and documents.", tag.getDescription());
                break;
            }
        }
        assertTrue("Print Report tag should be present", foundPrintReportTag);
    }

    /**
     * Test add adds correct schema.
     */
    @Test
    public void testAdd_addsCorrectSchema() {
        // When
        printDocumentEndpoint.add(openAPI);

        // Then
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSchemas());
        assertTrue(openAPI.getComponents().getSchemas().containsKey("PrintDocumentResponse"));

        Schema<?> schema = openAPI.getComponents().getSchemas().get("PrintDocumentResponse");
        assertEquals("string", schema.getType());
        assertEquals("html", schema.getFormat());
    }
}