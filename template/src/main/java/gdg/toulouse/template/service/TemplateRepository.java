package gdg.toulouse.template.service;

import gdg.toulouse.design.annotations.ServiceInterface;

import java.util.Map;
import java.util.function.Function;

@ServiceInterface
public interface TemplateRepository {

    Function<Map<String, String>, TemplateInstance> getGenerator();

}
