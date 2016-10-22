package gdg.toulouse.template.domain.impl;

import gdg.toulouse.data.Try;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.service.TemplateInstance;
import gdg.toulouse.template.service.TemplateRepository;

class TemplateModelImpl implements TemplateModel {

    private final TemplateRepository templateRepository;

    TemplateModelImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Try<TemplateInstance> instantiate(TemplateData[] templateData) {
        return templateRepository.getGenerator().apply(templateData);
    }
}
