package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.design.annotations.Factory;
import gdg.toulouse.template.domain.TemplateModel;

import static java.util.Objects.requireNonNull;

@Factory(AttendeeModel.class)
public final class AttendeeModelBuilder {

    private AttendeeModelBuilder() {
        // prevent useless construction
    }

    public static AttendeeModel create(AttendeeRepository attendeeRepository, TemplateModel templateModel) {
        requireNonNull(attendeeRepository, "Attendee Repository");
        requireNonNull(templateModel, "Template Model");

        return new AttendeeModelImpl(attendeeRepository, templateModel);
    }

}
