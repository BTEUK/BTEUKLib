package net.bteuk.api.gui;

import net.bteuk.services.api.Player;

@FunctionalInterface
public interface GuiAction {
    void click(Player player);
}
