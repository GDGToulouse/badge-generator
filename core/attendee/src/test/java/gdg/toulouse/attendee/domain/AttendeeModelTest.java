package gdg.toulouse.attendee.domain;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.service.AttendeeRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static gdg.toulouse.attendee.domain.impl.AttendeeModelBuilder.create;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendeeModelTest {

    @Test
    public void shouldRetrieveAllAttendeeMails() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest());

        assertThat(attendeeModel.getAttendees()).containsExactly("a.b@c.d", "e.f@g.h");
    }

    @Test
    public void shouldRetrieveAnAttendee() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest());

        assertThat(attendeeModel.getAttendee("a.b@c.d").isPresent()).isTrue();
    }

    @Test
    public void shouldNotRetrieveAnAttendee() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest());

        assertThat(attendeeModel.getAttendee("a.b@c.bar").isPresent()).isFalse();
    }

    //
    // Private behaviors
    //

    private class AttendeeRepositoryTest implements AttendeeRepository {

        private Collection<Attendee> attendees;

        AttendeeRepositoryTest() {
            this.attendees = new ArrayList<>();
            this.attendees.add(new Attendee("a.b@c.d", "A", "B", "a.b@c.d", null, null));
            this.attendees.add(new Attendee("e.f@g.h", "E", "F", "e.f@g.h", null, null));
        }

        @Override
        public Collection<String> getAttendeesIdentifiers() {
            return this.attendees.stream().map(Attendee::getMail).collect(Collectors.toList());
        }

        @Override
        public Optional<Attendee> findByIdentifier(String identifier) {
            return this.attendees.stream().filter(attendee -> attendee.getMail().equals(identifier)).findFirst();
        }
    }

}