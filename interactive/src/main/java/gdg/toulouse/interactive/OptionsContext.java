package gdg.toulouse.interactive;

import gdg.toulouse.attendee.service.AttendeeRepository;
import gdg.toulouse.command.command.Command;
import gdg.toulouse.command.command.CommandDeclaration;
import gdg.toulouse.command.command.CommandFinder;
import gdg.toulouse.data.Try;
import gdg.toulouse.template.service.TemplateRepository;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

class OptionsContext {
    private final Options options;
    private final Map<String, Consumer<String>> optionCallback;

    private final AtomicReference<AttendeeRepository> attendeeRepositoryReference;
    private final AtomicReference<TemplateRepository> templateRepositoryReference;

    OptionsContext() {
        this.options = new Options();
        this.optionCallback = new HashMap<>();
        this.attendeeRepositoryReference = new AtomicReference<>();
        this.templateRepositoryReference = new AtomicReference<>();

        this.populateOptions();
    }

    Options getOptions() {
        return options;
    }

    AttendeeRepository getAttendeeRepository() {
        return attendeeRepositoryReference.get();
    }

    TemplateRepository getTemplateRepository() {
        return templateRepositoryReference.get();
    }

    Try<CommandLine> parse(String[] args) {
        try {
            final CommandLine parse = new DefaultParser().parse(options, args);

            Arrays.asList(parse.getOptions()).forEach(option -> {
                if (optionCallback.keySet().contains(option.getOpt())) {
                    optionCallback.get(option.getOpt()).accept(option.getValue());
                }
            });

            if (attendeeRepositoryReference.get() == null) {
                throw new ParseException("Waiting for an Attendee Repository");
            }

            if (templateRepositoryReference.get() == null) {
                throw new ParseException("Waiting for a Template Repository");
            }

            return Try.success(parse);
        } catch (ParseException e) {
            return Try.failure(e);
        }
    }

    private void populateOptions() {
        final CommandFinder commandFinder = new CommandFinder();

        commandFinder.getAttendeeCommands().forEach(commandDeclaration -> {
            addOption(commandDeclaration, attendeeRepositoryReference::set);
        });

        commandFinder.getTemplateCommands().forEach(commandDeclaration -> {
            addOption(commandDeclaration, templateRepositoryReference::set);
        });
    }

    private <T> void addOption(CommandDeclaration<? extends Command<T>> commandDeclaration, Consumer<T> commandConsumer) {
        this.options.addOption(new Option(commandDeclaration.getName(), true, commandDeclaration.getDescription()));
        this.optionCallback.put(commandDeclaration.getName(), s -> {
                    commandDeclaration.getCommand().create(s).
                            onSuccess(commandConsumer).
                            onFailure(Throwable::printStackTrace);
                }
        );
    }

}
