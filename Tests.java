package TipOver_V2;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


class Tests {
	private Implementation game;
	
	@BeforeEach //this prints the test name at the beginning of each test in the console
	void init(TestInfo testInfo) {
	    String methodName = testInfo.getTestMethod().orElseThrow().getName();
	    System.out.println("-----CURRENT TEST: " + methodName + "-----");
	}
	
	@BeforeEach
	protected void before() throws Exception
	{
		game = new Implementation();
		game.makeBoard();
	}
	
	@Test //User story 0: Create a 6x6 board for solving the TipOver puzzle to be printed out to the screen.
	void testBoard() {		
		assertTrue(game.boardCreated);
	}
	
	@Test //User story 1: Allow the user to place an identified tower upright at a specified location.
	void testPlaceTower() {	
		Tower t = new Tower('R');
		game.placeTower(2,2,t);
		assertEquals('R', game.board[2][2]);
	}
	
	@Test //User story 1.1: verify that the board is empty
	void testBoardEmpty() {	
		assertEquals('o', game.readPosition(3,1));
	}
	
	@Test //User story 1.2: Make board; verify location 2,2 is empty; place RED crate in 2,2; verify R there.
	void testVerifyPlaceTower() {	
		Tower t = new Tower('R');
		assertEquals('o', game.readPosition(5,5));
		game.placeTower(5, 5, t);
		assertEquals('R', game.readPosition(5,5));
	}
	
	@Test //User story 1.3: Attempt to place a tower in a non-empty square
	void testPlaceOnOccupied() {	
		Tower t1 = new Tower('R');
		Tower t2 = new Tower('Y');
		game.placeTower(3, 5, t1);
		assertEquals('R', game.readPosition(3,5));
		game.placeTower(3, 5, t2);
		assertEquals('R', game.readPosition(3,5));
	}
	
	@Test //User story 1.4: Attempt to place a tower with a non-valid color (not RYGB)
	void testPlaceNontower() {	
		Tower t = new Tower('L');
		game.placeTower(3, 5, t);
		assertEquals('o', game.readPosition(3,5));
	}
	
	@Test //User story 2: Allow the program to query the board to determine what is at a particular location. (upright tower)
	void testReadPositionR() {	
		Tower t = new Tower('R');
		game.placeTower(4, 2, t);
		assertEquals('R', game.readPosition(4,2));
	}
	
	@Test //User story 2.1: Allow the program to query the board to determine what is at a particular location. (empty)
	void testReadPositionO() {	
		assertEquals('o', game.readPosition(3,1));
	}
	
	@Test //User story 2.2: Allow the program to query the board to determine what is at a particular location. (segmented tower)
	void testReadPositionTipped() {	
		Tower t = new Tower('Y');
		game.placeTower(5, 3, t);
		game.placeGuy(5, 3);
		game.tipTower(5, 3, 2);
		assertEquals('y', game.readPosition(5,4));
	}
	
	@Test //User story 2.3: Attempt to tip a red tower
	void testTipRTower() {	
		Tower t = new Tower('R');
		game.placeTower(5, 3, t);
		game.tipTower(5, 3, 2);
		assertEquals('R', game.readPosition(5,3));
	}
	
	@Test //User story 2.4: Attempt to tip a tower more than once
	void testTowerTwice() {	
		Tower t = new Tower('Y');
		game.placeTower(2, 2, t);
		game.placeGuy(2, 2);
		game.tipTower(2, 2, 2);
		assertEquals('y', game.readPosition(2,3));
		game.tipTower(2, 2, 2);
		assertEquals('y', game.readPosition(2,3));
	}
	
	//YELLOW TOWER TESTS
	
	@Test //User story 3: Have a yellow (height 2) tower tip over to the left (west) when there is room.
	void testTipYTowerW() {	
		Tower t = new Tower('Y');
		game.placeTower(5,2,t);
		game.placeGuy(5, 2);
		game.tipTower(5,2,4);
		assertEquals('y', game.board[5][2-1]);
		assertEquals('y', game.board[5][2-2]);	
	}
	
	@Test //User story 3.1: Attempt to tip the yellow tower left (west) when there is already a tower there
	void testTipYTowerNoRoomW() {	
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		//game.placeGuy(2, 2);
		game.placeTower(2,1,t2);
		game.tipTower(2,2,4);
		assertEquals('R', game.board[2][1]);	
	}
	
