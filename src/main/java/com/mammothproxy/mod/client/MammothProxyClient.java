package com.mammothproxy.mod.client;

import com.mammothproxy.mod.imgui.ImGuiImpl;

import imgui.ImGui;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import java.util.HashMap;
import java.util.Map;

public class MammothProxyClient implements ClientModInitializer {
    private final Map<KeyBinding, Integer> keyPressTimes = new HashMap<>();

    @Override
    public void onInitializeClient() {
        KeyBinding guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.open.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "key.categpry.gui"));

        KeyBinding[] movementKeys = {
                KeyBindingHelper.registerKeyBinding(new KeyBinding("forward", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, "key.category.bots")),
                KeyBindingHelper.registerKeyBinding(new KeyBinding("back", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "key.category.bots")),
                KeyBindingHelper.registerKeyBinding(new KeyBinding("right", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, "key.category.bots")),
                KeyBindingHelper.registerKeyBinding(new KeyBinding("left", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, "key.category.bots"))
        };

        KeyBinding jumpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bots.jump", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "key.category.bots"));
        KeyBinding sneakKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bots.sneak", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.category.bots"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null){
                for (KeyBinding key : movementKeys) {
                    if (key.isPressed()) {
                        keyPressTimes.compute(key, (k, v) -> v == null ? 1 : v + 1);
                    } else {
                        int pressTime = keyPressTimes.getOrDefault(key, 0);
                        if (pressTime > 0) {
                            long pressTimeMs = (long) (pressTime / 0.02);
                            client.player.sendChatMessage("$ bots move " + key.getTranslationKey() + " " + pressTimeMs);
                            keyPressTimes.put(key, 0);
                        }
                    }
                }

                if (jumpKey.wasPressed()) {
                    client.player.sendChatMessage("$ bots jump");
                }

                if (sneakKey.wasPressed()) {
                    client.player.sendChatMessage("$ bots sneak");
                }

                if (guiKey.wasPressed()){
                    // Soon..
                }
            }
        });
    }
}