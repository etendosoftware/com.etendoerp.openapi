package com.etendoerp.openapi.events;

import com.etendoerp.copilot.eventhandler.AssistantKBSyncStatusHandler;
import com.etendoerp.openapi.data.OpenAPIRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.model.Entity;
import org.openbravo.base.model.ModelProvider;
import org.openbravo.client.kernel.event.EntityNewEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEvent;
import org.openbravo.client.kernel.event.EntityUpdateEvent;
import org.openbravo.erpCommon.utility.OBMessageUtils;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OpenAPIRequestNameHandlerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private EntityUpdateEvent updateEvent;

    @Mock
    private EntityNewEvent newEvent;

    @Mock
    private OpenAPIRequest openAPIRequest;

    @Mock
    private ModelProvider modelProvider;

    @Mock
    private Entity entity;

    private OpenAPIRequestNameHandler handler;
    private MockedStatic<ModelProvider> mockedModelProvider;
    private MockedStatic<OBMessageUtils> mockedOBMessageUtils;
    private Method isValidEventMethod;

    private final String INVALID_REQUEST_NAME_ERROR = "Invalid request name";

    @Before
    public void setUp() throws Exception {
        handler = new OpenAPIRequestNameHandler() {
            protected boolean isValidEvent(EntityPersistenceEvent event) {
                return true;
            }
        };
        mockedModelProvider = mockStatic(ModelProvider.class);
        mockedModelProvider.when(ModelProvider::getInstance).thenReturn(modelProvider);

        mockedOBMessageUtils = mockStatic(OBMessageUtils.class);
        mockedOBMessageUtils.when(() -> OBMessageUtils.getI18NMessage(OpenAPIRequestNameHandler.INVALID_REQUEST_NAME))
                .thenReturn(INVALID_REQUEST_NAME_ERROR);
        // Prepare reflection for isValidEvent if needed
        isValidEventMethod = OpenAPIRequestNameHandler.class.getSuperclass().getDeclaredMethod("isValidEvent", EntityPersistenceEvent.class);
        isValidEventMethod.setAccessible(true);
    }

    @After
    public void tearDown() {
        if (mockedModelProvider != null) {
            mockedModelProvider.close();
        }
        if (mockedOBMessageUtils != null) {
            mockedOBMessageUtils.close();
        }
    }

    @Test
    public void testOnUpdate_ValidName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("ValidName");

        // When
        handler.onUpdate(updateEvent);

        // Then
        verify(openAPIRequest, times(1)).getName();
    }

    @Test
    public void testOnUpdate_InvalidName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("Invalid123Name");
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onUpdate(updateEvent);
    }

    @Test
    public void testOnSave_ValidName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("ValidName");

        // When
        handler.onSave(newEvent);

        // Then
        verify(openAPIRequest, times(1)).getName();
    }

    @Test
    public void testOnSave_InvalidName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("Invalid123Name");
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onSave(newEvent);
    }

    @Test
    public void testOnUpdate_NullName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn(null);
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onUpdate(updateEvent);
    }

    @Test
    public void testOnSave_EmptyName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn(null);
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onSave(newEvent);
    }
}