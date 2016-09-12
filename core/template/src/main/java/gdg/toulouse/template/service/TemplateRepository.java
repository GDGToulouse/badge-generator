package gdg.toulouse.template.service;

import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ServiceInterface;
import gdg.toulouse.template.data.TemplateData;

import java.util.function.Function;

@ServiceInterface
public interface TemplateRepository {

    Function<TemplateData, Try<TemplateInstance>> getGenerator();

}
