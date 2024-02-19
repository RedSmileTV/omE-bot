package de.redsmiletv;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.ITwitchChat;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;

public class Main {
    public static void main(String[] args) {

        OAuth2Credential credential = new OAuth2Credential("twitch", "vbmggarmcie07qp72ap02hhvks8klw");

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();

        //twitchClient.getClientHelper().enableStreamEventListener("redsmiletv");

        twitchClient.getChat().joinChannel("redsmiletv");
        twitchClient.getChat().sendMessage("redsmiletv", "omE");




    }
}