	@Test //User story 3.2: Attempt to tip a yellow tower left (west) towards the edge of the board
	void testTipYTowerOffBoardW() {	
		Tower t = new Tower('Y');
		game.placeTower(1,1,t);
		//game.placeGuy(1, 1);
		game.tipTower(1,1,4);
		assertEquals('Y', game.board[1][1]);	
	}
	
	@Test //User story 4: Have a yellow tower tip over to the right (east) when there is room.
	void testTipYTowerE() {	
		Tower t = new Tower('Y');
		game.placeTower(5, 3, t);
		game.placeGuy(5, 3);
		game.tipTower(5, 3, 2);
		assertEquals('y', game.readPosition(5,4));
		assertEquals('y', game.readPosition(5,5));
	}
	
	@Test //User story 4.1: Attempt to tip the yellow tower right (east) when there is already a tower there
	void testTipYTowerNoRoomE() {	
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('R');
		game.placeTower(2,1,t1);
		game.placeTower(2,2,t2);
		game.tipTower(2,1,2);
		assertEquals('R', game.board[2][2]);	
	}
	
	@Test //User story 4.2: Attempt to tip a yellow tower right (east) towards the edge of the board
	void testTipYTowerOffBoardE() {	
		Tower t = new Tower('Y');
		game.placeTower(1,5,t);
		game.tipTower(1,5,2);
		assertEquals('Y', game.board[1][5]);
	}
	
	@Test //User story 5: Have a yellow tower tip over upwards (north) when there is room.
	void testTipYTowerN() {	
		Tower t = new Tower('Y');
		game.placeTower(5, 3, t);
		game.placeGuy(5,3);
		game.tipTower(5, 3, 1);
		assertEquals('y', game.readPosition(4,3));
		assertEquals('y', game.readPosition(3,3));
	}
	
	@Test //User story 5.1: Attempt to tip the yellow tower upwards (north) when there is already a tower there
	void testTipYTowerNoRoomN() {	
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		game.placeTower(1,2,t2);
		game.tipTower(2,2,1);
		assertEquals('R', game.board[1][2]);	
	}
	
	@Test //User story 5.2: Attempt to tip a yellow tower upwards (north) towards the edge of the board
	void testTipYTowerOffBoardN() {	
		Tower t = new Tower('Y');
		game.placeTower(0,0,t);
		game.tipTower(0,0,1);
		assertEquals('Y', game.board[0][0]);
	}
	

	
	@Test //User story 6: Have a yellow tower tip over downward (south) when there is room.
	void testTipYTowerS() {	
		Tower t = new Tower('Y');
		game.placeTower(2, 2, t);
		game.placeGuy(2, 2);
		game.tipTower(2, 2, 3);
		assertEquals('y', game.readPosition(3,2));
		assertEquals('y', game.readPosition(4,2));
	}
	
	@Test //User story 6.1: Attempt to tip the yellow tower downward (south) when there is already a tower there
	void testTipYTowerNoRoomS() {	
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		game.placeTower(3,2,t2);
		game.tipTower(2,2,3);
		assertEquals('R', game.board[3][2]);	
	}
	
	@Test //User story 6.2: Attempt to tip a yellow tower downward (south) towards the edge of the board
	void testTipYTowerOffBoardS() {	
		Tower t = new Tower('Y');
		game.placeTower(5,2,t);
		game.tipTower(5,2,3);
		assertEquals('Y', game.board[5][2]);
	}
	
	//GREEN TOWER TESTS
	
	@Test //User story 7: Have a green (height 3) tower tip over to the left (west) when there is room.
	void testTipGTowerW() {	
		Tower t = new Tower('G');
		game.placeTower(3,4,t);
		game.placeGuy(3,4);
		game.tipTower(3,4,4);
		assertEquals('g', game.board[3][4-1]);
		assertEquals('g', game.board[3][4-2]);
		assertEquals('g', game.board[3][4-3]);
	}
	
	@Test //User story 7: Attempt to tip the green tower left (west) when there is already a tower there
	void testTipGTowerNoRoomW() {	
		Tower t1 = new Tower('G');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		game.placeTower(2,1,t2);
		game.tipTower(2,2,4);
		assertEquals('R', game.board[2][1]);	
	}
	
