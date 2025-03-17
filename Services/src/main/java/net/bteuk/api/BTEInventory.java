package net.bteuk.api;

public interface BTEInventory<T> {

    void setItem(int index, BTEItemStack<?> ItemStack);

    void clear();

    T inventory();
}
