import java.util.*;


// DISCLAIMER: THE COLORS DON'T WORK ON ONLINE COMPILATORS. SPECIAL LIBRARY IS NEEDED!!! THE PROGRAM WILL STILL WORK BUT YOU WILL SEE ONLY WHITE.
// FOR SOME UNKNOWN REASON THE GAME DOES NOT WORK FOR 2 SYMBOLS FOR A WIN
public class Main {

    public static void main(String[] args) {

        //colors 
    	// 1. Reset color
        final String RESET = "\033[0m";
        // 2. Regular colors
	    final String BLACK = "\033[0;30m";   // BLACK
	    final String RED = "\033[0;31m";     // RED
	    final String GREEN = "\033[0;32m";   // GREEN
	    final String YELLOW = "\033[0;33m";  // YELLOW
	    final String BLUE = "\033[0;34m";    // BLUE
	    final String PURPLE = "\033[0;35m";  // PURPLE
	    final String CYAN = "\033[0;36m";    // CYAN
	    
	    //3. Special colors
        final String RED_BOLD_BRIGHT = "\033[1;91m";
        // more coming soon ... maybe :)
    	
    	
        Scanner kb = new Scanner(System.in);
        System.out.println("TicTacToe");
        System.out.println("Enter the size of the TicTacToe board (x by y), how many symbols in a row are needed to win, and the number of players.");
        System.out.println("All four values must be natural numbers bigger than 1, and separated by commas!\n");

        String[] size_and_length;
        boolean invalid_user_data = true;
        
        
        do {
            System.out.print("Choice: ");
            size_and_length = kb.nextLine().replaceAll("[^0-9,]", "").split(",");
            
            if (size_and_length.length != 4) {
                System.out.println(RED_BOLD_BRIGHT + "Please enter four values!" + RESET); //Red - Reset
                invalid_user_data = true;
            }
            
            
            if (size_and_length.length == 4) {
            	System.out.print(RED_BOLD_BRIGHT);
            	
            	if (Integer.parseInt(size_and_length[0]) < 2) {
            		if (Integer.parseInt(size_and_length[0]) < 1) {
            			System.out.println("The x length is not a positive integer!");
            		} else {
            			System.out.println("The x length too small!");
            		}
            		invalid_user_data = true;
            	}
            	if (Integer.parseInt(size_and_length[1]) < 2) {
            		if (Integer.parseInt(size_and_length[1]) < 1) {
            			System.out.println("The y length is not a positive integer!");
            		} else {
            			System.out.println("The y length too small!");
            		}
            		invalid_user_data = true;
            	}
            	if (Integer.parseInt(size_and_length[2]) < 2) {
            		if (Integer.parseInt(size_and_length[2]) < 1) {
            			System.out.println("The amount of symbols can't be zero or a negative integer!");
            		} else {
            			System.out.println("The amount of symbols is too small!");
            		}
            		invalid_user_data = true;
            	}
            	if (Integer.parseInt(size_and_length[3]) < 2) {
            		if (Integer.parseInt(size_and_length[3]) < 1) {
            			System.out.println("The number of players can't be zero or a negative ingteger!");
            		} else {
            			System.out.println("You can't play alone, can you?");
            		}
            		invalid_user_data = true;
            	}
            	if (Integer.parseInt(size_and_length[0]) > 1 &&
            		Integer.parseInt(size_and_length[1]) > 1 &&
            		Integer.parseInt(size_and_length[2]) > 1 &&
            		Integer.parseInt(size_and_length[3]) > 1) {
            		invalid_user_data = false;
            	}
            	
            	System.out.print(RESET);
            }
            
        } while (invalid_user_data);
        
        final int a = Integer.parseInt(size_and_length[0]); // x = a
        final int b = Integer.parseInt(size_and_length[1]); // y = b
        final int n = Integer.parseInt(size_and_length[2]);
        final int player_count = Integer.parseInt(size_and_length[3]);
        
        
        String[][] player = new String[player_count][2]; // symbol and color saved
        
        
        System.out.println("\nEnter the players' symbols and colors (separeted by a comma)!");
        System.out.println("If you want the default console color - don't write a comma - only your symbol");
        System.out.println("Here is a list of the available colors (write the number not the name!): ");
        System.out.println();
        System.out.println(BLACK + "1 - black\n" + RED + "2 - red\n" + GREEN + "3 - green\n" + YELLOW + "4 - yellow\n" + BLUE + "5 - blue\n" + PURPLE + "6 - purple\n" + CYAN + "7 - cyan\n" + RESET);
        
        for (int i = 0; i < player.length; i++) {
        	boolean invalid_input = true;
        	do {
        		System.out.print("Player " + (i+1) + ": ");
        		String[] player_data = kb.nextLine().replaceAll(" ", "").split(","); // symbol and color changing
        		if (player_data.length < 3) {
        			if (player_data[0].length() != 1) {
        				System.out.println(RED_BOLD_BRIGHT + "The symbol is only one character!" + RESET);
        			} else {
        				player[i][0] = player_data[0];
        				invalid_input = false;
        			}
        			//player[i][1] = player_data[1];
        			if (player_data.length == 2) {
        				switch (Integer.parseInt(player_data[1])) {
        					case 1: player[i][1] = BLACK; invalid_input = false; break;
        					case 2: player[i][1] = RED; invalid_input = false; break;
        					case 3: player[i][1] = GREEN; invalid_input = false; break;
        					case 4: player[i][1] = YELLOW; invalid_input = false; break;
        					case 5: player[i][1] = BLUE; invalid_input = false; break;
        					case 6: player[i][1] = PURPLE; invalid_input = false; break;
        					case 7: player[i][1] = CYAN; invalid_input = false; break;
        					default: System.out.println(RED_BOLD_BRIGHT + "Please enter a number between 1 and 7 including 1 and 7" + RESET); invalid_input = true;
        				}
        			} else {
        				player[i][1] = RESET;
        				invalid_input = false;
        			}
        		} else System.out.println(RED_BOLD_BRIGHT + "Please enter two things - the symbol and the color!" + RESET);
        	} while (invalid_input);
        }
        
        String[][] table = new String[a+1][b+1];
        
        // Initial configuration of the board
        for (int j = b; j >= 1; j--) {
            for (int i = 1; i <= a; i++) {
                table[i][j] = " ";
            }
        }
        tablica(table, a, b);
        
        System.out.println("This is how your game board looks!");
        System.out.println("To exit the game, type " + RED_BOLD_BRIGHT + "exit" + RESET);
        System.out.println("Please enter the coordinates of your choice in the format x, y where x and y are natural numbers, no greater than the dimensions of the board.");
        System.out.println("----------------------------------------------------------");
        
        String input;
        String cords[];
        int x, y;
        boolean invalid_input;
        
        while (true) {
            for (int i = 0; i < player.length; i++) {
                do {
                    System.out.print("Player " + player[i][1] + player[i][0] + RESET + ": ");
                    input = kb.nextLine().replaceAll(" ", "").toLowerCase();
                    if (input.equals("exit")) {System.out.println(RED_BOLD_BRIGHT + "You exited the game!" + RESET); System.exit(0);} //Red - Reset
                    cords = input.split(",");
            
                    x = Integer.parseInt(cords[0]);
                    y = Integer.parseInt(cords[1]);
                
                    invalid_input = x < 1 || y < 1 || x > a || y > b;
                
                    if (invalid_input) System.out.println(RED_BOLD_BRIGHT + "Invalid input!" + RESET); // Red - Reset
                
                    if (!invalid_input) {
                        if (!table[x][y].equals(" ")) {
                            System.out.println(RED_BOLD_BRIGHT + "The position is already occupied! Choose another spot!" + RESET); //Red - Reset
                            invalid_input = true;
                        }
                    }
                
                } while (invalid_input);
            
                table[x][y] = player[i][1] + player[i][0] + RESET;
                tablica(table, a, b);
                checkWin(table, a, b, n); // new here !!!
            }
        }
    }
    
    
    /*
        * | * | *
        - + - + -
        * | * | *
        - + - + -
        * | * | *
    */
    
