package net.bteuk.api.gui;

import lombok.Getter;

import lombok.Setter;
import net.bteuk.api.BTEInventory;
import net.bteuk.api.BTEItemStack;
import net.bteuk.api.BTEPlayer;
import net.bteuk.provider.BTEPlayerProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Gui {

    public static final Map<UUID, Gui> inventoriesByUUID = new HashMap<>();
    public static final Map<UUID, UUID> openInventories = new HashMap<>();

    //Information about the gui.
    private final UUID uuid;
    private final BTEInventory<?> inv;

    private final BTEPlayerProvider playerProvider;
    
    @Getter
    private final Map<Integer, GuiAction> actions;

    @Getter
    @Setter
    private boolean deleteOnClose = false;

    public Gui(BTEInventory<?> inv, BTEPlayerProvider playerProvider) {
        this.inv = inv;
        this.playerProvider = playerProvider;
        uuid = UUID.randomUUID();
        actions = new HashMap<>();
        inventoriesByUUID.put(this.uuid, this);
    }

    public BTEInventory<?> getInventory() {
        return inv;
    }

    public void setItem(int slot, BTEItemStack<?> stack, GuiAction action) {

        inv.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }

    }

    public void setItem(int slot, BTEItemStack<?> stack) {
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

    public void open(BTEPlayer BTEPlayer) {
        BTEPlayer.openInventory(inv);
        openInventories.put(BTEPlayer.getUuid(), this.uuid);
    }

    public void delete() {
        for (BTEPlayer p : playerProvider.getPlayers()) {
            openInventories.remove(p.getUuid(), this.uuid);
        }
        inventoriesByUUID.remove(this.uuid);
    }

    public abstract void refresh();
}
