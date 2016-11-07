package gdg.toulouse.template.service;

import gdg.toulouse.template.data.TemplateData;

public interface TemplateRenderer {

    public String getRole(TemplateData data);

    public String getColor(TemplateData data);

}
