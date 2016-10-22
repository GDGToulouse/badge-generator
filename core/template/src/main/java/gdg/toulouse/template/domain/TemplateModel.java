package gdg.toulouse.template.domain;

import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ApplicationInterface;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.service.TemplateInstance;

@ApplicationInterface
public interface TemplateModel {

    Try<TemplateInstance> instantiate(TemplateData[] templateData);

}
