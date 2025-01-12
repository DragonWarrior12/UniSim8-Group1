package com.badlogic.UniSim2.leaderboardManager;

// new class

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class LeaderboardManager {
    private static Json jsonFile = new Json();
    private static Preferences pref = Gdx.app.getPreferences(Consts.PREFERENCES_NAME);

    public static Map<String, Float> getLeaderboard() {
        String jsonString = pref.getString(Consts.PREFERENCES_KEY, "{}");
        return jsonFile.fromJson(HashMap.class, jsonString);
    }

    public static void saveLeaderboard(Map<String, Float> leaderboard) {
        pref.putString(Consts.PREFERENCES_KEY, jsonFile.toJson(leaderboard));
        pref.flush();
    }


    /**
     * adds the score of the user to the leaderboard
     * if a user already has a score then it doesn't get overwritten unless the new score is higher
     *
     * @param username the name of the player
     * @param score    the score the player scored
     */
    public static void addScore(String username, float score) {
        Map<String, Float> leaderboard = getLeaderboard();
        leaderboard.put(username, Math.max(score, leaderboard.getOrDefault(username, 0f)));
        saveLeaderboard(leaderboard);
    }

    /**
     * Gets the leaderboard and converts it into a list and sorts that list in descending order and then returns it
     *
     * @return the sorted leaderboard
     */
    public static ArrayList<Entry<String, Float>> getSortedLeaderboard() {
        Map<String, Float> leaderboard = getLeaderboard();
        ArrayList<Entry<String, Float>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());

        // Sorts in descending order based on score
        sortedLeaderboard.sort((score1, score2) -> score2.getValue().compareTo(score1.getValue()));

        return sortedLeaderboard;
    }


    public static void clearLeaderboard() {
        Map<String, Float> emptyLeaderboard = new HashMap<>();
        saveLeaderboard(emptyLeaderboard);
    }

    public static int getSize() {
        Map<String, Float> leaderboard = getLeaderboard();
        return leaderboard.size();
    }
}
