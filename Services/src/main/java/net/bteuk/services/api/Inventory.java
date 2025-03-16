package net.bteuk.services.api;

public interface Inventory {

    void setItem(int index, ItemStack itemStack);

    void clear();
}
