package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import Entities.PlayerStats;

public class GameResultsDAO {

	private static final String PLAYERSTATSFILE = "c:\\opt\\PlayerStats.txt";

	public static void writePlayerStatsToFile(PlayerStats playerStats) {

		// set the writer's append to true to allow for the constant recording
		// of each game's results.
		try (PrintWriter out = new PrintWriter(
				new BufferedWriter(new FileWriter(PLAYERSTATSFILE, true)))) {
			out.println(playerStats.getPlayerId() + "," + playerStats.getPlayerName() + ","
					+ playerStats.getNumberOfGuesses() + ","
					+ playerStats.getNumberOfCorrectGuesses() + "," + playerStats.getGameScore()
					+ "," + playerStats.getStartGameTime() + "," + playerStats.getEndGameTime()
					+ "," + playerStats.getAverageGuessTime());
		} catch (IOException ioe) {
			System.err.println("An error occurred while writing to " + PLAYERSTATSFILE);
		}
	}

	public static List<String> loadPlayerStatsFromFile() {

		List<String> playerStats = Collections.emptyList();

		try {
			playerStats = Files.readAllLines(Paths.get(PLAYERSTATSFILE), StandardCharsets.UTF_8);
		} catch (IOException ioe) {
			System.err.println("An error occurred while reading " + PLAYERSTATSFILE);
			ioe.printStackTrace();
		}
		return playerStats;
	}

	public static List<PlayerStats> formatPlayerStatsData(List<String> playerStatsString) {

		List<PlayerStats> playerStatsList = new ArrayList<>();

		for (String playerStatsRow : playerStatsString) {

			System.out.println("playerStatsRow = " + playerStatsRow);

			PlayerStats playerStatsRecord = new PlayerStats();
			StringTokenizer st = new StringTokenizer(playerStatsRow, ",");
			while (st.hasMoreElements()) {
				playerStatsRecord.setPlayerId(Integer.parseInt(st.nextElement().toString()));
				playerStatsRecord.setPlayerName(String.valueOf(st.nextElement()));
				playerStatsRecord.setNumberOfGuesses(Integer.parseInt(st.nextElement().toString()));
				playerStatsRecord
						.setNumberOfCorrectGuesses(Integer.parseInt(st.nextElement().toString()));
				playerStatsRecord
						.setGameScore((int) Double.parseDouble(st.nextElement().toString()));
				playerStatsRecord.setStartGameTime(Instant.parse(st.nextElement().toString()));
				playerStatsRecord.setEndGameTime(Instant.parse(st.nextElement().toString()));
				playerStatsRecord.setAverageGuessTime(Long.parseLong(st.nextElement().toString()));
			}
			playerStatsList.add(playerStatsRecord);
		}
		return playerStatsList;
	}

}
