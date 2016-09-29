package gdg.toulouse.command.command;

public class CommandDeclaration<C extends Command<?>> {
    private final String name;
    private final String description;
    private final C command;

    public CommandDeclaration(String name, String description, C command) {
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

    public C getCommand() {
        return command;
    }
}
