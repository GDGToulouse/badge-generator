package gdg.toulouse.attendee.domain;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ApplicationInterface;

import java.util.Collection;
import java.util.Optional;

@ApplicationInterface
public interface AttendeeModel {

    Collection<String> getAttendees();

    Optional<Attendee> getAttendee(String identifier);

    Try<AttendeeBadge> getAttendeeBadge(String identifier);

}
