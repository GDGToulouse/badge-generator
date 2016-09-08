package gdg.toulouse.template.service;

import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ServiceInterface;

import java.util.Map;
import java.util.function.Function;

@ServiceInterface
public interface TemplateRepository {

    Function<Map<String, String>, Try<TemplateInstance>> getGenerator();

}
