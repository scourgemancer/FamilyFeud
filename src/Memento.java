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
        /**
         * Things to reinstate:
         * todo - Each tile being revealed/hidden
         * todo - The current question
         * todo - The number of strikes
         * todo - The multiplier
         * todo - Each team's points
         * todo - Unallocated points
         */
    }
}
