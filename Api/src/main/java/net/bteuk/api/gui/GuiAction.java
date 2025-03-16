package net.bteuk.api.gui;

import net.bteuk.services.api.GuiClickEvent;

@FunctionalInterface
public interface GuiAction {
    void click(GuiClickEvent guiClickEvent);
}
