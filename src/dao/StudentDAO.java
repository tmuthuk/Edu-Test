package  dao;

import  entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface StudentDAO {
public HashMap<Integer, String> selectEnrolled(long student_id);
public int addCourseToEnroll(long student_id,String course_token);
public HashMap<Exercise, ArrayList<Attempts>> getHomeworksAttemptsInfo(long student_id,String token);
public HashMap<Exercise,Attempts> getCurrentHomeworks(long student_id, String token);
public int createNewAttempt(Exercise e,long studentID);
public ArrayList<Questions> fetchQuestions(Exercise e);
public ArrayList<Integer> fetchGroupId(Questions qs);
public ArrayList<Parameters>fetchParameters(Questions qs);
public ArrayList<Answers> fetchAnswerList(Questions qs);
public boolean saveScore(Exercise e,float totalScore,int attId,long studentID);
public void updateAttempt(int attid, ArrayList<Questions> questions);
public HashMap<Exercise, ArrayList<Attempts>> viewAttemptsPastDate(long sid,String token);
public HashMap<Exercise, ArrayList<Attempts>> viewAttemptsWithinDate(long sid,String token);
public HashMap<Integer,String> fetchNotifications();
public ArrayList<Questions> fetchAttempt(Exercise e, Attempts a);
public float fetchScore(Attempts a);
public HashMap<Exercise,Integer> getCurrentHomeworks1(long studentID, String token);
}
