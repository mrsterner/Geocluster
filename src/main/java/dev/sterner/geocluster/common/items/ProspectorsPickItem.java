package dev.sterner.geocluster.common.items;

import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.client.network.S2CProspectingPacket;
import dev.sterner.geocluster.common.utils.GeoclusterUtils;
import dev.sterner.geocluster.common.utils.ProspectingUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;

public class ProspectorsPickItem extends Item {
    public static Item.Settings props = new Item.Settings().maxCount(1).maxDamage(1024);
    private final Type TYPE;

    public ProspectorsPickItem(Type type) {
        super(props);
        TYPE = type;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World worldIn = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction facing = context.getSide();

        if (player == null) {
            return ActionResult.PASS;
        }

        if (player.isSneaking()) {
            return ActionResult.CONSUME;
        } else {
            ItemStack stack = player.getStackInHand(hand);

            if (worldIn.isClient) {
                player.swingHand(hand);
                return ActionResult.CONSUME;
            }

            if (!player.isCreative()) {
                stack.damage(1, player, (x) -> x.sendToolBreakStatus(hand));
            }

            int range = TYPE == Type.IRON ? GeoclusterConfig.PROSPECTORS_PICK_RANGE : TYPE == Type.COPPER ? GeoclusterConfig.PROSPECTORS_PICK_RANGE - 1 : GeoclusterConfig.PROSPECTORS_PICK_RANGE - 2;
            int diam = TYPE == Type.IRON ? GeoclusterConfig.PROSPECTORS_PICK_DIAMETER : TYPE == Type.COPPER ? GeoclusterConfig.PROSPECTORS_PICK_DIAMETER - 1 : GeoclusterConfig.PROSPECTORS_PICK_DIAMETER - 2;

            if (range <= 0) {
                range = 1;
            }

            if (diam <= 0) {
                diam = 1;
            }

            int zStart = facing == Direction.NORTH ? 0 : facing == Direction.SOUTH ? -range : -(diam / 2);
            int zEnd = facing == Direction.NORTH ? range : facing == Direction.SOUTH ? 0 : diam / 2;
            int xStart = facing == Direction.EAST ? -range : facing == Direction.WEST ? 0 : -(diam / 2);
            int xEnd = facing == Direction.EAST ? 0 : facing == Direction.WEST ? range : diam / 2;
            int yStart = facing == Direction.UP ? -range : facing == Direction.DOWN ? 0 : -(diam / 2);
            int yEnd = facing == Direction.UP ? 0 : facing == Direction.DOWN ? range : diam / 2;

            prospect(player, stack, worldIn, pos, facing, xStart, xEnd, yStart, yEnd, zStart, zEnd);
            player.swingHand(hand);
        }
        return ActionResult.CONSUME;
    }

    private void prospect(PlayerEntity player, ItemStack stack, World world, BlockPos pos, Direction facing, int xStart, int xEnd, int yStart, int yEnd, int zStart, int zEnd) {
        HashSet<BlockState> foundBlocks = new HashSet<>();
        HashSet<BlockPos> foundBlockPos = new HashSet<>();
        HashSet<BlockState> depositBlocks = ProspectingUtils.getDepositBlocks();

        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                for (int z = zStart; z <= zEnd; z++) {
                    BlockPos tmpPos = pos.add(x, y, z);
                    BlockState state = world.getBlockState(tmpPos);
                    if (depositBlocks.contains(state) && ProspectingUtils.canDetect(state)) {
                        foundBlocks.add(state);
                        foundBlockPos.add(tmpPos);
                    }
                }
            }
        }

        var optional = foundBlockPos.stream().findAny();
        if (!foundBlocks.isEmpty()) {
            S2CProspectingPacket.send(player, foundBlocks, facing.getOpposite().getName());
            optional.ifPresent(blockPos -> world.playSound(null, blockPos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.15F, 2F));
            return;
        }
        if (!GeoclusterConfig.DISABLE_IN_AREA_MESSAGE) {
            prospectChunk(world, pos, player);
        }
    }

    private void prospectChunk(World world, BlockPos pos, PlayerEntity player) {
        HashSet<BlockState> foundBlocks = new HashSet<>();
        HashSet<BlockState> depositBlocks = ProspectingUtils.getDepositBlocks();
        ChunkPos tempPos = new ChunkPos(pos);

        for (int x = tempPos.getStartX(); x <= tempPos.getEndX(); x++) {
            for (int z = tempPos.getStartZ(); z <= tempPos.getEndZ(); z++) {
                for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                    BlockState state = world.getBlockState(new BlockPos(x, y, z));
                    if (depositBlocks.contains(state) && ProspectingUtils.canDetect(state)) {
                        foundBlocks.add(state);
                    }
                }
            }
        }

        if (!foundBlocks.isEmpty()) {
            S2CProspectingPacket.send(player, foundBlocks, "");
            return;
        }

        player.sendMessage(GeoclusterUtils.tryTranslate("geocluster.pro_pick.tooltip.nonefound_surface"), true);
    }

    public enum Type {
        STONE,
        COPPER,
        IRON
    }
}
