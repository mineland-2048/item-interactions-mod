package dev.mineland.iteminteractions.neoforge;

import dev.mineland.iteminteractions.ItemInteractionsSettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ItemInteractionsConfigNeoforge implements IConfigScreenFactory {


    public ItemInteractionsConfigNeoforge() {}

    @Override
    public Screen createScreen(ModContainer modContainer, Screen arg) {
        return new ItemInteractionsSettingsScreen(arg);
    }
}
