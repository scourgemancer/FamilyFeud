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
			//todo - process keyboard input here
		});

		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
	}

	public static void main(String[] args){ Application.launch(args); }
}
