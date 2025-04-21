package dev.mineland.iteminteractions;


import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ItemInteractions {
    public static final String MOD_ID = "iteminteractions";
    final static Logger logger = LoggerFactory.getLogger(MOD_ID);
    //    public static List<Particle> UIParticles;




    public static enum animation {
        ANIM_SCALE("scale"),
        ANIM_SPEED("speed"),
        NONE("none");

        public final String name;
        public final Component component;
        private animation(String name) {
            this.name = name;
            component = Component.literal(this.name);

        }
    }

    public static void init() {
        logger.info("Initializing item interactions!");
        ItemInteractionsConfig.init();
        ItemInteractionsConfig.refreshConfig();


//        animationSetting = ItemInteractionsConfig.animationConfig;

    }

    public static void infoMessage(String message) {
        logger.info(message);
    }

    public static void warnMessage(String message) {
        logger.warn(message);
    }


    public static void refreshConfig() {
        logger.info("Refreshing config");
        ItemInteractionsConfig.refreshConfig();

    }

    public static animation getAnimationSetting() {
        return ItemInteractionsConfig.animationConfig;
    }
    public static String getAnimationSettingString(animation anim) {
        return switch (anim) {
            case animation.ANIM_SCALE -> "scale";
            case animation.ANIM_SPEED -> "speed";
            case null, default -> "none";
        };
    }



}
