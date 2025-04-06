package dev.mineland.iteminteractions.neoforge;

import dev.mineland.iteminteractions.ItemInteractions;
import net.neoforged.fml.common.Mod;

@Mod(ItemInteractions.MOD_ID)
public final class IteminteractionsNeoForge {
    public IteminteractionsNeoForge() {
        // Run our common setup.
        ItemInteractions.init();
    }
}
