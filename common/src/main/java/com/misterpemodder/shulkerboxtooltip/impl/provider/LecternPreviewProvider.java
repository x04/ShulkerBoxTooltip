package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.mojang.serialization.MapCodec;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.function.Supplier;

public class LecternPreviewProvider extends InventoryAwarePreviewProvider<Inventory> {
  private static final MapCodec<ItemStack> CODEC = ItemStack.CODEC.fieldOf("Book");

  public LecternPreviewProvider(int maxRowSize, Supplier<? extends Inventory> inventoryFactory) {
    super(maxRowSize, inventoryFactory);
  }

  @Override
  public List<ItemStack> getInventory(PreviewContext context) {
    int invMaxSize = this.getInventoryMaxSize(context);
    List<ItemStack> inv = DefaultedList.ofSize(invMaxSize, ItemStack.EMPTY);
    NbtComponent nbtComponent = context.stack().get(DataComponentTypes.BLOCK_ENTITY_DATA);

    if (nbtComponent != null)
      nbtComponent.get(CODEC).result().ifPresent(book -> inv.set(0, book));

    return inv;
  }

  @Override
  public boolean showTooltipHints(PreviewContext context) {
    return context.stack().contains(DataComponentTypes.BLOCK_ENTITY_DATA);
  }
}
