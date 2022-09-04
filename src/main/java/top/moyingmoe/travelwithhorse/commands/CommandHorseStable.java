package top.moyingmoe.travelwithhorse.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import top.moyingmoe.travelwithhorse.TravelWithHorse;
import top.moyingmoe.travelwithhorse.utils.HorseManager;
import top.moyingmoe.travelwithhorse.utils.Permission;
import top.moyingmoe.travelwithhorse.utils.TextFormatter;

public class CommandHorseStable
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("horse-stable");

		literal.requires((source) -> {
			return Permission.hasPermission(source, literal, false);
		})
				.then(CommandManager.literal("clear")
						.executes(context -> scClear(context)))
				.then(CommandManager.literal("info")
						.executes(context -> scInfo(context)));

		dispatcher.register(literal);    

	}

	public static int scClear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		HorseManager.removeHorseNbt(context.getSource().getPlayer());
		return 1;
	}

	public static int scInfo(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		PlayerEntity player = context.getSource().getPlayer();
		if (HorseManager.hasHorse(player)) {
			NbtCompound nbt = HorseManager.getHorseNbt(player);
			HorseEntity entity = new HorseEntity(EntityType.HORSE, context.getSource().getWorld());
			entity.readNbt(nbt);

			MutableText result = TextFormatter.info("===========================================\n").copy();
			result.append(TextFormatter.info(Text.translatable("commands.travelwithhorse.horsestable.info.title")));
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.name")));
			if (entity.hasCustomName()) {
				result.append(entity.getCustomName());
			} else {
				result.append(TextFormatter.info(entity.getName()));
			}
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.max_hp")));
			result.append(TextFormatter.info(String.valueOf(entity.getMaxHealth())));
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.jump_strength")));
			double jumpStrength = entity.getJumpStrength();
			double jumpBlock = -0.1817584952 * Math.pow(jumpStrength,3) +
					3.689713992 * Math.pow(jumpStrength,2) +
					2.128599134 * jumpStrength - 0.343930367;
			result.append(TextFormatter.info(String.format("%.3f",jumpBlock)));
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.speed")));
			double movementSpeed = nbt.getList("Attributes", 0xa).getCompound(4).getDouble("Base");
			result.append(TextFormatter.info(String.format("%.3f",movementSpeed*42)));
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.armor")));
			result.append(entity.getArmorType().toHoverableText());
			result.append("\n- ");
			result.append(TextFormatter.key(Text.translatable("commands.travelwithhorse.horsestable.info.saddle")));
			result.append(TextFormatter.info(Text.translatable("commands.travelwithhorse.horsestable.info.saddle." + (entity.isSaddled()?"true":"false"))));
			result.append(TextFormatter.info("\n==========================================="));

			player.sendMessage(result);
		} else {
			player.sendMessage(TextFormatter.error(Text.translatable("commands.travelwithhorse.horsestable.info.fail"), true));
		}

		return 1;
	}

}