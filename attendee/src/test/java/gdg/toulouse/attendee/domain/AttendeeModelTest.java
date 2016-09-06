package gdg.toulouse.attendee.domain;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.service.TemplateInstance;
import org.junit.Test;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
    public void shouldRetrieveAnAttenBadge() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendeeBadge("a.b@c.d").isPresent()).isTrue();
    }

    @Test
    public void shouldNotRetrieveAnAttenBadge() throws Exception {
        final AttendeeModel attendeeModel = create(new AttendeeRepositoryTest(), new TemplateModelTest());

        assertThat(attendeeModel.getAttendeeBadge("a.b@c.bar").isPresent()).isFalse();
    }

    //
    // Private behaviors
    //

    private class AttendeeRepositoryTest implements AttendeeRepository {

        private Collection<Attendee> attendees;

        AttendeeRepositoryTest() {
            this.attendees = new ArrayList<>();
            this.attendees.add(new Attendee("A", "B", "a.b@c.d", null, null));
            this.attendees.add(new Attendee("E", "F", "e.f@g.h", null, null));
        }

        @Override
        public Collection<String> getAttendeesMail() {
            return this.attendees.stream().map(Attendee::getMail).collect(Collectors.toList());
        }

        @Override
        public Optional<Attendee> findByMail(String mail) {
            return this.attendees.stream().filter(attendee -> attendee.getMail().equals(mail)).findFirst();
        }
    }

    private class TemplateModelTest implements TemplateModel {
        @Override
        public TemplateInstance instantiate(Map<String, String> parameters) {
            return new TemplateInstance() {
                @Override
                public Try<Unit> dump(OutputStream stream) {
                    return Try.success(Unit.UNIT);
                }
            };
        }
    }

}