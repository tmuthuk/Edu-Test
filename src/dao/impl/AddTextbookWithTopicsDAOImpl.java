/**
 * 
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;



import dao.AddTextbookWithTopicsDAO;
import dao.ConnectionFactory;
import entities.classToken;


public class AddTextbookWithTopicsDAOImpl implements AddTextbookWithTopicsDAO {

	
	Connection connection = null;

	static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}
	
	@Override
	public HashMap<Integer, String> generateTextbooksList(String str) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			//System.out.println("Calling Textbooks2");
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT isbn,title FROM textbook WHERE title LIKE '%"+str+"%'";
			String queryInExecution = "SELECT isbn,title FROM textbook WHERE title LIKE '% ? %'";
			ps = connection.prepareStatement(query);
			//ps.setString(1, str);
			
			System.out.println(ps.toString());
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			//if(!resultSet.next()){
			//	System.out.println("No data returned");
			//	return false;
			//}
			//else{
				while(resultSet.next()){
					/*Add each textbook with isbn and textbopok name*/
					hm.put(resultSet.getInt("isbn"), resultSet.getString("title"));
				}
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		return hm;
	}

	@Override
	public boolean insertTextbook(int isbn,String cid) {
		// TODO Auto-generated method stub
		boolean added = false;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "insert into course_textbook values(?,?)";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, isbn);
			ps.setString(2, cid);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			added = true;
				
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		
		return added;
	}

	@Override
	public ArrayList<classToken> generateCourseTopicList(String courseID) {
		// TODO Auto-generated method stub
		classToken ct = new classToken();
		ArrayList<classToken> al = new ArrayList<classToken>();
		//creating a arraylist of classToken.
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "select * from course_topics where cid=?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setString(1, courseID);
			
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			while(resultSet.next()){
				//take all the entries for database in the list.
				ct = new classToken();
				ct.setChapterNum(resultSet.getInt("chapter_no"));
				ct.setCid(resultSet.getString("cid"));
				ct.setTopicID(resultSet.getInt("topicid"));
				ct.setTopicName(resultSet.getString("topic_name"));
				ct.setIsbn(resultSet.getInt("isbn"));
				al.add(ct);
				
			}
				
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		return al;
	}

	@Override
	public HashMap generateTextbookTopics(int isbn, int courseID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertCourseTopics(int isbn, int courseID, int chapNum,
			String topicName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap generateTopics(int isbn) {
		// TODO Auto-generated method stub
		HashMap hm = new HashMap<>();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "select * from chapters where isbn=?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, isbn);
			
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			while(resultSet.next()){
				//take all the entries for database in the list.
				hm.put(resultSet.getInt("chapter_no"), resultSet.getString("title"));

			}
				
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		
		
		return hm;
	}

	@Override
	public void addCourseTopics(classToken ct) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "INSERT INTO course_topics VALUES (?,?,?,course_topic_seq.NEXTVAL,?)";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, ct.getIsbn());
			ps.setInt(2, ct.getChapterNum());
			ps.setString(3, ct.getCid());
			ps.setString(4, ct.getTopicName());
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
	}

	@Override
	public boolean deleteTextBookFromCourse(int isbn, String cid) {
		// TODO Auto-generated method stub
		boolean deleted = false;
		
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "delete from course_textbook where cid=? and isbn = ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setString(1, cid);
			ps.setInt(2, isbn);
			
			resultSet = ps.executeQuery();
			
			
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "delete from course_topics where cid=? and isbn = ?";
			ps = connection.prepareStatement(query);
			ps.setString(1, cid);
			ps.setInt(2, isbn);
			
			resultSet = ps.executeQuery();
			deleted = true;
			/*
			 * execute the query.
			 * */
			
			
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		
		return deleted;
	}

	@Override
	public void removeCourseTopics(classToken ct) {
		// TODO Auto-generated method stub

		
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
		
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "delete from course_topics where cid=? and isbn = ? and topicid=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, ct.getCid());
			ps.setInt(2, ct.getIsbn());
			ps.setInt(3, ct.getTopicID());
			
			resultSet = ps.executeQuery();
			/*
			 * execute the query.
			 * */
			
			
			//}
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
	}

}
