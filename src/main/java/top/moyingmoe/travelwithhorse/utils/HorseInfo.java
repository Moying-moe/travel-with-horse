/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse.utils;

import net.minecraft.nbt.NbtCompound;

public class HorseInfo {
    public String customName;
    public boolean customNameVisible;
    public double jumpStrength;
    public double maxHealth;
    public double moveSpeed;
    public String armorId;
    public boolean hasSaddle;
    public int age;
    public int variant;

    public NbtCompound setNbtCompound(NbtCompound nbt) {
        NbtCompound result = nbt.copy();

        result.putString("CustomName", customName);
        result.putBoolean("CustomNameVisible", customNameVisible);

        return result;
    }
}
