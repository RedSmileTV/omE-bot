package de.redsmiletv;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public abstract class Command {
    private final String command;

    public Command(String command) {
        this.command = command;
    }
    abstract void execute(ChannelMessageEvent event, String... args);

    public String getCommand() {
        return command;
    }


}
