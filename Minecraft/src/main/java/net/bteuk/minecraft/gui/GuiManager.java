package net.bteuk.minecraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * {@link Gui} manager, registers en unregisters Guis as wel as keeps track of players that are currently using a Gui.
 */
public final class GuiManager {

    private static final Map<UUID, Gui> registeredGuis = new HashMap<>();

    private static final Map<UUID, UUID> openGuis = new HashMap<>();

    private GuiManager() {
        // Private constructor so this class can not be instantiated.
    }

    public static void registerGui(Gui gui) {
        if (gui != null) {
            registeredGuis.put(gui.getUuid(), gui);
        }
    }

    public static void unregisterGui(Gui gui) {
        if (gui != null) {
            unregisterGuiByUuid(gui.getUuid());
        }
    }

    public static void unregisterGuiByUuid(UUID uuid) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            openGuis.remove(player.getUniqueId(), uuid);
        }
        registeredGuis.remove(uuid);
    }

    public static Gui getGuiByUuid(UUID uuid) {
        return registeredGuis.get(uuid);
    }

    public static UUID getOpenGuiUuidByPlayer(Player player) {
        return getOpenGuiUuidByPlayerUuid(player.getUniqueId());
    }

    public static UUID getOpenGuiUuidByPlayerUuid(UUID uuid) {
        return openGuis.get(uuid);
    }

    public static void openGui(Player player, Gui gui) {
        openGui(player.getUniqueId(), gui.getUuid());
    }

    public static void openGui(UUID playerUuid, UUID guiUuid) {
        openGuis.put(playerUuid, guiUuid);
    }

    public static void closeGui(Player player) {
        closeGui(player.getUniqueId());
    }

    public static void closeGui(UUID playerUuid) {
        openGuis.remove(playerUuid);
    }
}
