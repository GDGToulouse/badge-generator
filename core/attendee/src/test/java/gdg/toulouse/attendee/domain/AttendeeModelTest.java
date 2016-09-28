package gdg.toulouse.attendee.domain;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.service.TemplateInstance;
import org.junit.Test;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static gdg.toulouse.attendee.domain.impl.AttendeeModelBuilder.create;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendeeModelTest {

    @Test
    public void shouldRetrieveAllAttendeeMails() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendees()).containsExactly("a.b@c.d", "e.f@g.h");
    }

    @Test
    public void shouldRetrieveAnAttendee() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendee("a.b@c.d").isPresent()).isTrue();
    }

    @Test
    public void shouldNotRetrieveAnAttendee() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendee("a.b@c.bar").isPresent()).isFalse();
    }

    @Test
    public void shouldRetrieveAnAttendeeBadge() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendeeBadge("a.b@c.d").isSuccess()).isTrue();
    }

    @Test
    public void shouldNotRetrieveAnAttendeeBadge() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendeeBadge("a.b@c.bar").isSuccess()).isFalse();
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

    private class TemplateModelTest implements TemplateModel {
        @Override
        public Try<TemplateInstance> instantiate(TemplateData templateData) {
            return Try.success(new TemplateInstance() {
                @Override
                public Try<Unit> dump(OutputStream stream) {
                    return Try.success(Unit.UNIT);
                }
            });
        }
    }

}