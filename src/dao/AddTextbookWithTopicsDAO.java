package dao;

import java.util.ArrayList;
import java.util.HashMap;

import entities.classToken;

public interface AddTextbookWithTopicsDAO {

	public HashMap generateTextbooksList(String str);

	public boolean insertTextbook(int isbn, String cid);

	public ArrayList<classToken> generateCourseTopicList(String cid);

	public HashMap generateTextbookTopics(int isbn, int courseID);

	public boolean insertCourseTopics(int isbn, int courseID, int chapNum,
			String topicName);

	public HashMap generateTopics(int isbn);

	public void addCourseTopics(classToken ct);

	public boolean deleteTextBookFromCourse(int isbn, String cid);

	public void removeCourseTopics(classToken ct);
	
}
