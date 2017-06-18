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

import java.util.ArrayList;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI extends Application{
	private Polls polls;
	private Scene scene;
	private Thread audio;
	private ArrayList<AnswerTile> answerTiles;

	String leftName = "Apple";
	String rightName = "Pie";
	int leftTeam, rightTeam; //used for keeping track of team scores


	private void playAudio(String filename){
		stopAudio();
		audio = new Thread(() -> {
			Media hit = new Media("src\\resources\\" + filename);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		});
	}

	private void stopAudio(){ if(audio != null) if(audio.isAlive()) audio.interrupt(); }

	private class AnswerTile extends Rectangle{
		int rank;
		Answer answer;

		AnswerTile(int i){
			rank  = i;
			answerTiles.add(this);
		}

		void setAnswer(Answer a){ answer = a; }

		void reveal(){  }//todo - animate the question revealing itself
	}

	@Override
	public void init(){ polls = new Polls("questions.txt"); }

	@Override
	public void start(Stage stage){
		BorderPane window = new BorderPane();
		scene = new Scene(window);

		//Areas for the team names, scores, and current question value
		BorderPane scores = new BorderPane();
		BorderPane names = new BorderPane();
		VBox top = new VBox(names, scores);
		window.setTop(top);

		//The area containing the actual answers
		VBox leftAnswers = new VBox();
		for(int i=1; i<6; i++)
			leftAnswers.getChildren().add(new AnswerTile(i));
		VBox rightAnswers = new VBox();
		for(int i=1; i<6; i++)
			rightAnswers.getChildren().add(new AnswerTile(i));
		HBox answers = new HBox(leftAnswers, rightAnswers);
		window.setCenter(answers);

		//Handles user input with the program
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			//todo - finish processing keyboard input here
			switch(key.getCode().getName()){
				case "1": answerTiles.get(0).reveal(); break;
				case "2": answerTiles.get(1).reveal(); break;
				case "3": answerTiles.get(2).reveal(); break;
				case "4": answerTiles.get(3).reveal(); break;
				case "5": answerTiles.get(4).reveal(); break;
				case "6": answerTiles.get(5).reveal(); break;
				case "7": answerTiles.get(6).reveal(); break;
				case "8": answerTiles.get(7).reveal(); break;
				case "9": answerTiles.get(8).reveal(); break;
				case "0": answerTiles.get(9).reveal(); break;
				case "t": playAudio("theme.mp3"); break; //theme song
				case "x": playAudio("strike.mp3"); break; //strike sound
				case "s": stopAudio(); break; //stops all of the audio
				case "Left": break;
				case "Right": break;
				case "Space": break;
				case "Up": break;
				case "Down": break;
				case "Enter": break; //this goes forward if you've gone backwards w/o editing, else goes to fast money
				case "Backspace": break; //implement an undo function
			}
		});

		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
		stage.show();
	}

	public static void main(String[] args){ Application.launch(args); }
}
