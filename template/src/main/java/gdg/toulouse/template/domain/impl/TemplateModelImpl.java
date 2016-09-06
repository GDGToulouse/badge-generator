package gdg.toulouse.template.domain.impl;

import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;

import java.util.Map;

public class TemplateModelImpl implements TemplateModel {

    private final TemplateRepository templateRepository;

    public TemplateModelImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public TemplateInstance instantiate(Map<String, String> parameters) {
        return templateRepository.getGenerator().apply(parameters);
    }
}
