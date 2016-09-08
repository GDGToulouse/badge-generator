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
    public Collection<String> getAttendeesMail() {
        return this.attendees.stream().
                map(Attendee::getMail).
                collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Attendee> findByMail(String mail) {
        Objects.requireNonNull(mail, "Mail");

        return this.attendees.stream().
                filter(attendee -> attendee.getMail().equals(mail)).
                findFirst();
    }
}