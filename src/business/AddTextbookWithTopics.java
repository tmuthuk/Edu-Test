
package business;

import java.util.ArrayList;
import java.util.HashMap;

import dao.AddTextbookWithTopicsDAO;
import dao.impl.AddTextbookWithTopicsDAOImpl;
import entities.classToken;


public class AddTextbookWithTopics {

	public AddTextbookWithTopicsDAO att;
	
	public AddTextbookWithTopics(){
		att = new AddTextbookWithTopicsDAOImpl();
	}
	/*generate textbook list.*/
	public HashMap getTextbooks(String str){
		
		return att.generateTextbooksList(str);
	}
	
	public boolean addTextBook(int isbn,String cid){
		return att.insertTextbook(isbn,cid);
	}
	
	public ArrayList<classToken> getCourseTopic(String cid){
		return att.generateCourseTopicList(cid);
	}
	
	public HashMap getTextbookTopics(int isbn,int courseID){
		return att.generateTextbookTopics(isbn,courseID);
	}
	
	public boolean setCourseTopics(int isbn,int courseID,int chapNum,String topicName){
		return att.insertCourseTopics(isbn,courseID,chapNum,topicName);
	}
	public HashMap fetchAddingTopics(int isbn) {
		// TODO Auto-generated method stub
		return att.generateTopics(isbn);
	}
	public void addCourseTopics(classToken ct) {
		// TODO Auto-generated method stub
		att.addCourseTopics(ct);
	}
	public boolean deleteTextBook(int isbn, String cid) {
		// TODO Auto-generated method stub
		return att.deleteTextBookFromCourse(isbn,cid);
	}
	public void RemoveCourseTopics(classToken ct) {
		// TODO Auto-generated method stub
		att.removeCourseTopics(ct);
	}
	
	
}
