package io.github.f_anthropic.more.vanilla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public final class MoreVanilla implements ModInitializer {
    public static final String MOD_ID = "more_vanilla";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block SMOOTH_STONE_STAIRS = registerBlock(
            "smooth_stone_stairs",
            settings -> new StairBlock(Blocks.SMOOTH_STONE.defaultBlockState(), settings),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)
    );

    public static final Block SMOOTH_STONE_WALL = registerBlock(
            "smooth_stone_wall",
            WallBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)
    );

    public static final Block CUT_SANDSTONE_STAIRS = registerBlock(
            "cut_sandstone_stairs",
            settings -> new StairBlock(Blocks.CUT_SANDSTONE.defaultBlockState(), settings),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
    );

    public static final Block CUT_SANDSTONE_WALL = registerBlock(
            "cut_sandstone_wall",
            WallBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
    );

    public static final Block STONE_WALL = registerWall("stone_wall", Blocks.STONE);
    public static final Block QUARTZ_WALL = registerWall("quartz_wall", Blocks.QUARTZ_BLOCK);
    public static final Block PRISMARINE_BRICKS_WALL = registerWall("prismarine_bricks_wall", Blocks.PRISMARINE_BRICKS);
    public static final Block DARK_PRISMARINE_WALL = registerWall("dark_prismarine_wall", Blocks.DARK_PRISMARINE);
    public static final Block PURPUR_BLOCK_WALL = registerWall("purpur_block_wall", Blocks.PURPUR_BLOCK);
    public static final Block POLISHED_ANDESITE_WALL = registerWall("polished_andesite_wall", Blocks.POLISHED_ANDESITE);
    public static final Block POLISHED_GRANITE_WALL = registerWall("polished_granite_wall", Blocks.POLISHED_GRANITE);
    public static final Block POLISHED_DIORITE_WALL = registerWall("polished_diorite_wall", Blocks.POLISHED_DIORITE);

    private static Block registerBlock(
            String name,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        Block registeredBlock = factory.apply(properties.setId(blockKey));
        Registry.register(BuiltInRegistries.BLOCK, blockKey, registeredBlock);

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        Registry.register(
                BuiltInRegistries.ITEM,
                itemKey,
                new BlockItem(registeredBlock, new Item.Properties().setId(itemKey))
        );
        return registeredBlock;
    }

    private static Block registerWall(String name, Block baseBlock) {
        return registerBlock(name, WallBlock::new, BlockBehaviour.Properties.ofFullCopy(baseBlock));
    }

    @Override
    public void onInitialize() {
        ResourceKey<net.minecraft.world.item.CreativeModeTab> buildingBlocks = ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath("minecraft", "building_blocks")
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.SMOOTH_STONE, SMOOTH_STONE_STAIRS)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.SMOOTH_STONE_SLAB, SMOOTH_STONE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.CUT_SANDSTONE, CUT_SANDSTONE_STAIRS)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.CUT_SANDSTONE_SLAB, CUT_SANDSTONE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.STONE_SLAB, STONE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.QUARTZ_SLAB, QUARTZ_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.PRISMARINE_BRICK_SLAB, PRISMARINE_BRICKS_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.DARK_PRISMARINE_SLAB, DARK_PRISMARINE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.PURPUR_SLAB, PURPUR_BLOCK_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.POLISHED_ANDESITE_SLAB, POLISHED_ANDESITE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.POLISHED_GRANITE_SLAB, POLISHED_GRANITE_WALL)
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.insertAfter(Blocks.POLISHED_DIORITE_SLAB, POLISHED_DIORITE_WALL)
        );
        LOGGER.info("MoreVanilla initialized");
    }
}
