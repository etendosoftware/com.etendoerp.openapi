package com.etendoerp.openapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.openbravo.base.session.OBPropertiesProvider;
import org.openbravo.base.weld.WeldUtils;
import org.openbravo.base.weld.test.WeldBaseTest;
import org.openbravo.dal.core.OBContext;

import com.etendoerp.openapi.model.OpenAPIEndpoint;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.OpenAPI;

public class OpenAPIControllerTest extends WeldBaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OpenAPIController controller;
    private AutoCloseable mocks;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @Mock
    private OBPropertiesProvider propertiesProvider;

    private MockedStatic<OBPropertiesProvider> mockedPropertiesProvider;
    private MockedStatic<WeldUtils> mockedWeldUtils;

    @Before
    public void setUp() throws Exception {
        mocks = MockitoAnnotations.openMocks(this);
        controller = new OpenAPIController();

        // Mock OBPropertiesProvider
        mockedPropertiesProvider = mockStatic(OBPropertiesProvider.class);
        mockedPropertiesProvider.when(OBPropertiesProvider::getInstance).thenReturn(propertiesProvider);
        when(propertiesProvider.getOpenbravoProperties())
                .thenReturn(mock(java.util.Properties.class));

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        // Mock WeldUtils for OpenAPIEndpoint instances
        mockedWeldUtils = mockStatic(WeldUtils.class);
        List<OpenAPIEndpoint> endpoints = new ArrayList<>();
        mockedWeldUtils.when(() -> WeldUtils.getInstances(OpenAPIEndpoint.class))
                .thenReturn(endpoints);
    }

    @After
    public void tearDown() throws Exception {
        if (mockedPropertiesProvider != null) {
            mockedPropertiesProvider.close();
        }
        if (mockedWeldUtils != null) {
            mockedWeldUtils.close();
        }
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testDoGet_Success() throws Exception {
        // Given
        when(request.getParameter("tag")).thenReturn(null);
        when(request.getParameter("host")).thenReturn(null);

        // When
        controller.doGet("", request, response);

        // Then
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).getWriter();
    }

    @Test
    public void testGetOpenAPIJson_ValidContent() throws Exception {
        // When
        String json = controller.getOpenAPIJson(null, "http://test.com");

        // Then
        assertNotNull("OpenAPI JSON should not be null", json);
        
        // Validate JSON structure
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        
        assertTrue("Should contain info section", root.has("info"));
        assertTrue("Should contain servers section", root.has("servers"));
        assertTrue("Should contain components section", root.has("components"));
        assertTrue("Should contain security section", root.has("security"));
    }

    @Test
    public void testGetOpenAPIJson_WithTag() throws Exception {
        // Given
        String tag = "test";
        
        // When
        String json = controller.getOpenAPIJson(tag, "http://test.com");

        // Then
        assertNotNull("OpenAPI JSON should not be null", json);
        assertTrue("JSON should be properly formatted", json.contains("\"title\""));
    }

    @Test
    public void testGetOpenAPIJson_DefaultBaseUrl() throws Exception {
        // Given
        when(propertiesProvider.getOpenbravoProperties().getProperty("context.name"))
                .thenReturn("etendo");

        // When
        String json = controller.getOpenAPIJson(null, null);

        // Then
        assertNotNull("OpenAPI JSON should not be null", json);
        assertTrue("Should contain default base URL", 
                json.contains("http://localhost:8080/etendo"));
    }

    @Test
    public void testSecuritySchemes() throws Exception {
        // When
        String json = controller.getOpenAPIJson(null, "http://test.com");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // Then
        JsonNode components = root.get("components");
        JsonNode securitySchemes = components.get("securitySchemes");
        
        assertTrue("Should contain basicAuth scheme", securitySchemes.has("basicAuth"));
        assertTrue("Should contain bearerAuth scheme", securitySchemes.has("bearerAuth"));
        
        JsonNode basicAuth = securitySchemes.get("basicAuth");
        assertEquals("HTTP", basicAuth.get("type").asText());
        assertEquals("basic", basicAuth.get("scheme").asText());
        
        JsonNode bearerAuth = securitySchemes.get("bearerAuth");
        assertEquals("HTTP", bearerAuth.get("type").asText());
        assertEquals("bearer", bearerAuth.get("scheme").asText());
        assertEquals("JWT", bearerAuth.get("bearerFormat").asText());
    }

    @Test
    public void testUnsupportedOperations() throws Exception {
        // Test POST
        controller.doPost("", request, response);
        // No exception should be thrown

        // Test PUT
        controller.doPut("", request, response);
        // No exception should be thrown

        // Test DELETE
        controller.doDelete("", request, response);
        // No exception should be thrown
    }
}