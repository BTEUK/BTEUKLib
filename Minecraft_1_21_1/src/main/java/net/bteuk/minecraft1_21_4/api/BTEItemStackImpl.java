package net.bteuk.minecraft1_21_4.api;

import lombok.Getter;
import net.bteuk.api.BTEItemStack;
import org.bukkit.inventory.ItemStack;

@Getter
public record BTEItemStackImpl(ItemStack itemStack) implements BTEItemStack<ItemStack> { }
