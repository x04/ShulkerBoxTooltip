package com.misterpemodder.shulkerboxtooltip.impl.provider;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.BlockEntityPreviewProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.LootableInventory;

import java.util.function.Supplier;

/**
 * A {@link BlockEntityPreviewProvider} that uses a {@link Inventory} instance to get its information.
 */
public class InventoryAwarePreviewProvider<I extends Inventory> extends BlockEntityPreviewProvider {

  private final Supplier<? extends I> inventoryFactory;

  private final ThreadLocal<I> cachedInventory = ThreadLocal.withInitial(() -> null);

  public InventoryAwarePreviewProvider(int maxRowSize, Supplier<? extends I> inventoryFactory) {
    super(27, false, maxRowSize);
    this.inventoryFactory = inventoryFactory;
  }

  private I getInventory() {
    I inv = this.cachedInventory.get();
    if (inv == null) {
      inv = this.inventoryFactory.get();
      this.cachedInventory.set(inv);
    }
    return inv;
  }

  @Override
  public int getInventoryMaxSize(PreviewContext context) {
    return this.getInventory().size();
  }

  @Override
  public boolean canUseLootTables() {
    return this.getInventory() instanceof LootableInventory;
  }
}
