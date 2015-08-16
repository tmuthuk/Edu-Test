/**
 * 
 */
package business;

import java.util.ArrayList;
import java.util.HashMap;

import  dao.TaDAO;
import  dao.impl.TaDAOImpl;
import  entities.Exercise;
import  entities.Questions;


public class TaBusiness {
	/*
	 * File includes business logic for TA. 
	 * */
	public TaDAO tdata;
	
	public TaBusiness(){
		tdata = new TaDAOImpl();
	}
	
	public ArrayList selectTaCourses(long userID) {
		// TODO Auto-generated method stub
		//System.out.println("Calling naach meri jaan2");
		return tdata.generateTaCourses(userID);
	}

	public HashMap getAllHomeworks(String courseToken) {
		// TODO Auto-generated method stub
		return tdata.generateAllHomeworks(courseToken);
		
	}

	public Exercise getHomeworkDetails(int courseTokenSelected) {
		// TODO Auto-generated method stub
		return tdata.generateHomeworkDetails(courseTokenSelected);
	}

	public ArrayList getQuestionsOfHomework(int homeworkID) {
		// TODO Auto-generated method stub
		return tdata.generateQuestionsListOfHomework(homeworkID);
	}

	public Questions getQuestion(int value) {
		// TODO Auto-generated method stub
		return tdata.generateQuestions(value);
	}

	public ArrayList executeFirstQuery(long userID, String courseToken,
			int homeworkID) {
		// TODO Auto-generated method stub
		return tdata.generateFirstQuery(userID,courseToken,homeworkID);
	}

	public ArrayList executeSecondQuery(long userID, String courseToken,
			int homeworkID) {
		// TODO Auto-generated method stub
		return tdata.generateSecondQuery(userID,courseToken,homeworkID);
	}

	public ArrayList executeThirdQuery(long userID, String courseToken) {
		// TODO Auto-generated method stub
		return tdata.generateThirdQuery(userID,courseToken);
	}



	

	

	
	
	
	
}
