package gdg.toulouse.attendee.service;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.design.annotations.ServiceInterface;

import java.util.Collection;
import java.util.Optional;

@ServiceInterface
public interface AttendeeRepository {

    Collection<String> getAttendeesIdentifiers();

    Optional<Attendee> findByIdentifier(String identifier);

}
