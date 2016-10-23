package gdg.toulouse.billetweb.codec;

import gdg.toulouse.attendee.data.Attendee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("FieldCanBeLocal")
public class BilletWebCSVDecoder {

    private static final String BILLET = "Billet";
    private static String SURNAME = "Prénom";
    private static String NAME = "Nom";
    private static String ROLE = "Tarif";
    private static String MAIL = "E-mail";

    public static List<Attendee> getAttendeesFromStream(InputStream stream) throws IOException {
        final List<String> lines = getLineStream(stream).collect(Collectors.toList());

        return lines.stream().findFirst().map((line) -> {
            final List<String> attributeNames = getTermsFromLine(line);

            final int identifier = attributeNames.indexOf(BILLET);
            final int surname = attributeNames.indexOf(SURNAME);
            final int name = attributeNames.indexOf(NAME);
            final int role = attributeNames.indexOf(ROLE);
            final int mail = attributeNames.indexOf(MAIL);

            return lines.stream().
                    map(BilletWebCSVDecoder::getTermsFromLine).
                    map(s -> getAttendee(identifier, surname, name, role, mail, s)).
                    filter(getValidAttendee()).
                    collect(Collectors.toList());

        }).orElseGet(() -> Collections.EMPTY_LIST);
    }

    //
    // Private behaviors
    //

    private static Stream<String> getLineStream(InputStream stream) throws UnsupportedEncodingException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-16"));
        return reader.lines();
    }

    private static List<String> getTermsFromLine(String line) {
        return Arrays.asList(line.split("\t"));
    }

    private static Attendee getAttendee(int identifier, int surname, int name, int role, int mail, List<String> s) {
        final Function<Integer, String> extract = (i) -> {
            if (i > -1) {
                return s.get(i);
            } else {
                return "";
            }
        };

        return new Attendee(extract.apply(identifier), extract.apply(surname), extract.apply(name), extract.apply(role), extract.apply(mail), null, null);
    }

    private static Predicate<Attendee> getValidAttendee() {
        return attendee -> !attendee.getSurname().equals(SURNAME);
    }

}
