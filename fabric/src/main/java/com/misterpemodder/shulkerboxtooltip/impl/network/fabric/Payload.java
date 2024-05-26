package com.misterpemodder.shulkerboxtooltip.impl.network.fabric;

import net.minecraft.network.packet.CustomPayload;

record Payload<T>(CustomPayload.Id<?> id, T value) implements CustomPayload {
  @Override
  public Id<? extends CustomPayload> getId() {
    return this.id;
  }
}
