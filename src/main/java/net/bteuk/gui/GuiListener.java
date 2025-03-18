package net.bteuk.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Gui listener, handles player interactions with a {@link Gui}.
 */
public final class GuiListener implements Listener {

    private static GuiListener guiListener;

    private GuiListener(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Register the Gui listener.
     *
     * @param plugin the plugin for which the listener is registered
     */
    public static void register(JavaPlugin plugin) {
        if (guiListener == null) {
            guiListener = new GuiListener(plugin);
        }
    }

    /**
     * Unregister the Gui listener.
     */
    public static void unregister() {
        HandlerList.unregisterAll(guiListener);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            UUID inventoryUUID = GuiManager.getOpenGuiUuidByPlayerUuid(player.getUniqueId());
            if (inventoryUUID != null) {
                e.setCancelled(true);
                Gui gui = GuiManager.getGuiByUuid(inventoryUUID);
                GuiAction action = gui.getActions().get(e.getRawSlot());

                if (action != null) {
                    action.click(e);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();

        // Get the uuid of the open inventory, if exists.
        UUID guiUuid = GuiManager.getOpenGuiUuidByPlayer(player);

        if (guiUuid != null) {

            // Get the gui.
            Gui gui = GuiManager.getGuiByUuid(guiUuid);

            // If the gui should delete on close, delete it.
            if (gui != null && gui.isDeleteOnClose()) {
                gui.delete();
            } else {
                //Remove the player from the list of open inventories.
                GuiManager.closeGui(player);
            }
        }
    }
}
