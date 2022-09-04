package top.moyingmoe.travelwithhorse.utils;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

public class Permission {

	public static boolean hasPermission(ServerCommandSource source, LiteralArgumentBuilder<ServerCommandSource> literal,
										boolean needOP) {
			
		
		if(needOP){
			return source.hasPermissionLevel(4);
		}else {
			return true;
		}
	}

	public static boolean hasPermission(ServerCommandSource source, String literal,
										boolean needOP) {
		if(needOP){
			return source.hasPermissionLevel(4);
		}else {
			return true;
		}
	}

}
