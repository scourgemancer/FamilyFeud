import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Represents one of the tiles containing an answer and it's value for the GUI's
 */
class AnswerTile extends BorderPane{
    private Answer answer;
    int rank;
    int value;

    private GameGUI gui;

    Text answerText;
    Text valueText;
    Text rankText;
    private BorderPane tile;

    boolean hidden;
    boolean isAnAnswer;

    private double width;
    private double height;
    private double depth;

    AnswerTile(GameGUI gui, int i){
        super();
        rank  = i;
        this.gui = gui;
        gui.answerTiles.add(this);

        rankText = new Text(Integer.toString(rank));
        gui.styleText(rankText, gui.screen.getHeight()/11);

        width = gui.screen.getWidth()/2.6225;
        height = gui.screen.getHeight()/8.5714;
        depth = height / 2;
        setPrefSize(width, height);

        clear();
    }

    void setAnswer(Answer a){
        answer = a;
        value = answer.points;
        isAnAnswer = true;
        hidden = true;

        answerText = new Text(answer.answer);

        double size = 12.0;
        gui.styleText(answerText, gui.screen.getHeight()/size);

        //Decreases the size of the text until it fits in it's section of the box
        while(answerText.getBoundsInLocal().getWidth() > ((gui.screen.getWidth()/2.6225)*(11.0/14.0))){
            size += 0.001; //increases because it's used to divide another number by
            gui.styleText(answerText, gui.screen.getHeight()/size);
        }

        value = answer.points;
        valueText = new Text(Integer.toString(value));
        gui.styleText(valueText, gui.screen.getHeight()/9);

        tile = new BorderPane();
        tile.setLeft(answerText);
        BorderPane.setAlignment(answerText, Pos.CENTER_LEFT);
        BorderPane.setMargin(answerText, new Insets(0, 0, 0, width/75));
        tile.setRight(valueText);
        BorderPane.setAlignment(valueText, Pos.CENTER_RIGHT);
        if(valueText.getText().length() < 2){
            BorderPane.setMargin(valueText, new Insets(0, width/19, 0, 0));
        }else{
            BorderPane.setMargin(valueText, new Insets(0, width/75, 0, 0));
        }

        this.getChildren().clear();
        ImageView front = new ImageView("resources\\numbered answer tile.png");
        front.setFitHeight(height);
        front.setFitWidth(width);
        this.getChildren().add(front);
        this.setCenter(rankText);
    }

    void reveal(boolean isVisible){
        if(hidden && isAnAnswer){
            if(isVisible) gui.caretaker.save();
            if(isVisible) gui.playAudio("reveal.mp3");
            this.getChildren().clear();
            ImageView front = new ImageView("resources\\revealed answer tile.png");
            front.setFitHeight(height);
            front.setFitWidth(width);
            this.getChildren().add(front);
            this.setCenter(tile);
            hidden = false;
            gui.scoreAnswer(value);
            if(isVisible){
                //todo - animate the question revealing itself (flip while rotating in place && play sound)
            }
        }
    }

    void hide(){
        this.getChildren().clear();
        ImageView front = new ImageView("resources\\numbered answer tile.png");
        front.setFitHeight(height);
        front.setFitWidth(width);
        this.getChildren().add(front);
        this.setCenter(rankText);
        hidden = true;
    }

    void clear(){
        hidden = true;
        isAnAnswer = false;
        this.getChildren().clear();
        ImageView front = new ImageView("resources\\blank answer tile.png");
        front.setFitHeight(height);
        front.setFitWidth(width);
        this.getChildren().add(front);
    }
}
