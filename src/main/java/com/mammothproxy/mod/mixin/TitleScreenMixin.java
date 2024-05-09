package com.mammothproxy.mod.mixin;

import com.mammothproxy.mod.imgui.ImGuiImpl;
import imgui.ImGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DirectConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgetsNormal", at = @At(value = "TAIL"))
    public void removeRealmsButton(int y, int spacingY, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
            TitleScreenAccessor1_16_5 ts = (TitleScreenAccessor1_16_5) MinecraftClient.getInstance().currentScreen;

            for (ClickableWidget button : ts.getButtons()) {
                if (button instanceof ButtonWidget) {
                    ButtonWidget buttonWidget = (ButtonWidget) button;
                    Text message = buttonWidget.getMessage();
                    TranslatableText t = (TranslatableText) message;
                    if (t.getKey().equals("menu.online")) {
                        button.visible = false;
                    }
                }
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addCustomButton(int y, int spacingY, CallbackInfo ci){
        this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 2, 200, 20, Text.of("MammothProxy"), (buttonWidget) -> {
            MinecraftClient.getInstance().openScreen(new ConnectScreen(new TitleScreen(), MinecraftClient.getInstance(), new ServerInfo("MammothProxy", "mam-proxy.xyz:25565", false)));
        }));
    }
}