	@Test //User story 7: Attempt to tip a green tower left (west) towards the edge of the board
	void testTipGTowerOffBoardW() {	
		Tower t = new Tower('G');
		game.placeTower(1,1,t);
		game.tipTower(1,1,4);
		assertEquals('G', game.board[1][1]);	
	}
	
	@Test //User story 7: Have a green tower tip over to the right (east) when there is room.
	void testTipGTowerE() {	
		Tower t = new Tower('G');
		game.placeTower(4, 2,t);
		game.placeGuy(4, 2);
		game.placeGuy(4, 2);
		game.tipTower(4, 2, 2);
		assertEquals('g', game.readPosition(4,3));
		assertEquals('g', game.readPosition(4,4));
		assertEquals('g', game.readPosition(4,5));
	}
	
	@Test //User story 7: Attempt to tip the green tower right (east) when there is already a tower there
	void testTipGTowerNoRoomE() {	
		Tower t1 = new Tower('G');
		Tower t2 = new Tower('R');
		game.placeTower(2,1,t1);
		game.placeTower(2,2,t2);
		game.tipTower(2,1,2);
		assertEquals('R', game.board[2][2]);	
	}
	
	@Test //User story 7: Attempt to tip a green tower right (east) towards the edge of the board
	void testTipGTowerOffBoardE() {	
		Tower t = new Tower('G');
		game.placeTower(1,3,t);
		game.tipTower(1,3,2);
		assertEquals('G', game.board[1][3]);
	}
	
	@Test //User story 7: Have a green tower tip over upwards (north) when there is room.
	void testTipGTowerN() {	
		Tower t = new Tower('G');
		game.placeTower(5, 3, t);
		game.placeGuy(5,3);
		game.tipTower(5, 3, 1);
		assertEquals('g', game.readPosition(2,3));
		assertEquals('g', game.readPosition(3,3));
		assertEquals('g', game.readPosition(4,3));
	}
	
	@Test //User story 7: Attempt to tip the green tower upwards (north) when there is already a tower there
	void testTipGTowerNoRoomN() {	
		Tower t1 = new Tower('G');
		Tower t2 = new Tower('R');
		game.placeTower(4,2,t1);
		game.placeTower(3,2,t2);
		game.tipTower(4,2,1);
		assertEquals('R', game.board[3][2]);	
	}
	
	@Test //User story 7: Attempt to tip a green tower upwards (north) towards the edge of the board
	void testTipGTowerOffBoardN() {	
		Tower t = new Tower('G');
		game.placeTower(2,2,t);
		game.tipTower(2,2,1);
		assertEquals('G', game.board[2][2]);
	}
	
	@Test //User story 7: Have a green tower tip over downward (south) when there is room.
	void testTipGTowerS() {	
		Tower t = new Tower('G');
		game.placeTower(2, 2, t);
		game.placeGuy(2, 2);
		game.tipTower(2, 2, 3);
		assertEquals('g', game.readPosition(3,2));
		assertEquals('g', game.readPosition(4,2));
		assertEquals('g', game.readPosition(5,2));
	}
	
	@Test //User story 7: Attempt to tip the green tower downward (south) when there is already a tower there
	void testTipGTowerNoRoomS() {	
		Tower t1 = new Tower('G');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		game.placeTower(3,2,t2);
		game.tipTower(2,2,3);
		assertEquals('R', game.board[3][2]);	
	}
	
	@Test //User story 7: Attempt to tip a green tower downward (south) towards the edge of the board
	void testTipGTowerOffBoardS() {	
		Tower t = new Tower('G');
		game.placeTower(3,2,t);
		game.tipTower(3,2,3);
		assertEquals('G', game.board[3][2]);
	}
	
	//BLUE TOWER TESTS
	
	@Test //User story 8: Have a blue (height 3) tower tip over to the left (west) when there is room.
	void testTipBTowerW() {	
		Tower t = new Tower('B');
		game.placeTower(1,4,t);
		game.placeGuy(1,4);
		game.tipTower(1,4,4);			
		assertEquals('b', game.readPosition(1,3));
		assertEquals('b', game.readPosition(1,2));
		assertEquals('b', game.readPosition(1,1));
		assertEquals('b', game.readPosition(1,0));
	}
	
