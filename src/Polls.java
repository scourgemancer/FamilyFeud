import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Makes all of the Question objects and organizes answers into each Question
 */
public class Polls{
	public ArrayList<Question> polls;	//todo - populate with all of the questions from the XML document

	public Polls(String filename){

	}

	public Polls(){
		try{
			File questionFile = new File("questions.txt");
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( questionFile );
			doc.getDocumentElement().normalize();
			NodeList questions = doc.getElementsByTagName("question");
			for(int i=0; i < questions.getLength(); i++){
				Node n = questions.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE){
					Element question = (Element) n;
					String questionText = question.getAttribute("text");
					NodeList answerNodes = question.getElementsByTagName("answer");
				}
			}
			//todo - add the questions to a list and go over XML parsing in Java
		}catch(Exception e){ e.printStackTrace(); }
	}
}
