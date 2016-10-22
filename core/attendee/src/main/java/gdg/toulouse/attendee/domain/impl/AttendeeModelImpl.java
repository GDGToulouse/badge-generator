package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.service.AttendeeRepository;

import java.util.Collection;
import java.util.Optional;

class AttendeeModelImpl implements AttendeeModel {

    private final AttendeeRepository attendeeRepository;

    AttendeeModelImpl(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Override
    public Collection<String> getAttendees() {
        return attendeeRepository.getAttendeesIdentifiers();
    }

    @Override
    public Optional<Attendee> getAttendee(String identifier) {
        return attendeeRepository.findByIdentifier(identifier);
    }
/*
    @Override
    public Try<Badge> getAttendeeBadge(String identifier) {
        return attendeeRepository.findByIdentifier(identifier).
                map(attendee -> getAttendeeBadge(getTemplateDataFromAttendee(attendee))).
                orElseGet(() -> Try.failure(new AttendeeNotFoundException(identifier)));
    }

    //
    // Private behaviors
    //

    private Try<Badge> getAttendeeBadge(TemplateData templateData) {
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
*/
}