	@Test //User story 8: Attempt to tip the blue tower left (west) when there is already a tower there
	void testTipBTowerNoRoomW() {	
		Tower t1 = new Tower('B');
		Tower t2 = new Tower('R');
		game.placeTower(2,4,t1);
		game.placeTower(2,3,t2);
		game.tipTower(2,4,4);
		assertEquals('R', game.board[2][3]);	
	}
	
	@Test //User story 8: Attempt to tip a blue tower left (west) towards the edge of the board
	void testTipBTowerOffBoardW() {	
		Tower t = new Tower('B');
		game.placeTower(1,3,t);
		game.tipTower(1,3,4);
		assertEquals('B', game.board[1][3]);	
	}
	
	@Test //User story 8: Have a blue tower tip over to the right (east) when there is room.
	void testTipBTowerE() {	
		Tower t = new Tower('B');
		game.placeTower(4, 1, t);
		game.placeGuy(4,1);
		game.tipTower(4, 1, 2);
		assertEquals('b', game.readPosition(4,2));
		assertEquals('b', game.readPosition(4,3));
		assertEquals('b', game.readPosition(4,4));
		assertEquals('b', game.readPosition(4,5));			
	}
	
	@Test //User story 8: Attempt to tip the blue tower right (east) when there is already a tower there
	void testTipBTowerNoRoomE() {	
		Tower t1 = new Tower('B');
		Tower t2 = new Tower('R');
		game.placeTower(2,1,t1);
		game.placeTower(2,2,t2);
		game.tipTower(2,1,2);
		assertEquals('R', game.board[2][2]);	
	}
	
	@Test //User story 8: Attempt to tip a blue tower right (east) towards the edge of the board
	void testTipBTowerOffBoardE() {	
		Tower t = new Tower('B');
		game.placeTower(1,3,t);
		game.tipTower(1,3,2);
		assertEquals('B', game.board[1][3]);
	}
	
	@Test //User story 8: Have a blue tower tip over upwards (north) when there is room.
	void testTipBTowerN() {	
		Tower t = new Tower('B');
		game.placeTower(5, 3, t);
		game.placeGuy(5,3);
		game.tipTower(5, 3, 1);
		assertEquals('b', game.readPosition(1,3));
		assertEquals('b', game.readPosition(2,3));
		assertEquals('b', game.readPosition(3,3));
		assertEquals('b', game.readPosition(4,3));
	}
	
	@Test //User story 8: Attempt to tip the blue tower upwards (north) when there is already a tower there
	void testTipBTowerNoRoomN() {	
		Tower t1 = new Tower('B');
		Tower t2 = new Tower('R');
		game.placeTower(4,2,t1);
		game.placeTower(3,2,t2);
		game.tipTower(4,2,1);
		assertEquals('R', game.board[3][2]);	
	}
	
	@Test //User story 8: Attempt to tip a blue tower upwards (north) towards the edge of the board
	void testTipBTowerOffBoardN() {	
		Tower t = new Tower('B');
		game.placeTower(2,2,t);
		game.tipTower(2,2,1);
		assertEquals('B', game.board[2][2]);
	}
	
	@Test //User story 8: Have a blue tower tip over downward (south) when there is room.
	void testTipBTowerS() {	
		Tower t = new Tower('B');
		game.placeTower(1, 2, t);
		game.placeGuy(1, 2);
		game.tipTower(1, 2, 3);
		assertEquals('b', game.readPosition(2,2));
		assertEquals('b', game.readPosition(3,2));
		assertEquals('b', game.readPosition(4,2));
		assertEquals('b', game.readPosition(5,2));
	}
	
	@Test //User story 8: Attempt to tip the blue tower downward (south) when there is already a tower there
	void testTipBTowerNoRoomS() {	
		Tower t1 = new Tower('B');
		Tower t2 = new Tower('R');
		game.placeTower(2,2,t1);
		game.placeTower(3,2,t2);
		game.tipTower(2,2,3);
		assertEquals('R', game.board[3][2]);	
	}
	
	@Test //User story 8: Attempt to tip a blue tower downward (south) towards the edge of the board
	void testTipBTowerOffBoardS() {	
		Tower t = new Tower('B');
		game.placeTower(3,2, t);
		game.tipTower(3,2,3);
		assertEquals('B', game.board[3][2]);
	}
	
	@Test //User story 9: Place a guy on the given upright tower
	void testPlaceGuy() {	
		Tower t = new Tower('Y');
		game.placeTower(1, 1, t);
		game.placeGuy(1,1);
		assertTrue(game.wheresTheGuy[1][1]);
	}
	
