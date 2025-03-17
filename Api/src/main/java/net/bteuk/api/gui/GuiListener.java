package net.bteuk.api.gui;

import net.bteuk.api.GuiClickEvent;
import net.bteuk.api.GuiCloseEvent;

import java.util.UUID;

/**
 * Listener that implements the necessary methods to allow the player to interact with a {@link Gui}.
 */
public abstract class GuiListener {

    /**
     * Action to perform when a Player clicks in a gui.
     *
     * @param guiClickEvent the click event
     */
    protected final void onClick(GuiClickEvent guiClickEvent) {

        UUID inventoryUUID = Gui.openInventories.get(guiClickEvent.getPlayer().getUuid());

        if (inventoryUUID != null) {

            guiClickEvent.setCancelled(true);

            Gui gui = Gui.inventoriesByUUID.get(inventoryUUID);

            if (gui != null) {
                GuiAction action = gui.getActions().get(guiClickEvent.getSlot());

                if (action != null) {
                    action.click(guiClickEvent);
                }
            }
        }
    }

    /**
     * Action to perform when a Player closes a gui.
     *
     * @param guiCloseEvent the close event
     */
    protected final void onClose(GuiCloseEvent guiCloseEvent) {

        // Get the uuid of the open inventory, if exists.
        UUID inventoryUUID = Gui.openInventories.get(guiCloseEvent.getPlayer().getUuid());

        if (inventoryUUID != null) {

            // Get the gui.
            Gui gui = Gui.inventoriesByUUID.get(inventoryUUID);

            // If the gui should delete on close, delete it.
            if (gui != null && gui.isDeleteOnClose()) {
                gui.delete();
            } else {
                Gui.openInventories.remove(guiCloseEvent.getPlayer().getUuid(), inventoryUUID);
            }
        }
    }
}
