package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.domain.AttendeeBadge;
import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.exception.AttendeeNotFoundException;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.data.Try;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.domain.TemplateModel;

import java.util.Collection;
import java.util.Optional;

class AttendeeModelImpl implements AttendeeModel {

    private final AttendeeRepository attendeeRepository;
    private final TemplateModel templateModel;

    AttendeeModelImpl(AttendeeRepository attendeeRepository, TemplateModel templateModel) {
        this.attendeeRepository = attendeeRepository;
        this.templateModel = templateModel;
    }

    public Collection<String> getAttendees() {
        return attendeeRepository.getAttendeesIdentifiers();
    }

    @Override
    public Optional<Attendee> getAttendee(String identifier) {
        return attendeeRepository.findByIdentifier(identifier);
    }

    @Override
    public Try<AttendeeBadge> getAttendeeBadge(String identifier) {
        return attendeeRepository.findByIdentifier(identifier).
                map(attendee -> getAttendeeBadge(getTemplateDataFromAttendee(attendee))).
                orElseGet(() -> Try.failure(new AttendeeNotFoundException(identifier)));
    }

    //
    // Private behaviors
    //

    private Try<AttendeeBadge> getAttendeeBadge(TemplateData templateData) {
        return templateModel.instantiate(templateData).map(AttendeeBadgeBuilder::create);
    }

    private TemplateData getTemplateDataFromAttendee(Attendee attendee) {
        return new TemplateData(
                attendee.getSurname(),
                attendee.getName(),
                attendee.getMail(),
                attendee.getCompany().orElse(null),
                attendee.getTwitter().orElse(null));
    }
}
