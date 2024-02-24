package de.redsmiletv;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Main {
    private TwitchClient twitchClient;
    private TwitchChat chat;
    private String TOKEN;
    private String CHANNEL_NAME;
    private CommandHandler commandHandler;
    ArrayList<EventUser> lurkers = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();

        // TODO web UI?
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

        chat = twitchClient.getChat();

        chat.joinChannel(CHANNEL_NAME);
        chat.sendMessage(CHANNEL_NAME, "Bot ist jetzt da!");

        commandHandler = new CommandHandler();
        startEvents();


        commandHandler.registerCommand(new Command("say") {
            @Override
            void execute(ChannelMessageEvent event, String... args) {
                String message = String.join(" ", args);
                chat.sendMessage(CHANNEL_NAME, message);
            }
        });

        commandHandler.registerCommand(new Command("lurk") {



            @Override
            void execute(ChannelMessageEvent event, String... args) {
                lurkers.add(event.getUser());
                chat.sendMessage(CHANNEL_NAME, event.getUser().getName() + " is now lurking");
            }
        });



    }

    private void startEvents() {
        EventManager eventManager = twitchClient.getEventManager();

        eventManager.onEvent(ChannelMessageEvent.class, event -> {
            commandHandler.handleCommand(event);

            // region lurk
            if (lurkers.contains(event.getUser()) && !event.getMessage().contains("lurk")) {
                lurkers.remove(event.getUser());
                chat.sendMessage(CHANNEL_NAME, event.getUser().getName() + " is no longer lurking");
            }

            // region Random omE
            int randomInt = new Random().nextInt(100);
            System.out.println(randomInt);
            if (!(randomInt == 69 && event.getMessage().contains("omE"))) return;
            chat.sendMessage("redsmiletv", "omE");
        });

    }
}