package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Entities.Profile;

public class ProfileLoader {

	JSONParser parser = new JSONParser();
	private static Map<String, Profile> profileMap = new HashMap<>();

	public static Map<String, Profile> getProfilesFromWillow(boolean challengeMode,
			String[] challengeValues) {

		URL willowURL;
		JSONParser jsonParser = new JSONParser();

		// sort the row for use in a binary search in the parseProfileObject
		// method.
		Arrays.sort(challengeValues);

		try {
			willowURL = new URL("https://www.willowtreeapps.com/api/v1.0/profiles/");
			BufferedReader profileReader;
			try {
				profileReader = new BufferedReader(new InputStreamReader(willowURL.openStream()));
				Object profileObj = jsonParser.parse(profileReader);
				JSONArray profilesList = (JSONArray) profileObj;
				profilesList.forEach(profileData -> parseProfileObject((JSONObject) profileData,
						challengeMode, challengeValues));
				profileReader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		} catch (MalformedURLException malEX) {
			malEX.printStackTrace();
		}

		return profileMap;
	}

	private static void parseProfileObject(JSONObject profileData, boolean challengeMode,
			String[] challengeValues) {
		boolean selectRow = true;
		Profile profile = new Profile();

		profile.setId((String) profileData.get("id"));
		profile.setSlug((String) profileData.get("slug"));
		profile.setJobTitle((String) profileData.get("jobTitle"));
		profile.setFirstName((String) profileData.get("firstName"));
		profile.setLastName((String) profileData.get("lastName"));
		profile.setHeadshot((JSONObject) profileData.get("headshot"));

		if (challengeMode) {
			if (Arrays.binarySearch(challengeValues, profile.getFirstName()) <= 0) {
				selectRow = false;
			}
		}

		if (selectRow) {
			profileMap.put(profile.getId(), profile);
		}
	}

}
