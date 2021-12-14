import javafx.scene.control.Button;

public class GameButton extends Button {
	int c;
	int r;
	Integer number;
	
	public GameButton(int row, int column, int x) {
		// TODO Auto-generated constructor stub
		c = column;
		r = row;
		number = x;
	
		createStyle();
	}
	
	private void createStyle() {
		// is it an empty button?
		if(number == 0) {
			setText("");
			setStyle(" -fx-background-color: #802200;"
					+ "-fx-padding: 20px 20px 20px 20px ");
			setOpacity(0.6);
		}
		
		else {
			setText(number.toString());
			setStyle("-fx-font-size:40; "
					+ "-fx-font-weight:bold ;"
					+ " -fx-text-base-color: #802200;"
					+ " -fx-background-color: #ffbb00;"
					+ "-fx-padding: 20px 20px 20px 20px ");
			setOpacity(1);
		}// else
	}// createStyle
	
	public void createStyle(int x) {
		number = x;
		
		createStyle();	
	}
}
