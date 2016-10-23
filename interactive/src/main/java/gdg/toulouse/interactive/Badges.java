package gdg.toulouse.interactive;

import gdg.toulouse.attendee.data.Attendee;
import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.domain.Badge;
import gdg.toulouse.attendee.domain.impl.AttendeeModelBuilder;
import gdg.toulouse.attendee.domain.impl.BadgeBuilder;
import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.template.data.TemplateData;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.domain.impl.TemplateModelBuilder;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Badges implements Action {

    static <T> List<List<T>> split(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    @Override
    public String description() {
        return "action dedicated to attendees badge generation";
    }

    public Unit execute(String[] args) {

        final OptionsContext context = new OptionsContext();

        final Option outputOption = new Option("o", true, "Output directory");
        outputOption.setRequired(true);
        context.getOptions().addOption(outputOption);

        context.parse(args).onSuccess(parser ->
                generateBadgesForAllAttendees(context, parser.getOptionValue("o"))
        ).onFailure(throwable -> {
            System.out.println("#ERROR " + throwable.getMessage());
            new HelpFormatter().printHelp("attendee " + args[0], context.getOptions());
            System.exit(2);
        });

        return Unit.UNIT;
    }

    //
    // Private methods
    //

    private FileOutputStream getFileOutputStream(String outputDirectory, String attendee) throws FileNotFoundException {
        return new FileOutputStream(new File(outputDirectory, attendee + ".pdf"));
    }

    private void generateBadgesForAllAttendees(OptionsContext context, String outputDirectory) {
        final int numberOfAttendeePerPage = 4;
        final AttendeeModel attendeeModel = AttendeeModelBuilder.create(context.getAttendeeRepository());
        final TemplateModel templateModel = TemplateModelBuilder.create(context.getTemplateRepository());

        final List<TemplateData> attendees = attendeeModel.getAttendees().stream().
                map(attendeeModel::getAttendee).
                filter(Optional::isPresent).
                map(Optional::get).
                map(this::getTemplateDataFromAttendee).
                sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());

        final int[] idx = {0};
        split(attendees, numberOfAttendeePerPage).forEach(data -> {
            System.err.println("making badge " + idx[0]);
                    getBadge(templateModel, data.toArray(new TemplateData[0])).onSuccess(badge -> {
                        try (FileOutputStream stream = getFileOutputStream(outputDirectory, "badge-" + idx[0]++)) {
                            badge.generate(stream);
                        } catch (IOException e) {
                            e.printStackTrace(); // TODO(didier) manage this error
                        }
                    });
                }
        );
    }

    private Try<Badge> getBadge(TemplateModel templateModel, TemplateData[] templateData) {
        return templateModel.instantiate(templateData).map(BadgeBuilder::create);
    }

    private TemplateData getTemplateDataFromAttendee(Attendee attendee) {
        return new TemplateData(
                attendee.getSurname(),
                attendee.getName(),
                attendee.getRole(),
                attendee.getMail(),
                attendee.getCompany().orElse(null),
                attendee.getTwitter().orElse(null));
    }
}
