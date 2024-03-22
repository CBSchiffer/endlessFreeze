package com.clokkwork.endlessfreeze.entities;

import com.clokkwork.endlessfreeze.EndlessFreezeMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FrostlingRenderer extends MobRenderer<Frostling, FrostlingModel<Frostling>>{
    public FrostlingRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostlingModel<>(context.bakeLayer(FrostlingModel.LAYER_LOCATION)), 0.5F);
    }

    public static final ResourceLocation FROST_CORE_TEXTURE = new ResourceLocation(EndlessFreezeMod.MODID, "textures/entities/frost_core.png");


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Frostling ling) {
        return FROST_CORE_TEXTURE;
    }

    @Override
    protected void scale(@NotNull Frostling entity, PoseStack stack, float p_225620_3_) {
        stack.scale(0.5F, 0.5F, 0.5F);
    }
}
