package net.bteuk.services.entity;

import net.bteuk.services.api.Location;
import net.bteuk.services.api.Player;

public record Movement(Player player, Location from, Location to) {
}
