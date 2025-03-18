package net.bteuk.gui;

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
@Getter
public abstract class Gui {

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

    public void setItem(int slot, ItemStack stack, GuiAction action) {
        inventory.setItem(slot, stack);
        setAction(slot, action);
    }

    public void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    public void setAction(int slot, GuiAction action) {
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void clearGui() {
        inventory.clear();
        actions.clear();
    }

    public void open(Player player) {
        player.openInventory(inventory);
        GuiManager.openGui(player, this);
    }

    public void delete() {
        GuiManager.unregisterGuiByUuid(this.uuid);
    }
}
