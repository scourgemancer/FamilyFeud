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
        this.gui = gui;
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

    void reinstate(){
        if(gui.currentQuestion != questionIndex) gui.setupQuestion(questionIndex);
        for(int i=0; i < isHidden.length; i++){
            if(isHidden[i]){
                if(!gui.answerTiles.get(i).hidden) gui.answerTiles.get(i).hide();
            }else{
                if(gui.answerTiles.get(i).hidden) gui.answerTiles.get(i).reveal(false);
            }
        }
        if(gui.multiplier != savedMultiplier) gui.setMultiplier(savedMultiplier);
        if(gui.numWrong != savedNumStrikes) gui.numWrong = savedNumStrikes;
        if(gui.leftTeam != leftTeamPoints){
            gui.leftTeam = leftTeamPoints;
            gui.leftPoints.setText(Integer.toString(leftTeamPoints));
        }
        if(gui.rightTeam != rightTeamPoints){
            gui.rightTeam = rightTeamPoints;
            gui.rightPoints.setText(Integer.toString(rightTeamPoints));
        }
        if(gui.currentPoints != unallocatedPoints){
            gui.currentPoints = unallocatedPoints;
            gui.currentPointsText.setText(Integer.toString(unallocatedPoints));
        }
    }
}
