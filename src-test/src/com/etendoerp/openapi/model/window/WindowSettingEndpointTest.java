package com.etendoerp.openapi.model.window;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Window setting endpoint test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class WindowSettingEndpointTest {

    private WindowSettingEndpoint endpoint;
    
    @Mock
    private OpenAPI mockOpenAPI;
    
    @Mock
    private Paths mockPaths;

    private static final String PATHS_SHOULD_BE_CREATED = "Paths should be created";
    private static final String PARAMETERS_SHOULD_EXIST = "Parameters should exist";
    private static final String WINDOW_SETTINGS_ACTION_HANDLER_PATH = "/org.openbravo.client.kernel?stateless=true&_action=org.openbravo.client.application.WindowSettingsActionHandler";
    private static final String FORM_INIT_COMPONENT_NEW = "/org.openbravo.client.kernel?stateless=true&_action=org.openbravo.client.application.window.FormInitializationComponent&MODE=NEW";
    private static final String FORM_INIT_COMPONENT_CHANGE = "/org.openbravo.client.kernel?stateless=true&_action=org.openbravo.client.application.window.FormInitializationComponent&MODE=CHANGE";

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        endpoint = new WindowSettingEndpoint();
    }

    /**
     * Test is valid null tag.
     */
    @Test
    public void testIsValidNullTag() {
        // When
        boolean result = endpoint.isValid(null);

        // Then
        assertTrue("Should be valid for null tag", result);
    }

    /**
     * Test is valid valid tag.
     */
    @Test
    public void testIsValidValidTag() {
        // When
        boolean result = endpoint.isValid("Window Settings");

        // Then
        assertTrue("Should be valid for 'Window Settings' tag", result);
    }

    /**
     * Test is valid invalid tag.
     */
    @Test
    public void testIsValidInvalidTag() {
        // When
        boolean result = endpoint.isValid("Invalid Tag");

        // Then
        assertFalse("Should be invalid for unknown tag", result);
    }

    /**
     * Test add window settings endpoint.
     */
    @Test
    public void testAddWindowSettingsEndpoint() {
        // Given
        OpenAPI openAPI = new OpenAPI();
        
        // When
        endpoint.add(openAPI);
        
        // Then
        assertNotNull(PATHS_SHOULD_BE_CREATED, openAPI.getPaths());
        PathItem pathItem = openAPI.getPaths().get(WINDOW_SETTINGS_ACTION_HANDLER_PATH);
        assertNotNull("Window settings path should exist", pathItem);
        
        Operation operation = pathItem.getGet();
        assertNotNull("GET operation should exist", operation);
        
        List<Parameter> parameters = operation.getParameters();
        assertNotNull(PARAMETERS_SHOULD_EXIST, parameters);
        assertEquals("Should have 2 parameters", 2, parameters.size());
        
        Parameter actionParam = parameters.get(0);
        assertEquals("First parameter should be _action", "_action", actionParam.getName());
        assertTrue("_action parameter should be required", actionParam.getRequired());
        
        Parameter windowIdParam = parameters.get(1);
        assertEquals("Second parameter should be windowId", "windowId", windowIdParam.getName());
        assertTrue("windowId parameter should be required", windowIdParam.getRequired());
    }

    /**
     * Test add form init endpoint.
     */
    @Test
    public void testAddFormInitEndpoint() {
        // Given
        OpenAPI openAPI = new OpenAPI();
        
        // When
        endpoint.add(openAPI);
        
        // Then
        assertNotNull(PATHS_SHOULD_BE_CREATED, openAPI.getPaths());
        PathItem pathItem = openAPI.getPaths().get(FORM_INIT_COMPONENT_NEW);
        assertNotNull("Form init path should exist", pathItem);
        
        Operation operation = pathItem.getPost();
        assertNotNull("POST operation should exist", operation);
        
        List<Parameter> parameters = operation.getParameters();
        assertNotNull(PARAMETERS_SHOULD_EXIST, parameters);
        assertTrue("Should have parameters", parameters.size() > 0);
        
        assertNotNull("Request body should exist", operation.getRequestBody());
        assertTrue("Request body should be required", operation.getRequestBody().getRequired());
    }

    /**
     * Test add form change endpoint.
     */
    @Test
    public void testAddFormChangeEndpoint() {
        // Given
        OpenAPI openAPI = new OpenAPI();
        
        // When
        endpoint.add(openAPI);
        
        // Then
        assertNotNull(PATHS_SHOULD_BE_CREATED, openAPI.getPaths());
        PathItem pathItem = openAPI.getPaths().get(FORM_INIT_COMPONENT_CHANGE);
        assertNotNull("Form change path should exist", pathItem);
        
        Operation operation = pathItem.getPost();
        assertNotNull("POST operation should exist", operation);
        
        List<Parameter> parameters = operation.getParameters();
        assertNotNull(PARAMETERS_SHOULD_EXIST, parameters);
        assertTrue("Should have parameters", parameters.size() > 0);
        
        assertNotNull("Request body should exist", operation.getRequestBody());
        assertTrue("Request body should be required", operation.getRequestBody().getRequired());
    }

    /**
     * Test add components and schemas.
     */
    @Test
    public void testAddComponentsAndSchemas() {
        // Given
        OpenAPI openAPI = new OpenAPI();
        
        // When
        endpoint.add(openAPI);
        
        // Then
        assertNotNull("Components should be created", openAPI.getComponents());
        assertNotNull("Schemas should be created", openAPI.getComponents().getSchemas());
        
        Schema<?> windowSettingsResponseSchema = openAPI.getComponents().getSchemas().get("WindowSettingResponse");
        assertNotNull("WindowSettingResponse schema should exist", windowSettingsResponseSchema);
        
        Schema<?> formInitResponseSchema = openAPI.getComponents().getSchemas().get("FormInitResponse");
        assertNotNull("FormInitResponse schema should exist", formInitResponseSchema);
        
        Schema<?> formChangeResponseSchema = openAPI.getComponents().getSchemas().get("FormChangeResponse");
        assertNotNull("FormChangeResponse schema should exist", formChangeResponseSchema);
    }
}