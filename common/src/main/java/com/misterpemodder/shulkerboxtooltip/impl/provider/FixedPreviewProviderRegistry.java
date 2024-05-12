package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import com.misterpemodder.shulkerboxtooltip.impl.util.ShulkerBoxTooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public record FixedPreviewProviderRegistry<I extends Inventory>(PreviewProviderRegistry registry,
                                                                BiFunction<Integer, Supplier<I>, PreviewProvider> providerFactory) {
  public FixedPreviewProviderRegistry<I> register(String id, int maxRowSize,
      BiFunction<BlockPos, BlockState, I> inventoryFactory, Block block) {
    var provider = providerFactory.apply(maxRowSize,
        () -> inventoryFactory.apply(BlockPos.ORIGIN, block.getDefaultState()));
    registry.register(ShulkerBoxTooltipUtil.id(id), provider, block.asItem());
    return this;
  }
}
