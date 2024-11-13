package com.etendoerp.openapi.model;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public interface OpenAPIEndpoint {
  List<String> getTags();
  void add(OpenAPI openAPI);
}
