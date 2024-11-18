import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		//cvetove sloji
		
		Scanner kb = new Scanner(System.in);
		System.out.println("TicTacToe");
		System.out.println("Въведи размера на морския шах (x на y) , колко символа трябва да има един до друг, за да се спечели играта и броят играчи.");
		System.out.println("Четирите стойности трябва да са естествени числа и да са отделени със запетая!");
		
		String[] size_and_length;
		
		do {
			System.out.print("Избор: ");
			size_and_length = kb.nextLine().replaceAll("[^0-9,]", "").split(",");
			if (size_and_length.length != 4) {
				System.out.println("Моля въведете четири стойности!");
			}
		} while (size_and_length.length != 4);
		
		final int a = Integer.parseInt(size_and_length[0]); // x = a
		final int b = Integer.parseInt(size_and_length[1]); // y = b
		final int n = Integer.parseInt(size_and_length[2]);
		final int player_count = Integer.parseInt(size_and_length[3]);
		
		String[] player = new String[player_count]; // символ и цвят
		
		System.out.println("\nВъведете символите на играчите!");
		
		for (int i = 0; i < player.length; i++) {
			System.out.print("Символ на играч " + (i+1) + ": ");
			player[i] = kb.next();
		}
		
		
		String[][] table = new String[a+1][b+1];
		
		//първоначална конфигурация на таблицата
		for (int j = b; j >= 1; j--) {
			for (int i = 1; i <= a; i++) {
				table[i][j] = " ";
			}
		}
		tablica(table, a, b);
		
		System.out.println("Така изглежда вашата таблица за игра!");
		System.out.println("За да излезете от играта, напишете exit");
		System.out.println("Моля въвеждайте координатите на Вашия избор във вида x, y където x и y са естествени числа, не по-големи от размерите на таблицата.");
		System.out.println("----------------------------------------------------------");
		kb.nextLine();
		
		String input;
		String cords [];
		int x, y;
		boolean invalid_input;
		
		while (true) {
			for (int i = 0; i < player.length; i++) {
				do {
					System.out.print("Играч " + player[i] + ": ");
					input = kb.nextLine().replaceAll(" ", "").toLowerCase();
					if (input.equals("exit")) {System.out.println("Излязохте от играта!"); System.exit(0);}
					cords = input.split(",");
			
					x = Integer.parseInt(cords[0]);
					y = Integer.parseInt(cords[1]);
				
					invalid_input = x < 1 || y < 1 || x > a || y > b;
				
					if (invalid_input) System.err.println("Грешни входни данни!");
				
					if (!invalid_input) {
						if (!table[x][y].equals(" ")) {
							System.err.println("Позицията вече е заета! Изберете друго място!");
							invalid_input = true;
						}
					}
				
				} while (invalid_input);
			
				table[x][y] = player[i];
				tablica(table, a, b);
				checkWin(table, a, b, n); // ново е тук !!!
			}
		}
	}
	
	
	
	
	/*    * | * | *
	      - + - + -
	      * | * | *
	      - + - + -
	      * | * | *
	                   */
	
	
	
	
	
	public static void tablica(String[][] table, int a, int b) {
		// нов ред преди да се отпечата
		System.out.println();
		for (int j = b; j >= 1; j--) {
			// table
			System.out.print("\t");
			for (int i = 1; i <= a; i++) {
				System.out.print(table[i][j]); //default
				if (i!=a) System.out.print(" | ");
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
		// нов ред след като се отпечата таблицата
		System.out.println();
	}
	
	public static void checkWin(String[][] table, int a, int b, int n) {
		// вертикална проверка
		for (int i = 1; i <= a; i++) {
			int counter = 1;
			for (int j = 1; j <= b-1; j++) {
				if (table[i][j] == table[i][j+1] && table[i][j] != " ") {
					counter ++;
				} else {
					counter = 1;
					break;
				}
				if (counter == n) {
					System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
					System.exit(0);
				}
			}
		}
		
		// хоризонтална проверка
		for (int j = 1; j <= b; j++) {
			int counter = 1;
			for (int i = 1; i <= a-1; i++) {
				if (table[i][j] == table[i+1][j] && table[i][j] != " ") {
					counter ++;
				} else {
					counter = 1;
					break;
				}
				if (counter == n) {
					System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
					System.exit(0);
				}
			}
		}

		// диагонална проверка ляво-дясно
		for (int i = 1; i <= a-n; i++) {
			int counter = 1;
		    for (int j = 1; j <= b-n; j++) {
		        for (int k = 1; k < n; k++) {
		            if ( (table[i][j] == table[i + k][j + k]) && (table[i][j] != " ") ) {
		                counter++;
		            } else {
		                counter = 1;
		                break;
		            }
		        }
		        if (counter == n) {
		            System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
		            System.exit(0);
		        }
		    }
		}
		
		// диагонална проверка дясно-ляво
		for (int i = 3; i <= a; i++) {
			int counter = 1;
			for (int j = 1; j <= b-n; j++) {
				for (int k = 1; k < n; k++) {
		            if ( (table[i][j] == table[i - k][j + k]) && (table[i][j] != " ") ) {
		                counter++;
		            } else {
		                counter = 1;
		                break;
		            }
				}
		        if (counter == n) {
		            System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
		            System.exit(0);
		        }
			}
		}
		
		// проверка за равенство
		int count = 0;
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= b; j++) {
				if (table[i][j] != " ") count++;
			}
		}
		if (count == a*b) {
			System.out.println("Никой не печели! Равенство!");
			System.exit(0);
		}
	}
	
	
}
