package dev.sterner.geocluster.mixin;

import dev.sterner.geocluster.client.IOreToastManager;
import dev.sterner.geocluster.client.OreToastManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IOreToastManager {

    private OreToastManager manager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectManager(RunArgs args, CallbackInfo ci){
        manager = new OreToastManager(MinecraftClient.class.cast(this));
    }

    @Override
    public OreToastManager getManager(){
        return this.manager;
    }
}
