package dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import Entities.PlayerStats;

@RunWith(JUnitPlatform.class)
class GameResultsDAO_JUnit {

	private final static Path PLAYERSTATSFILEPATH = Paths.get("/opt/PlayerStats.txt");

	@BeforeAll
	static void testSetup() {

		System.err.println("Before");
		// delete the stats file to prepare for the junit tests.
		try {
			Files.delete(PLAYERSTATSFILEPATH);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Test
	void testFileWrite() {

		PlayerStats playerStats = new PlayerStats();
		playerStats.setPlayerId(1);
		playerStats.setPlayerName("Albert C.");
		playerStats.setStartGameTime(Instant.now());
		playerStats.setEndGameTime(Instant.now());
		playerStats.setNumberOfGuesses(10);
		playerStats.setNumberOfCorrectGuesses(10);
		playerStats.setGameScore(100.0);
		playerStats.setAverageGuessTime(100);
		GameResultsDAO.writePlayerStatsToFile(playerStats);

		// verify that the file exists (per the write).
		assertTrue("The PlayerStats file was not created (via the write).",
				(Files.exists(PLAYERSTATSFILEPATH, LinkOption.NOFOLLOW_LINKS)));

	}

	@Test
	void testFileLoad() {
		// verify that results are returned from the file load method.
		assertTrue("There was no data returned from the file loading step.",
				(GameResultsDAO.loadPlayerStatsFromFile().size() > 0));
	}

	@Test
	void testDataFormatting() {

		StringJoiner stringJoiner = new StringJoiner(",");
		stringJoiner.add(String.valueOf(1)).add("Albert C.").add(String.valueOf(50))
				.add(String.valueOf(50)).add("100.0").add("2018-08-15T14:02:00.00Z")
				.add("2018-08-15T14:04:00.00Z").add(String.valueOf(100));

		List<String> sjList = new ArrayList<>();
		sjList.add(stringJoiner.toString());

		List<PlayerStats> playerStatsList = GameResultsDAO.formatPlayerStatsData(sjList);

		// verify that results are returned from the data formatting step.
		assertTrue("There was no data returned from the data formatting step.",
				playerStatsList.size() > 0);

		for (PlayerStats plStat : playerStatsList) {
			assertTrue("Missing player id", plStat.getPlayerId() != 0);
			assertTrue("Missing player name", plStat.getPlayerName() != "");
			assertTrue("Missing player number of guesses", plStat.getNumberOfGuesses() != 0);
			assertTrue("Missing player number of correct guesses",
					plStat.getNumberOfCorrectGuesses() != 0);
		}

	}

	private List<PlayerStats> getPlayerStatsData() {

		return null;
	}

}
