package dev.sterner.geocluster;

import dev.sterner.geocluster.client.network.S2CProspectingPacket;
import dev.sterner.geocluster.client.toast.IOreToastManager;
import dev.sterner.geocluster.client.toast.OreToastManager;
import dev.sterner.geocluster.common.data.WorldGenDataReloadListener;
import dev.sterner.geocluster.common.registry.GeoclusterObjects;
import dev.sterner.geocluster.common.registry.GeoclusterWorldgenRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Geocluster implements ModInitializer, ClientModInitializer {
    public static final String MODID = "geocluster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final RegistryKey<ItemGroup> GEOCLUSTER_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, id(MODID));

    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    @Override
    public void onInitialize() {
        MidnightConfig.init(MODID, GeoclusterConfig.class);
        GeoclusterObjects.init();

        Registry.register(Registries.ITEM_GROUP, GEOCLUSTER_ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(GeoclusterObjects.ZINC_INGOT))
                .displayName(Text.translatable(MODID + ".group.main"))
                .build());

        GeoclusterWorldgenRegistry.init();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WorldGenDataReloadListener());

        DynamicRegistrySetupCallback.EVENT.register(registryView -> {
            registryView.getOptional(RegistryKeys.CONFIGURED_FEATURE).ifPresent(configuredFeatures -> {
                GeoclusterWorldgenRegistry.init(registryView, configuredFeatures);
            });
        });
    }

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2CProspectingPacket.ID, S2CProspectingPacket::handle);
        HudRenderCallback.EVENT.register(this::renderFoundOres);
    }

    private void renderFoundOres(DrawContext ctx, float v) {
        if (!MinecraftClient.getInstance().skipGameRender) {
            OreToastManager manager = ((IOreToastManager) MinecraftClient.getInstance()).getManager();
            manager.draw(ctx);
        }
    }
}