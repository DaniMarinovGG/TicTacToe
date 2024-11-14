package paket4e;

import java.util.*;

public class TicTacToe {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		System.out.println("TicTacToe");
		System.out.println("Напиши размера на морския шах!");
		System.out.print("Размер x: ");
		int a = kb.nextInt();
		
		System.out.print("Размер y: ");
		int b = kb.nextInt();
		
//		System.out.println("Въведи размера на морския шах (x на y) и колко символа трябва да има един до друг, за да се спечели играта. Трите стойности трябва да са естествени числа и да са отделени със запетая!");
//		
//		String size_and_length = kb.nextLine().replaceAll(" ", "").replace("[^0-9]", "")
		
		
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
		int x=1, y=1;
		boolean invalid_input;
		
		while (true) {
			// играч 1
			do {
				System.out.print("Играч О: ");
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
			
			table[x][y] = "O";
			tablica(table, a, b);
			checkWin(table, a, b); // ново е тук !!!
			// играч 2
			do {
				System.out.print("Играч X: ");
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
			
			table[x][y] = "X";
			tablica(table, a, b);
			checkWin(table, a, b);
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
				if (table[i][j] == "O") {
					System.out.print("\u001B[32m" + table[i][j] + "\u001B[0m"); // green
				} else if (table[i][j] == "X") {
					System.out.print("\u001B[31m" + table[i][j] + "\u001B[0m"); //red
				} else {
					System.out.print(table[i][j]); //default
				}
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
	
	public static void checkWin(String[][] table, int a, int b) {
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
				if (counter == 3) {
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
				if (counter == 3) {
					System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
					System.exit(0);
				}
			}
		}

		// диагонална проверка ляво-дясно
		for (int i = 1; i <= a-2; i++) {
			int counter = 1;
		    for (int j = 1; j <= b-2; j++) {
		        for (int k = 1; k < 3; k++) {
		            if ( (table[i][j] == table[i + k][j + k]) && (table[i][j] != " ") ) {
		                counter++;
		            } else {
		                counter = 1;
		                break;
		            }
		        }
		        if (counter == 3) {
		            System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
		            System.exit(0);
		        }
		    }
		}
		
		// диагонална проверка дясно-ляво
		for (int i = 3; i <= a; i++) {
			int counter = 1;
			for (int j = 1; j <= b-2; j++) {
				for (int k = 1; k < 3; k++) {
		            if ( (table[i][j] == table[i - k][j + k]) && (table[i][j] != " ") ) {
		                counter++;
		            } else {
		                counter = 1;
		                break;
		            }
				}
		        if (counter == 3) {
		            System.out.println("\"" + table[i][j] + "\"" + " е победителят!" + "\n" + "Пуснете играта отново, ако искате да играете още! :)");
		            System.exit(0);
		        }
			}
		}
		
		
		
	}
	
	
}
