package com.etendoerp.openapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import com.etendoerp.openapi.model.OpenAPIEndpoint;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Open api controller test class.
 */
public class OpenAPIControllerTest extends WeldBaseTest {

    /**
     * The Expected exception.
     */
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

    private static final String HOST_ADDRESS = "http://localhost:8080";
    private static final String TEST_URL = "http://test.com";
    private static final String OPENAPI_JSON_ERROR = "OpenAPI JSON should not be null";

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

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
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

    /**
     * Test do get success.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDoGetSuccess() throws Exception {
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

    /**
     * Test get open api json valid content.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetOpenAPIJsonValidContent() throws Exception {
        // When
        String json = controller.getOpenAPIJson(HOST_ADDRESS, null, TEST_URL);

        // Then
        assertNotNull(OPENAPI_JSON_ERROR, json);
        
        // Validate JSON structure
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        
        assertTrue("Should contain info section", root.has("info"));
        assertTrue("Should contain servers section", root.has("servers"));
        assertTrue("Should contain components section", root.has("components"));
        assertTrue("Should contain security section", root.has("security"));
    }

    /**
     * Test get open api json with tag.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetOpenAPIJsonWithTag() throws Exception {
        // Given
        String tag = "test";
        
        // When
        String json = controller.getOpenAPIJson(HOST_ADDRESS, tag, TEST_URL);

        // Then
        assertNotNull(OPENAPI_JSON_ERROR, json);
        assertTrue("JSON should be properly formatted", StringUtils.contains(json, "title"));
    }

    /**
     * Test get open api json default base url.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetOpenAPIJsonDefaultBaseUrl() throws Exception {
        // Given
        when(propertiesProvider.getOpenbravoProperties().getProperty("context.name"))
                .thenReturn("etendo");

        // When
        String json = controller.getOpenAPIJson(HOST_ADDRESS, null, null);

        // Then
        assertNotNull(OPENAPI_JSON_ERROR, json);
        assertTrue("Should contain default base URL", 
                StringUtils.contains(json, "http://localhost:8080/etendo"));
    }

    /**
     * Test security schemes.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSecuritySchemes() throws Exception {
        // When
        String json = controller.getOpenAPIJson(HOST_ADDRESS, null, TEST_URL);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // Then
        JsonNode components = root.get("components");
        JsonNode securitySchemes = components.get("securitySchemes");
        
        assertTrue("Should contain basicAuth scheme", securitySchemes.has("basicAuth"));
        assertTrue("Should contain bearerAuth scheme", securitySchemes.has("bearerAuth"));
        
        JsonNode basicAuth = securitySchemes.get("basicAuth");
        assertEquals("http", basicAuth.get("type").asText());
        assertEquals("basic", basicAuth.get("scheme").asText());
        
        JsonNode bearerAuth = securitySchemes.get("bearerAuth");
        assertEquals("http", bearerAuth.get("type").asText());
        assertEquals("bearer", bearerAuth.get("scheme").asText());
        assertEquals("JWT", bearerAuth.get("bearerFormat").asText());
    }

    /**
     * Test unsupported operations.
     *
     * @throws Exception the exception
     */
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