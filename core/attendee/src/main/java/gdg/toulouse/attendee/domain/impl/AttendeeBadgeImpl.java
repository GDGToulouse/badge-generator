package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.domain.AttendeeBadge;
import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;

import java.io.OutputStream;

class AttendeeBadgeImpl implements AttendeeBadge {

    private TemplateInstance templateInstance;

    AttendeeBadgeImpl(TemplateInstance consumer) {
        this.templateInstance = consumer;
    }

    @Override
    public Try<Unit> generate(OutputStream stream) {
        return templateInstance.dump(stream);
    }
}
