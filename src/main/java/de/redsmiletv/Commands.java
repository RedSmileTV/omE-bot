package de.redsmiletv;


import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;


public class Commands {
    private TwitchChat chat;

    public Commands(TwitchChat pChat) {
        chat = pChat;
    }
    private void say() {
        chat.getEventManager().onEvent(ChannelMessageEvent.class, event -> {


            if (event.getMessage().toString().startsWith("!say")) {
                chat.sendMessage("redsmiletv", event.getMessage().toString().substring(5));
            }
        });


    }





}
