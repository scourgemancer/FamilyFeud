import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Represents one of the tiles containing an answer and it's value for the GUI's
 */
public class AnswerTile extends AnchorPane{
    int rank;
    Answer answer;
    int value;

    Text answerText;
    Text valueText;

    AnswerTile(GameGUI gui, int i){
        super();
        rank  = i;
        gui.answerTiles.add(this);

        gui.setImageAsBackground(this, "revealed answer tile.png", gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
        if(rank > 3) gui.setImageAsBackground(this, "numbered answer tile.png", gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
        if(rank > 7) gui.setImageAsBackground(this, "blank answer tile.png", gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
        if(rank<4) {
            answerText = new Text("Tile's rank: " + rank);
            gui.styleText(answerText, gui.screen.getHeight() / 12);
            valueText = new Text("23");
            gui.styleText(valueText, gui.screen.getHeight() / 9);

            HBox tile = new HBox(answerText, valueText);
            this.getChildren().add(tile);

            setTopAnchor(tile, 0.0);
            setLeftAnchor(tile, 0.0);
            setRightAnchor(tile, 0.0);
            setBottomAnchor(tile, 0.0);
        }
        setPrefSize(gui.screen.getWidth()/2.96, gui.screen.getHeight()/8.3);
    }

    void setAnswer(Answer a){ answer = a; }//todo - add or remove a numbered back if relevant rank && update Labels

    void reveal(){  }//todo - animate the question revealing itself (flip while rotating in place && play sound)
}
