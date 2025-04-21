package dev.mineland.iteminteractions.itemcarriedalgs;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.mineland.iteminteractions.GlobalDirt;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import static dev.mineland.iteminteractions.GlobalDirt.*;


public class AnimTemplate {


    public static void setVariables() {
        GlobalDirt.currentMilis = Util.getMillis();

        tickRate = Minecraft.getInstance().level != null ?
                Minecraft.getInstance().level.tickRateManager().tickrate() : 20;

        GlobalDirt.tickScale = tickRate / 20;

        GlobalDirt.frameTime = currentMilis - lastMilis;
        GlobalDirt.tickDelta = ((frameTime) / 1000f);



    }

    public static void setLastVariables() {
        msCounter += tickDelta;
        msCounter %= 1000;

        lastMilis = currentMilis;


    }
}
