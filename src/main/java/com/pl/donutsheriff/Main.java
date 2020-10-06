package com.pl.donutsheriff;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main {

    public static ShardManager manager;
    public static final String STATUS = "testing donuts";

    public static void main(String[] args) throws LoginException {
        init();
    }

    private static void init() throws LoginException {
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(Token.token);
        builder.setActivity(Activity.of(Activity.ActivityType.DEFAULT, STATUS));

        builder.addEventListeners(new Muter());

        manager = builder.build();
    }
}
