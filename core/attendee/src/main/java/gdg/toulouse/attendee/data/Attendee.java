package gdg.toulouse.attendee.data;

import gdg.toulouse.design.annotations.ValueObject;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@ValueObject
public class Attendee {

    private final String identifier;
    private final String surname;
    private final String name;
    private final String mail;
    private final String company;
    private final String twitter;

    public Attendee(String identifier, String surname, String name, String mail, String company, String twitter) {
        this.identifier = identifier;
        requireNonNull(surname, "Surname");
        requireNonNull(surname, "name");
        requireNonNull(surname, "mail");

        this.surname = surname;
        this.name = name;
        this.mail = mail;
        this.company = company;
        this.twitter = twitter;
    }

    public String getIdentifier() {
        return identifier;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendee attendee = (Attendee) o;

        if (surname != null ? !surname.equals(attendee.surname) : attendee.surname != null) return false;
        if (name != null ? !name.equals(attendee.name) : attendee.name != null) return false;
        if (mail != null ? !mail.equals(attendee.mail) : attendee.mail != null) return false;
        if (company != null ? !company.equals(attendee.company) : attendee.company != null) return false;
        return twitter != null ? twitter.equals(attendee.twitter) : attendee.twitter == null;

    }

    @Override
    public int hashCode() {
        int result = surname != null ? surname.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        return result;
    }
}
