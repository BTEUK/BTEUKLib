package org.btuk.minecraft.selection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.btuk.holograms.HologramManager;
import org.btuk.outlines.Outlines;
import org.btuk.outlines.geometry.IntPoint2d;

public class EditableSelection extends Selection {

    private final Map<UUID, List<UUID>> playerHolograms = new HashMap<>();

    private final Map<UUID, EditMode> playerEditMode = new HashMap<>();

    private final HologramManager hologramManager;

    public EditableSelection(JavaPlugin plugin, ItemStack selectionTool, Outlines outlines, HologramManager hologramManager) {
        super(plugin, selectionTool, outlines);
        this.hologramManager = hologramManager;
    }

    public void toggleEditable(Player player, boolean editable) {
        if (editable) {
            playerHolograms.putIfAbsent(player.getUniqueId(), new ArrayList<>());
            playerEditMode.remove(player.getUniqueId());
            replaceHolograms(player);
        } else {
            List<UUID> holograms = playerHolograms.remove(player.getUniqueId());
            removeHolograms(holograms);
        }
    }

    public void movePoint(int index, Location newLocation, Player player) {
        int x = newLocation.getBlockX();
        int z = newLocation.getBlockZ();
        UUID playerId = player.getUniqueId();

        List<IntPoint2d> selection = activeSelections.get(playerId);
        if (selection == null || selection.size() <= index) {
            return;
        }

        IntPoint2d point = new IntPoint2d(x, z);
        if (!pointValidator.test(player, point)) {
            return;
        }

        selection.set(index, point);
        replaceOutline(playerId, selection);
        selectionUpdateHook.accept(player, selection);
    }

    @Override
    public void startSelection(Player player, List<IntPoint2d> points) {
        super.startSelection(player, points);
        replaceHolograms(player);
    }

    @Override
    protected void addPoint(Player player, UUID playerId, PlayerInteractEvent event) {
        if (playerEditMode.containsKey(playerId)) {
            return;
        }
        super.addPoint(player, playerId, event);
        replaceHolograms(player);
    }

    @Override
    protected void resetSelection(UUID playerId) {
        if (playerEditMode.containsKey(playerId)) {
            return;
        }
        super.resetSelection(playerId);
        activeSelections.remove(playerId);
        List<UUID> holograms = playerHolograms.get(playerId);
        if (holograms == null) {
            return;
        }
        removeHolograms(holograms);
    }

    private void removeHolograms(List<UUID> holograms) {
        if (holograms == null) {
            return;
        }
        for (UUID hologramId : holograms) {
            hologramManager.removeHologram(hologramId);
        }
        holograms.clear();
    }

    private void replaceHolograms(Player player) {
        UUID playerId = player.getUniqueId();
        List<UUID> holograms = playerHolograms.computeIfAbsent(playerId, k -> new ArrayList<>());
        for (UUID hologramId : holograms) {
            hologramManager.removeHologram(hologramId);
        }
        holograms.clear();
        List<IntPoint2d> points = activeSelections.get(playerId);
        if (points == null) {
            return;
        }
        World world = player.getWorld();
        for (int i = 0; i < points.size(); i++) {
            IntPoint2d point = points.get(i);
            Location location = new Location(world, point.x() + 0.5, 2 + world.getHighestBlockYAt(point.x(), point.z()), point.z() + 0.5);
            String text = i == 0 ? "&a&lFirst Corner" : "&b&lClick to move corner";
            UUID hologramId = hologramManager.createHologram(location, Collections.singletonList(text));
            hologramManager.setPlayerVisibility(hologramId, player, true);
            holograms.add(hologramId);
        }
        for (int i = 0; i < holograms.size(); i++) {
            UUID hologramId = holograms.get(i);
            int cornerIndex = i;
            hologramManager.addHologramClickEvent(hologramId, event -> {
                toggleEditable(player, false);
                player.getInventory().removeItem(selectionTool);
                EditMode mode = playerEditMode.put(playerId, new EditMode(cornerIndex, this, player, plugin));
                if (mode != null) {
                    mode.cancel();
                }
            });
        }
    }
}
