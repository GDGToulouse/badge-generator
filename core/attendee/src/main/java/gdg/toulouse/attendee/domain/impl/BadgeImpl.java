package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.domain.Badge;
import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.service.TemplateInstance;

import java.io.OutputStream;

class BadgeImpl implements Badge {

    private TemplateInstance templateInstance;

    BadgeImpl(TemplateInstance consumer) {
        this.templateInstance = consumer;
    }

    @Override
    public Try<Unit> generate(OutputStream stream) {
        return templateInstance.dump(stream);
    }
}
