package com.mammothproxy.mod;

import com.mammothproxy.mod.discord.rpc.DiscordRPC;
import com.mammothproxy.mod.imgui.ImGuiImpl;
import imgui.ImGui;
import net.fabricmc.api.ModInitializer;

public class MammothProxyMod implements ModInitializer {
	DiscordRPC discordRPC = new DiscordRPC();

	@Override
	public void onInitialize() {
        discordRPC.start();
		discordRPC.update("In Menu");
    }
}