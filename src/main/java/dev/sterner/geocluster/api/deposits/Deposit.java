package dev.sterner.geocluster.api.deposits;

import dev.sterner.geocluster.api.DepositUtils;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import dev.sterner.geocluster.common.utils.FeatureUtils;
import dev.sterner.geocluster.common.utils.SampleUtils;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import java.util.HashMap;
import java.util.Map;

import static dev.sterner.geocluster.common.blocks.SampleBlock.FACING;

public abstract class Deposit {

    public Deposit() {

    }

    public static void findAndPlaceSample(int maxSampleCnt, BlockState sampleState, StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        for (int i = 0; i < maxSampleCnt; i++) {
            BlockState tmp = sampleState;
            if (tmp == null) {
                continue;
            }

            BlockPos samplePos = SampleUtils.getSamplePosition(world, new ChunkPos(pos), pos);

            if (samplePos == null || SampleUtils.inNonWaterFluid(world, samplePos)) {
                continue;
            }

            if (SampleUtils.isInWater(world, samplePos) && tmp.contains(Properties.WATERLOGGED)) {
                tmp = tmp.with(Properties.WATERLOGGED, Boolean.TRUE);
            }

            FeatureUtils.enqueueBlockPlacement(world, samplePos, tmp.with(FACING, Direction.fromHorizontal(world.getRandom().nextBetween(0, 3))), deposits, chunksGenerated);
            FeatureUtils.fixSnowyBlock(world, samplePos);
        }
    }
}
