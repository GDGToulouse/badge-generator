package gdg.toulouse.command.command;

public class CommandDeclaration<E extends Command<?>> {
    private final String name;
    private final String description;
    private final E command;

    public CommandDeclaration(String name, String description, E command) {
        this.name = name;
        this.description = description;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public E getCommand() {
        return command;
    }
}
