/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse.items;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityIndex;
import org.apache.logging.log4j.core.jmx.Server;
import top.moyingmoe.travelwithhorse.TravelWithHorse;
import top.moyingmoe.travelwithhorse.utils.HorseManager;
import top.moyingmoe.travelwithhorse.utils.TextFormatter;

import java.util.Objects;

public class ItemHorseWhistle extends Item {
    public ItemHorseWhistle(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        boolean isClient = player.getWorld().isClient;
        if (isClient) {
            if (HorseManager.hasHorse(player)) {
                player.playSound(SoundEvents.GOAT_HORN_SOUNDS.get(7), 1, 2);
                player.playSound(SoundEvents.ENTITY_HORSE_GALLOP, 1, 1);
            }
        } else {
            player.getItemCooldownManager().set(this, 100);

            NbtCompound nbt = HorseManager.getHorseNbt(player);
            if (nbt != null) {
                NbtList pos = nbt.getList("Pos",0x6);
                pos.set(0, NbtDouble.of(player.getX()));
                pos.set(1, NbtDouble.of(player.getY()));
                pos.set(2, NbtDouble.of(player.getZ()));
                nbt.put("Pos", pos);

                HorseEntity entity = new HorseEntity(EntityType.HORSE, world);
                entity.readNbt(nbt);

                HorseEntity worldEntity = (HorseEntity) ((ServerWorld)world).getEntity(entity.getUuid());
                if (Objects.nonNull(worldEntity)) {
                    worldEntity.remove(Entity.RemovalReason.CHANGED_DIMENSION);
                }
                world.spawnEntity(entity);
                player.dismountVehicle();
                entity.interactMob(player, Hand.MAIN_HAND);

            } else {
                player.sendMessage(TextFormatter.error(Text.translatable("item.travelwithhorse.horse_whistle.fail"), true));
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
