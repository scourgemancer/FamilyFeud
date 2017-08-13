import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Makes all of the Question objects and organizes answers into each Question
 */
public class Polls{
	public ArrayList<Question> questions = new ArrayList<>();

	public Polls(String filename){
		try{
			File questionFile = new File(Paths.get("src/" + filename).toUri());
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( questionFile );
			doc.getDocumentElement().normalize();
			NodeList questions = doc.getElementsByTagName("question");
			for(int i=0; i < questions.getLength(); i++){
				Node n = questions.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE){
					Element questionNode = (Element) n;
					String questionText = questionNode.getAttribute("text");
					Question question = new Question(questionText);
					NodeList answerNodes = questionNode.getElementsByTagName("answer");
					for(int j=0; j < answerNodes.getLength(); j++){
						Node a = answerNodes.item(j);
						if(a.getNodeType() == Node.ELEMENT_NODE){
							Element answer = (Element) a;
							question.addAnswer( answer.getAttribute("text"), Integer.parseInt( answer.getAttribute("points") ) );
						}
					}
					this.questions.add( question );
				}
			}
		}catch(Exception e){ e.printStackTrace(); }
	}

	public Polls(){ this("questions.txt"); }
}
