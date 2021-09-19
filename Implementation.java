package TipOver_V2;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Implementation {
	boolean boardCreated = false;
	char[][] board = new char[6][6]; //character array for the board
	char temp; //temporary character to serve as a placeholder for the tower the Guy is currently on; used in process of placing Guy
	boolean wheresTheGuy[][] = new boolean[6][6]; //boolean array to keep track of the Guy's location
	boolean gameWon = false; //boolean to determine if the game has been won 
	boolean guyOnBoard = false; 
	boolean testing = false;
	
	public void makeBoard() {
		for (int i = 0; i < board.length; i++) {			
			for (int j = 0; j < board.length; j++) {
				board[i][j] = 'o'; //blank spaces are represented by 'o'
			}
		}
		//printBoard(); //COMMENTED OUT TO SHORTEN TEST UNIT OUTPUT
		boardCreated = true;
	}
	
	public void placeTower(int row, int col, Tower tower) {
		if(gameWon || guyOnBoard){
			
		}
		else {
			if(board[row][col] != 'o') { //checks to see if there is no room (space taken by another tower)
				System.out.println("There is already a tower here.");
			}else {	
				tower.setRow(row);
				tower.setCol(col);
				board[tower.row][tower.col] = tower.color;

			}		
			if(!testing) {
				printBoard();
			}
		}
	}
	
	public char readPosition(int row, int col) { //returns the character at a given position
		return board[row][col];
	}
	
	public void printBoard() { //prints the board
		for (int i = 0; i < board.length; i++) {		
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 'X') {
					board[i][j] = temp;
					wheresTheGuy[i][j]=true;
					System.out.print("{" + board[i][j]+ "}"); //curly braces indicate when the Guy is on a tower
				}else {
					System.out.print("[" + board[i][j]+ "]"); //regular brackets surround each space on the board, unless the Guy is in a non-empty space
					wheresTheGuy[i][j]=false;
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void tipTower(int row, int col, int tipDirection) { //method to handle tipping the tower	
		if(row<0 || col<0 || row>5 || col>5 || tipDirection<0 || tipDirection>4) {
			System.out.println("Make sure your entries for row and column are between 0 and 5, and your entry for tip direction is between 1 and 4.");
			System.out.println();
			return;
		}
		if(gameWon) {
			
		}
		else {
			if(wheresTheGuy[row][col]) { //Only the towers with the guy on it can be tipped
				if(board[row][col] == 'Y' || board[row][col] == 'G' || board[row][col] == 'B') {
					int spaces = 0;
					char colorTipped = 0;
					int rowOffset = 0;
					int colOffset = 0;			
				
					if(board[row][col] == 'Y') { //Yellow = 2 spaces
						spaces = 2;
						colorTipped = 'y'; //tipped yellow towers are represented by lower case Y's
					}		
					if(board[row][col] == 'G') { //Green = 3 spaces
						spaces = 3;
						colorTipped = 'g'; //tipped green towers are represented by lower case G's
					}		
					if(board[row][col] == 'B') { //Blue = 4 spaces
						spaces = 4;
						colorTipped = 'b'; //tipped blue towers are represented by lower case B's
					}
					if(board[row][col] == 'R') { //red towers are 1 space, they cannot be tipped
						System.out.println("You cannot tip a red tower.");
						return;
					}
				
					for (int s=1; s<spaces+1; s++) {						
						if((tipDirection == 1 && row >0 && board[row-s][col] != 'o')||(tipDirection == 2 && col <5 && board[row][col+s] != 'o')||(tipDirection == 3 && row <5 && board[row+s][col] != 'o')||(tipDirection == 4 && col >0 && board[row][col-s] != 'o')) { //prevents tower from tipping onto another
							System.out.println("Space occupied, cannot tip tower. Please tip a different tower.");
							return;
						}
					}
					 if((tipDirection == 1 && row-spaces < 0)||(tipDirection == 2 && col+spaces > 5)||(tipDirection == 3 && row+spaces > 5)||(tipDirection == 4 && col-spaces < 0)) { //prevents the tower from being tipped off the board
						System.out.println("Cannot tip tower off the board.");
					}else{
						for (int i =0; i<spaces+1; i++) {			
							if (tipDirection == 1 && row-spaces >= 0) {	//north	
								rowOffset = -1*i;
								colOffset = 0;
							}
							if (tipDirection == 2 && col+spaces <= 5) { //east
								rowOffset = 0;
								colOffset = i;
							}
							if (tipDirection == 3 && row+spaces <= 5) { //south
								rowOffset = i;
								colOffset = 0;
							}
							if (tipDirection == 4 && col-spaces >= 0) { //west
								rowOffset = 0;
								colOffset = -1*i;
							}
							
							board[row+rowOffset][col+colOffset] = colorTipped; //Each alotted space following the tipped tower is now occupied the lowercase letters
							board[row][col] = 'o';
						}
						tipWithGuy(row+rowOffset,col+colOffset); //moves the Guy to the  space furthest from the tower’s original square
						//board[row][col] = 'o'; //the tower ceases to occupy the square on which it was originally standing					
					}								
					//printBoard();
				}
			}
			else {
				System.out.println("The guy must be on the tower in order to tip it.");
			}			
		}
	}
	
	public void placeGuy(int row, int col) { //used for initially placing the Guy (only on upright towers that aren't red)
		if(gameWon) {
			
		}
		else {
			if (board[row][col] == 'o' || board[row][col] == 'y' || board[row][col] == 'g' || board[row][col] == 'b' || board[row][col] == 'R') {//cannot initially place guy on tipped tower
				System.out.println("Must place guy on an upright tower.");
			} else {
				temp = board[row][col]; //stores the designated tower as the temp
				board[row][col] = 'X'; //temporarily replaces the designated tower's character with 'X'
				guyOnBoard = true;
			}
			printBoard();
		}
	}
	
	public void placeGuy2(int row, int col) { //used for placing the Guy on another tower after the game starts i.e. moving the Guy
		if (board[row][col] == 'o') {
			System.out.println("Must move guy on a tower");
		}
		else if(board[row][col] == 'R') {
			System.out.println("You win!");
			temp = board[row][col]; 
			board[row][col] = 'X';
			gameWon = true;
		}
		else {
			temp = board[row][col];
			board[row][col] = 'X';
		}
		printBoard();
	}
	
	public void tipWithGuy(int row, int col) { //ensures that tipping a tower with the guy actaully moves the position of the guy
		if(board[row][col] == 'R') {
			System.out.println("You win!");
			temp = board[row][col];
			board[row][col] = 'X';
			gameWon = true;
		}
		else {
			temp = board[row][col];
			board[row][col] = 'X';
		}
		printBoard();
	}
	
	public void moveGuy(int row, int col, int dir) { //moves the guy from one space to another. takes a row,col as the starting point and a direction to move
		if(gameWon) {
			
		}
		else {
			if(wheresTheGuy[row][col]==true) {//makes sure the guy is on the designated space			
				if (dir == 1) {//north
					if(row >0 && board[row-1][col] != 'o') {
						placeGuy2(row-1, col);
					}else {
						System.out.println("Cannot move guy onto empty space.");
					}
				}
				if (dir == 2) {//east				
					if(col<5 && board[row][col+1] != 'o') {
						placeGuy2(row, col+1);
					}else {
						System.out.println("Cannot move guy onto empty space.");
					}
				}
				if (dir == 3) {//south				
					if(row <5 && board[row+1][col] != 'o') {
						placeGuy2(row+1, col);
					}else {
						System.out.println("Cannot move guy onto empty space.");
					}
				}
				if (dir == 4) {//west				
					if(col >0 && board[row][col-1] != 'o') {
						placeGuy2(row, col-1);
					}else {
						System.out.println("Cannot move guy onto empty space.");
					}
				}			
			}
		}
	}
	
	public void sampleGame1() { //Creates sample board with towers
		testing = true;
		Tower t1 = new Tower('B');
		placeTower(0, 0, t1);
		Tower t2 = new Tower('G');
		placeTower(1, 4, t2);
		Tower t3 = new Tower('Y');
		placeTower(4, 3, t3);
		Tower goal = new Tower('R');
		placeTower(4, 0, goal);
		placeGuy(0, 0);		
	}
	
	public static void main(String[] args) {
		Implementation A = new Implementation();
		A.makeBoard();
		
		//Interactive sample game with user input
		System.out.println("Welcome to TipOver! Here is the board: ");
		A.sampleGame1();
				
		Scanner sc = new Scanner(System.in);
		System.out.println("Your goal is to get the Guy (indicated by the {curly braces}) to reach the R tower.");
		System.out.println("NOTE: Make sure to press the ENTER key each time you type an input!");
		System.out.println();
		while(!A.gameWon) {
			boolean validInput = false;
			while(!validInput) {				
				try {
					System.out.println("Type 1 to tip a tower, type 2 to move the guy."); 
					int userChoice = sc.nextInt();
					
					if(userChoice==1) { //tip a tower
						System.out.println("Type the row, column, and direction for the tower you would like to tip (1 = North, 2 = East, 3 = South, 4 = West), then press enter!");
						System.out.println("Type the tower's row, then press enter.");						
						int tipTowRow = sc.nextInt();
						System.out.println("Type the tower's column, then press enter.");
						int tipTowCol = sc.nextInt();
						System.out.println("Type the direction to tip the tower, then press enter.");
						int tipTowDir = sc.nextInt();
						A.tipTower(tipTowRow, tipTowCol, tipTowDir);
					}
					if(userChoice==2) { //move the guy
						System.out.println("Type the row and column of the guy, then the direction to move the guy (1 = North, 2 = East, 3 = South, 4 = West)");
						System.out.println("Type the guy's row, then press enter.");
						int moveRow = sc.nextInt();
						System.out.println("Type the guy's column, then press enter.");
						int moveCol = sc.nextInt();
						System.out.println("Type the direction to move the guy, then press enter.");
						int moveDir = sc.nextInt();
						A.moveGuy(moveRow, moveCol, moveDir);
					}
					validInput = true;
				}catch(InputMismatchException e) {
			        System.out.println("Please enter an integer.");
			        System.out.println();
			        sc.next();
			    }
			}
			
		}
		if(A.gameWon) {
			sc.close();
		}		
		/*
		//Solution to the sample game 
		A.tipTower(0, 0, 2);
		A.moveGuy(0, 4, 3);
		A.tipTower(1, 4, 3);
		A.moveGuy(4, 4, 4);
		A.tipTower(4, 3, 4);
		A.moveGuy(4, 1, 4);
		*/
	}
}
