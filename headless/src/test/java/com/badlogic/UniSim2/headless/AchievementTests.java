package com.badlogic.UniSim2.headless;

// new class

import com.badlogic.UniSim2.GUImanager.GameMenu;
import com.badlogic.UniSim2.achievements.Achievement;
import com.badlogic.UniSim2.achievements.AchievementManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AchievementTests {
    AchievementManager achievementManager;
    boolean complete;

    @BeforeEach
    public void setup() {
        achievementManager = new AchievementManager(mock(GameMenu.class));
        achievementManager.incompleteAchievements.clear();
        complete = false;
    }

    @Test
    public void testAchievementCompletion() {
        Achievement achievement = new Achievement(
            "Test",
            "",
            () -> complete,
            1.2f
            );

        achievementManager.incompleteAchievements.add(achievement);

        achievementManager.checkAchievements();

        assertTrue(achievementManager.incompleteAchievements.contains(achievement), "Achievement removed from incomplete early");
        assertFalse(achievementManager.completeAchievements.contains(achievement), "Achievement added to completed early");

        complete = true;

        achievementManager.checkAchievements();

        assertFalse(achievementManager.incompleteAchievements.contains(achievement), "Achievement not removed form incomplete");
        assertTrue(achievementManager.completeAchievements.contains(achievement), "Achievement not added to complete");
    }

    @Test
    public void testAchievementOnComplete() {
        Achievement achievement = new Achievement(
            "Test",
            "",
            () -> true,
            1.2f,
            () -> complete = true
        );

        achievementManager.incompleteAchievements.add(achievement);

        achievementManager.checkAchievements();

        assertTrue(complete, "Achievement OnComplete didn't run");
    }
}
