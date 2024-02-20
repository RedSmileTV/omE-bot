package de.redsmiletv;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.api.domain.IEventSubscription;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    // Temp credentials, will be replaced with .env or .properties file
    public TwitchClient twitchClient;
    private String TOKEN;
    private String CHANNEL_NAME;
    private Commands commands;

    public static void main(String[] args) {
        new Main().run();

        // Need to check on that later
        //twitchClient.getClientHelper().enableStreamEventListener("redsmiletv");
    }

    private void init() {
        File file = new File("src/main/resources/.env");
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            System.out.println("File not found");
        }

        TOKEN = properties.getProperty("TOKEN");
        CHANNEL_NAME = properties.getProperty("CHANNEL_NAME");

    }

    private void run() {
        init();

        OAuth2Credential credential = new OAuth2Credential("twitch", TOKEN);
        twitchClient = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();


        twitchClient.getChat().joinChannel(CHANNEL_NAME);
        twitchClient.getChat().sendMessage(CHANNEL_NAME, "Bot ist jetzt da!");

        commands = new Commands(twitchClient.getChat());
        startEvents();
    }

    private void startEvents() {
        EventManager eventManager = twitchClient.getEventManager();

        eventManager.onEvent(ChannelMessageEvent.class, event -> {
            commands.say(event);
            commands.weather(event);
        });

    }
}