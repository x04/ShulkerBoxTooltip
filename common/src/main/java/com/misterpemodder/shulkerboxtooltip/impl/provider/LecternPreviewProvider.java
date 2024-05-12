package com.misterpemodder.shulkerboxtooltip.impl.provider;

import net.minecraft.inventory.Inventory;

import java.util.function.Supplier;

public class LecternPreviewProvider extends SingleStackBlockEntityPreviewProvider<Inventory> {
  public LecternPreviewProvider(int maxRowSize, Supplier<? extends Inventory> inventoryFactory) {
    super(maxRowSize, inventoryFactory, "Book");
  }
}
