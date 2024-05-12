package com.misterpemodder.shulkerboxtooltip.impl;

import com.misterpemodder.shulkerboxtooltip.ShulkerBoxTooltip;
import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.impl.config.Configuration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record PreviewContextImpl(ItemStack stack, @Nullable PlayerEntity owner, Configuration config,
                                 @Nullable RegistryWrapper.WrapperLookup registryLookup) implements PreviewContext {
  public static class Builder implements PreviewContext.Builder {
    private final ItemStack stack;
    private PlayerEntity owner;
    private RegistryWrapper.WrapperLookup registryLookup;

    public Builder(ItemStack stack) {
      this.stack = stack;
    }

    @Override
    public Builder withOwner(@Nullable PlayerEntity owner) {
      this.owner = owner;
      return this;
    }

    @Override
    public Builder withRegistryLookup(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
      this.registryLookup = registryLookup;
      return this;
    }

    @Nonnull
    @Override
    public PreviewContext build() {
      if (this.registryLookup == null && this.owner != null) {
        this.registryLookup = this.owner.getWorld() != null ? this.owner.getWorld().getRegistryManager() : null;
      }
      return new PreviewContextImpl(this.stack, this.owner, ShulkerBoxTooltip.config, this.registryLookup);
    }
  }
}
