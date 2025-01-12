package com.badlogic.UniSim2.headless;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BuildingTests {
    Building building;

    @BeforeEach
    public void setup() {
        Texture tex = mock(Texture.class);
        when(tex.getWidth()).thenReturn(Consts.RECREATIONAL_WIDTH);
        when(tex.getHeight()).thenReturn(Consts.RECREATIONAL_HEIGHT);

        building = new Building(
            tex,
            tex,
            tex,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            Building.BuildingTypes.Recreational
        );
    }

    @Test
    public void testHandleDragging() {
        Vector2 mousePos = new Vector2(300, 400);

        checkMousePos(mousePos);

        mousePos.x = 309.99f;

        checkMousePos(mousePos);

        mousePos.x = 310f;

        checkMousePos(mousePos);

        mousePos.y = 319.99f;

        checkMousePos(mousePos);

        mousePos.y = 320f;

        checkMousePos(mousePos);
    }

    @Test
    public void testClampPosition() {
        Vector2 mousePos = new Vector2(-100, -100);

        building.handleDragging(mousePos, false);

        building.clampPosition();

        assertEquals(Consts.MAP_MIN_X_BOUNDARY, building.getX());
        assertEquals(Consts.MAP_MIN_Y_BOUNDARY, building.getY());

        mousePos = new Vector2(100_000_000, 100_000_000);

        building.handleDragging(mousePos, false);

        building.clampPosition();

        assertEquals(Consts.MAP_MAX_X_BOUNDARY - building.getBuildingWidth(), building.getX());
        assertEquals(Consts.MAP_MAX_Y_BOUNDARY - building.getBuildingHeight(), building.getY());
    }

    @Test
    public void testGetCentre() {
        checkCentre();

        building.handleDragging(new Vector2(200, 500), true);

        checkCentre();
    }

    @Test
    public void testSelection() {
        building.selectBuilding();

        assert(building.getIsSelected());
    }

    @Test
    public void testPlacement() {
        BuildingMenu bMenu = new BuildingMenu(mock(Stage.class), mock(BuildingManager.class));
        bMenu.createBuildingMenu();
        mock(SoundManager.class);

        building.selectBuilding();

        building.placeBuilding();

        assert(!building.getIsSelected());
        assert(building.getIsPlaced());
    }

    @Test
    public void testGetDistanceFromBuilding() {
        Texture tex = mock(Texture.class);
        when(tex.getWidth()).thenReturn(Consts.LIBRARY_WIDTH);
        when(tex.getHeight()).thenReturn(Consts.LIBRARY_HEIGHT);

        Building building2 = new Building(
            tex,
            tex,
            tex,
            Consts.LIBRARY_WIDTH,
            Consts.LIBRARY_HEIGHT,
            Building.BuildingTypes.Library
        );

        Vector2 buildingSize = new Vector2(building.getBuildingWidth(), building.getBuildingHeight()).scl(0.5f);
        Vector2 building2Size = new Vector2(building2.getBuildingWidth(), building2.getBuildingHeight()).scl(0.5f);

        building.handleDragging(new Vector2(0, 100).add(buildingSize), false);
        building2.handleDragging(new Vector2(0, 0).add(building2Size), false);

        assertEquals(100, building.getDistanceFrom(building2), 0.001);
        assertEquals(building2.getDistanceFrom(building), building.getDistanceFrom(building2), 0.001);


        building.handleDragging(new Vector2(100, 100).add(buildingSize), false);
        building2.handleDragging(new Vector2(400, 300).add(building2Size), false);

        assertEquals(500, building.getDistanceFrom(building2), 0.001);
        assertEquals(building2.getDistanceFrom(building), building.getDistanceFrom(building2), 0.001);
    }

    void checkCentre() {
        Vector2 centre = building.getCentre();
        assertEquals(building.getX() + building.getBuildingWidth() / 2f, centre.x, 0.0001);
        assertEquals(building.getY() + building.getBuildingHeight() / 2f, centre.y, 0.0001);
    }

    void checkMousePos(Vector2 mousePos) {
        building.handleDragging(mousePos, false);
        assertEquals(MathUtils.round((mousePos.x - building.getBuildingWidth() / 2f) / Consts.CELL_SIZE) * Consts.CELL_SIZE,
            building.getX(),
            0.001f);
        assertEquals(MathUtils.round((mousePos.y - building.getBuildingHeight() / 2f) / Consts.CELL_SIZE) * Consts.CELL_SIZE,
            building.getY(),
            0.001f);
    }
}
