package com.clokkwork.endlessfreeze.misc;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class FrostflameEff extends MobEffect  {

    public FrostflameEff() {
        super(MobEffectCategory.HARMFUL, 0x51E4F5);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        entity.hurt(entity.damageSources().magic(), 1.0F * (amplifier + 1));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, (amplifier + 1)));
        if(entity instanceof Player) {
            Player player = (Player) entity;
            player.causeFoodExhaustion(0.025F * (amplifier + 1));
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int temp = 40 >> amplifier;
        if (temp > 0) {
            return duration % temp == 0;
        } else {
            return true;
        }
    }

}
