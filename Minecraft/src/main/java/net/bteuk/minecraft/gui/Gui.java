package net.bteuk.minecraft.gui;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Instance of a Gui, a Gui is a Minecraft inventory containing items.
 * The player can interact with items to trigger actions.
 */
public abstract class Gui {

    @Getter
    private final UUID uuid;
    private final Inventory inventory;
    private final Map<Integer, GuiAction> actions;

    @Setter
    private boolean deleteOnClose = false;

    public Gui(int inventorySize, Component inventoryName) {
        this(Bukkit.createInventory(null, inventorySize, inventoryName));
    }

    public Gui(Inventory inventory) {
        this.inventory = inventory;
        uuid = UUID.randomUUID();
        actions = new HashMap<>();
    }

    public void setItem(int slot, ItemStack stack, net.bteuk.minecraft.gui.GuiAction action) {
        inventory.setItem(slot, stack);
        setAction(slot, action);
    }

    public void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    public net.bteuk.minecraft.gui.GuiAction getAction(int slot) {
        return actions.get(slot);
    }

    public void setAction(int slot, net.bteuk.minecraft.gui.GuiAction action) {
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void removeAction(int slot) {
        actions.remove(slot);
    }

    public void clear() {
        inventory.clear();
        actions.clear();
    }

    public void open(Player player) {
        player.openInventory(inventory);
        GuiManager.openGui(player, this);
    }

    public void close(Player player) {
        // If the gui should delete on close, delete it.
        if (this.deleteOnClose) {
            delete();
        } else {
            //Remove the player from the list of open inventories.
            net.bteuk.minecraft.gui.GuiManager.closeGui(player);
        }
    }

    public void delete() {
        net.bteuk.minecraft.gui.GuiManager.unregisterGuiByUuid(this.uuid);
    }
}
