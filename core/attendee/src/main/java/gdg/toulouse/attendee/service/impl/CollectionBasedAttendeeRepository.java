package gdg.toulouse.attendee.service.impl;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.design.annotations.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CollectionBasedAttendeeRepository implements AttendeeRepository {

    private final Collection<Attendee> attendees;

    public CollectionBasedAttendeeRepository(Collection<Attendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public Collection<String> getAttendeesIdentifiers() {
        return this.attendees.stream().
                map(Attendee::getIdentifier).
                collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Attendee> findByIdentifier(String identifier) {
        Objects.requireNonNull(identifier, "Identitier");

        return this.attendees.stream().
                filter(attendee -> attendee.getIdentifier().equals(identifier)).
                findFirst();
    }
}