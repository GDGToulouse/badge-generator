package gdg.toulouse.billetweb;

import gdg.toulouse.attendee.data.Attendee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BilletWebCSVDecoder {

    private static String SURNAME = "Prénom";
    private static String NAME = "Nom";
    private static String MAIL = "E-mail";

    public static List<Attendee> getAttendeesFromStream(InputStream stream) throws IOException {
        final List<String> lines = getLineStream(stream).collect(Collectors.toList());

        List<String> attributeNames = getTermsFromLine(lines.stream().findFirst().get());

        final int surname = attributeNames.indexOf(SURNAME);
        final int name = attributeNames.indexOf(NAME);
        final int mail = attributeNames.indexOf(MAIL);

        return lines.stream().
                map(BilletWebCSVDecoder::getTermsFromLine).
                map(s -> getAttendee(surname, name, mail, s)).
                filter(getValidAttendee()).
                collect(Collectors.toList());
    }

    //
    // Private behaviors
    //

    private static Predicate<Attendee> getValidAttendee() {
        return attendee -> !attendee.getSurname().equals(SURNAME);
    }

    private static Attendee getAttendee(int surname, int name, int mail, List<String> s) {
        final Function<Integer, String> extract = (i) -> {
            if (i > -1) {
                return s.get(i);
            } else {
                return "";
            }
        };

        return new Attendee(extract.apply(surname), extract.apply(name), extract.apply(mail), null, null);
    }

    private static Stream<String> getLineStream(InputStream stream) throws UnsupportedEncodingException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-16"));
        return reader.lines();
    }

    private static List<String> getTermsFromLine(String line) {
        return Arrays.asList(line.split(","));
    }

}
