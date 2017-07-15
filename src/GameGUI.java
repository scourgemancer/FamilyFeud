import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
	public ArrayList<AnswerTile> answerTiles;

	String leftName = "Apple";
	String rightName = "Pie";
	int leftTeam, rightTeam = 0; //used for keeping track of team scores
	boolean onLeft; //used to signify if the left team is highlighted or not
	int currentQuestion = -1;
	int multiplier = 1;
	int currentPoints;

	Rectangle2D screen; //used for increased readability when referencing the screen size

	public void playAudio(String filename){
		stopAudio();
		audio = new Thread(() -> {
			Media hit = new Media("src\\resources\\" + filename);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		});
	}

	public void stopAudio(){ if(audio != null) if(audio.isAlive()) audio.interrupt(); }

	public void styleText(Text text, double size){
		text.setFont(Font.font("Calibri", FontWeight.BLACK, size));
		text.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
	}

    public void setImageAsBackground( Region region, String image, double width, double height ){
        BackgroundImage bi = new BackgroundImage(
                new Image("resources\\" + image, width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        region.setBackground( new Background(bi) );
    }

	private void setupQuestion(int i){
        currentPoints = 0;
        //todo - unhighlight the selected team
        Question q = new Question("Will be replaced by the actual question");
        if(i == 0){ //start from the beginning
            currentQuestion = 0;
            q = polls.questions.get(0);
        }else if(i == -1){ //go backwards one question
            if(currentQuestion > 0){
                q = polls.questions.get(--currentQuestion);
            }
        }else if (i == 1){ //go forwards to the next question
            if(currentQuestion < polls.questions.size()-1){
                q = polls.questions.get(++currentQuestion);
            }
        }
	    for(int j=0; j<q.answers.size(); j++){
	        answerTiles.get(j).setAnswer(q.answers.get(j));
        }
    }

    public void scoreAnswer(int answerValue){
	    //todo - animate the currentPoint value increasing
        currentPoints += answerValue * multiplier;
    }

    private void scoreQuestion(){
	    if(onLeft){
	        leftTeam += currentPoints;
        }else{
	        rightTeam += currentPoints;
        }
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


        //Setup the background of the program
        Rectangle2D screen =Screen.getPrimary().getBounds();
        setImageAsBackground(window, "background.png", screen.getWidth(), screen.getHeight());


		//Areas for the team names, scores, and current question value
		BorderPane top = new BorderPane();

		VBox leftFamily = new VBox();
		leftFamily.setAlignment(Pos.CENTER);
		Text leftName = new Text("Hooffields");
        styleText(leftName, screen.getHeight()/10.55);
		Text leftPoints = new Text("0");
		styleText(leftPoints, screen.getHeight()/5.63);
		leftFamily.getChildren().addAll(leftName, leftPoints);
		top.setLeft(leftFamily);
		leftFamily.setSpacing(screen.getHeight()/100);
		BorderPane.setMargin(leftFamily, new Insets(screen.getHeight()/100, 0, 0, screen.getWidth()/28));

		Text currentPointsText = new Text("0");
		styleText(currentPointsText, screen.getHeight()/4.69);
		top.setCenter(currentPointsText);
		BorderPane.setMargin(currentPointsText, new Insets(screen.getHeight()/15, screen.getWidth()/80, 0, 0));

		VBox rightFamily = new VBox();
		rightFamily.setAlignment(Pos.CENTER);
		Text rightName = new Text("McColts");
        styleText(rightName, screen.getHeight()/10.55);
		Text rightPoints = new Text("0");
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
            leftAnswers.getChildren().add(new AnswerTile(this, i));
		leftAnswers.setSpacing(screen.getHeight()/106.6);

		VBox rightAnswers = new VBox();
        for(int i=6; i<11; i++)
            rightAnswers.getChildren().add(new AnswerTile(this, i));
        rightAnswers.setSpacing(screen.getHeight()/106.6);

        HBox answers = new HBox(leftAnswers, rightAnswers);
        answers.setSpacing(screen.getWidth()/150);
		window.setCenter(answers);
		BorderPane.setMargin(answers, new Insets(screen.getHeight()/21, 0, 0, screen.getWidth()/6.5));

		setupQuestion(0);


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
/** restart */  case "r": setupQuestion(0); break;
/** back */     case "b": setupQuestion(-1); break;
/** next */     case "n": setupQuestion(1); break;
/** theme */    case "t": playAudio("theme.mp3"); break;
/** strike */	case "x": playAudio("strike.mp3"); break;
/** stop */		case "s": stopAudio(); break; //stops all of the audio
				case "Left": onLeft = true; break;
				case "Right": onLeft = false; break;
				case "Up": multiplier++; break;
				case "Down": if(multiplier > 1) multiplier--; break;
                case "Space": scoreQuestion();
                case "Backspace": break; //todo - undo
				case "Enter": break; //todo - redoes if an action was just undone, else goes to fast money
			}
		});


		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); //removes the esc hint and keeps it fullscreen
        //todo - take a screenshot of completed look and use it as an icon
		stage.show();
	}

	public static void main(String[] args){ Application.launch(args); }
}
