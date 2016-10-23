package gdg.toulouse.attendee.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendeeTest {
    @Test
    public void shouldGetIdentifier() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getIdentifier()).isEqualTo("I");
    }

    @Test
    public void shouldGetSurname() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getSurname()).isEqualTo("S");
    }

    @Test
    public void shouldGetName() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getName()).isEqualTo("N");
    }

    @Test
    public void shouldGetRole() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getRole()).isEqualTo("R");
    }

    @Test
    public void shouldGetMail() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getMail()).isEqualTo("M");
    }

    @Test
    public void shouldGetNoCompany() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getCompany().isPresent()).isFalse();
    }

    @Test
    public void shouldGetCompany() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", "C", null).getCompany().get()).isEqualTo("C");
    }

    @Test
    public void shouldGetNoTwitter() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, null).getTwitter().isPresent()).isFalse();
    }

    @Test
    public void shouldGetTwitter() throws Exception {
        assertThat(new Attendee("I", "S", "N", "R", "M", null, "T").getTwitter().get()).isEqualTo("T");
    }
}