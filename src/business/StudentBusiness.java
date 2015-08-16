package business;

import dao.StudentDAO;
import dao.impl.StudentDAOImpl;
import entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class StudentBusiness {

	public HashMap<Integer, String> selectEnrolledCourses(long student_id) {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.selectEnrolled(student_id);
	}

	public int addToEnroll(long student_id, String course_token) {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.addCourseToEnroll(student_id, course_token);
	}

	public HashMap<Exercise, ArrayList<Attempts>> getHomeworkAttemptInfo(
			long student_id, String token) {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.getHomeworksAttemptsInfo(student_id, token);
	}

	public HashMap<Exercise, Attempts> getCurrentHomeworkInfo(long student_id, String token) {
		StudentDAO studentdao = new StudentDAOImpl();
		return studentdao.getCurrentHomeworks(student_id, token);
	}

	public int createNewAttempt(Exercise e, long studentID) {
		int attid = 0;
		StudentDAO studentdao = new StudentDAOImpl();
		attid = studentdao.createNewAttempt(e, studentID);
		return attid;
	}

	public ArrayList<Questions> fetchExerciseQuestions(Exercise e) {
		ArrayList<Questions> questions = new ArrayList<Questions>();
		StudentDAO studentdao = new StudentDAOImpl();
		questions = studentdao.fetchQuestions(e);
		ArrayList<Questions> questions1 = assignGroupID(questions);
		ArrayList<Questions> questions2 = fetchAnswers(questions1);
		ArrayList<Questions> questions3 = fetchParameters(questions2);
		return questions;
	}

	public ArrayList<Questions> assignGroupID(ArrayList<Questions> questions) {
		StudentDAO studentdao = new StudentDAOImpl();
		Iterator<Questions> iter = questions.iterator();
		ArrayList<Questions> qsList = new ArrayList<Questions>();

		while (iter.hasNext()) {
			Questions qs = iter.next();
			if (qs.getIsParameterized() == 1) {
				ArrayList<Integer> groupList = studentdao.fetchGroupId(qs);
				Collections.shuffle(groupList);
				qs.setGroupID(groupList.get(1));
				qsList.add(qs);
			} else {
				qsList.add(qs);
			}

		}
		return qsList;
	}

	public ArrayList<Questions> fetchParameters(ArrayList<Questions> questions) {
		ArrayList<Questions> qsList = new ArrayList<Questions>();
		StudentDAO studentdao = new StudentDAOImpl();
		Iterator<Questions> iter = questions.iterator();
		while (iter.hasNext()) {
			Questions qs = iter.next();
			if (qs.getIsParameterized() == 1) {
				ArrayList<Parameters> params = studentdao.fetchParameters(qs);
				qs.setParams(params);
				qsList.add(qs);
			} else {
				qsList.add(qs);
			}
		}
		return qsList;
	}

	public ArrayList<Questions> fetchAnswers(ArrayList<Questions> questions) {
		StudentDAO studentdao = new StudentDAOImpl();
		Iterator<Questions> iterator = questions.iterator();
		ArrayList<Questions> ques = new ArrayList<Questions>();
		int count_c, count_ic;
		while (iterator.hasNext()) {
			Questions qs = iterator.next();
			ArrayList<Answers> answerList = studentdao.fetchAnswerList(qs);
			Collections.shuffle(answerList);
			// Retrieve answers such that 4 are taken, 1 correct, 3 incorrect
			Iterator<Answers> it = answerList.iterator();
			ArrayList<Answers> answersDisplay = new ArrayList<Answers>();
			count_c = 0;
			count_ic = 0;
			while (it.hasNext()) {

				Answers ans = new Answers();
				ans = it.next();
				if (ans.getValid().equals("C") && count_c < 1) {
					answersDisplay.add(ans);
					count_c++;
				} else if (ans.getValid().equals("IC") && count_ic < 3) {
					answersDisplay.add(ans);
					count_ic++;
				}
				if (answersDisplay.size() == 4) {
					break;
				}
			}
			Collections.shuffle(answersDisplay);
			qs.setAns(answersDisplay);
			ques.add(qs);
		}

		return ques;
	}

	public boolean saveScore(Exercise e, float totalScore, int attId,
			long studentID) {
		StudentDAO studentdao = new StudentDAOImpl();
		boolean status = studentdao.saveScore(e, totalScore, attId, studentID);
		return status;
	}

	public void updateAttemptsHistory(int attid, ArrayList<Questions> questions) {
		StudentDAO studentdao = new StudentDAOImpl();
		studentdao.updateAttempt(attid, questions);
	}

	
	public HashMap<Exercise, ArrayList<Attempts>> getHWPastDate(long studentID,String token){
		StudentDAO studentdao = new StudentDAOImpl();
		HashMap<Exercise,ArrayList<Attempts>> exList =  studentdao.viewAttemptsPastDate(studentID, token);
		return exList;
	}

	public HashMap<Exercise, ArrayList<Attempts>> getHWWithinDate(long studentID,String token){
		StudentDAO studentdao = new StudentDAOImpl();
		HashMap<Exercise, ArrayList<Attempts>> exList =  studentdao.viewAttemptsWithinDate(studentID, token);
		return exList;
	}
	
	public HashMap<Integer,String> fetchNotifications(){
		HashMap<Integer,String> notifications = new HashMap<Integer,String>();
		StudentDAO studentdao = new StudentDAOImpl();
		notifications = studentdao.fetchNotifications();
		return notifications;
	}
	

    public ArrayList<Questions> fetchPastAttempt(Exercise e, Attempts a)
    {
        StudentDAO studentdao = new StudentDAOImpl();
        return studentdao.fetchAttempt(e,a);

    }

    public float fetchScoreForAttempt(Attempts a) {
        StudentDAO studentdao = new StudentDAOImpl();
        return studentdao.fetchScore(a);
    }

    public HashMap<Exercise, Integer> getCurrentHomeworkInfo1(long studentID, String token) {
        StudentDAO studentdao = new StudentDAOImpl();
        return studentdao.getCurrentHomeworks1(studentID,token);
    }
}
