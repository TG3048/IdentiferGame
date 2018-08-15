package UserInterfaces;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import Entities.PlayerStats;
import dao.GameResultsDAO;

public class StatisticsDisplay {
	
	public static void presentGameStatistics(PlayerStats playerStats) {

		JOptionPane.showMessageDialog(null, "Player  : " 
				+ playerStats.getPlayerId() + " " + playerStats.getPlayerName() + "\n" 
				+ "Game score : " + (int) playerStats.getGameScore() + "\n" 
				+ "Number of guesses : " + playerStats.getNumberOfGuesses() + "\n"
				+ "Number of correct guesses  : " + playerStats.getNumberOfCorrectGuesses() + "\n" 
				+ "Start time : " + playerStats.getStartGameTime() + "\n" 
				+ "End time : " + playerStats.getEndGameTime() + "\n"
				+ "Average guess time (in seconds) : " + playerStats.getAverageGuessTime());
	}

	public static void displayBoard() {

		StringJoiner stringJoiner = new StringJoiner(" ");
		String padding1 = "                ";
		String padding2 = "                                    ";
		List<PlayerStats> playerStatsList = GameResultsDAO
				.formatPlayerStatsData(GameResultsDAO.loadPlayerStatsFromFile());
		
		// sort the list by the game score value.
		playerStatsList.sort(PlayerStats.gameScoreComparator);
		// now that the list is sorted by the game score, select the top ten into a lost to be fed into the dialog box. 
		List<PlayerStats> topTenList = playerStatsList.stream().limit(10).collect(Collectors.toList());		
		
		// set-up the column headings for the display.
		stringJoiner.add("Player ")
			.add(padding1)
			.add("  Game Score")
			.add("  Number of Guesses")
			.add("  Number of Correct Guesses")
			.add("\n");
		
		topTenList.forEach(s -> stringJoiner.add(String.valueOf(s.getPlayerId()))
				.add(" ")
				.add(s.getPlayerName())
				.add("              ")
				.add(String.valueOf(s.getGameScore()))
				.add(padding1)
				.add(String.valueOf(s.getNumberOfGuesses()))
				.add(padding2)
				.add(String.valueOf(s.getNumberOfCorrectGuesses()))
				.add("\n"));
		
		// display the top ten game scores.
		JOptionPane.showMessageDialog(null, stringJoiner.toString(), "Leader Board", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
