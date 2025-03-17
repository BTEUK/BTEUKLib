package net.bteuk.minecraft1_21_4.api;

import lombok.Getter;
import net.bteuk.api.BTEInventory;
import net.bteuk.api.BTEItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public record BTEInventoryImpl(Inventory inventory) implements BTEInventory<Inventory> {

    @Override
    public void setItem(int index, BTEItemStack<?> bteItemStack) {
        if (bteItemStack.itemStack() instanceof ItemStack itemStack) {
            inventory.setItem(index, itemStack);
        }
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
