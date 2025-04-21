package dev.mineland.iteminteractions.neoforge;

import dev.mineland.iteminteractions.ItemInteractions;
import dev.mineland.iteminteractions.ItemInteractionsSettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ItemInteractions.MOD_ID)
public final class IteminteractionsNeoForge {
    

    public IteminteractionsNeoForge(ModContainer container) {
        // This will use NeoForge's ConfigurationScreen to display this mod's configs

        container.registerExtensionPoint(IConfigScreenFactory.class, new ItemInteractionsConfigNeoforge() {
        });
        ItemInteractions.init();

    }
}
