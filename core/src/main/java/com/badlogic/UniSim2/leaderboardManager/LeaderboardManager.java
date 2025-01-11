package com.badlogic.UniSim2.leaderboardManager;

// new class

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class LeaderboardManager {
    public static final String PREF_NAME = "UniSim-Group8";
    public static final String KEY = "leaderboard";

    /**
     * adds the score of the user to the leaderboard
     * if a user already has a score then it doesn't get overwritten unless the new score is higher
     *
     * @param username the name of the player
     * @param score    the score the player scored
     */
    public static void addScore(String username, float score) {
        Preferences pref = Gdx.app.getPreferences(PREF_NAME);

        Json jsonFile = new Json();
        String jsonFileString = pref.getString(KEY, "{}");

        Map<String, Float> leaderboard = jsonFile.fromJson(HashMap.class, jsonFileString);

        leaderboard.put(username, Math.max(score, leaderboard.getOrDefault(username, 0f)));

        pref.putString(KEY, jsonFile.toJson(leaderboard));
        pref.flush();
    }

    /**
     * Gets the leaderboard and converts it into a list and sorts that list in descending order and then returns it
     *
     * @return the sorted leaderboard
     */
    public static ArrayList<Entry<String, Float>> getSortedLeaderboard() {
        Preferences pref = Gdx.app.getPreferences(PREF_NAME);

        Json jsonFile = new Json();
        String jsonString = pref.getString(KEY, "{}");

        Map<String, Float> leaderboard = jsonFile.fromJson(HashMap.class, jsonString);

        ArrayList<Entry<String, Float>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());

        // sorts in descending order
        sortedLeaderboard.sort((score1, score2) -> score2.getValue().compareTo(score1.getValue()));

        return sortedLeaderboard;
    }

    public static void clearLeaderboard() {
        Preferences pref = Gdx.app.getPreferences(PREF_NAME);
        Json jsonFile = new Json();

        Map<String, Float> emptyLeaderboard = new HashMap<>();

        pref.putString(KEY, jsonFile.toJson(emptyLeaderboard));

        pref.flush();
    }
}
