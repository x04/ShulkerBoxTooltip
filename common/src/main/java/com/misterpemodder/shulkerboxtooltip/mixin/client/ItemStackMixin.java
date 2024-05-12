package com.misterpemodder.shulkerboxtooltip.mixin.client;

import com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltip;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {
  @Inject(at = @At("HEAD"), method = "appendTooltip(Lnet/minecraft/component/DataComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipType;)V", cancellable = true)
  private void removeLore(DataComponentType<?> componentType, Item.TooltipContext context, Consumer<Text> textConsumer,
      TooltipType type, CallbackInfo ci) {
    if (componentType == DataComponentTypes.LORE) {
      Item item = ((ItemStack) (Object) this).getItem();

      //noinspection UnreachableCode
      if (ShulkerBoxTooltip.config != null && ShulkerBoxTooltip.config.tooltip.hideShulkerBoxLore
          && item instanceof BlockItem blockitem && blockitem.getBlock() instanceof ShulkerBoxBlock) {
        ci.cancel();
      }
    }
  }
}
