package dev.mineland.iteminteractions;

import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;


public class GlobalDirt {
    public static boolean isCurrentItem3d;
    public static ItemStack carriedItem;

    public static long lastMilis = 0;

//    public static int animationSetting = Iteminteractions.getAnimationSetting();
    public static float msCounter = 0;

    public static boolean debugStuck = false;

    public static float     lastMouseX = 0, lastMouseY = 0,
                            speedX = 0, speedY = 0;

    public static int topPos = 0, leftPos = 0;

    public static float deceleration = 0.8f;

    public static float shortFPS = 0;

    public static Quaternionf rollback;

//    public static List<TransitionItem> transItems;


//    public static void addTransItem(ItemStack itemStack, Slot slot, int lastMouseX, int lastMouseY) {
//        transItems.add(new TransitionItem(itemStack, slot, lastMouseX, lastMouseY));
//    }
}


