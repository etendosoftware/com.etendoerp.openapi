OB.ETAPI = OB.ETAPI || {};
OB.ETAPI.swagger = OB.ETAPI.swagger || {};

OB.ETAPI.swagger.openSwagger = function(params, view) {
  // Get the selected record's name from the context
  let name = "";
  if (
    params &&
    params.processOwnerView &&
    params.processOwnerView.viewGrid &&
    typeof params.processOwnerView.viewGrid.getSelectedRecords === "function"
  ) {
    let selected = params.processOwnerView.viewGrid.getSelectedRecords();
    if (selected.length > 0 && selected[0].name) {
      name = selected[0].name;
    }
  }

  // Encode tag for the URL
  let encodedTag = encodeURIComponent(name);

  // Build Swagger URL
  let baseUrl = "/etendo/web/com.etendoerp.openapi/#/";
  let fullUrl = baseUrl + encodedTag;

  window.open(fullUrl, "_blank");

  if (OB && OB.Utilities && typeof OB.Utilities.showSuccess === "function") {
    OB.Utilities.showSuccess("Swagger UI is opening in a new tab for: " + name);
  }
};
