package com.badlogic.UniSim2.headless;

// new class

import com.badlogic.UniSim2.resources.Assets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AssetTests extends AbstractHeadlessGdxTest {
    @Test
    public void testAssetsExist() {
        try {
            Assets.loadTextures();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
