package net.bteuk.entity;

import net.bteuk.api.Location;

public record Movement(net.bteuk.api.BTEPlayer BTEPlayer, Location from, Location to) {
}
