package Game;

import static dao.GameResultsDAO.writePlayerStatsToFile;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import Entities.Player;
import Entities.PlayerStats;
import Entities.Profile;
import UserInterfaces.StatisticsDisplay;
import UserInterfaces.TheGameUserInterface;

public class TheGame {

	private static Map<String, Profile> profileMap = new HashMap<>();
	private static Map<Integer, Profile> gameLevelProfileMap = new HashMap<>();
	private static Map<Integer, Duration> gameDurationMap = new HashMap<>();
	private static Random random = new Random();
	private static boolean playGame = true;

	public TheGame() {

	}

	private void runTheGame() {

		// prompt for user name and to start the game
		int playerId = 1;
		String playerName = "Albert E";

		Player player = new Player(playerId, playerName);
		PlayerStats playerStats = new PlayerStats(player.getPlayerId(), playerName);
		gameDurationMap.clear();

		boolean challengeMode = false;
		String[] challengeValues = { "Matt", "Mat" };

		profileMap = getProfileData(challengeMode, challengeValues);
		if (profileMap.isEmpty()) {
			TheGameUserInterface.noProfileDataAvailable();
			return;
		}

		// do until user exists/quits
		while (playGame) {
			// load 6 profiles at a time for a game session
			gameLevelProfileMap = buildGameSessionMap(profileMap);
			if (gameLevelProfileMap.size() == 0) {
				TheGameUserInterface.noMoreProfilesToDisplay();
				playGame = false;
			} else {
				// TheGameUserInterface.displayProfiles(gameLevelProfileMap);
				playerStats = performUserInteraction(playerStats);
			}
		}

		playerStats = updateGameStatistics(player, playerStats);
		writePlayerStatsToFile(playerStats);
		StatisticsDisplay.presentGameStatistics(playerStats);

		if (TheGameUserInterface.promptForLeaderBoardDisplay()) {
			StatisticsDisplay.displayBoard();
		}
	}

	private Map<String, Profile> getProfileData(boolean challengeMode, String[] challengeValues) {

		return ProfileLoader.getProfilesFromWillow(challengeMode, challengeValues);
	}

	// shift the contents of the base profile map to a map to be used
	// for a game session which consists of 6 profiles at a time.
	private Map<Integer, Profile> buildGameSessionMap(Map<String, Profile> profileMap) {

		Map<Integer, Profile> gameLevelProfileMap = new HashMap<>();
		int i = 0;

		for (Iterator<Map.Entry<String, Profile>> profileIter = profileMap.entrySet()
				.iterator(); profileIter.hasNext();) {
			if (i <= 5) {
				Map.Entry<String, Profile> profileEntry = profileIter.next();
				gameLevelProfileMap.put(i, profileEntry.getValue());
				profileIter.remove();
			} else {
				break;
			}
			i++;
		}
		return gameLevelProfileMap;
	}

	private PlayerStats performUserInteraction(PlayerStats playerStats) {

		Instant guessStartTime = Instant.now();
		// generate random value in place of photos to try to remember.
		int randomGeneratedNumber = 0;
		while (randomGeneratedNumber == 0) {
			randomGeneratedNumber = random.nextInt(6 - 1);
		}

		String validProfileId = String.valueOf(randomGeneratedNumber);
		System.out.println("validProfileId = " + validProfileId);

		boolean guessing = true;
		// keep prompting until user gets the correct answer or cancels out of
		// the quiz.
		while (guessing) {
			String selectedProfileId = TheGameUserInterface.selectProfileId(gameLevelProfileMap);
			if (selectedProfileId == null) {
				playGame = false;
				guessing = false;
			} else {
				playerStats.setNumberOfGuesses(playerStats.getNumberOfGuesses() + 1);
				if (evaluatePlayerResponse(validProfileId, selectedProfileId)) {
					playerStats
							.setNumberOfCorrectGuesses(playerStats.getNumberOfCorrectGuesses() + 1);
					guessing = false;
				} else {
					TheGameUserInterface.incorrectSelection();
				}
			}
		}

		Instant guessEndTime = Instant.now();
		Duration guessTime = Duration.between(guessStartTime, guessEndTime);
		gameDurationMap.put(gameDurationMap.size() + 1, guessTime);

		return playerStats;
	}

	private boolean evaluatePlayerResponse(String validProfileId, String selectedProfileId) {

		return selectedProfileId.equals(validProfileId);
	}

	private PlayerStats updateGameStatistics(Player player, PlayerStats playerStats) {

		// do final stats update
		playerStats.setPlayerId(player.getPlayerId());
		double gameScore = playerStats.getNumberOfGuesses() > 0
				? ((double) playerStats.getNumberOfCorrectGuesses()
						/ (double) playerStats.getNumberOfGuesses()) * 100
				: 0.0;
		playerStats.setGameScore(gameScore);

		// calculate average guess time.
		Duration totalDurations = Duration.ZERO;
		for (Duration duration : gameDurationMap.values()) {
			totalDurations = totalDurations.plus(duration);
		}

		Duration averageGuessTimeD = totalDurations.dividedBy(gameDurationMap.size());
		playerStats.setAverageGuessTime(averageGuessTimeD.getSeconds());
		playerStats.setEndGameTime(Instant.now());

		return playerStats;
	}

	public static void main(String[] args) {

		TheGame theGame = new TheGame();
		theGame.runTheGame();
		System.exit(0);
	}
}
