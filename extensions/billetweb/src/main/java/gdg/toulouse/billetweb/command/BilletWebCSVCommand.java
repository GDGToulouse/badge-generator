package gdg.toulouse.billetweb.command;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.attendee.service.impl.CollectionBasedAttendeeRepository;
import gdg.toulouse.billetweb.codec.BilletWebCSVDecoder;
import gdg.toulouse.command.command.AttendeeCommand;
import gdg.toulouse.data.Try;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BilletWebCSVCommand implements AttendeeCommand {

    @Override
    public Try<AttendeeRepository> create(String parameter) {
        try (InputStream stream = new FileInputStream(parameter)) {
            return Try.success(getAttendeeRepository(stream));
        } catch (IOException e) {
            return Try.failure(e);
        }
    }

    private  CollectionBasedAttendeeRepository getAttendeeRepository(InputStream stream) throws IOException {
        final List<Attendee> attendees = BilletWebCSVDecoder.getAttendeesFromStream(stream);
        return new CollectionBasedAttendeeRepository(attendees);
    }

}
