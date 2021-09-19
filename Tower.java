package TipOver_V2;

public class Tower {
	int height;
	public char color;
	int row;
	int col;
	boolean guyOnTop = false;
	
	Implementation A = new Implementation();
	
	public Tower(char color) {		
		if((color == 'R') || (color == 'Y') || (color =='G') || (color =='B')) {
			this.color = color;
		}
		else {
			this.color = 'o';
		}
		
		if(color == 'Y') {
			height = 2;
		}		
		if(color == 'G') {
			height = 3;
		}		
		if(color == 'B') {
			height = 4;
		}
		if(color == 'R') {
			height = 1;
		}
	}
	
	
	public void setIsGuyOnTop() {
		if (A.wheresTheGuy[row][col]) {
			guyOnTop = true;
		}
		else {
			guyOnTop = false;
		}
	}
	
	public boolean checkForGuy() {
		return guyOnTop;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col){
		this.col = col;
	}
}
