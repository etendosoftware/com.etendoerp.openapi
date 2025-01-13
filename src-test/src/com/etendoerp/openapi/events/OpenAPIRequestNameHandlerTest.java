package com.etendoerp.openapi.events;

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

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Open api request name handler test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenAPIRequestNameHandlerTest {

    /**
     * The Expected exception.
     */
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

    private static final String INVALID_REQUEST_NAME_ERROR = "Invalid request name";

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
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

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        if (mockedModelProvider != null) {
            mockedModelProvider.close();
        }
        if (mockedOBMessageUtils != null) {
            mockedOBMessageUtils.close();
        }
    }

    /**
     * Test on update valid name.
     */
    @Test
    public void testOnUpdateValidName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("ValidName");

        // When
        handler.onUpdate(updateEvent);

        // Then
        verify(openAPIRequest, times(1)).getName();
    }

    /**
     * Test on update invalid name.
     */
    @Test
    public void testOnUpdateInvalidName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("Invalid123Name");
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onUpdate(updateEvent);
    }

    /**
     * Test on save valid name.
     */
    @Test
    public void testOnSaveValidName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("ValidName");

        // When
        handler.onSave(newEvent);

        // Then
        verify(openAPIRequest, times(1)).getName();
    }

    /**
     * Test on save invalid name.
     */
    @Test
    public void testOnSaveInvalidName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn("Invalid123Name");
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onSave(newEvent);
    }

    /**
     * Test on update null name.
     */
    @Test
    public void testOnUpdateNullName() {
        // Given
        when(updateEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn(null);
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onUpdate(updateEvent);
    }

    /**
     * Test on save empty name.
     */
    @Test
    public void testOnSaveEmptyName() {
        // Given
        when(newEvent.getTargetInstance()).thenReturn(openAPIRequest);
        when(openAPIRequest.getName()).thenReturn(null);
        expectedException.expect(OBException.class);
        expectedException.expectMessage(INVALID_REQUEST_NAME_ERROR);

        // When
        handler.onSave(newEvent);
    }
}