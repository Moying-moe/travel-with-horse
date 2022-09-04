/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.visitor.StringNbtWriter;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import top.moyingmoe.travelwithhorse.TravelWithHorse;
import top.moyingmoe.travelwithhorse.utils.HorseManager;
import top.moyingmoe.travelwithhorse.utils.TextFormatter;

import java.util.UUID;

public class ItemEnderLead extends Item {
    public ItemEnderLead(Item.Settings settings) {
        super(settings);
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
//        //playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
//        return TypedActionResult.success(playerEntity.getStackInHand(hand));
//    }

    private boolean isHorseLegal(PlayerEntity player, LivingEntity entity, boolean chat) {


        if (entity.getType() != EntityType.HORSE) {
            // 不是马
            if (chat)
                player.sendMessage(TextFormatter.error(Text.translatable("item.travelwithhorse.ender_lead.use.fail"), true));
            return false;
        }

        NbtCompound horseNbt = new NbtCompound();
        entity.saveNbt(horseNbt);

        boolean tamed = horseNbt.getBoolean("Tame");
        if (!tamed) {
            // 未驯服
            if (chat)
                player.sendMessage(TextFormatter.error(Text.translatable("item.travelwithhorse.ender_lead.use.fail"), true));
            return false;
        }


        if (!horseNbt.getUuid("Owner").equals(player.getUuid())) {
            // 不是主人
            if (chat)
                player.sendMessage(TextFormatter.error(Text.translatable("item.travelwithhorse.ender_lead.use.notown"), true));
            return false;
        }

        if (HorseManager.hasHorse(player)) {
            // 已经有马了
            if (chat)
                player.sendMessage(TextFormatter.error(Text.translatable("item.travelwithhorse.ender_lead.use.alreadyhas"), true));
            return false;
        }

        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        boolean isClient = player.getWorld().isClient;
        boolean horseLegal = isHorseLegal(player, entity, isClient);

        if (isClient) {
            if (horseLegal) {
                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        } else {
            player.getItemCooldownManager().set(this, 100);

            if (horseLegal) {
                NbtCompound horseNbt = new NbtCompound();
                entity.saveNbt(horseNbt);

                HorseManager.setHorseNbt(player, horseNbt);
                entity.remove(Entity.RemovalReason.CHANGED_DIMENSION);

                stack.decrement(1);

                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        }
    }
}
