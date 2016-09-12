package gdg.toulouse.attendee.domain.impl;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.domain.AttendeeBadge;
import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.exception.AttendeeNotFoundException;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.data.Try;
import gdg.toulouse.template.domain.TemplateModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static gdg.toulouse.attendee.utils.Constants.COMPANY;
import static gdg.toulouse.attendee.utils.Constants.MAIL;
import static gdg.toulouse.attendee.utils.Constants.NAME;
import static gdg.toulouse.attendee.utils.Constants.SURNAME;
import static gdg.toulouse.attendee.utils.Constants.TWITTER;

class AttendeeModelImpl implements AttendeeModel {

    private final AttendeeRepository attendeeRepository;
    private final TemplateModel templateModel;

    AttendeeModelImpl(AttendeeRepository attendeeRepository, TemplateModel templateModel) {
        this.attendeeRepository = attendeeRepository;
        this.templateModel = templateModel;
    }

    public Collection<String> getAttendees() {
        return attendeeRepository.getAttendeesMail();
    }

    public Try<AttendeeBadge> getAttendeeBadge(String identifier) {
        return attendeeRepository.findByMail(identifier).
                map(attendee -> getAttendeeBadge(getParametersForGeneration(attendee))).
                orElseGet(() -> Try.failure(new AttendeeNotFoundException(identifier)));
    }

    //
    // Private behaviors
    //

    private Try<AttendeeBadge> getAttendeeBadge(Map<String, String> parameters) {
        return templateModel.instantiate(parameters).map(AttendeeBadgeBuilder::create);
    }

    private HashMap<String, String> getParametersForGeneration(Attendee attendee) {
        final HashMap<String, String> parameters = new HashMap<>();

        parameters.put(SURNAME, attendee.getSurname());
        parameters.put(NAME, attendee.getName());
        parameters.put(MAIL, attendee.getMail());

        attendee.getCompany().ifPresent(company -> {
            parameters.put(COMPANY, company);
        });
        attendee.getTwitter().ifPresent(twitter -> {
            parameters.put(TWITTER, twitter);
        });

        return parameters;
    }
}
