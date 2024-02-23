package de.redsmiletv;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        commands.put(command.getCommand(), command);
    }

    public void executeCommand(String command, String... args) {
        if (commands.containsKey(command)) {
            getCommand(command).execute(args);
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public void removeCommand(String command) {
        commands.remove(command);
    }

    public void handleCommand(ChannelMessageEvent event) {
        String message = event.getMessage().toLowerCase();
        if (message.startsWith("!")) {
            String[] split = message.split(" ");
            String command = split[0].substring(1);
            String[] args = new String[split.length - 1];
            System.arraycopy(split, 1, args, 0, split.length - 1);
            executeCommand(command, args);
        }
    }

}
