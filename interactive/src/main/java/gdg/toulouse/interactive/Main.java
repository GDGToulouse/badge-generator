package gdg.toulouse.interactive;

import gdg.toulouse.attendee.domain.AttendeeModel;
import gdg.toulouse.attendee.domain.impl.AttendeeModelBuilder;
import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.command.command.CommandFinder;
import gdg.toulouse.template.domain.TemplateModel;
import gdg.toulouse.template.domain.impl.TemplateModelBuilder;
import gdg.toulouse.template.service.TemplateRepository;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class Main {

    public static void main(String[] args) {
        final AtomicReference<AttendeeRepository> attendeeCommandReference = new AtomicReference<>();
        final AtomicReference<TemplateRepository> templateCommandReference = new AtomicReference<>();

        final Map<String, Consumer<String>> optionCallback = new HashMap<>();
        final Options options = new Options();

        populateOptions(attendeeCommandReference, templateCommandReference, options, optionCallback);

        //
        // Parse options
        //

        final HelpFormatter formatter = new HelpFormatter();

        try {
            Arrays.asList(new DefaultParser().parse(options, args).getOptions()).forEach(option -> {
                if (optionCallback.keySet().contains(option.getOpt())) {
                    optionCallback.get(option.getOpt()).accept(option.getValue());
                }
            });

            if (attendeeCommandReference.get() == null) {
                System.err.println("Waiting for an Attentee Repository");
                formatter.printHelp("attendee", options);
                System.exit(1);
                return;
            }

            if (templateCommandReference.get() == null) {
                System.err.println("Waiting for an Template Repository");
                formatter.printHelp("attendee", options);
                System.exit(1);
                return;
            }


            final TemplateModel templateModel = TemplateModelBuilder.create(templateCommandReference.get());
            final AttendeeModel attendeeModel = AttendeeModelBuilder.create(attendeeCommandReference.get(), templateModel);

            attendeeModel.getAttendees().stream().forEach(attendee -> {
                System.out.println("Generating badge for " + attendeeModel.getAttendee(attendee).get().getName().toUpperCase());
                attendeeModel.getAttendeeBadge(attendee).onSuccess(attendeeBadge -> {
                    try (FileOutputStream stream = getFileOutputStream(attendee)) {
                        attendeeBadge.generate(stream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("attendee", options);
            System.exit(2);
            return;
        }
    }

    private static FileOutputStream getFileOutputStream(String attendee) throws FileNotFoundException {
        return new FileOutputStream("/tmp/" + attendee + ".pdf");
    }

    private static void populateOptions(AtomicReference<AttendeeRepository> attendeeCommandReference, AtomicReference<TemplateRepository> templateCommandReference, Options options, Map<String, Consumer<String>> optionCallback) {
        final CommandFinder commandFinder = new CommandFinder();
        commandFinder.getAttendeeCommands().forEach(commandDeclaration -> {
            options.addOption(new Option(commandDeclaration.getName(), true, commandDeclaration.getDescription()));
            optionCallback.put(commandDeclaration.getName(), s ->
                    commandDeclaration.getCommand().create(s).
                            onSuccess(attendeeCommandReference::set).
                            onFailure(Throwable::printStackTrace)
            );
        });

        commandFinder.getTemplateCommands().forEach(commandDeclaration -> {
            options.addOption(new Option(commandDeclaration.getName(), true, commandDeclaration.getDescription()));
            optionCallback.put(commandDeclaration.getName(), s ->
                    commandDeclaration.getCommand().create(s).
                            onSuccess(templateCommandReference::set).
                            onFailure(Throwable::printStackTrace)
            );
        });
    }
}
