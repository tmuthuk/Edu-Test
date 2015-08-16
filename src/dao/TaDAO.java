/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;

import  entities.Exercise;
import  entities.Questions;

/**
 * @author rituraj
 *
 */
public interface TaDAO {

	public ArrayList generateTaCourses(long userID);

	public HashMap generateAllHomeworks(String courseToken);

	public Exercise generateHomeworkDetails(int courseTokenSelected);

	public ArrayList generateQuestionsListOfHomework(int homeworkID);

	public Questions generateQuestions(int value);

	public ArrayList generateFirstQuery(long userID, String courseToken,
			int homeworkID);

	public ArrayList generateSecondQuery(long userID, String courseToken,
			int homeworkID);

	public ArrayList generateThirdQuery(long userID, String courseToken);

	

	

}
