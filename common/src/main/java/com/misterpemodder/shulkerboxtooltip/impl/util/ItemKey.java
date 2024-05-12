package com.misterpemodder.shulkerboxtooltip.impl.util;

import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.Objects;

/**
 * Used as a key in maps
 */
public class ItemKey {
  private final Item item;
  private final int id;
  private final ComponentMap components;
  private final boolean ignoreComponents;

  public ItemKey(ItemStack stack, boolean ignoreComponents) {
    this.item = stack.getItem();
    this.id = Registries.ITEM.getRawId(this.item);
    this.components = stack.getComponents();
    this.ignoreComponents = ignoreComponents;
  }

  @Override
  public int hashCode() {
    return 31 * id + (this.ignoreComponents || components == null ? 0 : components.hashCode());
  }

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof ItemKey key))
      return false;

    return key.item == this.item && key.id == this.id && (this.ignoreComponents || Objects.equals(key.components,
        this.components));
  }
}
