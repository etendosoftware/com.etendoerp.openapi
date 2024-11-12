package com.etendoerp.openapi.model.window;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Form data with clients, organizations, and roles")
public class FormData {

  @Schema(description = "Mapping of clients, where the key is the client ID and the value is the client name", example = "{\"23C59575B9CF467C9620760EB255B389\": \"F&B International Group\"}")
  private Map<String, String> clients;

  @Schema(description = "Mapping of organizations, where the key is the organization ID and the value is the organization name", example = "{\"E443A31992CB4635AFCAEABE7183CE85\": \"F&B Spain - Northern Region\"}")
  private Map<String, String> orgs;

  @Schema(description = "Mapping of roles, where the key is the role ID and the value is the role name", example = "{\"0\": \"System Administrator\"}")
  private Map<String, String> roles;

}
