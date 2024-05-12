package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import com.misterpemodder.shulkerboxtooltip.impl.util.ShulkerBoxTooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public record FixedPreviewProviderRegistry<B extends BlockEntity & Inventory>(PreviewProviderRegistry registry,
                                                                              BiFunction<Integer, Supplier<B>, PreviewProvider> providerFactory) {
  public FixedPreviewProviderRegistry<B> register(String id, int maxRowSize,
      BiFunction<BlockPos, BlockState, B> blockEntityFactory, Block block) {
    var provider = providerFactory.apply(maxRowSize,
        () -> blockEntityFactory.apply(BlockPos.ORIGIN, block.getDefaultState()));
    registry.register(ShulkerBoxTooltipUtil.id(id), provider, block.asItem());
    return this;
  }
}
