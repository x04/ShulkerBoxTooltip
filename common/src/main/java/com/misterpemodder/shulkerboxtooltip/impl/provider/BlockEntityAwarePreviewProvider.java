package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.BlockEntityPreviewProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventory;

import java.util.function.Supplier;

/**
 * A {@link BlockEntityPreviewProvider} that uses a {@link BlockEntity} instance to get its information.
 */
public class BlockEntityAwarePreviewProvider<B extends BlockEntity & Inventory> extends BlockEntityPreviewProvider {

  private final Supplier<? extends B> blockEntityFactory;

  private final ThreadLocal<B> cachedBlockEntity = ThreadLocal.withInitial(() -> null);

  public BlockEntityAwarePreviewProvider(int maxRowSize, Supplier<? extends B> blockEntityFactory) {
    super(27, false, maxRowSize);
    this.blockEntityFactory = blockEntityFactory;
  }

  private B getBlockEntity() {
    B be = this.cachedBlockEntity.get();
    if (be == null) {
      be = this.blockEntityFactory.get();
      this.cachedBlockEntity.set(be);
    }
    return be;
  }

  @Override
  public int getInventoryMaxSize(PreviewContext context) {
    return this.getBlockEntity().size();
  }

  @Override
  public boolean canUseLootTables() {
    return this.getBlockEntity() instanceof LootableContainerBlockEntity;
  }
}
