/*
 * Copyright 2022 moying All Rights Reserved.
 *
 * Distributed under MIT license.
 * See file LICENSE for detail or copy at https://opensource.org/licenses/MIT
 */

package top.moyingmoe.travelwithhorse.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.moyingmoe.travelwithhorse.TravelWithHorse;

public class TextFormatter {
    public static final Formatting fModName = Formatting.GOLD;
    public static final Formatting fError = Formatting.RED;
    public static final Formatting fSuccess = Formatting.GREEN;
    public static final Formatting fWarning = Formatting.LIGHT_PURPLE;
    public static final Formatting fInfo = Formatting.AQUA;
    public static final Formatting fKey = Formatting.YELLOW;

    private static MutableText getModName() {
        return Text.of("[" + Text.translatable("modinfo.travelwithhorse.name").getString() + "] ").copy().formatted(fModName);
    }

    public static MutableText error(String string) {
        return error(string, false);
    }

    public static MutableText error(Text text) {
        return error(text, false);
    }

    public static MutableText error(String string, boolean withModName) {
        return error(Text.of(string), withModName);
    }

    public static MutableText error(Text text, boolean withModName) {
        MutableText body = text.copy().formatted(fError);
        if (withModName) {
            return getModName().append(body);
        } else {
            return body;
        }
    }

    public static MutableText info(String string) {
        return info(string, false);
    }

    public static MutableText info(Text text) {
        return info(text, false);
    }

    public static MutableText info(String string, boolean withModName) {
        return info(Text.of(string), withModName);
    }

    public static MutableText info(Text text, boolean withModName) {
        MutableText body = text.copy().formatted(fInfo);
        if (withModName) {
            return getModName().append(body);
        } else {
            return body;
        }
    }

    public static MutableText key(String string) {
        return key(string, false);
    }

    public static MutableText key(Text text) {
        return key(text, false);
    }

    public static MutableText key(String string, boolean withModName) {
        return key(Text.of(string), withModName);
    }

    public static MutableText key(Text text, boolean withModName) {
        MutableText body = text.copy().formatted(fKey);
        if (withModName) {
            return getModName().append(body);
        } else {
            return body;
        }
    }
}
