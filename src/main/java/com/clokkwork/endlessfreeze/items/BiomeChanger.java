package com.clokkwork.endlessfreeze.items;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.BiomeManager;

import java.util.List;
import java.util.Optional;

public class BiomeChanger extends Item {
    public static final List<String> BLOCKS_TO_REPLACE = List.of("minecraft:grass_block", "minecraft:dirt", "minecraft:sand");
    public BiomeChanger(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        LevelChunk chunk = world.getChunkAt(pos);

        // Replace all blocks in the list with the block at the clicked position
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                for(int y = 0; y < world.getMaxBuildHeight(); y++) {
                    BlockPos blockPos = new BlockPos(chunk.getPos().getMinBlockX() + x, y, chunk.getPos().getMinBlockZ() + z);
                    if(BLOCKS_TO_REPLACE.contains(world.registryAccess().registryOrThrow(Registries.BLOCK).getKey(world.getBlockState(blockPos).getBlock()).toString())) {
                        world.setBlockAndUpdate(blockPos, Blocks.SNOW_BLOCK.defaultBlockState());
                    }
                }
            }
        }

        // Replace the biome of the chunk with the ice spikes biome
//        Registry<Biome> biomeRegistry = world.registryAccess().registryOrThrow(Registries.BIOME);
//        Optional<Biome> iceSpikesKey = biomeRegistry.getOptional(ResourceKey.create(Registries.BIOME, new ResourceLocation("minecraft:ice_spikes")));
//        if(iceSpikesKey.isPresent()) {
//            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
//            buf.writeInt(biomeRegistry.getId(iceSpikesKey.get()));
//            chunk.replaceBiomes(buf);
//        }
        // BiomeManager.addBiome(BiomeManager.BiomeType.ICY, world.registryAccess().registryOrThrow(BuiltInRegistries.BIOME_SOURCE.getKey(world.getBiome(pos)).get());

        return InteractionResult.PASS;
    }
}
