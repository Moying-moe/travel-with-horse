/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse.utils;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.visitor.StringNbtWriter;

import java.util.HashMap;
import java.util.UUID;

public class HorseManager {
    private static final HashMap<UUID, String> horseNbtMap = new HashMap<>();

    public static boolean hasHorse(PlayerEntity player) {
        return horseNbtMap.containsKey(player.getUuid());
    }

    public static NbtCompound getHorseNbt(PlayerEntity player) {
        UUID playerUUID = player.getUuid();
        if (horseNbtMap.containsKey(playerUUID)) {
            try {
                return StringNbtReader.parse(horseNbtMap.get(playerUUID));
            } catch (CommandSyntaxException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void setHorseNbt(PlayerEntity player, NbtCompound nbt) {
        horseNbtMap.put(player.getUuid(), (new StringNbtWriter()).apply(nbt));
    }

    public static void removeHorseNbt(PlayerEntity player) {
        horseNbtMap.remove(player.getUuid());
    }

}
