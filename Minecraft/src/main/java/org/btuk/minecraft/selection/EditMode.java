package org.btuk.minecraft.selection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import org.btuk.minecraft.misc.ComponentUtils;
import org.btuk.minecraft.misc.ItemUtils;

/**
 * Represents the edit mode of a selection for a specific player.
 */
public class EditMode implements Listener {

    private static final ItemStack SELECTION_EDIT_ITEM = ItemUtils.createItem(Material.ARMOR_STAND, 1, ComponentUtils.title("Edit Selection"), ComponentUtils.line("Place the armour stand to edit"), ComponentUtils.line("the selection. Drop to cancel."));

    private final int index;

    private final EditableSelection editableSelection;

    private final Player editingPlayer;

    private final JavaPlugin plugin;

    public EditMode(int index, EditableSelection selection, Player editingPlayer, JavaPlugin plugin) {
        this.index = index;
        this.editableSelection = selection;
        this.editingPlayer = editingPlayer;
        this.plugin = plugin;

        editingPlayer.getInventory().setItemInMainHand(SELECTION_EDIT_ITEM);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void cancel() {
        EntityPlaceEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
        InventoryDragEvent.getHandlerList().unregister(this);
        InventoryMoveItemEvent.getHandlerList().unregister(this);
        PlayerSwapHandItemsEvent.getHandlerList().unregister(this);

        editingPlayer.getInventory().removeItem(SELECTION_EDIT_ITEM);
        editableSelection.toggleEditable(editingPlayer, true);
        editableSelection.giveSelectionTool(editingPlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.getPlayer() != null && event.getPlayer().equals(this.editingPlayer)
            && event.getPlayer().getInventory().getItemInMainHand().equals(SELECTION_EDIT_ITEM)) {

            event.setCancelled(true);
            editableSelection.movePoint(index, event.getBlock().getLocation(), event.getPlayer());
            cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().equals(this.editingPlayer) && event.getItemDrop().getItemStack().equals(SELECTION_EDIT_ITEM)) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked().equals(this.editingPlayer) && event.getOldCursor().equals(SELECTION_EDIT_ITEM)) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (event.getInitiator().equals(this.editingPlayer.getInventory()) && event.getItem().equals(SELECTION_EDIT_ITEM)) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().equals(this.editingPlayer) && event.getOffHandItem().equals(SELECTION_EDIT_ITEM)) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().equals(this.editingPlayer)) {
            cancel();
        }
    }
}
