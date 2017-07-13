import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

	Rectangle2D screen; //used for increased readability when referencing the screen size

	private void playAudio(String filename){
		stopAudio();
		audio = new Thread(() -> {
			Media hit = new Media("src\\resources\\" + filename);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		});
	}

	private void stopAudio(){ if(audio != null) if(audio.isAlive()) audio.interrupt(); }

	private void styleText(Text text, double size){
		text.setFont(Font.font("Calibri", FontWeight.BLACK, size));
		text.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
	}

	private class AnswerTile extends Rectangle{
		int rank;
		Answer answer;

		AnswerTile(int i){
			rank  = i;
			answerTiles.add(this);
		}

		void setAnswer(Answer a){ answer = a; }//todo - add or remove a number if relevant rank

		void reveal(){  }//todo - animate the question revealing itself (flip while rotating in place && play sound)
	}

	private void setupQuestion(Question q){
	    //todo
    }

	@Override
	public void init(){
		polls = new Polls("questions.txt");
		screen = Screen.getPrimary().getVisualBounds();
	}

	@Override
	public void start(Stage stage){
		BorderPane window = new BorderPane();
		scene = new Scene(window);

		//Areas for the team names, scores, and current question value
		BorderPane top = new BorderPane();

		VBox leftFamily = new VBox();
		leftFamily.setAlignment(Pos.CENTER);
		Text leftName = new Text("Hooffields");
		Text leftPoints = new Text("6969");
		styleText(leftName, screen.getHeight()/10.55);
		styleText(leftPoints, screen.getHeight()/5.63);
		leftFamily.getChildren().addAll(leftName, leftPoints);
		top.setLeft(leftFamily);
		leftFamily.setSpacing(screen.getHeight()/100);
		BorderPane.setMargin(leftFamily, new Insets(screen.getHeight()/100, 0, 0, screen.getWidth()/28));

		Text currentPoints = new Text("6969");
		styleText(currentPoints, screen.getHeight()/4.69);
		top.setCenter(currentPoints);
		BorderPane.setMargin(currentPoints, new Insets(screen.getHeight()/15, screen.getWidth()/80, 0, 0));

		VBox rightFamily = new VBox();
		rightFamily.setAlignment(Pos.CENTER);
		Text rightName = new Text("McColts");
		Text rightPoints = new Text("6969");
		styleText(rightName, screen.getHeight()/10.55);
		styleText(rightPoints, screen.getHeight()/5.63);
		rightFamily.getChildren().addAll(rightName, rightPoints);
		top.setRight(rightFamily);
		rightFamily.setSpacing(screen.getHeight()/100);
		BorderPane.setMargin(rightFamily, new Insets(screen.getHeight()/100, screen.getWidth()/18, 0, 0));

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
                case "b": break; //todo - back a question
                case "n": break; //todo - next question
				case "t": playAudio("theme.mp3"); break; //theme song
				case "x": playAudio("strike.mp3"); break; //strike sound
				case "s": stopAudio(); break; //stops all of the audio
				case "Left": onLeft = true; break;
				case "Right": onLeft = false; break;
				case "Up": multiplier++; break;
				case "Down": if(multiplier > 1) multiplier--; break;
                case "Space": break; //todo - increase current team's points
                case "Backspace": break; //todo - undo
				case "Enter": break; //todo - redoes if an action was just undone, else goes to fast money
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
