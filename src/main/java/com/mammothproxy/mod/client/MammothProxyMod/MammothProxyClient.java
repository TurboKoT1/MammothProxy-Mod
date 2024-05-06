package com.mammothproxy.mod.client.MammothProxyMod;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;

public class MammothProxyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding jumpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bots.jump", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "key.category.bots.jump"));
        KeyBinding sneakKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bots.sneak", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.category.bots.sneak"));

        ClientTickCallback.EVENT.register(client -> {
            if (client.player != null && client.player.getServer() != null && client.player.getServer().getServerIp().contains("mam-proxy.xyz")){
                while (jumpKey.wasPressed()) {
                    client.player.sendChatMessage("$ bots jump");
                }

                while (sneakKey.wasPressed()) {
                    client.player.sendChatMessage("$ bots sneak");
                }
            }
        });
    }
}