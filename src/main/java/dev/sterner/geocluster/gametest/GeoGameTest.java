package dev.sterner.geocluster.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class GeoGameTest implements FabricGameTest {
    @GameTest(templateName = EMPTY_STRUCTURE, batchId = "placeOreAndValidate")
    public void placeOreAndValidateSample(TestContext ctx){
        ctx.complete();
    }
}
