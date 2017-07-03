import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
	int leftTeam, rightTeam = 0; //used for keeping track of team scores
	boolean onLeft; //used to signify if the left team is highlighted or not
	int multiplier = 1;

	//loading in a custom font for improved visibility
	Font customFont = Font.loadFont("resources\\carbono_pw.ttf", 20);

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

		void reveal(){  }//todo - animate the question revealing itself (flip while rotating in place && play sound)
	}

	@Override
	public void init(){ polls = new Polls("questions.txt"); }

	@Override
	public void start(Stage stage){
		BorderPane window = new BorderPane();
		scene = new Scene(window);

		//Areas for the team names, scores, and current question value
		BorderPane top = new BorderPane();
		VBox leftFamily = new VBox();
		Text leftName = new Text("Hooffields");
		Text leftPoints = new Text("0");
		leftName.setFont(customFont);
		leftPoints.setFont(customFont);
		leftFamily.getChildren().addAll(leftName, leftPoints);
		top.setLeft(leftFamily);

		Text currentPoints = new Text("0");
		currentPoints.setFont(customFont);
		top.setCenter(currentPoints);

		VBox rightFamily = new VBox();
		Text rightName = new Text("McColts");
		Text rightPoints = new Text("0");
		rightName.setFont(customFont);
		rightPoints.setFont(customFont);
		rightFamily.getChildren().addAll(rightName, rightPoints);
		top.setRight(rightFamily);

		window.setTop(top);

		//The area containing the actual answers
		answerTiles = new ArrayList<>();
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
				case "Left": onLeft = true; break;
				case "Right": onLeft = false; break;
				case "Space": break;
				case "Up": multiplier++; break;
				case "Down": if(multiplier > 1) multiplier--; break;
				case "Enter": break; //this goes forward if you've gone backwards w/o editing, else goes to fast money
				case "Backspace": break; //implement an undo function
			}
		});

		//Setup the background of the program
		Rectangle2D screen =Screen.getPrimary().getBounds();
		BackgroundImage bi = new BackgroundImage(
				new Image("resources\\background.png", screen.getWidth(), screen.getHeight(), false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		window.setBackground( new Background(bi) );

		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
		stage.show();
	}

	public static void main(String[] args){ Application.launch(args); }
}
