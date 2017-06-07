import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI{
	Polls polls = new Polls("questions.txt");

	private void playAudio(String filename){
		Media hit = new Media("src\\resources\\" + filename);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}

	public static void main(String[] args){
	}
}
