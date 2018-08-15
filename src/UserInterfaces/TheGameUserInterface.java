package UserInterfaces;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.swing.JOptionPane;

import Entities.Profile;

public class TheGameUserInterface {

	private static final Scanner enteredId = new Scanner(System.in);

	public static void displayProfiles(Map<Integer, Profile> profileMap) {

		profileMap.forEach((k, v) -> System.out
				.println(v.getFirstName() + " " + v.getLastName() + " has a profile id of " + v.getId()));
	}

	public static void noProfileDataAvailable() {

		JOptionPane.showConfirmDialog(null, "There is no profile information available to play the game with.", null,
				JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	}

	public static String selectProfileId(Map<Integer, Profile> profileMap) {

		boolean continuePrompt = true;
		String selectedValue = "";
		String[] validSelections = { "1", "2", "3", "4", "5", "6" };

		// build the string to be used by the selection dialog box.
		StringJoiner sj = new StringJoiner("\n");
		profileMap.forEach((k, v) -> sj.add((k + 1) + "   " + v.getFirstName() + " " + v.getLastName()));

		while (continuePrompt) {
			selectedValue = JOptionPane.showInputDialog(null, sj.toString());
			// check for use of cancel and resulting null value
			if (selectedValue == null) {
				continuePrompt = false;
			} else {
				if (Arrays.binarySearch(validSelections, selectedValue) >= 0) {
					continuePrompt = false;
				}
			}
		}
		return selectedValue;
	}

	public static void incorrectSelection() {

		JOptionPane.showConfirmDialog(null, "The selected profile is not correct.  Please try again.", null,
				JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	}

	public static boolean checkToContinue() {

		boolean continueGame = true;
		String continueGameStr = "";

		while (!continueGameStr.equalsIgnoreCase("N") && !continueGameStr.equalsIgnoreCase("Y")) {
			System.out.print("Would you like to continue to the next set of profiles (Y/N)? ");
			continueGameStr = enteredId.next();
		}

		if (continueGameStr.equalsIgnoreCase("N")) {
			continueGame = false;
		}
		return continueGame;
	}

	public static boolean promptForLeaderBoardDisplay() {
		// response values... 0 = yes, 1 = no, 2 = cancel
		boolean displayLeaderBoard = JOptionPane.showConfirmDialog(null,
				"Would you like to see the leader board results?") == 0 ? true : false;
		return displayLeaderBoard;
	}

	public static void noMoreProfilesToDisplay() {

		JOptionPane.showConfirmDialog(null, "There are no more profiles to evaluate - game over!", null,
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
}
