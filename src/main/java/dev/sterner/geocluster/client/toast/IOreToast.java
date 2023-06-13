package dev.sterner.geocluster.client.toast;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public interface IOreToast {
    Object TYPE = new Object();

    default Object getType() {
        return TYPE;
    }

    default int getWidth() {
        return 160;
    }

    default int getHeight() {
        return 32;
    }

    IOreToast.Visibility draw(DrawContext ctx, OreToastManager oreToastManager, long l);

    default int getRequiredSpaceCount() {
        return MathHelper.ceilDiv(this.getHeight(), 32);
    }

    @Environment(EnvType.CLIENT)
    enum Visibility {
        SHOW(),
        HIDE();

        Visibility() {
        }
    }
}
