package Entities;

public class Player {

	private int playerId;
	private String playerName;
	private double playerHighScore;
	
	public Player(int playerId, String playerName) {
		this.playerId = playerId;
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public double getPlayerHighScore() {
		return playerHighScore;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPlayerHighScore(double playerHighScore) {
		this.playerHighScore = playerHighScore;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "Player = " + String.valueOf(this.getPlayerId()) + this.getPlayerName();
	}

	@Override
	public boolean equals(Object playerObject) {

		if (playerObject == null) {
			return false;
		}

		if (!(playerObject instanceof Player)) {
			return false;
		}	

		Player player = (Player) playerObject;
		return this.playerId == player.playerId;
	}

	@Override
	public int hashCode() {
		return this.getPlayerId() * (this.getPlayerName().length() * 7);
	}

}
