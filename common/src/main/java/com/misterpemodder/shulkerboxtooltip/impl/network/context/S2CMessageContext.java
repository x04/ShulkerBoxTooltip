package com.misterpemodder.shulkerboxtooltip.impl.network.context;

import com.misterpemodder.shulkerboxtooltip.impl.network.channel.Channel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public record S2CMessageContext<T>(Channel<T> channel) implements MessageContext<T> {
  @Override
  public void execute(Runnable task) {
    MinecraftClient.getInstance().execute(task);
  }

  @Override
  public ClientPlayerEntity getPlayer() {
    return MinecraftClient.getInstance().player;
  }

  @Override
  public Channel<T> getChannel() {
    return this.channel;
  }

  @Override
  public Side getReceivingSide() {
    return Side.CLIENT;
  }
}
