/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.moyingmoe.travelwithhorse.commands.CommandHorseStable;
import top.moyingmoe.travelwithhorse.items.ItemEnderLead;
import top.moyingmoe.travelwithhorse.items.ItemHorseWhistle;

public class TravelWithHorse implements ModInitializer {
    public static String MOD_ID = "travelwithhorse";
    public static Logger LOGGER = LogManager.getLogger("TravelWithHorse");

    /** items */
    public static final Item ENDER_LEAD = new ItemEnderLead(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    public static final Item HORSE_WHISTLE = new ItemHorseWhistle(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    /** item group */
    public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "itemgroup"))
            .icon(() -> new ItemStack(ENDER_LEAD))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ENDER_LEAD));
                stacks.add(new ItemStack(HORSE_WHISTLE));
                stacks.add(new ItemStack(Items.HORSE_SPAWN_EGG));
                stacks.add(new ItemStack(Items.SKELETON_HORSE_SPAWN_EGG));
            })
            .build();


    @Override
    public void onInitialize() {
        // 注册物品
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "ender_lead"), ENDER_LEAD);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "horse_whistle"), HORSE_WHISTLE);

        // 注册命令
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandHorseStable.register(dispatcher);
        });
    }
}
