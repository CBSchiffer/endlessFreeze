package com.clokkwork.endlessfreeze.entities;

import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrostCore extends FrostMob {
    private double pointsBank = 0.0D;
    private double corruptionRadius = 1.0D;
    private double pointsRequired = corruptionRadius;
    private Map<Block, Block> blockConversions = new HashMap<>();


    public FrostCore(EntityType<? extends FrostCore> type, Level world) {
        super(type, world);
        List<Block> tempTop = List.of(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.RED_SAND, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.DIRT_PATH);
        List<Block> tempCavern = List.of(Blocks.STONE, Blocks.COBBLESTONE, Blocks.SANDSTONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.NETHERRACK, Blocks.END_STONE, Blocks.BLACKSTONE, Blocks.BASALT, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.RED_SANDSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.GRAVEL);
        for (Block block : tempTop) {
            blockConversions.put(block, Blocks.SNOW_BLOCK);
        }
        for (Block block : tempCavern) {
            blockConversions.put(block, Blocks.PACKED_ICE);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();

        pointsBank += 1.0D;

        if (pointsBank >= pointsRequired) {
            this.corruptionRadius += 1.0D;
            this.pointsRequired = corruptionRadius;
            this.pointsBank = 0.0D;
            corruptBlocks();
        }

        if (this.level().isClientSide) {
            double radius = 1.0D;
            double speed = 0.1D;
            double verticalSpeed = 0.5D;

            double angle = (this.tickCount * speed) % (2 * Math.PI);

            double vertical = Math.sin(this.tickCount * verticalSpeed) * 0.5D;

            double x = this.getX() + radius * Math.cos(angle);
            double z = this.getZ() + radius * Math.sin(angle);

            double x2 = this.getX() + radius * Math.cos(2 * Math.PI - angle);
            double z2 = this.getZ() + radius * Math.sin(2 * Math.PI - angle);

            this.level().addParticle(ParticleTypes.SNOWFLAKE, x, this.getY() + 1.0D - vertical, z2, 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.SNOWFLAKE, x2, this.getY() + 1.0D - vertical, z, 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.SNOWFLAKE, x, this.getY() + 1.0D + vertical, z, 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.SNOWFLAKE, x2, this.getY() + 1.0D + vertical, z2, 0.0D, 0.0D, 0.0D);
        }
    }

    private void corruptBlocks() {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = (int) (this.getX() - corruptionRadius); x <= this.getX() + corruptionRadius; x++) {
            for (int y = (int) (this.getY() - corruptionRadius); y <= this.getY() + corruptionRadius; y++) {
                for (int z = (int) (this.getZ() - corruptionRadius); z <= this.getZ() + corruptionRadius; z++) {
                    mutablePos.set(x, y, z);
                    BlockState state = this.level().getBlockState(mutablePos);
                    Block newBlock = blockConversions.get(state.getBlock());
                    if (newBlock != null) {
                        this.level().setBlock(mutablePos, newBlock.defaultBlockState(), 1);
                    }
                }
            }
        }
    }

}
