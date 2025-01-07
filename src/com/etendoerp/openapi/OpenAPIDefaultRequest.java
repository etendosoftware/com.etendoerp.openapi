package com.etendoerp.openapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;

import com.etendoerp.openapi.data.OpenAPIRequest;
import com.etendoerp.openapi.data.OpenApiFlow;
import com.etendoerp.openapi.data.OpenApiFlowPoint;
import com.etendoerp.openapi.model.OpenAPIEndpoint;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Abstract class that adds OpenAPI documentation for the default endpoints. A default endpoint is an endpoint that is
 * not documented by another OpenAPIEndpoint implementation. This abstract class adds the endpoints to the "Default" tag
 * and to the tags of the flows where the endpoints are related.
 */
public abstract class OpenAPIDefaultRequest implements OpenAPIEndpoint {
  public final static String DEFAULT_TAG = "Default";

  protected abstract Class<?>[] getClasses();

  protected abstract String getEndpointPath();

  private static final Logger log = Logger.getLogger(OpenAPIDefaultRequest.class);

  private ThreadLocal<String> requestedTag = new ThreadLocal<>();


  /**
   * Retrieves a list of OpenApiFlow objects.
   *
   * @return a list of OpenApiFlow objects.
   */
  private List<OpenApiFlow> getFlows() {
    return OBDal.getInstance().createCriteria(OpenApiFlow.class).list();
  }

  /**
   * Retrieves a list of tags, this tags are the tags where this class will add the endpoints. In general, this list is
   * a list of the flows where the endpoints added by this class are related. Addionally, a "generic" tag is added to
   * allow the user to see the endpoints of this class.
   *
   * @return a list of tags.
   */
  private List<String> getTags() {
    Set<String> tags = getFlows().stream().filter(this::flowHasDefaultRequests).map(OpenApiFlow::getName).collect(
        Collectors.toSet());
    tags.add(DEFAULT_TAG);
    return tags.stream().sorted().collect(Collectors.toList());
  }

  /**
   * Checks if the given OpenApiFlow has any default requests.
   * <p>
   * This method iterates over the list of OpenApiFlowPoint objects associated with the given OpenApiFlow,
   * retrieves the OpenAPIRequest for each flow point, and checks if any of the requests match the classes
   * defined in this class.
   *
   * @param flow
   *     the OpenApiFlow object to check for default requests
   * @return true if any of the OpenAPIRequest objects match the classes, false otherwise
   */
  private boolean flowHasDefaultRequests(OpenApiFlow flow) {
    return flow.getETAPIOpenApiFlowPointList().stream()
        .map(OpenApiFlowPoint::getEtapiOpenapiReq)
        .anyMatch(this::matchesClass);
  }

  /**
   * Checks if the given OpenAPIRequest matches any class in the list of classes.
   * <p>
   * This method iterates over the classes and checks if the class name matches the classname of the provided OpenAPIRequest.
   *
   * @param etapiOpenapiReq
   *     the OpenAPIRequest object to check
   * @return true if the classname of the OpenAPIRequest matches any class name, false otherwise
   */
  private boolean matchesClass(OpenAPIRequest etapiOpenapiReq) {
    return Arrays.stream(getClasses())
        .anyMatch(clazz -> StringUtils.equalsIgnoreCase(etapiOpenapiReq.getClassname(), clazz.getName()));
  }

  /**
   * Check if the requested tag, if any, is contained in the list of valid tags where this class can add endpoints.
   *
   * @param tag
   *     the tag to check.
   * @return true if the tag is valid, false otherwise.
   */
  @Override
  public boolean isValid(String tag) {
    try {
      OBContext.setAdminMode();
      if (tag == null) {
        return true;
      }
      if (getTags().contains(tag)) {
        requestedTag.set(tag);
        return true;
      }
      return false;
    } finally {
      OBContext.restorePreviousMode();
    }
  }

  /**
   * Adds OpenAPI documentation for the requested tags.
   *
   * @param openAPI
   *     the OpenAPI object to add documentation to.
   */
  @Override
  public void add(OpenAPI openAPI) {
    try {
      OBContext.setAdminMode();
      HashMap<String, String> descriptions = new HashMap<>();
      AtomicBoolean addedEndpoints = new AtomicBoolean(false);
      getFlows().forEach(flow -> {
        if (requestedTag.get() == null || StringUtils.equals(requestedTag.get(), flow.getName())) {
          var endpoints = flow.getETAPIOpenApiFlowPointList();
          for (OpenApiFlowPoint endpoint : endpoints) {
            OpenAPIRequest etapiOpenapiReq = endpoint.getEtapiOpenapiReq();
            if (isDefaultType(etapiOpenapiReq)) {
              addedEndpoints.set(true);
              if (StringUtils.isNotEmpty(etapiOpenapiReq.getDescription())) {
                descriptions.put(etapiOpenapiReq.getName(), etapiOpenapiReq.getDescription());
              }
              addDefinition(openAPI, endpoint);
            }
          }
          Tag tag = new Tag().name(flow.getName()).description(flow.getDescription());
          if (openAPI.getTags() == null) {
            openAPI.setTags(new ArrayList<>());
          }
          openAPI.getTags().add(tag);
        }
      });
    } finally {
      requestedTag.remove();
      OBContext.restorePreviousMode();
    }
  }

  /**
   * Checks if the given OpenAPIRequest is of type "DEF".
   * <p>
   * This method compares the type of the provided OpenAPIRequest with the string "DEF" using a case-insensitive comparison.
   *
   * @param etapiOpenapiReq
   *     the OpenAPIRequest object to check
   * @return true if the type of the OpenAPIRequest is "DEF", false otherwise
   */
  private static boolean isDefaultType(OpenAPIRequest etapiOpenapiReq) {
    return StringUtils.equalsIgnoreCase(etapiOpenapiReq.getType(), "DEF");
  }


  /**
   * Adds a definition to the OpenAPI object.
   *
   * @param openAPI
   *     the OpenAPI object to add the definition to.
   * @param endpoint
   */
  private void addDefinition(OpenAPI openAPI, OpenApiFlowPoint endpoint) {

    Paths paths = openAPI.getPaths();
    if (paths == null) {
      paths = new Paths();
      openAPI.setPaths(paths);
    }
    //check if path already exists
    if (paths.get(getEndpointPath()) == null) {
      paths.addPathItem(getEndpointPath(), new io.swagger.v3.oas.models.PathItem());
    }
    io.swagger.v3.oas.models.PathItem pathItem = paths.get(getEndpointPath());

    if (endpoint.isPost()) {
      Operation postOp = getPOSTEndpoint();
      pathItem.setPost(postOp);
    }
    if (endpoint.isGet()) {
      Operation getOp = getGETEndpoint();
      pathItem.setGet(getOp);
    }

    if (endpoint.isPut()) {
      Operation putOp = getPUTEndpoint();
      pathItem.setPut(putOp);
    }

  }

  public Operation getGETEndpoint() {
    log.debug("GET endpoint not implemented");
    return null;
  }

  public Operation getPOSTEndpoint() {
    log.debug("POST endpoint not implemented");

    return null;
  }


  public Operation getPUTEndpoint() {

    log.debug("PUT endpoint not implemented");
    return null;

  }

}
