import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class GameLogic {
	
	private GameButton[][] matrix;
	public int[] puzzle;
	public int[] testPuzzle;
	public int[] impossiblePuzzle;
	public ArrayList<Node> solutionPath;
	
	String method;
	
	public GameLogic() {
		// TODO Auto-generated constructor stub
		matrix = new GameButton[4][4];
		puzzle = getPuzzle();	
		int[] x = {1,0,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		testPuzzle = x;
		int[] y = {13,9,5,1,15,10,6,2,14,11,7,3,0,12,8,4};
		impossiblePuzzle = y;
		
	}
	
	class MyRunnable implements Runnable{
		
		Button SeeSolution;
		Button infoBar;
		Future<ArrayList<Node>> future;
		public MyRunnable(Future<ArrayList<Node>> f, Button SeeSolution, Button infoBar) {
			// TODO Auto-generated constructor stub
			
			future = f;
			this.SeeSolution = SeeSolution;
			this.infoBar = infoBar;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			
			try {
				Platform.runLater(() -> {
					SeeSolution.setDisable(true);
					infoBar.setText("Solving with AI... Please do NOT change the state of the game!!!");
				});
				
				solutionPath = future.get();
				
				Platform.runLater(() -> {
					SeeSolution.setDisable(false);
					infoBar.setText("AI solver is done, please click \"See The Solution\"");
				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// run
	}//Myrunnable
	
	public void setMethod(String s) {
		method = s;
	}
	
	public void setButton(int row, int col, GameButton b) {
		matrix[row][col] = b;
	}
	
	public GameButton getButton(int row, int col) {
		return matrix[row][col];
	}
	
	public int[] getPuzzle() {
		int[][] puzzles = new int[15][16];
		
		for(int i = 0; i < 15; i++) {
			if(i == 0) {
				int[] x = {13,14,9,5,15,0,6,1,8,10,12,4,11,7,3,2};
				puzzles[i] =  x;				
			}
			else if(i == 1) {
				int[] x = {1,9,6,2,0,15,10,5,13,3,8,7,14,12,11,4};
				puzzles[i] =  x;	
			}
			else if(i == 2) {
				int[] x = {5,14,1,2,11,6,0,4,9,3,15,7,13,10,12,8};
				puzzles[i] =  x;	
			}
			else if(i == 3) {
				int[] x = {13,0,9,5,8,7,6,14,1,4,3,2,11,10,12,15};
				puzzles[i] =  x;	
			}
			
			else if(i == 4) {
				int[] x = {15,13,5,1,9,14,6,2,12,11,8,3,4,10,0,7};
				puzzles[i] =  x;	
			}
			
			else if(i == 5) {
				int[] x = {13,10,3,9,2,14,0,1,11,6,4,7,15,5,12,8};
				puzzles[i] =  x;	
			}
			
			else if(i == 6) {
				int[] x = {13,9,0,5,8,1,10,7,2,11,6,15,14,12,4,3};
				puzzles[i] =  x;	
			}
			
			else if(i == 7) {
				int[] x = {6,1,13,3,9,0,5,2,14,8,4,15,10,12,11,7};
				puzzles[i] =  x;	
			}
			
			else if(i == 8) {
				int[] x = {13,15,12,9,14,6,4,3,1,11,2,0,5,8,7,10};
				puzzles[i] =  x;	
			}
			
			else if(i == 9) {
				int[] x = {6,14,2,5,9,8,0,3,7,15,1,4,10,13,11,12};
				puzzles[i] =  x;	
			}
			else if(i == 10) {
				int[] x = {13,5,0,1,14,15,9,8,12,2,10,3,11,7,6,4};
				puzzles[i] =  x;	
			}
			
			else if(i == 11) {
				int[] x = {8,14,9,1,11,13,5,6,15,10,7,2,12,4,3,0};
				puzzles[i] =  x;	
			}
			
			else if(i == 12) {
				int[] x = {8,2,9,0,13,15,10,14,12,5,1,4,7,11,3,6};
				puzzles[i] =  x;	
			}
			
			else if(i == 13) {
				int[] x = {13,14,10,5,0,7,9,6,15,12,2,1,3,11,8,4};
				puzzles[i] =  x;	
			}
			
			else if(i == 14) {
				int[] x = {10,7,5,1,11,14,13,0,9,3,12,6,15,2,4,8};
				puzzles[i] =  x;	
			}
		}// for
		
		int random = (int)Math.floor(Math.random()*(14-0+1)+0);
		
		return puzzles[random];
	}// getPuzzle
	

	public boolean checkWinning() {
		int count = 0;
		for(int i = 0; i < 4; i++ ) {
			for(int j = 0; j < 4; j++) {
				if(count != getButton(i, j).number)
					return false;
				count++;
			}
		}// row
		
		return true;
	}
	
	public int[] getCurrentPuzzle() {
		int[] curPuzzle = new int[16];
		int index = 0;
		
		for(int i = 0; i < 4; i++ ) {
			for(int j = 0; j < 4; j++) {
				curPuzzle[index] = getButton(i, j).number;
				index++;
			}
		}// row
		
		return curPuzzle;
	}
	
	public void getPath(String AI_Method){

		Node currentState = new Node(getCurrentPuzzle());
		
		DB_Solver2 solver = new DB_Solver2(currentState, AI_Method);
		Node solution = solver.findSolutionPath();
		
		if(solution != null) {
			solutionPath =  solver.getSolutionPath(solution);
		}
		
		else
			solutionPath = null;
	}
}


class MyCall implements Callable<ArrayList<Node>>{
	Node currentState;
	DB_Solver2 solver;
	Node solution;
	
	String s;
	GameLogic game;

	public MyCall(String AI_Method, GameLogic g) {
		// TODO Auto-generated constructor stub
		game = g;
		
		s = AI_Method;
	}
	
	@Override
	public ArrayList<Node> call() throws Exception {
		// TODO Auto-generated method stub
		
		Node currentState = new Node(game.getCurrentPuzzle());
		
		DB_Solver2 solver = new DB_Solver2(currentState, s);
		Node solution = solver.findSolutionPath();
		if(solution != null) {
			return  solver.getSolutionPath(solution);
			
		}
		
		return null;
	}
	
}