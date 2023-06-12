package dev.sterner.geocluster.client.toast;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public interface IOreToast {
    Object TYPE = new Object();
    int BASE_HEIGHT = 24;

    default Object getType() {
        return TYPE;
    }

    default int getWidth() {
        return 140;
    }

    default int getHeight() {
        return BASE_HEIGHT;
    }

    IOreToast.Visibility draw(DrawContext ctx, OreToastManager oreToastManager, long l);

    default int getRequiredSpaceCount() {
        return MathHelper.ceilDiv(this.getHeight(), BASE_HEIGHT);
    }

    @Environment(EnvType.CLIENT)
    enum Visibility {
        SHOW(),
        HIDE();

        Visibility() {
        }
    }
}
