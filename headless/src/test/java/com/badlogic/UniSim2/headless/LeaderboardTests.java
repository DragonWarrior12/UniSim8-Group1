package com.badlogic.UniSim2.headless;

import com.badlogic.UniSim2.leaderboardManager.LeaderboardManager;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.badlogic.UniSim2.leaderboardManager.LeaderboardManager.getSortedLeaderboard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaderboardTests {
    public static String savedLeaderboard;
    public static Preferences preferences;

    @BeforeAll
    public static void setup() {
        HeadlessLauncher.main(new String[0]);
        preferences = Gdx.app.getPreferences(Consts.PREFERENCES_NAME);
        savedLeaderboard = preferences.getString(Consts.PREFERENCES_KEY, "{}");
    }

    @BeforeEach
    public void clearLeaderboardEntry() {
        preferences.remove(Consts.PREFERENCES_KEY);
        preferences.flush();
    }

    @Test
    public void testAddScore() {
        LeaderboardManager.addScore("Test1", 100f);
        assertEquals(1, getSortedLeaderboard().size(), "Incorrect number of scores");

        LeaderboardManager.addScore("Test1", 90f);
        assertEquals(100f, getSortedLeaderboard().get(0).getValue(), 0.0001, "Score overwritten with lower value");

        LeaderboardManager.addScore("Test2", 100f);
        assertEquals(2, getSortedLeaderboard().size(), "Incorrect number of scores");
    }

    @Test
    public void testClear() {
        LeaderboardManager.addScore("Test2", 100f);
        LeaderboardManager.clearLeaderboard();
        assertEquals(0, getSortedLeaderboard().size(), "Leaderboard not empty");
    }

    @Test
    public void testSortScores() {
        LeaderboardManager.addScore("Test1", 100f);
        LeaderboardManager.addScore("Test2", 101f);
        LeaderboardManager.addScore("Test3", 99f);
        LeaderboardManager.addScore("Test4", 102f);
        LeaderboardManager.addScore("Test5", 98f);
        LeaderboardManager.addScore("Test6", 100f);

        ArrayList<Map.Entry<String, Float>> sortedLeaderboard = LeaderboardManager.getSortedLeaderboard();

        for (int x = 0; x < sortedLeaderboard.size() - 1; x++) { // skip last
            assertTrue(sortedLeaderboard.get(x).getValue() >= sortedLeaderboard.get(x + 1).getValue());
        }
    }

    @AfterAll
    public static void cleanPreferences() {
        preferences.putString(Consts.PREFERENCES_KEY, savedLeaderboard);
        preferences.flush();
    }
}
