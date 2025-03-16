package net.bteuk.api.gui;

import net.bteuk.services.api.GuiClickEvent;
import net.bteuk.services.api.GuiCloseEvent;

import java.util.UUID;

public final class GuiListener {

    /**
     * Action to perform when a Player clicks in a gui.
     *
     * @param guiClickEvent the click event
     */
    public void onClick(GuiClickEvent guiClickEvent) {

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
    public void onClose(GuiCloseEvent guiCloseEvent) {

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
