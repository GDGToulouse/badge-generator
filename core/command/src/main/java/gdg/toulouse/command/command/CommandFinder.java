package gdg.toulouse.command.command;

import gdg.toulouse.command.finder.ClassPathFinder;
import org.apache.commons.vfs2.FileObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandFinder {

    private final Collection<Properties> properties;

    public CommandFinder() {
        this.properties = lift(givenCommandClassPathFinder().getResources().stream().map(this::getProperties));
    }

    public Collection<CommandDeclaration<AttendeeCommand>> getAttendeeCommands() {
        return lift(this.properties.stream().map(CommandFactory::getAttendeeFromProperties));
    }

    public Collection<CommandDeclaration<TemplateCommand>> getTemplateCommands() {
        return lift(this.properties.stream().map(CommandFactory::getTemplateFromProperties));
    }

    private <T> Collection<T> lift(Stream<Optional<T>> list) {
        return list.
                filter(Optional::isPresent).
                map(Optional::get).
                collect(Collectors.toList());
    }

    private Optional<Properties> getProperties(FileObject fileObject) {
        try {
            try (InputStream inputStream = fileObject.getContent().getInputStream()) {
                final Properties properties = new Properties();
                properties.load(inputStream);
                return Optional.of(properties);
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private ClassPathFinder givenCommandClassPathFinder() {
        return new ClassPathFinder(Pattern.compile("command[.].*[.]props"));
    }

}
