import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents each individual Question and contains the Question and each of its answers, in order
 */
public class Question{
	public String question;

	public ArrayList<Answer> answers;

	public Question(String q){
		question = q;
		answers = new ArrayList<>();
	}

	public Question(String q, Answer[] a){
		question = q;
		answers = new ArrayList<>();
		answers = new ArrayList<>( Arrays.asList(a) );
	}

	public Question(String q, ArrayList<Answer> a){
		question = q;
		answers = a;
	}

	public Question(String q, String[] ans, int[] scores){
		question = q;
		answers = new ArrayList<>();
		addAnswer(ans, scores);
	}

	public Question(String q, ArrayList<String> ans, ArrayList<Integer> scores){
		question = q;
		answers = new ArrayList<>();
		addAnswer(ans, scores);
	}

	public void addAnswer(Answer answer){
		answers.add(answer);
	}

	public void addAnswer(String answer, int score){
		Answer a = new Answer(answer, score);
		int idx = answers.size()-1;
		while(idx > 0){
			if(answers.get(idx).points > score) break;
			idx--;
		}
		answers.add(++idx, a);
	}

	public void addAnswer(String[] answers, int[] scores) throws AssertionError{
		if(answers.length != scores.length) throw new AssertionError("Answers and scores don't match");
		for(int i=0; i < answers.length; i++)
			addAnswer(answers[i], scores[i]);
	}

	public void addAnswer(ArrayList<String> answers, ArrayList<Integer> scores) throws AssertionError{
		if(answers.size() != scores.size()) throw new AssertionError("Answers and scores don't match");
		for(int i=0; i < answers.size(); i++)
			addAnswer(answers.get(i), scores.get(i));
	}
}
