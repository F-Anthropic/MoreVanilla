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

    @Override
    public void onInitialize() {
        ResourceKey<net.minecraft.world.item.CreativeModeTab> buildingBlocks = ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath("minecraft", "building_blocks")
        );
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(output ->
                output.accept(SMOOTH_STONE_STAIRS.asItem().getDefaultInstance())
        );
        LOGGER.info("MoreVanilla initialized");
    }
}
