import java.util.ArrayList;
import java.util.concurrent.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to 15 Puzzle");
		
		Scene scene = new Scene(welcomeScreen(primaryStage), 1100,720);
		primaryStage.setScene(scene);
		primaryStage.show();
	}// start
	
	public BorderPane welcomeScreen(Stage primaryStage) {
		BorderPane bp = new BorderPane();
				
		addBackground(bp);
		
		Button welcomB = new Button("Welcome To 15 Puzzle!");
		welcomB.setStyle("-fx-font-size:65; "
				+ "-fx-font-weight:bold ;"
				+ " -fx-background-color: transparent;"
				+ " -fx-text-base-color: #802200;");
		
		//welcomB.setPrefSize(300, 200);
		
		VBox v = new VBox(welcomB);
		v.setAlignment(Pos.CENTER);
		bp.setCenter(v);
		
		PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
		pause.setOnFinished(event -> {
			Scene newScene = new Scene(gameScene(primaryStage),1100,720);
			primaryStage.setScene(newScene);}
		);
		pause.play();
		
				
		return bp;
	}// welcomeScreens
	
	public BorderPane gameScene(Stage primaryStage) {
		GameLogic game = new GameLogic();
		BorderPane bp = new BorderPane();
		
		GridPane gameGrid = new GridPane();
		
		Button newPuzzle = createButton("New Puzzle");
		Button solve1 = createButton("AI H1");
		Button solve2 = createButton("AI H2");
		Button seeSolution = createButton("See The Solution");
		Button exitButton = createButton("Exit Game :(");
		Button infoBar = new Button("To solve with AI, choose the solver method \"AI H1\" or \"AI H2\" and then click \"See The Solution\"");
		
		// infoBar Design
		infoBar.setStyle("-fx-font-size:19; "
				+ "-fx-font-weight:bold ;"
				+ " -fx-text-base-color: #ffbb00;"
				+" -fx-background-color: #802200;"
				+ " -fx-background-radius: 50px ; "
				+ " -fx-border-radius: 50px ; "
				+ "-fx-padding: 20px 20px 20px 20px ");
		
		infoBar.setMinSize(1070, 50);
		infoBar.setDisable(true);
		infoBar.setOpacity(0.6);
		// 
		
		seeSolution.setDisable(true);
		
		exitButton.setOnAction(e-> primaryStage.close());
		
		newPuzzle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				// TODO Auto-generated method stub
				Scene newScene = new Scene(gameScene(primaryStage),1100,720);
				primaryStage.setScene(newScene);
			}// handle
		});
		
		solve1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				// TODO Auto-generated method stub
				
				ExecutorService ex = Executors.newFixedThreadPool(5);
				
				Future<ArrayList<Node>> future = ex.submit(new MyCall("heuristicOne", game));
				
				//create a runnable on a different thread for the .get() method
				Future<?> f = ex.submit(game.new MyRunnable(future, seeSolution, infoBar));
				
				ex.shutdown();
				
				solve1.setDisable(true);
				solve2.setDisable(true);
			}// handle
		});
		
		
		solve2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				// TODO Auto-generated method stub
				
				ExecutorService ex = Executors.newFixedThreadPool(5);
				
				Future<ArrayList<Node>> future = ex.submit(new MyCall("heuristicTwo", game));
				
				//create a runnable on a different thread for the .get() method
				Future<?> f = ex.submit(game.new MyRunnable(future, seeSolution, infoBar));
				
				ex.shutdown();
				
				solve1.setDisable(true);
				solve2.setDisable(true);
			}// handle
		});
		
		seeSolution.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				// TODO Auto-generated method stub
				
				if(game.solutionPath != null) {
					
					// ExecutorService to handle pause between moves
					ExecutorService ex = Executors.newFixedThreadPool(1);
					
					// make sure the puzzle isn't solved in less than 10 moves
					int size;
					if(game.solutionPath.size() < 10)
						size = game.solutionPath.size();
					else
						size = 10;
						
					for(int i = 0; i < size; i++) {
						// Pause Runnable is used to handle the pause between moves
						Future<?> f = ex.submit(new Pause(i, game, solve1, solve2, infoBar, gameGrid, seeSolution));
						
					}// for
					ex.shutdown();
					
					seeSolution.setDisable(true);
				}
			}// handle
		});
		
		EventHandler<ActionEvent> gridButtonHandler = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				GameButton b1 = (GameButton)e.getSource();
				GameButton target = checkNeighborButton(b1, game);
				if(target != null) {
					int targetNumber = target.number;
					
					int b1Number = b1.number;
					
					b1.createStyle(targetNumber);
					target.createStyle(b1Number);
					
					// check if the puzzle is solved by the user (automatedMove is false)
					winningGrid(false, game, gameGrid, solve1, solve2, seeSolution);
				}//if
				
			}// handle
		};
		
		// Layout code
		VBox buttonMenu = new VBox(20, newPuzzle, solve1, solve2, seeSolution, exitButton);
		addGrid(gameGrid, game, gridButtonHandler);
		
		HBox gameLayout = new HBox(20, gameGrid, buttonMenu);
		
		VBox generalLayout = new VBox(gameLayout, infoBar);
		
		VBox.setMargin(infoBar, new Insets(5, 0, 5, 0));
		
		gameLayout.setAlignment(Pos.CENTER);
		generalLayout.setAlignment(Pos.CENTER);
		gameGrid.setAlignment(Pos.CENTER);
		buttonMenu.setAlignment(Pos.CENTER);
		addBackground(bp);
		bp.setCenter(generalLayout);
			
		return bp;
	}
	
	private void addGrid(GridPane gameGrid, GameLogic game, EventHandler<ActionEvent> gridButtonHandler) {
		int count = 0;
		for(int x = 0; x<4; x++) {
			
			for(int i = 0; i<4; i++) {
				GameButton b1 = new GameButton(x,i, game.puzzle[count] );
				// make sure to link the button to the matrix in GameLogic
				game.setButton(x, i, b1);
				
				b1.setMinSize(150, 150);	
				b1.setOnAction(gridButtonHandler);
				
				gameGrid.add(b1, i, x);
				GridPane.setMargin(b1, new Insets(5, 5, 5, 5));
				count++;
			}// for
		}// for	
		
	}

	// Method to handle the adding of background to the BorderPane
	private void addBackground(BorderPane b) {
		Image image = new Image("/background.png");
		BackgroundSize bgSize = new BackgroundSize(1100, 720, false, false, false, false);
		BackgroundImage bgImage = new BackgroundImage(image, 
												BackgroundRepeat.NO_REPEAT,
												BackgroundRepeat.NO_REPEAT, 
												BackgroundPosition.CENTER, 
												bgSize);
		b.setBackground(new Background(bgImage));
	}
	
	
	// Method to create the option buttons on the right of the grid
	private Button createButton(String s) {
		Button b = new Button(s);
		b.setMinSize(420, 100);
		b.setStyle("-fx-font-size:40; "
				+ "-fx-font-weight:bold ;"
				+ " -fx-background-color: #ffbb00;"
				+ " -fx-text-base-color: #802200;"
				+ " -fx-background-radius: 50px ; "
				+ " -fx-border-radius: 50px ; "
				+ "-fx-padding: 20px 20px 20px 20px ");
		return b;
	}
	
	// Method to check if the neighboring button is an empty button. 
	//If that's the case, then return that button
	private GameButton checkNeighborButton(GameButton b, GameLogic game) {
		int c = b.c;
		int r = b.r;
		
		if(c > 0 && c < 3  ) {
			if(game.getButton(r, c+1).number == 0)
				return game.getButton(r, c+1);
			if(game.getButton(r, c-1).number == 0)
				return game.getButton(r, c-1);
		}//if
		
		if(r < 3 && r > 0) {
			if(game.getButton(r-1, c).number == 0)
				return game.getButton(r-1, c);
			if(game.getButton(r+1, c).number == 0)
				return game.getButton(r+1, c);
		}
		if(c == 0 && game.getButton(r, c+1).number == 0)
			return game.getButton(r, c+1);
		
		if(c == 3 && game.getButton(r, c-1).number == 0)
			return game.getButton(r, c-1);
		
		if(r == 0 && game.getButton(r+1, c).number == 0)
			return game.getButton(r+1, c);
		
		if(r == 3 && game.getButton(r-1, c).number == 0)
			return game.getButton(r-1, c);
		
		return null;
	}
	
	// changeGrid to solve with AI
	private void changeGrid(GameLogic game, int x, GridPane gameGrid, Button solve1, Button solve2, Button seeSolution) {
		int[] stateArr = game.solutionPath.get(x).getKey();
		int index = 0;
		
		for(int i = 0; i<4; i++) {
			for(int j = 0; j < 4; j++) {
				game.getButton(i, j).createStyle(stateArr[index]);
				if(winningGrid(true, game, gameGrid, solve1, solve2, seeSolution))
					break;
				index++;
			}
		}// for
			
	}// changeGrid
	
	// Change the GridPane when the game ends and show the results
	public boolean winningGrid(boolean automatedWin, GameLogic game, GridPane gameGrid, Button solve1, Button solve2, Button seeSolution) {
		if(game.checkWinning()) {
			gameGrid.getChildren().clear();
			Button b;
			if(automatedWin)
				b = new Button("GAME IS SOLVED!\n \n PLEASE SELECT A NEW\n PUZZLE :) \n \n OR EXIT THE GAME :(");
			
			else
				b = new Button("CONGRATULATIONS\n YOU WON!!!\n \n PLEASE SELECT A NEW\n PUZZLE :) \n \n OR EXIT THE GAME :(");
			
			b.setStyle("-fx-font-size:40; "
					+ "-fx-font-weight:bold ;"
					+ " -fx-text-base-color: #ffbb00;"
					+" -fx-background-color: #802200;"
					+ "-fx-padding: 20px 20px 20px 20px ");
			
			 b.setOpacity(0.6);
			 b.setTextAlignment(TextAlignment.CENTER);
			 b.setMinSize(600, 600);
			 b.setMaxSize(600, 600);
			 gameGrid.getChildren().add(b);
			 solve1.setDisable(true);
			 solve2.setDisable(true);
			 seeSolution.setDisable(true);
			 return true;
		}// if
		return false;
	}// winningGrid
	
	class Pause implements Runnable{
		int index;
		GameLogic game;
		Button solve1;
		Button solve2;
		Button infoBar;
		GridPane gameGrid;
		Button seeSolution;
		public Pause(int index, GameLogic game, Button solve1, Button solve2, Button infoBar, GridPane gameGrid, Button seeSolution) {
			// TODO Auto-generated constructor stub
			this.index = index;
			this.game = game;
			this.solve1 = solve1;
			this.solve2 = solve2;
			this.infoBar = infoBar;
			this.seeSolution = seeSolution;
			this.gameGrid = gameGrid;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			Platform.runLater(() -> {
				
				changeGrid(game, index, gameGrid, solve1, solve2,  seeSolution );
				solve1.setDisable(true);
				solve2.setDisable(true);
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Platform.runLater(() -> {
				solve1.setDisable(false);
				solve2.setDisable(false);
				infoBar.setText("To solve with AI, choose the solver method \"AI H1\" or \"AI H2\" and then click \"See The Solution\"");
			});
		}// run
		
	}// Pause

}
