package com.etendoerp.openapi.model.window;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Form and view personalization")
public class Personalization {

  @Schema(description = "Mapping of personalized forms, where the key is the form ID and the value is the name", example = "{\"clients\": {\"23C59575B9CF467C9620760EB255B389\": \"F&B International Group\"}}")
  private Map<String, String> forms;

  @Schema(description = "List of available personalized views")
  private List<String> views;

  @Schema(description = "Form data, including clients, organizations, and roles")
  private FormData formData;

}
