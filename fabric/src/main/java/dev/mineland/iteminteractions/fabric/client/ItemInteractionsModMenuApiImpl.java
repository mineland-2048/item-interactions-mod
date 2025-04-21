package dev.mineland.iteminteractions.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.mineland.iteminteractions.ItemInteractionsSettingsScreen;
import net.minecraft.client.gui.screens.Screen;

public class ItemInteractionsModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ItemInteractionsSettingsScreen::new;
    }
}
