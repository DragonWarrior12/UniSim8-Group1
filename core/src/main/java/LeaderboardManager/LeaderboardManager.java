	package LeaderboardManager;
	
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Map.Entry;
	
	
	import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.Preferences;
	import com.badlogic.gdx.utils.Json;
	import com.badlogic.gdx.utils.JsonValue;
	
	public class LeaderboardManager {
		public static final String PREF_NAME = "myGamePref";
		public static final String KEY = "leaderboard";
		
		/**
		 * adds the score of the user to the leaderboard
		 * if a user already has a score than it doesn't get overwritten unless the new score is higher  
		 * 
		 * @param username the name of the player
		 * @param score the score the player scored
		 * @throws Exception if score is negative
		 */
		@SuppressWarnings("unchecked")
		public static void addScore(String username, int score) throws Exception {
			if (score < 0) {
		        throw new Exception("Invalid score, it cannot be less than 0");
		    }
			
			Preferences pref = Gdx.app.getPreferences(PREF_NAME);
			
	        Json jsonFile = new Json();
	        String jsonFileString = pref.getString(KEY, "{}");

	        // Deserialising the JSON file
	        Map<String, Integer> leaderboard = jsonFile.fromJson(HashMap.class, jsonFileString);

	        // Amending the score of the user
	        leaderboard.put(username, Math.max(score, leaderboard.getOrDefault(username, 0)));

	        pref.putString(KEY, jsonFile.toJson(leaderboard));
	        pref.flush();
		}
		
		/**
		 * Gets the leaderboard and converts it into a list and sorts that list in descending order and then returns it
		 * @return the sorted leaderboard
		 */
	    @SuppressWarnings("unchecked")
		public static ArrayList<Entry<String, Integer>> getSortedLeaderboard() {
	    	Preferences pref = Gdx.app.getPreferences(PREF_NAME);

	        Json jsonFile = new Json();
	        String jsonString = pref.getString(KEY, "{}");

	        // Deserializing the JSON file
	        Map<String, Integer> leaderboard = jsonFile.fromJson(HashMap.class, jsonString);

	        ArrayList<Entry<String, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());

	        // Sort the leaderboard in descending order based on the score
	        sortedLeaderboard.sort((a, b) -> b.getValue().compareTo(a.getValue()));

	        return sortedLeaderboard;
	    }
	    
	    
	    public static void clearLeaderboard() {
	        Preferences pref = Gdx.app.getPreferences(PREF_NAME);
	        Json jsonFile = new Json();

	        // Creates an empty map
	        Map<String, Integer> emptyLeaderboard = new HashMap<>();
	        
	        // Empty map serialises to json
	        pref.putString(KEY, jsonFile.toJson(emptyLeaderboard));

	        pref.flush();
	    }
	}
