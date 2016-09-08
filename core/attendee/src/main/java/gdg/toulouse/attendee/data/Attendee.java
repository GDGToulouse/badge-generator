package gdg.toulouse.attendee.data;

import gdg.toulouse.design.annotations.ValueObject;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@ValueObject
public class Attendee {

    private final String surname;
    private final String name;
    private final String mail;
    private final String company;
    private final String twitter;

    public Attendee(String surname, String name, String mail, String company, String twitter) {
        requireNonNull(surname, "Surname");
        requireNonNull(surname, "name");
        requireNonNull(surname, "mail");

        this.surname = surname;
        this.name = name;
        this.mail = mail;
        this.company = company;
        this.twitter = twitter;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public Optional<String> getCompany() {
        return Optional.ofNullable(company);
    }

    public Optional<String> getTwitter() {
        return Optional.ofNullable(twitter);
    }
}
