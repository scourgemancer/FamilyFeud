import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Represents the questions in the game and holds their answers
 */
public class Questions{
	public Questions(String filename){

	}

	public Questions(){
		try{
			File questionFile = new File("questions.txt");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( questionFile );
			doc.getDocumentElement().normalize();
			NodeList questionList = doc.getElementsByTagName("question");
			//todo - add the questions to a list and go over XML parsing in Java
		}catch(Exception e){ e.printStackTrace(); }
	}
}
