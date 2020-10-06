package com.pl.donutsheriff;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Main {

    public static ShardManager manager;

    public static void main(String[] args) throws LoginException {
        init();
    }

    private static void init() throws LoginException {
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("NjUzNjc1MzMxMzI4NDA5NjE4.Xj_7Ww.8CWeVl0-SOhgEC9B1ankBPbDmr0");
        builder.setActivity(Activity.of(Activity.ActivityType.DEFAULT, "reloading"));

        builder.addEventListeners(new Muter());

        manager = builder.build();
    }
}
