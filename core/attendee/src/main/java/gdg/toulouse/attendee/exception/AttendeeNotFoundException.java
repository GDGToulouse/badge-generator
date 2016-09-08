package gdg.toulouse.attendee.exception;

public class AttendeeNotFoundException extends Throwable {

    public AttendeeNotFoundException(String identifier) {
        super(identifier);
    }

}
