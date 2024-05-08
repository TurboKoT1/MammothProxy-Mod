package com.mammothproxy.mod.discord.rpc;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordRPC {
    private boolean running = true;
    private long created = 0;

    public void start() {
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {

        }).build();

        net.arikia.dev.drpc.DiscordRPC.discordInitialize("1196399756793090098", handlers, true);

        new Thread("Discord Rpc Callback") {
            @Override
            public void run() {
                while(running){
                    net.arikia.dev.drpc.DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void stop() {
        running = false;
        net.arikia.dev.drpc.DiscordRPC.discordShutdown();
    }

    public void update(String state) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(state);
        b.setBigImage("logo", "MOD V1.0");
        b.setStartTimestamps(created);

        net.arikia.dev.drpc.DiscordRPC.discordUpdatePresence(b.build());
    }
}