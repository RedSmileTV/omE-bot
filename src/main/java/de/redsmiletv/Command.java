package de.redsmiletv;

public abstract class Command {
    private final String command;

    abstract void execute(String... args);
    public Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }


}
