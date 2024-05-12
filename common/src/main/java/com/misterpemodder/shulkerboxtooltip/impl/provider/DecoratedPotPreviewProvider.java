package com.misterpemodder.shulkerboxtooltip.impl.provider;

import net.minecraft.block.entity.DecoratedPotBlockEntity;

import java.util.function.Supplier;

public class DecoratedPotPreviewProvider extends SingleStackBlockEntityPreviewProvider<DecoratedPotBlockEntity> {
  public DecoratedPotPreviewProvider(int maxRowSize, Supplier<? extends DecoratedPotBlockEntity> inventoryFactory) {
    super(maxRowSize, inventoryFactory, "item");
  }
}
