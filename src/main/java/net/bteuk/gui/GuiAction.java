package net.bteuk.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface GuiAction {

    void click(InventoryClickEvent clickEvent);
}
