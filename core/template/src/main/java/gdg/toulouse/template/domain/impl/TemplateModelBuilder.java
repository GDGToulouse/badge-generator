package gdg.toulouse.template.domain.impl;

import gdg.toulouse.design.annotations.Factory;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.service.TemplateRepository;

import static java.util.Objects.requireNonNull;

@Factory(TemplateModel.class)
public final class TemplateModelBuilder {

    private TemplateModelBuilder() {
        // prevent useless construction
    }

    public static TemplateModel create(TemplateRepository templateRepository) {
        requireNonNull(templateRepository, "Template Repository");

        return new TemplateModelImpl(templateRepository);
    }

}