    public static void tablica(String[][] table, int a, int b) {
        // New line before printing
        System.out.println();
        for (int j = b; j >= 1; j--) {
            // table
            System.out.print("\t");
            for (int i = 1; i <= a; i++) {
                System.out.print(table[i][j]); //default
                if (i != a) System.out.print(" | ");
            }
            System.out.println();
            
            // -+-+-
            System.out.print("\t");
            if (j > 1) {
                for (int i = 1; i <= a; i++) {
                    System.out.print(i < a ? "- + " : "-");
                }
                System.out.println();
            }
        }
        // New line after printing the board
        System.out.println();
    }
    
    public static void checkWin(String[][] table, int a, int b, int n) {
        // Vertical check
        for (int i = 1; i <= a; i++) {
            int counter = 1;
            for (int j = 1; j <= b-1; j++) {
                if (table[i][j].equals(table[i][j+1]) && table[i][j] != " ") {
                    counter++;
                } else {
                    counter = 1;
                    break;
                }
                if (counter == n) {
                    System.out.println("\"" + table[i][j] + "\"" + " is the winner!" + "\n" + "Restart the game if you want to play again! :)");
                    System.exit(0);
                }
            }
        }
        
        // Horizontal check
        for (int j = 1; j <= b; j++) {
            int counter = 1;
            for (int i = 1; i <= a-1; i++) {
                if (table[i][j].equals(table[i+1][j]) && table[i][j] != " ") {
                    counter++;
                } else {
                    counter = 1;
                    break;
                }
                if (counter == n) {
                    System.out.println("\"" + table[i][j] + "\"" + " is the winner!" + "\n" + "Restart the game if you want to play again! :)");
                    System.exit(0);
                }
            }
        }

        // Diagonal check left-to-right
        for (int i = 1; i <= a-n; i++) {
            int counter = 1;
            for (int j = 1; j <= b-n; j++) {
                for (int k = 1; k < n; k++) {
                    if (table[i][j].equals(table[i + k][j + k]) && table[i][j] != " ") {
                        counter++;
                    } else {
                        counter = 1;
                        break;
                    }
                }
                if (counter == n) {
                    System.out.println("\"" + table[i][j] + "\"" + " is the winner!" + "\n" + "Restart the game if you want to play again! :)");
                    System.exit(0);
                }
            }
        }
        
        // Diagonal check right-to-left
        for (int i = 3; i <= a; i++) {
            int counter = 1;
            for (int j = 1; j <= b-n; j++) {
                for (int k = 1; k < n; k++) {
                    if (table[i][j].equals(table[i - k][j + k]) && table[i][j] != " ") {
                        counter++;
                    } else {
                        counter = 1;
                        break;
                    }
                }
                if (counter == n) {
                    System.out.println("\"" + table[i][j] + "\"" + " is the winner!" + "\n" + "Restart the game if you want to play again! :)");
                    System.exit(0);
                }
            }
        }
        
        // Check for a draw
        int count = 0;
        for (int i = 1; i <= a; i++) {
            for (int j = 1; j <= b; j++) {
                if (table[i][j] != " ") count++;
            }
        }
        if (count == a*b) {
            System.out.println("No one wins! It's a draw!");
            System.exit(0);
        }
    }
}



// owo pi = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679
