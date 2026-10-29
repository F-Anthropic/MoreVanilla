package io.github.f_anthropic.more.vanilla;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;

import java.util.List;

public final class WallConnectionTests {
    private static final BlockPos CENTER = new BlockPos(3, 2, 3);

    private static List<Block> walls() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> block instanceof WallBlock)
                .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MoreVanilla.MOD_ID))
                .toList();
    }

    @GameTest
    public void wallsAreTagged(GameTestHelper helper) {
        helper.assertValueEqual(walls().size(), 10, "Number of custom walls");
        for (Block wall : walls()) {
            helper.assertTrue(wall.defaultBlockState().is(BlockTags.WALLS),
                    "Missing minecraft:walls: " + BuiltInRegistries.BLOCK.getKey(wall));
        }
        helper.succeed();
    }

    @GameTest
    public void wallItemsAreTagged(GameTestHelper helper) {
        for (Block wall : walls()) {
            helper.assertTrue(wall.asItem().getDefaultInstance().is(ItemTags.WALLS),
                    "Missing item minecraft:walls: " + BuiltInRegistries.BLOCK.getKey(wall));
        }
        helper.succeed();
    }

    @GameTest(maxTicks = 100)
    public void allWallPairsConnectBothWays(GameTestHelper helper) {
        for (Block first : walls()) {
            for (Block second : walls()) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    assertPair(helper, first, second, direction);
                }
            }
        }
        helper.succeed();
    }

    @GameTest(maxTicks = 100)
    public void vanillaWallsConnectBothWays(GameTestHelper helper) {
        List<Block> vanillaWalls = BuiltInRegistries.BLOCK.stream()
                .filter(block -> block instanceof WallBlock)
                .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals("minecraft"))
                .toList();
        helper.assertTrue(!vanillaWalls.isEmpty(), "Vanilla wall list must not be empty");
        for (Block wall : walls()) {
            for (Block vanillaWall : vanillaWalls) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    assertPair(helper, wall, vanillaWall, direction);
                    assertPair(helper, vanillaWall, wall, direction);
                }
            }
        }
        helper.succeed();
    }

    @GameTest(maxTicks = 100)
    public void straightCornersJunctionsAndTallSidesMatchVanilla(GameTestHelper helper) {
        for (Block wall : walls()) {
            for (int mask = 0; mask < 16; mask++) {
                for (boolean covered : new boolean[]{false, true}) {
                    clear(helper);
                    int index = 0;
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if ((mask & (1 << index)) != 0) {
                            place(helper, CENTER.relative(direction), Blocks.COBBLESTONE_WALL);
                        }
                        index++;
                    }
                    if (covered) {
                        helper.setBlock(CENTER.above(), Blocks.STONE);
                    }
                    BlockState expected = placement(helper, CENTER, Blocks.COBBLESTONE_WALL);
                    BlockState actual = placement(helper, CENTER, wall);
                    helper.assertValueEqual(actual.getValue(WallBlock.UP), expected.getValue(WallBlock.UP),
                            "Post visibility: " + BuiltInRegistries.BLOCK.getKey(wall));
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        var property = WallBlock.PROPERTY_BY_DIRECTION.get(direction);
                        helper.assertValueEqual(actual.getValue(property), expected.getValue(property),
                                "Side " + direction + ": " + BuiltInRegistries.BLOCK.getKey(wall));
                    }
                }
            }
        }
        helper.succeed();
    }

    private static void assertPair(GameTestHelper helper, Block first, Block second, Direction direction) {
        clear(helper);
        BlockPos neighbor = CENTER.relative(direction);
        place(helper, CENTER, first);
        place(helper, neighbor, second);
        helper.assertBlockProperty(CENTER, WallBlock.PROPERTY_BY_DIRECTION.get(direction), WallSide.LOW);
        helper.assertBlockProperty(neighbor, WallBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), WallSide.LOW);
        helper.setBlock(neighbor, Blocks.AIR);
        helper.assertBlockProperty(CENTER, WallBlock.PROPERTY_BY_DIRECTION.get(direction), WallSide.NONE);
    }

    private static BlockState placement(GameTestHelper helper, BlockPos pos, Block block) {
        return block.getStateForPlacement(new DirectionalPlaceContext(helper.getLevel(), helper.absolutePos(pos),
                Direction.DOWN, block.asItem().getDefaultInstance(), Direction.UP));
    }

    private static void place(GameTestHelper helper, BlockPos pos, Block block) {
        helper.setBlock(pos, placement(helper, pos, block));
    }

    private static void clear(GameTestHelper helper) {
        helper.setBlock(CENTER, Blocks.AIR);
        helper.setBlock(CENTER.above(), Blocks.AIR);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            helper.setBlock(CENTER.relative(direction), Blocks.AIR);
        }
    }
}
