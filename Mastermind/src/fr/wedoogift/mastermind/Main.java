/**
 * 
 */
package fr.wedoogift.mastermind;

/**
 * @author Junaid KHALID
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static boolean found = false;
	
	public static void main(String[] args) {
		printInstructions();		
		play();
	}
	
	private static void printInstructions(){
		System.out.println("\nComputer has chosen the combination, now it's your turn to guess!");
		System.out.println("You have 10 chances to guess the right combination\n" +
						   "Possible colors are R J B O V and N\n" +
						   "For example Type (RJBO) to try the R, J, B, and O colors in order.\n" +
						   "Good Luck!.\n");
		System.out.println("Start guessing: \n");
	}
	
	private static void play() {
		

		Scanner scanner = new Scanner(System.in);	
		int chances = 0;			
		Random random = new Random();
		String colors = "RJBOVN";	
		String[] computer = new String[3];
		String rand = "";
		
		for(int i=0; i<4; i++) {
			rand += colors.charAt(random.nextInt(colors.length())) ;
		}
		computer = rand.split("");
		
		// if chances are not 10 and guess still didn't found
		while(chances!=10 && !found) {
			
			System.out.println("Choose 4 colors among R J B O V N, for example Type (RJBO)");
			System.out.print("You: ");
			String input = scanner.next().toUpperCase();
	
		    String[] guess = new String[3];
		    
		    // if user input length is less or greater than 4, and input doesn't among the alphabets R J B O V N
		    if(input.length()!=4 || !input.matches("[A-Z&&[^ACDEFGHIKLMPQSTUWXYZ]]+")) {
		    	System.out.println("Error: input is wrong; must be the combination of 4 colors among R J B O V N");
		    } else {
		    	guess = input.split("");
		    	
		    	toGuess(computer, guess);
		    	
		    	chances++;
		    	
		    	System.out.println("You took " + chances + (chances==1 ? " chance" : " chances")+" out of 10!");
		    	
		    	System.out.println();
		    	System.out.println(chances==10&&!found ? "Game Over! "+ "Right guess was: " + rand : "");
		    }
	    
		}
	    scanner.close();
	}
	
	private static void toGuess(String[] computer, String[] guess) {
		
		int rightPosition = 0;
		int notAtRightPosition = 0;
		
		
		final String[] comp = computer;
		String[] gs = guess;
		
		
		Map<Integer, String> mapIndex = new HashMap<>();
		List<Integer> indexFound = new ArrayList<>();

		// map index to string
		for (int i = 0; i < gs.length; i++) {
		    mapIndex.put(i, gs[i]);
		}

		for (Iterator<Map.Entry<Integer, String>> iterate = mapIndex.entrySet().iterator(); iterate.hasNext();) {
		    Map.Entry<Integer, String> entry = iterate.next();

		    // string is at the same index
		    if (entry.getValue().equals(comp[entry.getKey()])) {
		        rightPosition++;

		        // capture index where it was found
		        indexFound.add(entry.getKey());

		        // remove from map to not check it again
		        iterate.remove();
		    }
		}

		// iterate over the map again; now it contains only string that were not in correct positions
		for (Map.Entry<Integer, String> entry : mapIndex.entrySet()) {

		    // iterated comp array
		    for (int i = 0; i < comp.length; i++) {

		        // if we are not at one of the string that was found above, and the string exists
		        if (!indexFound.contains(i) && comp[i].equals(entry.getValue())) {
		            notAtRightPosition++;

		            indexFound.add(i);
		            break;
		        }
		    }
		}
		
		if(rightPosition == 4) {
			found = true;
			System.out.println("You won!");
		}
		
		System.out.println("At correct position: "+rightPosition);
		System.out.println("Not at correct position: "+notAtRightPosition);
	}
}

