import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Makes all of the Question objects and organizes answers into each Question
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
