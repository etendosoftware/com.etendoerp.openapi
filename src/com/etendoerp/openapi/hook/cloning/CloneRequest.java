package com.etendoerp.openapi.hook.cloning;

import static com.etendoerp.openapi.hook.cloning.CloneFlow.removeModuleInClonedChildren;

import javax.enterprise.context.ApplicationScoped;

import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.client.kernel.ComponentProvider;
import org.openbravo.dal.service.OBDal;

import com.etendoerp.openapi.data.OpenAPIRequest;
import com.smf.jobs.hooks.CloneRecordHook;

/**
 * CloneRequest class for handling the cloning of OpenAPIRequest records.
 * <p>
 * This class extends CloneRecordHook and provides custom implementations for
 * pre-copy, post-copy, and child record handling during the cloning process.
 */
@ApplicationScoped
@ComponentProvider.Qualifier(OpenAPIRequest.ENTITY_NAME)
public class CloneRequest extends CloneRecordHook {

  /**
   * Determines whether to copy child records.
   * <p>
   * This method always returns true, indicating that child records should be copied.
   *
   * @param uiCopyChildren
   *     A boolean indicating if the UI requests to copy children.
   * @return true, indicating that child records should be copied.
   */
  @Override
  public boolean shouldCopyChildren(boolean uiCopyChildren) {
    return true;
  }

  /**
   * Pre-copy hook.
   * <p>
   * This method is called before the original record is copied. It returns the original record without modifications.
   *
   * @param originalRecord
   *     The original record to be copied.
   * @return The original record.
   */
  @Override
  public BaseOBObject preCopy(BaseOBObject originalRecord) {
    return originalRecord;
  }

  /**
   * Post-copy hook.
   * <p>
   * This method is called after the original record is copied. It modifies the cloned record by setting a new name and nullifying the module.
   * The cloned record is then saved, flushed, and refreshed in the database.
   *
   * @param originalRecord
   *     The original record that was copied.
   * @param newRecord
   *     The newly created cloned record.
   * @return The modified cloned record.
   */
  @Override
  public BaseOBObject postCopy(BaseOBObject originalRecord, BaseOBObject newRecord) {
    OpenAPIRequest originalReq = (OpenAPIRequest) originalRecord;
    OpenAPIRequest cloneReq = (OpenAPIRequest) newRecord;
    cloneReq.setName("Copy of " + originalReq.getName());
    cloneReq.setModule(null);

    OBDal.getInstance().save(cloneReq);
    OBDal.getInstance().flush();
    removeModuleInClonedChildren(cloneReq);

    OBDal.getInstance().refresh(cloneReq);

    return cloneReq;
  }
}