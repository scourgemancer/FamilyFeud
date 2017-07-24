import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Represents one of the tiles containing an answer and it's value for the GUI's
 */
public class AnswerTile extends BorderPane{
    int rank;
    Answer answer;
    int value;

    private GameGUI gui;

    private Text answerText;
    private Text valueText;
    private Text rankText;
    private HBox tile;

    boolean hidden;
    boolean isAnAnswer;

    double width;
    double height;
    double depth;

    private RotateTransition flip;

    AnswerTile(GameGUI gui, int i){
        super();
        rank  = i;
        this.gui = gui;
        gui.answerTiles.add(this);

        rankText = new Text(Integer.toString(rank));
        gui.styleText(rankText, gui.screen.getHeight()/11);

        width = gui.screen.getWidth()/2.6225;
        height = gui.screen.getHeight()/8.5714;
        depth = height*0.7;
        setPrefSize(width, height);

        clear();

        ImageView front = new ImageView("resources\\numbered answer tile.png");
        front.setFitWidth(width);
        front.setFitHeight(height);
        front.setTranslateZ(depth/2);
        front.setRotationAxis(Rotate.X_AXIS);

        ImageView back = new ImageView("resources\\revealed answer tile.png");
        back.setFitWidth(width);
        back.setFitHeight(height);
        back.setTranslateZ(-depth/2);
        back.setRotationAxis(Rotate.X_AXIS);
        back.setRotate(180);

        Rectangle top = new Rectangle(width, depth, Paint.valueOf("blue"));
        top.setRotationAxis(Rotate.X_AXIS);
        top.setRotate(90);
        top.setTranslateY(height/2);

        this.getChildren().addAll( new Group(back, top, front) );

        flip = new RotateTransition(Duration.millis(3000), this);
        flip.setAxis(Rotate.X_AXIS);
        flip.setByAngle(-180);
        flip.setCycleCount(1);
    }

    void setAnswer(Answer a){
        answer = a;
        value = answer.points;
        isAnAnswer = true;
        hidden = true;

        //Set up the contents of the tile
        answerText = new Text(answer.answer);
        gui.styleText(answerText, gui.screen.getHeight()/12);
        answerText.setWrappingWidth(gui.screen.getWidth()/4);
        answerText.setLineSpacing(-30);
        value = answer.points;
        valueText = new Text(Integer.toString(value));
        gui.styleText(valueText, gui.screen.getHeight()/9);
        tile = new HBox(answerText, valueText);
        tile.setOpacity(0);

        this.setCenter(rankText);
    }

    void reveal(){
        if(hidden && isAnAnswer){
            gui.playAudio("reveal.mp3");
            hidden = false;

            //The fade-in animation for the contents
            this.setCenter(tile);
            FadeTransition appear = new FadeTransition(Duration.millis(100), tile);
            appear.setFromValue(0);
            appear.setToValue(1);
            appear.setCycleCount(1);

            flip.play();
            appear.play();
            gui.scoreAnswer(value);
        }
    }

    void clear(){
        hidden = true;
        isAnAnswer = false;
        this.getChildren().remove(tile);
        //gui.setImageAsBackground(this, "blank answer tile.png", width, height);
    }
}
