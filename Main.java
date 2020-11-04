import java.util.*;

// Key: Pattern
// Value: Num Occurrences
/**
 * CECS 277 Lab10 Mind Reader- Program guesses the users input based on whether
 * the user has used the same inputs multiple of times or on previous attempts
 *
 *
 * @author Blake Del Rey
 * @author Jacob Sunia 
 * 3 November 2020 CECS 277
 */
class Main {

	/**
	 * Main function that carries out the purpose of the assignment purpose
	 * specified at the top of the document
	 *
	 * args No arguments provided for this program
	 */
	public static void main(String[] args) {
		final char SENTINAL = 'Q';

		String pattern = "    ";
		int numSelected = 0;
		int numCorrect = 0;

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// Initialize choice so that while loop will run the first time
		// Intentionally avoiding using do while loop because my other classes said do
		// while
		// Loops were bad practice
		char prediction = makePrediction(map, pattern);
		char choice = getInput();
		numSelected++;
		while (choice != SENTINAL) {
			System.out.println("Comp: " + prediction);
			if (prediction == choice) {
				numCorrect++;
			}

			double percentCorrect = ((double) numCorrect / numSelected) * 100;
			System.out.print("% Wins: ");
			System.out.printf("%.2f", percentCorrect);
			System.out.println("%");

			// Append new choice to end of pattern and shift other characters over to the
			// left
			pattern = Character.toString(pattern.charAt(1)) + Character.toString(pattern.charAt(2))
					+ Character.toString(pattern.charAt(3)) + Character.toString(choice);

			if (numSelected >= 4) {
				storePattern(map, pattern);
			}

			// Prompt for a new choice to continue loop
			choice = getInput();
			numSelected++;
			prediction = makePrediction(map, pattern);

		}

	}

	/**
	 * makePrediction guesses the users input based off of the HashMap it checks for
	 * the keyValues and returns either the char 'X' or 'O' based on the pattern
	 * 
	 * @param map     The empty HashMap that helps guess the pattern
	 * @param pattern The pattern that is used to help guess the users choice
	 * @return the character of the programs guessing
	 */
	public static char makePrediction(HashMap<String, Integer> map, String pattern) {
		final int PATTERN_SIZE = 4;
		if (map.size() == 0) {
			Random rand = new Random();
			int randomGuessInt = rand.nextInt(2);
			if (randomGuessInt == 0) {
				return 'X';
			}
			if (randomGuessInt == 1) {
				return 'O';
			}
		}

		String xKeyCheck = pattern.substring(1, PATTERN_SIZE) + "X";

		String oKeyCheck = pattern.substring(1, PATTERN_SIZE) + "O";
		int xVal = 0;
		int oVal = 0;

		if (map.containsKey(xKeyCheck)) {
			xVal = map.get(xKeyCheck);
		}
		if (map.containsKey(oKeyCheck)) {
			oVal = map.get(oKeyCheck);
		}

		if (xVal > oVal) {

			return 'X';
		}
		return 'O';

	}

	/**
	 * getInput takes user input and validates whether the input is valid or invalid
	 * 
	 * @return the choice of the user. Does not return a invalid choice
	 */
	public static char getInput() {
		char choice = 'a';
		while (choice != 'X' && choice != 'O' && choice != 'Q') {
			System.out.println("Enter X or O, or Q to quit.");
			// Gets input as string and takes first character as input
			String choiceString = CheckInput.getString();
			if (choiceString.length() > 0) {
				choice = choiceString.charAt(0);
			}
			choice = Character.toUpperCase(choice);
			if (choice != 'X' && choice != 'O' && choice != 'Q') {
				System.out.println("**ERROR NOT A VALID INPUT**");
			}

		}
		return choice;
	}

	/**
	 * storePattern checks if a specific pattern has been used while incrementing
	 * the usage value, otherwise it adds the String to the hashmap.
	 * 
	 * @param map     Passes in a empty HashMap and adds according to getInput
	 * @param pattern is the pattern that the program detects and adds it to the
	 *                hash map to determine whether the user has used the sequence
	 *                before
	 *
	 */
	public static void storePattern(HashMap<String, Integer> map, String pattern) {
		// If pattern has been guessed increment value stored in map
		// Else initialize to 1
		if (map.containsKey(pattern)) {
			int newVal = map.get(pattern);
			newVal++;
			map.put(pattern, newVal);
		} else {
			map.put(pattern, 1);
		}
	}
}