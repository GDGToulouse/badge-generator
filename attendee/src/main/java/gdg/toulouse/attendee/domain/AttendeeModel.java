package gdg.toulouse.attendee.domain;

import gdg.toulouse.design.annotations.ApplicationInterface;

import java.util.Collection;
import java.util.Optional;

@ApplicationInterface
public interface AttendeeModel {

    Collection<String> getAttendees();

    Optional<AttendeeBadge> getAttendeeBadge(String identifier);

}
