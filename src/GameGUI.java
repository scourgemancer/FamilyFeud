import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI extends Application{
	private Polls polls;
	private Thread audio;

	int leftTeam, rightTeam;
	String leftName = "Apple";
	String rightName = "Pie";
	Scene scene;

	private void playAudio(String filename){
		stopAudio();
		audio = new Thread(() -> {
			Media hit = new Media("src\\resources\\" + filename);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		});
	}

	private void stopAudio(){ if(audio != null) if(audio.isAlive()) audio.interrupt(); }

	/** Each tile can be made uniform and internalize complexity **/
	private class AnswerTile extends Rectangle{
		//todo - add all internal complexity and functions to be pressed, animate itself, et cetera
	}

	@Override
	public void init(){ polls = new Polls("questions.txt"); }

	@Override
	public void start(Stage stage){
		BorderPane window = new BorderPane();
		scene = new Scene(window);

		BorderPane scores = new BorderPane();
		BorderPane names = new BorderPane();
		VBox top = new VBox(names, scores);
		window.setTop(top);

		VBox leftAnswers = new VBox();
		for(int i=1; i<6; i++)
			leftAnswers.getChildren().add(new AnswerTile());
		VBox rightAnswers = new VBox();
		for(int i=1; i<6; i++)
			rightAnswers.getChildren().add(new AnswerTile());
		HBox answers = new HBox(leftAnswers, rightAnswers);
		window.setCenter(answers);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			//todo - finish processing keyboard input here
			switch(key.getText()){
				case "1": break;
				case "2": break;
				case "3": break;
				case "4": break;
				case "5": break;
				case "6": break;
				case "7": break;
				case "8": break;
				case "9": break;
				case "0": break;
				case "t": break; //theme song
				case "x": break; //strike sound
				case "LEFT": break;
				case "RIGHT": break;
				case "UP": break;
				case "DOWN": break;
				case "ENTER": break; //this goes forward if you've gone backwards w/o editing, else goes to fast money
				case "BACKSPACE": break; //implement an undo function
			}
		});

		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
	}

	public static void main(String[] args){ Application.launch(args); }
}
