package business;

import java.util.ArrayList;
import entities.Answers;
import java.util.HashMap;	

 

import dao.ProfessorDAO;
import dao.impl.ProfessorDAOImpl;
import entities.Exercise;
import entities.Parameters;
import entities.Questions;
public class ProfessorBusiness {
	
	private ProfessorDAO pdao;
	
	public ProfessorBusiness(){
		pdao =new ProfessorDAOImpl();		
	}
	public HashMap<Integer,String> selectTaughtCourses(long prof_id)
	{		
		return pdao.selectTaught(prof_id);
	}


	public boolean addHomework(Exercise ex){
		boolean result = false;		
		result = pdao.insertExercise(ex);		
		return result;
	}
			
	
	public HashMap<Integer,String> getTopicsList(String courseChoice){		
		HashMap<Integer,String> topicsList = pdao.fetchTopics(courseChoice);
		return topicsList;
	}
	
	public HashMap<Integer, Exercise> getHomeworks(String courseToken){
		
		HashMap<Integer, Exercise> exlist = new HashMap<Integer, Exercise>();
		exlist = pdao.getHomeworks(courseToken);
		return exlist;
	}
	
	public HashMap<Integer, Questions> fetchQuestionsList(Exercise ex, String token){
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		qsList = pdao.fetchQuestionsList(ex,token);
		return qsList;
	}
	
	public HashMap<Integer, ArrayList<Parameters>> fetchParams(Questions qs){
		HashMap<Integer,ArrayList<Parameters>> paramsList = new HashMap<Integer, ArrayList<Parameters>>();
		paramsList = pdao.fetchParams(qs);		
		return paramsList;
	}
	
	public boolean addQuestions(Exercise ex,ArrayList<Integer> qsList){
		boolean status = pdao.addQuestions(ex,qsList);
		return status;
	}
	
	public HashMap<Integer,Questions> fetchQuestions(Exercise ex){
		HashMap<Integer,Questions> qsList = new HashMap<Integer,Questions>();
		qsList = pdao.fetchQuestions(ex);
		return qsList;
	}
	
	public boolean removeQuestions(Exercise ex,ArrayList<Integer> qsList){
		boolean status = pdao.removeQuestions(ex,qsList);
		return status;
	}
	
	public boolean updateEndDate(Exercise ex){
		boolean status=false;
		status = pdao.updateEndDate(ex);		
		return status;
	}

	public HashMap<Integer,String> fetchNotifications(long professorID){
		HashMap<Integer,String> notifications = new HashMap<Integer,String>();
		notifications = pdao.fetchNotifications(professorID);
		return notifications;
	}
	
	public ArrayList<Answers> fetchAnswers(String query){
		ArrayList<Answers> ansList = pdao.fetchAnswers(query);
		return ansList;
	}
	
	public boolean updateNoOfQuestions(Exercise ex){
		boolean status = pdao.updateNoOfQuestions(ex);
		return status;
	}
}
