package net.bteuk.api;

public interface GuiClickEvent {

    BTEPlayer getPlayer();

    int getSlot();

    boolean isRightClick();

    boolean isLeftClick();

    boolean isShiftClick();

    void setCancelled(boolean cancelled);

}
