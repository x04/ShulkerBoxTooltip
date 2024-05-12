package com.misterpemodder.shulkerboxtooltip.mixin.client.fabric;

import com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltipClient;
import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.impl.tooltip.PreviewTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(ItemStack.class)
public class ItemStackMixin {
  @Inject(at = @At("HEAD"), method = "getTooltipData()Ljava/util/Optional;", cancellable = true)
  private void onGetTooltipData(CallbackInfoReturnable<Optional<TooltipData>> cir) {
    PreviewContext context = PreviewContext.builder((ItemStack) (Object) this).withOwner(
        ShulkerBoxTooltipClient.client == null ? null : ShulkerBoxTooltipClient.client.player).build();

    //noinspection UnreachableCode
    if (ShulkerBoxTooltipApi.isPreviewAvailable(context))
      cir.setReturnValue(Optional.of(
          new PreviewTooltipData(ShulkerBoxTooltipApi.getPreviewProviderForStack(context.stack()), context)));
  }

  @Inject(at = @At("RETURN"), method = "getTooltip(Lnet/minecraft/item/Item$TooltipContext;"
      + "Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipType;)Ljava/util/List;")
  private void onGetTooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type,
      CallbackInfoReturnable<List<Text>> cir) {
    var tooltip = cir.getReturnValue();
    ShulkerBoxTooltipClient.modifyStackTooltip((ItemStack) (Object) this, tooltip::addAll);
  }
}
