package com.mammothproxy.mod.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Screen.class)
public interface TitleScreenAccessor1_16_5
{
    @Accessor(value = "buttons")
    List<ClickableWidget> getButtons();
}