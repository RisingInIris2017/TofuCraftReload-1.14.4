package baguchan.mcmod.tofucraft.utils;

import net.minecraft.nbt.CompoundNBT;

public class NBTUtil {
    public static boolean getBoolean(CompoundNBT compound, String key, boolean ifnone) {
        if (compound == null) return ifnone;
        return !compound.isEmpty() ? compound.getBoolean(key) : ifnone;
    }

    public static int getInteger(CompoundNBT compound, String key, int ifnone) {
        if (compound == null) return ifnone;
        return !compound.isEmpty() ? compound.getInt(key) : ifnone;
    }

    public static String getString(CompoundNBT compound, String key, String ifnone) {
        if (compound == null) return ifnone;
        return !compound.isEmpty() ? compound.getString(key) : ifnone;
    }

    public static CompoundNBT setString(CompoundNBT compound, String key, String value) {
        if (compound == null) compound = new CompoundNBT();
        compound.putString(key, value);
        return compound;
    }

    public static CompoundNBT setInteger(CompoundNBT compound, String key, int value) {
        if (compound == null) compound = new CompoundNBT();
        compound.putInt(key, value);
        return compound;
    }

    public static CompoundNBT setBoolean(CompoundNBT compound, String key, boolean value) {
        if (compound == null) compound = new CompoundNBT();
        compound.putBoolean(key, value);
        return compound;
    }
}
