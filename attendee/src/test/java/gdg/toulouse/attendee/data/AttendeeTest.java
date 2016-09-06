package gdg.toulouse.attendee.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendeeTest {
    @Test
    public void shouldGetSurname() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, null).getSurname()).isEqualTo("S");
    }

    @Test
    public void shouldGetName() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, null).getName()).isEqualTo("N");
    }

    @Test
    public void shouldGetMail() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, null).getMail()).isEqualTo("M");
    }

    @Test
    public void shouldGetNoCompany() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, null).getCompany().isPresent()).isFalse();
    }

    @Test
    public void shouldGetCompany() throws Exception {
        assertThat(new Attendee("S", "N", "M", "C", null).getCompany().get()).isEqualTo("C");
    }

    @Test
    public void shouldGetNoTwitter() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, null).getTwitter().isPresent()).isFalse();
    }

    @Test
    public void shouldGetTwitter() throws Exception {
        assertThat(new Attendee("S", "N", "M", null, "T").getTwitter().get()).isEqualTo("T");
    }
}