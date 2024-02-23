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
import java.util.Random;

public class Main {
    public TwitchClient twitchClient;
    private String TOKEN;
    private String CHANNEL_NAME;
    private CommandHandler commandHandler;

    public static void main(String[] args) {
        new Main().run();

        // TODO test a Swing UI to control the bot

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

        commandHandler = new CommandHandler();
        startEvents();


        commandHandler.registerCommand(new Command("say") {
            @Override
            void execute(String... args) {
                String message = String.join(" ", args);
                twitchClient.getChat().sendMessage(CHANNEL_NAME, message);
            }
        });



    }

    private void startEvents() {
        EventManager eventManager = twitchClient.getEventManager();

        eventManager.onEvent(ChannelMessageEvent.class, event -> {
            commandHandler.handleCommand(event);

            // region Random omE
            // Maybe change it to only generate a number when message contains omE to save resources
            int randomInt = new Random().nextInt(100);
            System.out.println(randomInt);
            // 1% chance to send omE
            if (!(randomInt == 69 && event.getMessage().contains("omE"))) return;
            twitchClient.getChat().sendMessage("redsmiletv", "omE");
            // endregion
        });

    }
}