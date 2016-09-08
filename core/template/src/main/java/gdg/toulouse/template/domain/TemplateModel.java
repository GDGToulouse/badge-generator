package gdg.toulouse.template.domain;

import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ApplicationInterface;
import gdg.toulouse.template.service.TemplateInstance;

import java.util.Map;

@ApplicationInterface
public interface TemplateModel {

    Try<TemplateInstance> instantiate(Map<String, String> parameters);

}
