import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

        // Decreases the size of the text until it fits in it's section of the box
        while(answerText.getBoundsInLocal().getWidth() > ((gui.screen.getWidth()/2.6225)*(11.0/14.0))){
            size += 0.001; //increases because it's used to divide another number
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


        // Create the 3D cuboid for the tile
        TriangleMesh cuboid = new TriangleMesh();   //A simple 3D rectangle
        float fheight = (float) height;
        float fwidth  = (float) width;
        float fdepth  = (float) depth;
        cuboid.getPoints().addAll(          //All points are referenced from the top, left, center
                0f, 0f, fdepth/2,       // Top,    Left,  Front
                -fheight, 0f, fdepth/2,          // Bottom, Left,  Front
                0f, fwidth, fdepth/2,            // Top,    Right, Front
                -fheight, fwidth, fdepth/2,      // Bottom, Right, Front
                0f, 0f, -fdepth/2,               // Top,    Left,  Back
                -fheight, 0f, -fdepth/2,         // Bottom, Left,  Back
                0f, fwidth, -fdepth/2,           // Top,    Right, Back
                -fheight, fwidth, -fdepth/2      // Bottom, Right, Back
        );
        cuboid.getTexCoords().addAll(0,0); //todo - replace with percentages to 1.0 from top left corner
        cuboid.getFaces().addAll(//The faces are listed as they move down, back, and counterclockwise
                0,0, 2,0, 4,0,     // Top Front
                2,0, 4,0, 6,0,              // Top Back
                0,0, 1,0, 2,0,              // Front Top
                1,0, 2,0, 3,0,              // Front Bottom
                2,0, 3,0, 6,0,              // Right Top
                3,0, 6,0, 7,0,              // Right Bottom
                4,0, 6,0, 7,0,              // Back Top
                4,0, 5,0, 7,0,              // Back Bottom
                0,0, 1,0, 4,0,              // Left Top
                1,0, 4,0, 5,0,              // Left Bottom
                1,0, 3,0, 5,0,              // Bottom Front
                3,0, 5,0, 7,0               // Bottom Back
        );
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("resources\\AnswerTile Texture.png"));
        MeshView tile3D = new MeshView(cuboid);
        tile3D.setDrawMode(DrawMode.FILL);
        tile3D.setMaterial(material);
        this.getChildren().clear();
        this.setCenter(tile3D);


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
