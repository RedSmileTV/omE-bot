package de.redsmiletv;


import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.util.Random;


public class Commands {
    private TwitchChat chat;
    private Random random = new Random();

    public Commands(TwitchChat pChat) {
        chat = pChat;
    }

    public void say(ChannelMessageEvent event) {
        if (!event.getMessage().toString().startsWith("!say")) return;
        chat.sendMessage("redsmiletv", event.getMessage().toString().substring(5));
    }

    public void weather(ChannelMessageEvent event) {
        // TODO Weather API integration

        if (!event.getMessage().toString().startsWith("!weather")) return;

        String locationName = event.getMessage().toString().substring(9);

        if (locationName.isEmpty()) {
            chat.sendMessage("redsmiletv", "Please provide a location");
            return;
        }


        chat.sendMessage("redsmiletv", "The weather is nice today");
    }

    public void randomOmE(ChannelMessageEvent event) {
        // Maybe change it to only generate a number when message contains omE to save resources
        int randomInt = random.nextInt(100);
        System.out.println(randomInt);

        // 1% chance to send omE
        if (!(randomInt == 69 && event.getMessage().contains("omE"))) return;
        chat.sendMessage("redsmiletv", "omE");
    }







}
