package com.etendoerp.openapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.openbravo.client.kernel.BaseComponentProvider;
import org.openbravo.client.kernel.Component;
import org.openbravo.client.kernel.ComponentProvider;

@ApplicationScoped
@ComponentProvider.Qualifier(OpenApiProvider.ETAPI_ComponentProvider)
public class OpenApiProvider extends BaseComponentProvider {
    public static final String ETAPI_ComponentProvider = "ETAPI_ComponentProvider";

    @Override
    public Component getComponent(String componentId, Map<String, Object> parameters) {
        throw new IllegalArgumentException("Component id " + componentId + " not supported.");
    }

    @Override
    public List<ComponentResource> getGlobalComponentResources() {
        final List<ComponentResource> globalResources = new ArrayList<>();
        globalResources.add(createStaticResource(
                "web/com.etendoerp.openapi/js/etapi-swagger.js", false
        ));
        return globalResources;
    }

}
