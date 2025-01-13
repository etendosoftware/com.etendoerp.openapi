package com.etendoerp.openapi.model;

import io.swagger.v3.oas.models.OpenAPI;

/**
 * Interface representing an OpenAPI endpoint.
 */
public interface OpenAPIEndpoint {

  /**
   * Checks if the hook is valid for that tag.
   *
   * @param tag
   *     the tag to be checked
   * @return true if the tag is valid, false otherwise
   */
  boolean isValid(String tag);

  /**
   * Adds the given OpenAPI object to this endpoint with the specified tag.
   *
   * @param openAPI
   *     the OpenAPI object to be added
   */
  void add(OpenAPI openAPI);
}