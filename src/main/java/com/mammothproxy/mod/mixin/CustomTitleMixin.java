package com.mammothproxy.mod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class CustomTitleMixin
{
	@Inject(at = @At(value = "RETURN"), method = "getWindowTitle", cancellable = true)
	private void getWindowTitle(final CallbackInfoReturnable<String> info)
	{
		MinecraftClient client = (MinecraftClient) (Object) this;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("MammothProxy MOD [V1.0 BETA]");

		ClientPlayNetworkHandler clientPlayNetworkHandler = client.getNetworkHandler();
		if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isOpen())
		{
			stringBuilder.append(" - ");
			if (client.getServer() != null && !client.getServer().isRemote())
			{
				stringBuilder.append(I18n.translate("title.singleplayer", new Object[0]));
			}
			else if (client.getServer() == null && (client.getCurrentServerEntry() == null || !client.getCurrentServerEntry().isLocal()))
			{
				stringBuilder.append(I18n.translate("title.multiplayer.other", new Object[0]));
			}
			else
			{
				stringBuilder.append(I18n.translate("title.multiplayer.lan", new Object[0]));
			}
		}

		info.setReturnValue(stringBuilder.toString());
	}
}