	@Test //User story 9: Attempt to place the guy on more than one tower at 1 time
	void testPlaceMoreThan1Guy() {	
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('G');
		Tower t3 = new Tower('B');
		
		game.placeTower(3, 3, t1);
		game.placeTower(1, 2, t2);
		game.placeTower(0, 4, t3);
		
		game.placeGuy(3, 3);
		game.placeGuy(1, 2);
		game.placeGuy(0, 4);
		
		assertFalse(game.wheresTheGuy[3][3]);
		assertFalse(game.wheresTheGuy[1][2]);
		assertTrue(game.wheresTheGuy[0][4]);
	}
	
	@Test //User story 10: Move the guy 1 space
	void testMoveGuy() {
		Tower t1 = new Tower('Y');
		Tower t2 = new Tower('G');
		game.placeTower(3, 3, t1);
		game.placeTower(4, 3, t2);
		game.placeGuy(3, 3);
		game.moveGuy(3, 3, 3);
		assertFalse(game.wheresTheGuy[3][3]);
		assertTrue(game.wheresTheGuy[4][3]);
	}
	
	@Test //User story 10.1: Attempt to move guy off board
	void testMoveGuyOffBoard() {
		Tower t1 = new Tower('Y');
		game.placeTower(5, 5, t1);
		game.placeGuy(5, 5);
		game.moveGuy(5, 5, 3);
		assertTrue(game.wheresTheGuy[5][5]);
	}
	
	@Test //User story 10.2: Attempt to move guy onto an empty space
	void testMoveGuyEmpty() {
		Tower t1 = new Tower('Y');
		game.placeTower(2, 2, t1);
		game.placeGuy(2, 2);
		game.moveGuy(2, 2, 3);
		assertTrue(game.wheresTheGuy[2][2]);
	}
	
	@Test //User story 10.3: Attempt to move guy from an unoccupied tower is not on
	void testMoveGuyVacantTower() {
		Tower t1 = new Tower('Y');
		game.placeTower(2, 2, t1);
		Tower t2 = new Tower('B');
		game.placeTower(4, 2, t2);
		Tower t3 = new Tower('G');
		game.placeTower(4, 3, t3);
		game.placeGuy(2, 2);
		game.moveGuy(4, 2, 2);
		assertTrue(game.wheresTheGuy[2][2]);
	}
	
	@Test //User story 11: Move the guy more than one space
	void testMoveGuyMoreThan1Space() {
		Tower t1 = new Tower('B');
		game.placeTower(3, 1, t1);		
		game.placeGuy(3, 1);
		game.tipTower(3, 1, 2);
		
		game.moveGuy(3, 5, 4);
		game.moveGuy(3, 4, 4);
		assertTrue(game.wheresTheGuy[3][3]);
	}
	
	@Test //User story 11: Attempt to tip a tower that doesn't have the Guy on it
	void testTipTowerWithoutGuy() {
		Tower t1 = new Tower('B');
		game.placeTower(3, 1, t1);
		Tower t2 = new Tower('Y');
		game.placeTower(3, 3, t2);	
		game.placeGuy(3, 1);
		game.tipTower(3, 3, 2);
		assertEquals('Y', game.board[3][3]);
		assertTrue(game.wheresTheGuy[3][1]);
	}
	
	@Test //User story 12: When the guy reaches the red tower (of height 1) the puzzle is solved.
	void testWinGame() {
		Tower t1 = new Tower('R');
		game.placeTower(2,4,t1);
		Tower t2 = new Tower('G');
		game.placeTower(2, 5, t2);
		
		game.placeGuy(2, 5);
		game.moveGuy(2, 5, 4);
		assertTrue(game.wheresTheGuy[2][4]);
	}
	
	@Test //User story 12: Attempt to move the guy after the game is over
	void testEndGame() {
		Tower t1 = new Tower('R');
		game.placeTower(2,4,t1);
		Tower t2 = new Tower('G');
		game.placeTower(2, 5, t2);
		
		game.placeGuy(2, 5);
		game.moveGuy(2, 5, 4);
		game.moveGuy(2, 4, 2);
		assertTrue(game.wheresTheGuy[2][4]);
	}
	
	@AfterEach
	protected void after() throws Exception 
	{
		game = null;
	}	

}
