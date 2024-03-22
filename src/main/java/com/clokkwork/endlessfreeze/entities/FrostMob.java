package com.clokkwork.endlessfreeze.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class FrostMob extends Monster {
    protected FrostMob(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    protected static class FrostAttackNearestTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public FrostAttackNearestTargetGoal(FrostMob p_33005_, Class<T> p_33006_) {
            super(p_33005_, p_33006_, true);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.target != null && !(this.target instanceof FrostMob);
        }
    }
}
