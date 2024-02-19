package de.redsmiletv;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;

public class Main {
    // Temp credentials, will be replaced with .env or .properties file
    public static OAuth2Credential credential = new OAuth2Credential("twitch", "vbmggarmcie07qp72ap02hhvks8klw");
    public static void main(String[] args) {

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();

        // For listening messages like commands example !lol
        twitchClient.getClientHelper().enableStreamEventListener("redsmiletv");

        TwitchChat chat = twitchClient.getChat();

        chat.joinChannel("redsmiletv");
        //chat.sendMessage("redsmiletv", "omE");


        chat.getEventManager().onEvent(IRCMessageEvent.class, event -> {
            System.out.println(event.getMessage().toString());
            if (event.getMessage().toString().contains("[omE]")) {
                chat.sendMessage("redsmiletv", "omE");
            }
        });
    }
}