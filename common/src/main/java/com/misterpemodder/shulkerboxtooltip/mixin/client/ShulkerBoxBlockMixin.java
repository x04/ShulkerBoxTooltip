package com.misterpemodder.shulkerboxtooltip.mixin.client;

import com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltip;
import com.misterpemodder.shulkerboxtooltip.impl.config.Configuration.ShulkerBoxTooltipType;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
  @Inject(at = @At("HEAD"), method = "Lnet/minecraft/block/ShulkerBoxBlock;appendTooltip"
      + "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item$TooltipContext;"
      + "Ljava/util/List;Lnet/minecraft/client/item/TooltipType;)V", cancellable = true)
  private void onAppendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options,
      CallbackInfo ci) {
    if (ShulkerBoxTooltip.config != null && ShulkerBoxTooltip.config.tooltip.type != ShulkerBoxTooltipType.VANILLA)
      ci.cancel();
  }
}
