package dev.mineland.iteminteractions;

//import dev.architectury.transformer.shadowed.impl.com.google.gson.GsonBuilder;
import dev.mineland.iteminteractions.ItemInteractions.animation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import static dev.mineland.iteminteractions.GlobalDirt.deceleration;

public class ItemInteractionsConfig {
    private static final Path configPath = Path.of("config", "item_interactions.cfg");
    public static animation animationConfig;
    public static float scaleSpeed;
    public static float scaleAmount;


    /*  Default settings:
            animation = speed
            scale_speed = 4
            scale_amount = 0.1
    */
    /*
          TODO: Add a config screen.
                Currently, Settings reload upon closing the video settings screen
                Check if sodium works with this
    */
    public static void init() {
        animationConfig = animation.ANIM_SPEED;
        scaleSpeed = 1;
        scaleAmount = 0.1f;
        deceleration = 0.8f;
    }

//    I dont know if java has any counting function for strings, so i made this. Aeugh
    private static int count(String s, String match) {
        int length = match.length();
        int result = 0;
        for (int i = 0; i <= s.length() - length; i++) {
            if (s.substring(i, i+length).equals(match)) result++;
        }
        return result;
    }

    public static void refreshConfig() {
        try {
            File configFile = configPath.toFile();
            if (!configFile.exists()) {
                configFile.createNewFile();
            }

            Scanner lector = new Scanner(configFile);

            int lineCount = 0;
            while (lector.hasNext() && lineCount < 20) {
                String line = lector.nextLine();
                int equalCount = count(line, "=");
                if (equalCount != 1) {
                    ItemInteractions.infoMessage("Skipping line " + (lineCount+1) + ": `" + line + "`. Contains " + equalCount + " `=``");
                    continue;
                }
                line = line.trim();

                int equalIndex = line.indexOf("=");

                String arg = line.substring(0, equalIndex).trim();
                String value = line.substring(equalIndex+1).trim();

//                ItemInteractions.infoMessage("`" + arg + "` = `" + value + "` || '" + line + "'");

                switch (arg) {
                    case "animation":
                        switch (value) {
                            case "scale":
                                animationConfig = animation.ANIM_SCALE;
                                break;

                            case "speed":
                                animationConfig = animation.ANIM_SPEED;
                                break;

                            default:
                                ItemInteractions.warnMessage("Unknown animation setting. Using Default");
                                animationConfig = animation.ANIM_SPEED;
                                break;
                        }
                        break;

                    case "scale_speed":
                        try {
                            scaleSpeed = Float.parseFloat(value);
                        } catch (Exception e) {
                            ItemInteractions.warnMessage("Error parsing scale speed. Using default\n" + e.getMessage());
                            scaleSpeed = 1;
                        }
                        break;

                    case "scale_amount":
                        try {
                            scaleAmount = Float.parseFloat(value);
                        } catch (Exception e) {
                            ItemInteractions.warnMessage("Error parsing scale amount. Using default\n" + e.getMessage());
                            scaleAmount = 0.1f;
                        }
                        break;

                    case "deceleration":
                        try {
                            deceleration = Float.parseFloat(value);
                        } catch (Exception e) {
                            ItemInteractions.warnMessage("Error parsing deceleration. Using default\n" + e.getMessage());
                            deceleration = 0.8f;
                        }
                        break;


                    default:
                        ItemInteractions.infoMessage("Ignoring line " + (lineCount+1) + ". Unknown setting `" + arg + "`.");
                        break;
                }
                lineCount++;

//                ItemInteractions.infoMessage(arg + ", " + value);
            }


            if (animationConfig == null) {
                animationConfig = animation.ANIM_SPEED;
                ItemInteractions.infoMessage("Defaulting to animation = speed");
            }

//            ItemInteractions.infoMessage("Loop count: " + lineCount);


            writeConfig(configFile);

//            ItemInteractions.infoMessage("Final config file: \n" + configFileString);
            ItemInteractions.infoMessage("Configuration loaded! File has been sanitized");

        } catch (IOException e) {
            ItemInteractions.warnMessage("Failed to refresh the config! \n" + e.getMessage());

            ItemInteractions.warnMessage("Using the defaults");
            init();
        }


    }

    private static void writeConfig(File configFile) throws IOException {

        FileWriter obj = new FileWriter(configFile);

        String configFileString = String.format("""
                animation = %s
                scale_speed = %f
                scale_amount = %f
                deceleration = %f
                """,
                ItemInteractions.getAnimationSettingString(animationConfig),
                scaleSpeed,
                scaleAmount,
                deceleration
                );

        obj.write(configFileString);
        obj.flush();
    }


}
