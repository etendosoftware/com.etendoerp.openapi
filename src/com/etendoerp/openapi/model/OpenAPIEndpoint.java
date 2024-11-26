package com.etendoerp.openapi.model;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public interface OpenAPIEndpoint {
  boolean isValid(String tag);
  void add(OpenAPI openAPI);
}
