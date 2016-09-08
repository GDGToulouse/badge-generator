package gdg.toulouse.billetweb;

import gdg.toulouse.attendee.data.Attendee;
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
                new Attendee("B","A","a.b@g.com", null, null),
                new Attendee("D","C","c.d@g.com", null, null)
        );
    }

    //
    // Private behaviors
    //

    private InputStream givenAnInputStream() throws IOException {
        return BilletWebCSVDecoderTest.class.getResource("/attendees.bw").openStream();
    }
}