package dev.sterner.geocluster.client.toast;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import dev.sterner.geocluster.client.event.ClusterInfo;
import dev.sterner.geocluster.client.event.GeoclusterEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class OreToast implements IOreToast {
    private boolean justUpdated;
    private final List<Pair<BlockState, Direction>> blockStates = Lists.newArrayList();
    private ClientPlayerEntity player;

    public OreToast(ClientPlayerEntity player, BlockState blockState, Direction direction) {
        this.blockStates.add(Pair.of(blockState, direction));
        this.player = player;
    }

    public List<BlockState> getStates() {
        List<BlockState> stateList = new ArrayList<>();
        for (Pair<BlockState, Direction> s : blockStates) {
            stateList.add(s.getFirst());
        }
        return stateList;
    }

    @Override
    public Visibility draw(DrawContext ctx, OreToastManager oreToastManager, long startTime) {
        if (this.justUpdated) {
            this.justUpdated = false;
        }

        if (this.blockStates.isEmpty()) {
            return Visibility.HIDE;
        } else {

            Pair<BlockState, Direction> pair = this.blockStates.get((int) (startTime / Math.max(1L, 5000L / (long) this.blockStates.size()) % (long) this.blockStates.size()));
            BlockState blockState = pair.getFirst();
            TextRenderer textRenderer = oreToastManager.client.textRenderer;

            MutableText msg;
            if (pair.getSecond() == null) {
                msg = Text.translatable("geocluster.pro_pick.tooltip.found_surface");
            } else {
                msg = Text.translatable("geocluster.pro_pick.tooltip.found", pair.getSecond());
            }

            ClusterInfo info = GeoclusterEvent.MODIFY_CLUSTER_INFO.invoker().getClusterInfo(new ClusterInfo(player, blockState, blockState.getBlock().getName(), msg, Identifier.ofVanilla("toast/advancement"), false));
            if (info.isCancelled()) {
                return Visibility.HIDE;
            }

            ctx.drawGuiTexture(info.toastTexture(), 0, 0, this.getWidth(), this.getHeight());
            ctx.getMatrices().push();
            ctx.getMatrices().scale(0.85f, 0.85f, 1);
            ctx.drawTextWithShadow(textRenderer, info.name(), 30, 10, 16777215);
            ctx.drawTextWithShadow(textRenderer, info.msg(), 30, 22, 16777215);
            ctx.getMatrices().pop();

            ItemStack itemStack = info.state().getBlock().asItem().getDefaultStack();
            RenderSystem.applyModelViewMatrix();
            ctx.drawItemWithoutEntity(itemStack, 8, 8);
            return startTime >= 5000L ? IOreToast.Visibility.HIDE : IOreToast.Visibility.SHOW;
        }
    }
}
