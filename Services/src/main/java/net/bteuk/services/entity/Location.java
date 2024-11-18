package net.bteuk.services.entity;

import net.bteuk.services.api.World;

public record Location(World world, double x, double y, double z, float yaw, float pitch) {
}
