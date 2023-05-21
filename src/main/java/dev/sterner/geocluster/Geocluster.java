package dev.sterner.geocluster;

import dev.sterner.geocluster.api.GeoclusterAPI;
import dev.sterner.geocluster.client.network.S2CProspectingPacket;
import dev.sterner.geocluster.client.toast.IOreToastManager;
import dev.sterner.geocluster.client.toast.OreToastManager;
import dev.sterner.geocluster.common.data.WorldGenDataReloadListener;
import dev.sterner.geocluster.common.registry.GeoclusterObjects;
import dev.sterner.geocluster.common.registry.GeoclusterWorldgenRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Geocluster implements ModInitializer, ClientModInitializer {
    public static final String MODID = "geocluster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(GeoclusterObjects.ZINC_INGOT));

    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    @Override
    public void onInitialize() {
        MidnightConfig.init(MODID, GeoclusterConfig.class);
        GeoclusterAPI.init();
        GeoclusterObjects.init();
        GeoclusterWorldgenRegistry.init();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WorldGenDataReloadListener());
    }

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2CProspectingPacket.ID, S2CProspectingPacket::handle);
        HudRenderCallback.EVENT.register(this::renderFoundOres);
    }

    private void renderFoundOres(MatrixStack matrixStack, float v) {
        OreToastManager manager = ((IOreToastManager) MinecraftClient.getInstance()).getManager();
        manager.draw(matrixStack);
    }
}