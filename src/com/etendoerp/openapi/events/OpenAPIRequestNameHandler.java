/**
 * Copyright 2022-2023 Futit Services SL
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.etendoerp.openapi.events;

import com.etendoerp.openapi.data.OpenAPIRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.model.Entity;
import org.openbravo.base.model.ModelProvider;
import org.openbravo.client.kernel.event.EntityNewEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEventObserver;
import org.openbravo.client.kernel.event.EntityUpdateEvent;
import org.openbravo.erpCommon.utility.OBMessageUtils;

import javax.enterprise.event.Observes;

public class OpenAPIRequestNameHandler extends EntityPersistenceEventObserver {

  private static Entity[] entities = {
      ModelProvider.getInstance().getEntity(OpenAPIRequest.class) };
  private static final Logger logger = LogManager.getLogger();

  static final String INVALID_REQUEST_NAME = "ETAPI_InvalidName";

  @Override
  protected Entity[] getObservedEntities() {
    return entities;
  }

  public void onUpdate(@Observes EntityUpdateEvent event) {
    if (!isValidEvent(event)) {
      return;
    }
    validateEntityField((OpenAPIRequest) event.getTargetInstance());
  }

  public void onSave(@Observes EntityNewEvent event) {
    if (!isValidEvent(event)) {
      return;
    }
    validateEntityField((OpenAPIRequest) event.getTargetInstance());
  }

  void validateEntityField(OpenAPIRequest entityField) {
    if (!StringUtils.isAlpha(entityField.getName())) {
      logger.error("Invalid request name '{}'", entityField.getName());
      throw new OBException(OBMessageUtils.getI18NMessage(INVALID_REQUEST_NAME));
    }
  }

}
