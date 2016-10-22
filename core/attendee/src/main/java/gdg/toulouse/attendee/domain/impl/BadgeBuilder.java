package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.domain.Badge;
import gdg.toulouse.design.annotations.Factory;
import gdg.toulouse.template.service.TemplateInstance;

@Factory(Badge.class)
public final class BadgeBuilder {

    private BadgeBuilder() {
        // prevent useless construction
    }

    public static final Badge create(TemplateInstance templateInstance) {
        return new BadgeImpl(templateInstance);
    }

}
