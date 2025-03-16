package net.bteuk.api.gui;

import lombok.Getter;
import lombok.Setter;
import net.bteuk.api.BTEUKAPI;
import net.bteuk.services.api.Inventory;
import net.bteuk.services.api.ItemStack;
import net.bteuk.services.api.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Gui {

    public static final Map<UUID, Gui> inventoriesByUUID = new HashMap<>();
    public static final Map<UUID, UUID> openInventories = new HashMap<>();

    //Information about the gui.
    private final UUID uuid;
    private final Inventory inv;
    
    @Getter
    private final Map<Integer, GuiAction> actions;

    @Getter
    @Setter
    private boolean deleteOnClose = false;

    public Gui(Inventory inv) {
        this.inv = inv;
        uuid = UUID.randomUUID();
        actions = new HashMap<>();
        inventoriesByUUID.put(this.uuid, this);
    }

    public Inventory getInventory() {
        return inv;
    }

    public void setItem(int slot, ItemStack stack, GuiAction action) {

        inv.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }

    }

    public void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    public void setAction(int slot, GuiAction action){

        if (action != null) {
            actions.put(slot, action);
        }

    }

    public void clearGui() {
        inv.clear();
        actions.clear();
    }

    public void open(Player player) {
        player.openInventory(inv);
        openInventories.put(player.getUuid(), this.uuid);
    }

    public void delete() {
        for (Player p : BTEUKAPI.getOnlinePlayers()) {
            openInventories.remove(p.getUuid(), this.uuid);
        }
        inventoriesByUUID.remove(this.uuid);
    }

    public abstract void refresh();
}
