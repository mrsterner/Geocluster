package dev.sterner.geocluster.client;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public class OreToastManager extends DrawableHelper {
    private static final int SPACES = 5;
    final MinecraftClient client;
    private final List<Entry<?>> visibleEntries = new ArrayList<>();
    private final BitSet occupiedSpaces = new BitSet(SPACES);
    private final Deque<IOreToast> toastQueue = Queues.newArrayDeque();

    public OreToastManager(MinecraftClient client) {
        this.client = client;
    }

    public void draw(MatrixStack matrices) {
        if (!this.client.options.hudHidden) {
            int i = this.client.getWindow().getScaledWidth();
            this.visibleEntries.removeIf((visibleEntry) -> {


                if (visibleEntry != null) {
                    boolean bl = visibleEntry.draw(i, matrices);
                    if(bl){
                        this.occupiedSpaces.clear(visibleEntry.topIndex, visibleEntry.topIndex + visibleEntry.requiredSpaceCount);
                        return true;
                    }
                } else {
                    return false;
                }
                return false;
            });
            if (!this.toastQueue.isEmpty() && this.getEmptySpaceCount() > 0) {
                this.toastQueue.removeIf((toast) -> {
                    int k = toast.getRequiredSpaceCount();
                    int j = this.getTopIndex(k);
                    if (j != -1) {
                        this.visibleEntries.add(new Entry<>(toast, j, k));
                        this.occupiedSpaces.set(j, j + k);
                        return true;
                    } else {
                        return false;
                    }
                });
            }

        }
    }

    private int getTopIndex(int requiredSpaces) {
        int i = 0;
        int emptySpaceCount = getEmptySpaceCount();

        for (int j = 0; j < SPACES; j++) {
            i = (occupiedSpaces.get(j)) ? 0 : i + 1;
            if (i == requiredSpaces && emptySpaceCount >= requiredSpaces) {
                return j + 1 - i;
            }
        }

        return -1;
    }

    private int getEmptySpaceCount() {
        return 5 - this.occupiedSpaces.cardinality();
    }

    public void add(IOreToast toast) {
        this.toastQueue.add(toast);
    }

    public MinecraftClient getClient() {
        return this.client;
    }

    @Environment(EnvType.CLIENT)
    class Entry<T extends IOreToast> {
        private static final long DISAPPEAR_TIME = 600L;
        private final T instance;
        final int topIndex;
        final int requiredSpaceCount;
        private long startTime = -1L;
        private long showTime = -1L;
        private IOreToast.Visibility visibility;

        Entry(T instance, int topIndex, int requiredSpaceCount) {
            this.visibility = IOreToast.Visibility.SHOW;
            this.instance = instance;
            this.topIndex = topIndex;
            this.requiredSpaceCount = requiredSpaceCount;
        }

        private float getDisappearProgress(long time) {
            float f = MathHelper.clamp((float)(time - this.startTime) / DISAPPEAR_TIME, 0.0F, 1.0F);
            f *= f;
            return this.visibility == IOreToast.Visibility.HIDE ? 1.0F - f : f;
        }

        public boolean draw(int x, MatrixStack matrices) {
            long l = Util.getMeasuringTimeMs();
            if (this.startTime == -1L) {
                this.startTime = l;
            }

            if (this.visibility == IOreToast.Visibility.SHOW && l - this.startTime <= 600L) {
                this.showTime = l;
            }

            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.push();
            matrixStack.translate((float) x - (float) this.instance.getWidth() * this.getDisappearProgress(l), client.getWindow().getScaledHeight() - 32 - this.topIndex * 24, 800.0);
            RenderSystem.applyModelViewMatrix();
            IOreToast.Visibility visibility = this.instance.draw(matrices, OreToastManager.this, l - this.showTime);
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
            if (visibility != this.visibility) {
                this.startTime = l - (long)((int)((1.0F - this.getDisappearProgress(l)) * 600.0F));
                this.visibility = visibility;
            }
            return this.visibility == IOreToast.Visibility.HIDE && l - this.startTime > 600L;
        }
    }
}
