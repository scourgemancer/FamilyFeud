/**
 * A saved instance of the game that holds all of the relevant data and knows how to reset it all
 */
public class Memento{
    int questionIndex;
    int savedNumStrikes;
    int savedMultiplier;
    int leftTeamPoints;
    int rightTeamPoints;
    int unallocatedPoints;
    boolean[] isHidden;
    GameGUI gui;

    Memento(GameGUI gui){
        questionIndex = gui.currentQuestion;
        savedNumStrikes = gui.numWrong;
        savedMultiplier = gui.multiplier;
        leftTeamPoints = gui.leftTeam;
        rightTeamPoints = gui.rightTeam;
        unallocatedPoints = gui.currentPoints;
        isHidden = new boolean[gui.answerTiles.size()];
        for(int i=0; i < isHidden.length; i++)
            isHidden[i] = gui.answerTiles.get(i).hidden;
    }

    public void reinstate(){
        gui.setupQuestion(questionIndex);
        gui.setMultiplier(savedMultiplier);
        gui.leftTeam = leftTeamPoints;
        gui.leftPoints.setText(Integer.toString(leftTeamPoints));
        gui.rightTeam = rightTeamPoints;
        gui.rightPoints.setText(Integer.toString(rightTeamPoints));
        gui.currentPoints = unallocatedPoints;
        gui.currentPointsText.setText(Integer.toString(unallocatedPoints));
        gui.numWrong = savedNumStrikes;
        for(int i=0; i < isHidden.length; i++){
            if(isHidden[i]){
                //todo - make sure that it's hidden
            }else{
                //todo - make sure that it's revealed
            }
        }
    }
}
