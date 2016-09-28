package gdg.toulouse.command;

import gdg.toulouse.command.command.CommandFinder;

public final class Main {


    public static void main(String[] args) {
        final CommandFinder commandFinder = new CommandFinder();
        commandFinder.getAttendeeCommands().forEach(commandDeclaration -> {
            System.out.println("attendees - " + commandDeclaration.getName() + " - " + commandDeclaration.getDescription());
        });
        commandFinder.getTemplateCommands().forEach(commandDeclaration -> {
            System.out.println("templates - " + commandDeclaration.getName() + " - " + commandDeclaration.getDescription());
        });
    }

}
