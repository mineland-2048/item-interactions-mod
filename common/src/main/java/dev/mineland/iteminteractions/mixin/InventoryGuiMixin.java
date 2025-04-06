package dev.mineland.iteminteractions.mixin;

import dev.mineland.iteminteractions.GlobalDirt;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(AbstractContainerScreen.class)
public class InventoryGuiMixin {
    @Shadow protected int topPos;

    @Shadow protected int leftPos;

    @Inject(method = "renderFloatingItem",at = @At("HEAD"))
    protected void mixedRenderFloatingItem(GuiGraphics guiGraphics,
                                           ItemStack itemStack,
                                           int i, int j,
                                           @Nullable String string,
                                           CallbackInfo callbackInfo) {
        GlobalDirt.carriedItem = itemStack;



    }


    @Inject(method = "init", at = @At("TAIL"))
    protected void initMixin(CallbackInfo ci) {
        GlobalDirt.msCounter = 0;
        GlobalDirt.topPos = this.topPos;
        GlobalDirt.leftPos = this.leftPos;
        GlobalDirt.speedX = 0;
        GlobalDirt.speedY = 0;
    }
}



