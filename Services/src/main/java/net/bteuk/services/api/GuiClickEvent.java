package net.bteuk.services.api;

public interface GuiClickEvent {

    Player getPlayer();

    int getSlot();

    boolean isRightClick();

    boolean isLeftClick();

    boolean isShiftClick();

    void setCancelled(boolean cancelled);

}
