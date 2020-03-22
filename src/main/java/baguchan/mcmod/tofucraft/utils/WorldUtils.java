package baguchan.mcmod.tofucraft.utils;

import net.minecraft.world.World;

public class WorldUtils {
    public static boolean isDaytime(World world) {
        return world.getSkylightSubtracted() < 4;
    }
}
