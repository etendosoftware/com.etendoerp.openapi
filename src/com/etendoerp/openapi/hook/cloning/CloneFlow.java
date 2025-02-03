package com.etendoerp.openapi.hook.cloning;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.collection.internal.PersistentBag;
import org.openbravo.base.model.Property;
import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.client.kernel.ComponentProvider.Qualifier;
import org.openbravo.dal.service.OBDal;

import com.etendoerp.openapi.data.OpenApiFlow;
import com.smf.jobs.hooks.CloneRecordHook;


/**
 * CloneFlow class for handling the cloning of OpenApiFlow records.
 * <p>
 * This class extends CloneRecordHook and provides custom implementations for
 * pre-copy, post-copy, and child record handling during the cloning process.
 */
@ApplicationScoped
@Qualifier(OpenApiFlow.ENTITY_NAME)
public class CloneFlow extends CloneRecordHook {

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
    OpenApiFlow originalFlow = (OpenApiFlow) originalRecord;
    OpenApiFlow cloneFlow = (OpenApiFlow) newRecord;
    cloneFlow.setName("Copy of " + originalFlow.getName());
    cloneFlow.setModule(null);

    OBDal.getInstance().save(cloneFlow);
    OBDal.getInstance().flush();
    removeModuleInClonedChildren(cloneFlow);

    OBDal.getInstance().refresh(cloneFlow);
    return cloneFlow;
  }

  /**
   * Removes the module property from all cloned child records.
   * <p>
   * This method iterates over all one-to-many properties of the given cloned flow object.
   * For each child object, if it has a "module" property, the property is set to null and the child is saved.
   *
   * @param cloneFlow
   *     The cloned flow object whose child records' module property should be removed.
   */
  static void removeModuleInClonedChildren(BaseOBObject cloneFlow) {
    List<Property> props = cloneFlow.getEntity().getProperties();
    for (Property prop : props) {
      if (prop.isOneToMany()) {
        PersistentBag clonedChildren = (PersistentBag) cloneFlow.get(prop.getName());
        for (Object child : clonedChildren) {
          BaseOBObject clonedChild = (BaseOBObject) child;
          // if the child has a property "module", set it to null
          if (clonedChild.getEntity().hasProperty("module")) {
            clonedChild.set("module", null);
            OBDal.getInstance().save(clonedChild);
          }
        }
        OBDal.getInstance().flush();
      }
    }
  }
}
