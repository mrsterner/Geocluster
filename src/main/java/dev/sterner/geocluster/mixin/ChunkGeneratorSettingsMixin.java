package dev.sterner.geocluster.mixin;

import dev.sterner.geocluster.GeoclusterConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {


    @Inject(method = "oreVeins", at = @At(value = "RETURN"), cancellable = true)
    private void geocluster$removeVeins(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!GeoclusterConfig.REMOVE_VEINS);
    }
}
