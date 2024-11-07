package com.etendoerp.openapi.model.window;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Window Settings Configuration Response")
public class WindowSettingResponse {

  @Schema(description = "Mapping of UI patterns for different elements, where the key is the element ID and the value is the pattern type", example = "{\"186\": \"STD\", \"800222\": \"RO\"}")
  private Map<String, String> uiPattern;

  @Schema(description = "Indicates whether auto-save is enabled", example = "true")
  private boolean autoSave;

  @Schema(description = "Configuration personalization, including forms and views")
  private Personalization personalization;

  @Schema(description = "Indicates whether to show a confirmation before auto-saving", example = "false")
  private boolean showAutoSaveConfirmation;

  @Schema(description = "List of available tabs in the current configuration")
  private List<String> tabs;

  @Schema(description = "List of processes that are not accessible to the user")
  private List<String> notAccessibleProcesses;

  @Schema(description = "Additional settings specified as key-value pairs")
  private Map<String, String> extraSettings;

  @Schema(description = "Additional callbacks available in the current configuration")
  private List<String> extraCallbacks;

}
