package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.domain.AttendeeBadge;
import gdg.toulouse.design.annotations.Factory;
import gdg.toulouse.template.service.TemplateInstance;

@Factory(AttendeeBadge.class)
public final class AttendeeBadgeBuilder {

    private AttendeeBadgeBuilder() {
        // prevent useless construction
    }

    public static final AttendeeBadge create(TemplateInstance templateInstance) {
        return new AttendeeBadgeImpl(templateInstance);
    }

}
