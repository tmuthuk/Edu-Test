package  dao;

import java.util.*;

import entities.*;	

public interface ProfessorDAO {
    public HashMap<Integer,String> selectTaught(long prof_id);
    public HashMap<Integer, String> fetchTopics(String courseName);
    public boolean insertExercise(Exercise ex);
    public HashMap<Integer, Exercise> getHomeworks(String courseToken);
    public HashMap<Integer, Questions> fetchQuestionsList(Exercise ex, String token);
    public HashMap<Integer, ArrayList<Parameters>> fetchParams(Questions qs);
    public boolean addQuestions(Exercise ex,ArrayList qsList);
    public HashMap<Integer,Questions> fetchQuestions(Exercise ex);
    public boolean removeQuestions(Exercise ex,ArrayList<Integer> qsList);
    public void updateNoOfQuestionsAdd(Exercise ex,int noOfQuestions);
    public void updateNoOfQuestionsRemove(Exercise ex,int noOfQuestions);
    public boolean updateEndDate(Exercise ex);
    public HashMap<Integer,String> fetchNotifications(long professorID);
    public ArrayList<Answers >fetchAnswers(String query);
    public boolean updateNoOfQuestions(Exercise ex);
}

