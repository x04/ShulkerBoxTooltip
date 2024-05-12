package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.function.Supplier;

public class SingleStackBlockEntityPreviewProvider<I extends Inventory> extends InventoryAwarePreviewProvider<I> {
  private final String itemTagName;

  public SingleStackBlockEntityPreviewProvider(int maxRowSize, Supplier<? extends I> inventoryFactory,
      String itemTagName) {
    super(maxRowSize, inventoryFactory);
    this.itemTagName = itemTagName;
  }

  @Override
  public List<ItemStack> getInventory(PreviewContext context) {
    int invMaxSize = this.getInventoryMaxSize(context);
    List<ItemStack> inv = DefaultedList.ofSize(invMaxSize, ItemStack.EMPTY);
    NbtCompound blockEntityTag = context.stack().getSubNbt("BlockEntityTag");

    if (blockEntityTag != null && blockEntityTag.contains(this.itemTagName, 10)) {
      NbtCompound itemTag = blockEntityTag.getCompound(this.itemTagName);
      ItemStack s = ItemStack.fromNbt(itemTag);

      inv.set(0, s);
    }

    return inv;
  }
}
