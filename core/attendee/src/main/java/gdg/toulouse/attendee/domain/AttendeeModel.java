package gdg.toulouse.attendee.domain;

import gdg.toulouse.data.Try;
import gdg.toulouse.design.annotations.ApplicationInterface;

import java.util.Collection;

@ApplicationInterface
public interface AttendeeModel {

    Collection<String> getAttendees();

    Try<AttendeeBadge> getAttendeeBadge(String identifier);

}
