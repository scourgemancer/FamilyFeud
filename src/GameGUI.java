import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The graphical view for the game and starting point of the program
 */
public class GameGUI extends Application{
	private Polls polls;
	private Thread audio;

	private void playAudio(String filename){
		stopAudio();
		audio = new Thread(() -> {
			Media hit = new Media("src\\resources\\" + filename);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		});
	}

	private void stopAudio(){ if(audio != null) if(audio.isAlive()) audio.interrupt(); }

	@Override
	public void init(){
		polls = new Polls("questions.txt");
	}

	@Override
	public void start(Stage stage){

		stage.setTitle("Family Feud");
		stage.setFullScreen(true);
	}

	public static void main(String[] args){ Application.launch(args); }
}
