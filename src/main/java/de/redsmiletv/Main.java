package de.redsmiletv;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
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
    private static String TOKEN;
    private static String CHANNEL_NAME;
    public static TwitchClient twitchClient;
    private static Commands commands;
    public static void main(String[] args) {
        init();
        run();


        // For listening messages like commands example !lol
        //twitchClient.getClientHelper().enableStreamEventListener("redsmiletv");

        //TwitchChat chat = twitchClient.getChat();

        //chat.joinChannel("redsmiletv");
        //chat.sendMessage("redsmiletv", "omE");

//
//        chat.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
//
//            System.out.println(event.getUser() + ": " + event.getMessage().toString());
//
//            if (event.getMessage().toString().startsWith("!say")) {
//                chat.sendMessage("redsmiletv", event.getMessage().toString().substring(5));
//                //chat.sendMessage("redsmiletv", "omE");
//            }
//        });


    }

    private static void init() {
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

    public static void run() {
        OAuth2Credential credential = new OAuth2Credential("twitch", TOKEN);
        twitchClient = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();


        commands = new Commands(twitchClient.getChat());

        twitchClient.getChat().joinChannel(CHANNEL_NAME);
        twitchClient.getChat().sendMessage(CHANNEL_NAME, "omE");
    }

    public static TwitchClient getTwitchClient() {
        return twitchClient;
    }
}