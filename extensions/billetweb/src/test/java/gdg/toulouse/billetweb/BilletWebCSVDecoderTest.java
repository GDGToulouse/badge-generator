package gdg.toulouse.billetweb;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.billetweb.codec.BilletWebCSVDecoder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BilletWebCSVDecoderTest {

    @Test
    public void shouldDecodeStream() throws Exception {
        final List<Attendee> attendees = BilletWebCSVDecoder.getAttendeesFromStream(this.givenAnInputStream());

        assertThat(attendees).isNotEmpty();
    }

    @Test
    public void shouldRetrieveAllAttendess() throws Exception {
        final List<Attendee> attendees = BilletWebCSVDecoder.getAttendeesFromStream(this.givenAnInputStream());

        assertThat(attendees).containsOnly(
                new Attendee("1", "B", "A", "R", "a.b@g.com", null, null),
                new Attendee("2", "D", "C", "R", "c.d@g.com", null, null)
        );
    }

    //
    // Private behaviors
    //

    private InputStream givenAnInputStream() throws IOException {
        return BilletWebCSVDecoderTest.class.getResource("/attendees.bw").openStream();
    }
}