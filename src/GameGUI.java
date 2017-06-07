import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI extends Application{
	Polls polls = new Polls("questions.txt");

	private void playAudio(String filename){
		//todo - make this ocur in a separate thread
		//todo - store this in a variable and kill a running one first
		//todo - make a function to kill a currently running audio file
		Media hit = new Media("src\\resources\\" + filename);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}

	public void start(Stage stage){

	}

	public static void main(String[] args){
	}
}
