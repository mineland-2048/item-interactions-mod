package dev.mineland.iteminteractions.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.mineland.iteminteractions.GlobalDirt;
import dev.mineland.iteminteractions.ItemInteractions;
import dev.mineland.iteminteractions.ItemInteractionsConfig;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mineland.iteminteractions.GlobalDirt.*;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin{
    @Shadow @Final private ItemStackRenderState scratchItemStackRenderState;
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private PoseStack pose;

    @Shadow public abstract void flush();

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

//    @Shadow public abstract void drawCenteredString(Font arg, String string, int i, int j, int k);

    @Shadow public abstract int drawString(Font arg, String string, int i, int j, int k);

//    @Shadow public abstract void fill(int i, int j, int k, int l, int m);

    @Unique float iteminteractions$animScaleScale;

    @Unique int iteminteractions$offset = -8;


    @Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void renderItemHead(LivingEntity livingEntity, Level level, ItemStack itemStack, int i, int j, int k, int l, CallbackInfo ci) {

        if (!itemStack.isEmpty() && GlobalDirt.carriedItem == itemStack) {
            this.minecraft.getItemModelResolver().updateForTopItem(this.scratchItemStackRenderState, itemStack, ItemDisplayContext.GUI, level, livingEntity, k);
            GlobalDirt.isCurrentItem3d = this.scratchItemStackRenderState.usesBlockLight();


            if (!iteminteractions$isFrozen()) {
//                float tickDelta = iteminteractions$getFrameDelta();
                long currentMilis = Util.getMillis();



                float tickRate = this.minecraft.level != null ?
                        this.minecraft.level.tickRateManager().tickrate() : 20;

                float tickScale = tickRate / 20;

                long frametime = currentMilis - lastMilis;
                float tickDelta = ((frametime) / 1000f);


//                this.drawString(this.minecraft.font, "§fFrame time: " + frametime,      -GlobalDirt.leftPos,  -GlobalDirt.topPos, 0xFFFFFFFF);
//                this.drawString(this.minecraft.font, "§ftickDelta: " + tickDelta,        -GlobalDirt.leftPos,-GlobalDirt.topPos +  this.minecraft.font.lineHeight, 0xFFFFFFFF);
//                this.drawString(this.minecraft.font, "§fmsCounter: " + msCounter,    -GlobalDirt.leftPos,-GlobalDirt.topPos +  this.minecraft.font.lineHeight * 2, 0xFFFFFFFF);
//                this.drawString(this.minecraft.font, "§ffps: " + this.minecraft.getFps(),-GlobalDirt.leftPos,-GlobalDirt.topPos +  this.minecraft.font.lineHeight * 3, 0xFFFFFFFF);
//                tickDelta = tickDelta * 0.000000001f;



                switch (ItemInteractions.getAnimationSetting()) {
                    case ItemInteractions.animation.ANIM_SCALE -> {
                        float scale = (float) Math.abs(Math.cos(Math.PI * (msCounter / ((tickScale) / ItemInteractionsConfig.scaleSpeed)))) * ItemInteractionsConfig.scaleAmount;
                        iteminteractions$animScaleScale = scale;

                        this.pose.translate(-iteminteractions$offset -(i * scale),-iteminteractions$offset -(j * scale), 0);
                        this.pose.scale(1 + scale, 1 + scale, 1);
                        this.pose.translate(iteminteractions$offset, iteminteractions$offset, 0);

                    }

                    case ItemInteractions.animation.ANIM_SPEED -> {

                        float mouseDeltaX = i - lastMouseX;
                        float mouseDeltaY = j - lastMouseY;

                        float zPlane = (232.0f + 150f);

                        float drag = (float)
                                Math.pow(Math.min(1, (deceleration) * tickScale/1000),
                                        tickDelta * tickScale);

                        speedX = Math.clamp((speedX + mouseDeltaX) * drag,-40f,  40f);
                        speedY = Math.clamp((speedY + mouseDeltaY) * drag,-40f,  40f);
//
//                        this.drawString(this.minecraft.font, "§fspeedX: " + speedX,      -GlobalDirt.leftPos,  -GlobalDirt.topPos, 0xFFFFFFFF);
//                        this.drawString(this.minecraft.font, "§fspeedY: " + speedY,      -GlobalDirt.leftPos,  -GlobalDirt.topPos + this.minecraft.font.lineHeight, 0xFFFFFFFF);
//                        this.drawString(this.minecraft.font, "§ftickDelta: " + tickDelta,      -GlobalDirt.leftPos,  -GlobalDirt.topPos + this.minecraft.font.lineHeight * 2, 0xFFFFFFFF);
//                        this.drawString(this.minecraft.font, "§drag: " + drag,      -GlobalDirt.leftPos,  -GlobalDirt.topPos + this.minecraft.font.lineHeight * 3, 0xFFFFFFFF);


//                    guiGraphics.pose().translate(-this.leftPos, -this.topPos, 0);

                        if (GlobalDirt.isCurrentItem3d && itemStack == GlobalDirt.carriedItem) {


                            Quaternionf quatPointTo = new Quaternionf()
                                    .rotateTo(0, 0, zPlane,
                                            (speedX*4), (speedY*4), zPlane).normalize();

//                            int hThing = 128+16+8;
                            this.pose.rotateAround(quatPointTo, i , j , 150);

                            GlobalDirt.rollback = new Quaternionf()
                                    .rotateTo(0, 0, zPlane,
                                            (-speedX*4), (-speedY*4), zPlane).normalize();

//                        this.pose.translate(-i, -j, 0);
                        } else {

//                            speedY = Math.clamp (speedY, -20, 20);
                            float angleVertical = Mth.DEG_TO_RAD * 22.5f * Math.clamp((-speedY*0.1f), -1.5f ,1.5f);
                            float angleHorizontal = Mth.DEG_TO_RAD * speedX*0.8f;
                            Quaternionf quatRotateVertical = new Quaternionf()
                                    .rotateX(angleVertical)
                                    .rotateZ(angleHorizontal)
                                    .normalize();

                            this.pose.rotateAround(quatRotateVertical, i, j, 150);

                            rollback = new Quaternionf()
                                    .rotateZ(-angleHorizontal)
                                    .rotateX(-angleVertical)
                                    .normalize();





                        }


                        if (debugStuck) this.pose.translate(-i, -j, 0);


                    }

                }

//                this.fill(i - 8, j - 8, i+8, j+8, 0xFFFFFFFF);
                msCounter += tickDelta;
                msCounter %= 1000;
                lastMouseX = i;
                lastMouseY = j;
                lastMilis = currentMilis;


            }

        }
    }


    @Inject(at = @At("TAIL"), method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void renderItemTail(LivingEntity livingEntity, Level level, ItemStack itemStack, int i, int j, int k, int l, CallbackInfo ci) {
        if (!itemStack.isEmpty() && GlobalDirt.carriedItem == itemStack) {
            switch (ItemInteractions.getAnimationSetting()) {
                case ANIM_SPEED: {
                    if (rollback == null) break;
                    if (isCurrentItem3d) this.pose.rotateAround(GlobalDirt.rollback, i , j , 150);
                    else this.pose.rotateAround(rollback, i, j, 150);
                    break;
                }

                case ANIM_SCALE:
//                    this.drawString(this.minecraft.font,
//                            String.format("""
//                                §f i: %d j: %danimScaleScale: %f 1/1+anim: %f""",
//                            i, j, iteminteractions$animScaleScale, (1 / (1 + iteminteractions$animScaleScale))
//                            ),
//                            0, 0, 0
//                    );


                    this.pose.translate(-iteminteractions$offset, -iteminteractions$offset, 0);
                    this.pose.scale(1 / (1 + iteminteractions$animScaleScale), 1 / (1 + iteminteractions$animScaleScale), 1);
                    this.pose.translate(iteminteractions$offset + (i * iteminteractions$animScaleScale), iteminteractions$offset + (j * iteminteractions$animScaleScale), 0);

//                    this.pose.translate((i * iteminteractions$animScaleScale), (j * iteminteractions$animScaleScale), 0);
                    break;

            }
        }
    }



    @Unique
    private static boolean iteminteractions$isFrozen() {
        assert Minecraft.getInstance().level != null;
        return !Minecraft.getInstance().level.tickRateManager().runsNormally();
    }

    @Unique
    private static float iteminteractions$getFrameDelta() {
        return Minecraft.getInstance().getFrameTimeNs();
    }

    @Unique
    private static float iteminteractions$getTickRate() {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.tickRateManager().tickrate();
    }


}
