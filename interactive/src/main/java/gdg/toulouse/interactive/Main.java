package gdg.toulouse.interactive;

import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.domain.impl.AttendeeModelBuilder;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.domain.impl.TemplateModelBuilder;
import gdg.toulouse.template.service.TemplateRepository;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Main {

    public static void main(String[] args) {

        final OptionsContext context = new OptionsContext();

        final Option outputOption = new Option("o", true, "Output directory");
        outputOption.setRequired(true);
        context.getOptions().addOption(outputOption);

        context.parse(args).onSuccess(parser ->
                generateBadgesForAllAttendees(context, parser.getOptionValue("o"))
        ).onFailure(throwable -> {
            System.out.println("#ERROR " + throwable.getMessage());
            new HelpFormatter().printHelp("attendee", context.getOptions());
            System.exit(2);
        });
    }

    private static void generateBadgesForAllAttendees(OptionsContext context, String outputDirectory) {
        final AttendeeModel attendeeModel = getAttendeeModel(context.getAttendeeRepository(), context.getTemplateRepository());

        attendeeModel.getAttendees().stream().forEach(attendee -> {
            System.out.println("Manage " + attendee);
            attendeeModel.getAttendeeBadge(attendee).onSuccess(attendeeBadge -> {
                try (FileOutputStream stream = getFileOutputStream(outputDirectory, attendee)) {
                    attendeeBadge.generate(stream);
                } catch (IOException e) {
                    e.printStackTrace(); // TODO(didier) manage this error
                }
            });
        });
    }

    private static AttendeeModel getAttendeeModel(AttendeeRepository attendeeRepository, TemplateRepository templateRepository) {
        final TemplateModel templateModel = TemplateModelBuilder.create(templateRepository);
        return AttendeeModelBuilder.create(attendeeRepository, templateModel);
    }

    private static FileOutputStream getFileOutputStream(String outputDirectory, String attendee) throws FileNotFoundException {
        return new FileOutputStream(new File(outputDirectory, attendee + ".pdf"));
    }
}
