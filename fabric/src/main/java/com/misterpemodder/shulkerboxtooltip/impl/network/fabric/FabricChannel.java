package com.misterpemodder.shulkerboxtooltip.impl.network.fabric;

import com.misterpemodder.shulkerboxtooltip.impl.network.channel.Channel;
import com.misterpemodder.shulkerboxtooltip.impl.network.context.MessageContext;
import com.misterpemodder.shulkerboxtooltip.impl.network.message.MessageType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

abstract class FabricChannel<T> implements Channel<T> {
  protected final CustomPayload.Id<Payload<T>> id;
  protected final MessageType<T> type;
  protected final PacketCodec<RegistryByteBuf, Payload<T>> codec;

  protected FabricChannel(Identifier id, MessageType<T> type) {
    this.id = new CustomPayload.Id<>(id);
    this.type = type;
    this.codec = PacketCodec.ofStatic(this::encodePayload, this::decodePayload);
  }

  @Override
  public Identifier getId() {
    return this.id.id();
  }

  @Override
  public MessageType<T> getMessageType() {
    return this.type;
  }

  @Override
  public void onRegister(MessageContext<T> context) {
    this.type.onRegister(context);
  }

  @Override
  public void onUnregister(MessageContext<T> context) {
    this.type.onUnregister(context);
  }

  private void encodePayload(RegistryByteBuf buf, Payload<T> message) {
    this.type.encode(message.value(), buf);
  }

  private Payload<T> decodePayload(RegistryByteBuf buf) {
    return new Payload<>(this.id, this.type.decode(buf));
  }

}
