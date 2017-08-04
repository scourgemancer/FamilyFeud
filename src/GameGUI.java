import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI extends Application{
	private Polls polls; //holds all of the questions, which each hold their own answers
    int currentQuestion = 0;
	private MediaPlayer audio; //is what plays the audio
    ArrayList<AnswerTile> answerTiles; //holds all of the ties in the center, ordered by rank

    Caretaker caretaker; //what saves the game and provides the undo and redo features

	int leftTeam, rightTeam = 0; //used for keeping track of team scores
	Text leftPoints, currentPointsText, rightPoints;
	private int selectedTeam; //used to signify if the left(-1) or right(1) team is selected, or neither(0)
	int multiplier = 1;
	int currentPoints;
	int numWrong = 0;
	private Text leftMultiplier;
	private Text rightMultiplier;
	private HBox strikes;
	private HBox answers;
	private StackPane window;

	Rectangle2D screen; //used for increased readability when referencing the screen size

    void playAudio(String filename){
        if(audio != null) audio.stop();
        Media audioFile = new Media(Paths.get("src/resources/" + filename).toUri().toString());
        audio = new MediaPlayer(audioFile);
        audio.play();
    }

	void styleText(Text text, double size){
		text.setFont(Font.font("Calibri", FontWeight.BLACK, size));
		text.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
	}

    private void setImageAsBackground( Region region, String image, double width, double height ){
        BackgroundImage bi = new BackgroundImage(
                new Image("resources\\" + image, width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        region.setBackground( new Background(bi) );
    }

    /** Highlights and selects the team given by -1(left), 0(deselect), and 1(right) */
    private void selectTeam(int team){
        if(team == -1){
            selectedTeam = -1;
            setImageAsBackground(window, "left team selected.png", screen.getWidth(), screen.getHeight());
        }else if(team == 0){
            selectedTeam = 0;
            setImageAsBackground(window, "background.png", screen.getWidth(), screen.getHeight());
        }else  if(team == 1){
            selectedTeam = 1;
            setImageAsBackground(window, "right team selected.png", screen.getWidth(), screen.getHeight());
        }
    }

	void setupQuestion(int i){
        numWrong = 0;
        currentPoints = 0;
        currentPointsText.setText("0");
        selectTeam(0);
        for(AnswerTile tile: answerTiles)
            tile.clear();

        if(currentQuestion < 0) currentQuestion = 0;
        if(currentQuestion > polls.questions.size()-1) currentQuestion = polls.questions.size()-1;
        Question q = polls.questions.get(i);

        for(int j=0; j<q.answers.size(); j++)
	        answerTiles.get(j).setAnswer(q.answers.get(j));
    }

    private void restart(){
	    setupQuestion(0);
	    leftTeam = 0;
	    leftPoints.setText("0");
	    rightTeam = 0;
	    rightPoints.setText("0");
	    setMultiplier(1);
    }

    private void wrongAnswer(int wrong){
	    //Make the strike images to appear
	    ArrayList<ImageView> strikemarks = new ArrayList<>();
	    for(int i=0; i<wrong; i++)
	        strikemarks.add( new ImageView(new Image("resources\\strike.png")) );


	    //Make the transitions for the strikes to appear and disappear with
        FadeTransition disappear = new FadeTransition(Duration.millis(50), strikes);
        disappear.setFromValue(1);
        disappear.setToValue(0);
        disappear.setCycleCount(1);
        disappear.setOnFinished(e -> strikes.getChildren().clear());

        FadeTransition appear = new FadeTransition(Duration.millis(50), strikes);
        appear.setFromValue(0);
        appear.setToValue(1);
        appear.setCycleCount(1);
        appear.setOnFinished(e ->
            Platform.runLater(() -> {
                try{
                    Thread.sleep(1000); //done in a separate thread to not halt user input
                }catch(Exception exc){exc.printStackTrace();}
                disappear.play();
            })
        );


        //Style the strikes and add them to the screen
	    for(ImageView img : strikemarks){
	        img.setPreserveRatio(true);
	        img.setFitWidth(screen.getWidth()/5);
            strikes.getChildren().addAll(img);
        }


        //Actually play the animation
        playAudio("strike.mp3");
        appear.play();
    }

    void scoreAnswer(int answerValue){
        currentPoints += answerValue * multiplier;
        currentPointsText.setText(Integer.toString(currentPoints));
    }

    private void scoreQuestion(){
        if(selectedTeam == -1){
	        leftTeam += currentPoints;
	        leftPoints.setText(Integer.toString(leftTeam));
            currentPoints = 0;
            currentPointsText.setText("0");
        }else if(selectedTeam == 1){
	        rightTeam += currentPoints;
	        rightPoints.setText(Integer.toString(rightTeam));
            currentPoints = 0;
            currentPointsText.setText("0");
        }
    }

    void setMultiplier(int newMultiplier){
        if(newMultiplier < 1) return;
        this.multiplier = newMultiplier;
        if(multiplier == 1){
            leftMultiplier.setText("");
            BorderPane.setMargin(answers, new Insets(screen.getHeight()/21, 0, 0, screen.getWidth()/9.01));
            rightMultiplier.setText("");

        }else{
            if(multiplier < 10){
                double size = screen.getHeight()/6.5;
                leftMultiplier.setFont(Font.font("Calibri", FontWeight.BLACK, size));
                leftMultiplier.setStyle("-fx-fill: #FFE900; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
                rightMultiplier.setFont(Font.font("Calibri", FontWeight.BLACK, size));
                rightMultiplier.setStyle("-fx-fill: #FFE900; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
                leftMultiplier.setText(Integer.toString(multiplier) + "x");
                rightMultiplier.setText(Integer.toString(multiplier) + "x");
                BorderPane.setMargin(answers, new Insets(screen.getHeight()/21, 0, 0, screen.getWidth()/37));
                BorderPane.setMargin(rightMultiplier, new Insets(0, screen.getWidth()/175, 0, 0));
            }else{
                double size = screen.getHeight()/10;
                leftMultiplier.setFont(Font.font("Calibri", FontWeight.BLACK, size));
                leftMultiplier.setStyle("-fx-fill: #FF9800; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
                rightMultiplier.setFont(Font.font("Calibri", FontWeight.BLACK, size));
                rightMultiplier.setStyle("-fx-fill: #FF9800; -fx-stroke: black; -fx-stroke-width: " + size/30 + "px");
                leftMultiplier.setText(Integer.toString(multiplier) + "x");
                rightMultiplier.setText(Integer.toString(multiplier) + "x");
                BorderPane.setMargin(answers, new Insets(screen.getHeight()/21, 0, 0, screen.getWidth()/36));
            }
        }
    }

    @Override
	public void init(){
		polls = new Polls("questions.txt");
		caretaker = new Caretaker(this);
	}

	@Override
	public void start(Stage stage){
	    //Setup the overall stage and the top layer for strike animations
		BorderPane game = new BorderPane();
        strikes = new HBox();
        strikes.setAlignment(Pos.CENTER);
        window = new StackPane(game, strikes);
		Scene scene = new Scene(window);


        //Setup the background of the program
        screen = Screen.getPrimary().getBounds();
        setImageAsBackground(window, "background.png", screen.getWidth(), screen.getHeight());


		//Areas for the team names, scores, and current question value
		BorderPane top = new BorderPane();

		VBox leftFamily = new VBox();
		leftFamily.setAlignment(Pos.CENTER);
		Text leftName = new Text("Hooffields");
        styleText(leftName, screen.getHeight()/10.55);
		leftPoints = new Text("0");
		styleText(leftPoints, screen.getHeight()/5.63);
		leftFamily.getChildren().addAll(leftName, leftPoints);
		top.setLeft(leftFamily);
		leftFamily.setSpacing(screen.getHeight()/100);
		BorderPane.setMargin(leftFamily, new Insets(screen.getHeight()/100, 0, 0, screen.getWidth()/28));

		currentPointsText = new Text("0");
		styleText(currentPointsText, screen.getHeight()/4.69);
		top.setCenter(currentPointsText);
		BorderPane.setMargin(currentPointsText, new Insets(screen.getHeight()/15, screen.getWidth()/30, 0, 0));

		VBox rightFamily = new VBox();
		rightFamily.setAlignment(Pos.CENTER);
		Text rightName = new Text("McColts");
        styleText(rightName, screen.getHeight()/10.55);
		rightPoints = new Text("0");
		styleText(rightPoints, screen.getHeight()/5.63);
		rightFamily.getChildren().addAll(rightName, rightPoints);
		top.setRight(rightFamily);
		rightFamily.setSpacing(screen.getHeight()/100);
		BorderPane.setMargin(rightFamily, new Insets(screen.getHeight()/100, screen.getWidth()/17, 0, 0));

		game.setTop(top);


		//The multiplier symbols
		leftMultiplier = new Text("");
		game.setLeft(leftMultiplier);
		BorderPane.setAlignment(leftMultiplier, Pos.CENTER_LEFT);

		rightMultiplier = new Text("");
		game.setRight(rightMultiplier);
		BorderPane.setAlignment(rightMultiplier, Pos.CENTER_RIGHT);


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

        answers = new HBox(leftAnswers, rightAnswers);
        answers.setSpacing(screen.getWidth()/150);
		game.setCenter(answers);
		BorderPane.setMargin(answers, new Insets(screen.getHeight()/21, 0, 0, screen.getWidth()/9.01));

		setupQuestion(0);


		//Handles user input with the program
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch(key.getCode().getName()){
				case "1": answerTiles.get(0).reveal(true); break;
				case "2": answerTiles.get(1).reveal(true); break;
				case "3": answerTiles.get(2).reveal(true); break;
				case "4": answerTiles.get(3).reveal(true); break;
				case "5": answerTiles.get(4).reveal(true); break;
				case "6": answerTiles.get(5).reveal(true); break;
				case "7": answerTiles.get(6).reveal(true); break;
				case "8": answerTiles.get(7).reveal(true); break;
				case "9": answerTiles.get(8).reveal(true); break;
				case "0": answerTiles.get(9).reveal(true); break;
/** restart */  case "R": caretaker.save(); restart(); break;
/** back */     case "B": caretaker.save(); setupQuestion(--currentQuestion); break;
/** next */     case "N": caretaker.save(); setupQuestion(++currentQuestion); break;
/** theme */    case "T": playAudio("theme.mp3"); break;
/** strike */	case "X": caretaker.save(); wrongAnswer(++numWrong); break;
/** stop */		case "S": if(audio != null) audio.stop(); break;
				case "Left": selectTeam(-1); break;
				case "Right": selectTeam(1); break;
				case "Up": caretaker.save(); setMultiplier(multiplier+1); break;
				case "Down": caretaker.save(); setMultiplier(multiplier-1); break;
/** score */    case "Space": caretaker.save(); scoreQuestion(); break;
/** undo */     case "Backspace": caretaker.undo(); break;
/** redo */ 	case "Enter": caretaker.redo(); break;
			}
		});


		stage.setScene(scene);
		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); //removes the esc hint and keeps it fullscreen
        stage.getIcons().add(new Image("resources\\icon.png"));
		stage.show();
	}

	public static void main(String[] args){ Application.launch(args); }
}
