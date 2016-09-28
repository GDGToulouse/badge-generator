package gdg.toulouse.command.command;

import java.util.Optional;
import java.util.Properties;

public class CommandFactory {

    private static final String ARGUMENT_NAME = "argument.name";
    private static final String ARGUMENT_DESCRIPTION = "argument.description";
    private static final String COMMAND_CLASS = "command.class";

    public static Optional<CommandDeclaration<AttendeeCommand>> getAttendeeFromProperties(Properties properties) {
        return getCommandFromProperties(AttendeeCommand.class, properties);
    }

    public static Optional<CommandDeclaration<TemplateCommand>> getTemplateFromProperties(Properties properties) {
        return getCommandFromProperties(TemplateCommand.class, properties);
    }

    private static <T extends Command<?>> Optional<CommandDeclaration<T>> getCommandFromProperties(Class<T> type, Properties properties) {
        return getProperty(properties, ARGUMENT_NAME).flatMap(name ->
                getProperty(properties, ARGUMENT_DESCRIPTION).flatMap(description ->
                        getProperty(properties, COMMAND_CLASS).flatMap(clazz ->
                                getAttendeCommand(type, name, description, clazz)
                        )
                )
        );
    }

    private static Optional<String> getProperty(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }

    private static <T extends Command<?>> Optional<CommandDeclaration<T>> getAttendeCommand(Class<T> type, String name, String description, String clazz) {
        try {
            return Optional.of(new CommandDeclaration<>(name, description, type.cast(Class.forName(clazz).newInstance())));
        } catch (Throwable throwable) {
            return Optional.empty();
        }
    }

}
