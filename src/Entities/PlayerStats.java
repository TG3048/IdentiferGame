package Entities;

import java.time.Instant;
import java.util.Comparator;

public class PlayerStats {

	private int playerId;
	private String playerName;
	private int numberOfGuesses;
	private int numberOfCorrectGuesses;
	private double gameScore;
	private Instant startGameTime;
	private Instant endGameTime;
	private long averageGuessTime;

	public PlayerStats() {

	}

	public PlayerStats(int playerId, String playerName) {

		this.playerId = playerId;
		this.playerName = playerName;
		this.numberOfGuesses = 0;
		this.numberOfCorrectGuesses = 0;
		this.startGameTime = Instant.now();
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getNumberOfGuesses() {
		return numberOfGuesses;
	}

	public int getNumberOfCorrectGuesses() {
		return numberOfCorrectGuesses;
	}

	public long getAverageGuessTime() {
		return averageGuessTime;
	}

	public Instant getStartGameTime() {
		return startGameTime;
	}

	public Instant getEndGameTime() {
		return endGameTime;
	}

	public double getGameScore() {
		return gameScore;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public void setNumberOfGuesses(int numberOfGuesses) {
		this.numberOfGuesses = numberOfGuesses;
	}

	public void setNumberOfCorrectGuesses(int numberOfCorrectGuesses) {
		this.numberOfCorrectGuesses = numberOfCorrectGuesses;
	}

	public void setStartGameTime(Instant startGameTime) {
		this.startGameTime = startGameTime;
	}

	public void setEndGameTime(Instant endGameTime) {
		this.endGameTime = endGameTime;
	}

	public void setAverageGuessTime(long averageGuessTime) {
		this.averageGuessTime = averageGuessTime;
	}

	public void setGameScore(double gameScore) {
		this.gameScore = gameScore;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return "Player = " + String.valueOf(this.getPlayerId());
	}

	@Override
	public boolean equals(Object playerStatsObject) {

		if (playerStatsObject == null) {
			return false;
		}

		if (!(playerStatsObject instanceof PlayerStats)) {
			return false;
		}

		PlayerStats playerStats = (PlayerStats) playerStatsObject;
		return this.playerId == playerStats.playerId;
	}

	@Override
	public int hashCode() {
		return this.getPlayerId() * 31;
	}

	public static Comparator<PlayerStats> gameScoreComparator = new Comparator<PlayerStats>() {
		@Override
		public int compare(PlayerStats playerStats1, PlayerStats playerStats2) {
			return (playerStats2.getGameScore() < playerStats1.getGameScore() ? -1
					: (playerStats2.getGameScore() == playerStats1.getGameScore() ? 0 : 1));
		}
	};

}
