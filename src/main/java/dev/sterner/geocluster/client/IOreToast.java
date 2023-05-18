package dev.sterner.geocluster.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public interface IOreToast {
    Object TYPE = new Object();
    int BASE_HEIGHT = 24;

    default Object getType() {
        return TYPE;
    }

    default int getWidth() {
        return 100;
    }

    default int getHeight() {
        return BASE_HEIGHT;
    }

    IOreToast.Visibility draw(MatrixStack matrices, OreFoundManager oreFoundManager, long l);

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
