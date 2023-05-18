package dev.sterner.geocluster.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;

import java.util.List;

public class OreToast implements IOreToast {
    private boolean justUpdated;
    private final List<Pair<BlockState, Direction>> blockStates = Lists.newArrayList();

    public OreToast(BlockState blockState, Direction direction) {
        this.blockStates.add(Pair.of(blockState, direction));
    }

    @Override
    public Visibility draw(MatrixStack matrices, OreToastManager oreToastManager, long startTime) {
        if (this.justUpdated) {
            this.justUpdated = false;
        }

        if (this.blockStates.isEmpty()) {
            return Visibility.HIDE;
        } else {
            Pair<BlockState, Direction> pair = this.blockStates.get((int)(startTime / Math.max(1L, 5000L / (long)this.blockStates.size()) % (long)this.blockStates.size()));
            BlockState blockState = pair.getFirst();
            TextRenderer textRenderer = oreToastManager.client.textRenderer;
            MutableText msg;
            if(pair.getSecond() == null){
                msg = Text.translatable("geocluster.pro_pick.tooltip.found_surface");
            } else {
                msg = Text.translatable("geocluster.pro_pick.tooltip.found", pair.getSecond());
            }

            matrices.push();
            matrices.scale(0.5f,0.5f,0.5f);
            textRenderer.drawWithShadow(matrices, blockState.getBlock().getName(), 50.0F, 20.0F, 16777215);
            textRenderer.drawWithShadow(matrices, msg, 50.0F, 32.0F, 16777215);
            matrices.pop();


            ItemStack itemStack = blockState.getBlock().asItem().getDefaultStack();
            RenderSystem.applyModelViewMatrix();
            oreToastManager.getClient().getItemRenderer().renderInGui(itemStack, 8, 8);
            return startTime >= 5000L ? IOreToast.Visibility.HIDE : IOreToast.Visibility.SHOW;
        }
    }
}
