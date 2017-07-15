import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Represents one of the tiles containing an answer and it's value for the GUI's
 */
public class AnswerTile extends BorderPane{
    int rank;
    Answer answer;
    int value;

    GameGUI gui;

    Text answerText;
    Text valueText;
    HBox tile;
    Text rankText;

    boolean hidden;
    boolean isAnAnswer;

    AnswerTile(GameGUI gui, int i){
        super();
        rank  = i;
        this.gui = gui;
        gui.answerTiles.add(this);

        rankText = new Text(Integer.toString(rank));
        gui.styleText(rankText, 10.0);

        setPrefSize(gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);

        clear();
    }

    void setAnswer(Answer a){
        answer = a;
        value = answer.points;
        isAnAnswer = true;

        answerText = new Text(answer.answer);
        gui.styleText(answerText, gui.screen.getHeight()/12);
        valueText = new Text(Integer.toString(value));
        gui.styleText(valueText, gui.screen.getHeight()/9);
        HBox tile = new HBox(answerText, valueText);

        gui.setImageAsBackground(this, "numbered answer tile.png", gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
        this.setCenter(rankText);
    }

    void reveal(){
        if(hidden){
            gui.playAudio("reveal.mp3");
            gui.setImageAsBackground(this, "revealed answer tile.png", gui.screen.getWidth() / 2.96, gui.screen.getHeight() / 8.3);
            this.setCenter(tile);
        }
        //todo - animate the question revealing itself (flip while rotating in place && play sound)
    }

    void clear(){
        hidden = true;
        isAnAnswer = false;
        this.getChildren().clear();
        gui.setImageAsBackground(this, "blank answer tile.png", gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
    }
}
