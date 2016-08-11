package wordChainSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordChainSolver {

	/**
	 * Main Method
	 * <p>
	 * This method purely exists to read user input for the first and last words. The method the calls the solverMethod
	 * with these strings as parameters. 
	 * <p>
	 * @param  args java command line arguments.
	 * @return none
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter first and last words for chain solver, line seperated:\n");
		String firstWord = input.nextLine();
		String lastWord = input.nextLine();
		input.close();
		System.out.println("Thank you. Program running...\n\n");
		solverMethod(firstWord, lastWord);
	}

	/**
	 * solverMethod is a word chain solver method.
	 * <p>
	 * This method will one letter at a time convert the first word to the last word if possible. Conversions can only
	 * be from one dictionary word to another. Both first and last words must be of the same length.
	 * <p>
	 * Inside a loop the length of the words, indexBank checks whether the current character has been converted yet.
	 * If not an alphabet iterator is placed on the earliest index character checking whether any letter will form a
	 * valid word with the same character at the same index of the final word. The dictionary is accessed inside this
	 * looping which is read line by line into a string. ArrayLists are created for converted words so the same word is
	 * ignored on later loops. The index array stores the index when a letter has been converted so this index can be 
	 * ignored in future loops.
	 * <p>
	 * In terms of design decisions different data structures could have been used than ArrayLists for storing indices
	 * and strings. Some if's could have been connected but I broke these up due to readability and complexity.
	 * 
	 * @param firstWord	the first word entered by the user for the chain solver.
	 * @param lastWord	the last word entered by the user which the chain solver should convert the first word into.
	 * @return none
	 */
	private static void solverMethod(String firstWord, String lastWord) {	
		ArrayList<Integer> indexBank = new ArrayList<>();
		ArrayList<String> wordBank = new ArrayList<>();
		char[] firstWordChar  = firstWord.toLowerCase().toCharArray();
		char[] lastWordChar = lastWord.toLowerCase().toCharArray();
		char[] tempWordChar = firstWord.toLowerCase().toCharArray();
		String tempWord = null;

		outsideLoop:
		for (int i=0 ; i<firstWord.length() ; i++) {
			if (!indexBank.contains(i)) { 
				for(char alphabet='a'; alphabet<='z' ; alphabet++) {
					if (alphabet != firstWordChar[i]) {
						tempWordChar[i] = alphabet;
						tempWord = String.valueOf(tempWordChar);
						Scanner sc = null;
						try {
							File file = new File("websters-dictionary.txt");
							sc = new Scanner(file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} finally {
							while(sc.hasNextLine()) {
								if(tempWord.equals(sc.nextLine())) {
									if (tempWordChar[i] == lastWordChar[i]) {
										if (!wordBank.contains(tempWord)) {
											if (tempWord.equals(lastWord)) {
												System.out.println(firstWord + " becomes " + tempWord + ", DONE!!");
												break outsideLoop;
											}
											System.out.println(firstWord + " becomes " + tempWord);
											tempWordChar = tempWord.toCharArray();
											firstWord = tempWord;
											alphabet='a';
											indexBank.add(i);
											i=-1;
											continue outsideLoop;
										}
									}
									break;
								}
							}
						}
						if (alphabet == 'z') {
							tempWordChar[i]=firstWordChar[i];
						}
					}
				}
			}
			if (i == 4 && !tempWord.equals(lastWord)) {
				System.err.println("No dictionary solution found.");
			}
		}
	}
}