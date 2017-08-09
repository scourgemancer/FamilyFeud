import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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
    private TriangleMesh cuboid;
    private RotateTransition flip;

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
        cuboid = new TriangleMesh();   //A simple 3D rectangle
        float fheight = (float) height;
        float fwidth  = (float) width;
        float fdepth  = (float) depth;
        cuboid.getPoints().addAll(      //All points are referenced from the top, left, center
                0f, 0f, 0f,            // Top,    Left,  Front
                0f, fheight, 0f,              // Bottom, Left,  Front
                fwidth, 0f, 0f,                // Top,    Right, Front
                fwidth, fheight, 0f,          // Bottom, Right, Front
                0f, 0f, -fdepth,               // Top,    Left,  Back
                0f, fheight, -fdepth,         // Bottom, Left,  Back
                fwidth, 0f, -fdepth,           // Top,    Right, Back
                fwidth, fheight, -fdepth      // Bottom, Right, Back
        );
        cuboid.getTexCoords().addAll(       //0.0 to 1.0 percent from top left corner to the bottom right
                0, 0,
                1, 0,
                0, 0.25f,
                1, 0.25f,
                0, 0.375f,
                1, 0.375f,
                0, 0.5f,
                1, 0.5f,
                0, 0.75f,
                1, 0.75f,
                0.126f, 0.75f,
                0, 1,
                0.126f, 1
        );
        cuboid.getFaces().addAll(       //The faces are listed as they move down, back, and counterclockwise
                0,6, 2,7, 4,4,     // Top Front
                2,7, 6,5, 4,4,              // Top Back
                0,0, 1,2, 2,1,              // Front Top
                1,2, 3,3, 2,1,              // Front Bottom
                2,8, 3,11, 6,10,            // Right Top
                3,11, 7,12, 6,10,           // Right Bottom
                4,6, 7,9, 6,7,              // Back Top
                4,6, 5,8, 7,9,              // Back Bottom
                0,8, 5,12, 4,10,            // Left Top
                0,8, 1,11, 5,12,            // Left Bottom
                1,4, 3,5, 5,2,              // Bottom Front
                5,2, 3,5, 7,3               // Bottom Back
        );
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("resources\\texture.png"));
        MeshView tile3D = new MeshView(cuboid);
        tile3D.setMaterial(material);
        tile3D.setCullFace(CullFace.NONE);


        flip = new RotateTransition(Duration.millis(500), this);
        flip.setAxis(Rotate.X_AXIS);
        flip.setByAngle(180);


        this.getChildren().clear();
        this.getChildren().add(tile3D);
        this.setCenter(rankText);
    }

    void reveal(boolean isVisible){
        if(hidden && isAnAnswer){
            if(isVisible){
                gui.caretaker.save();
                gui.playAudio("reveal.mp3");
                flip.play();
            }
            hidden = false;
            this.setCenter(tile);
            gui.scoreAnswer(value);
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
