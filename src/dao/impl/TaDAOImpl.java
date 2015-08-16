package  dao.impl;

import dao.ConnectionFactory;
import dao.TaDAO;
import entities.Exercise;
import entities.Questions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

 
public class TaDAOImpl implements TaDAO {

	 
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
	public ArrayList generateTaCourses(long userID) {
		// TODO Auto-generated method stub
		/*
		 * request database to generate the part, and return string containing courses.
		 * */
		
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList arrayList = new ArrayList();
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT token FROM ta WHERE sid= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setLong(1, userID);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				arrayList.add(resultSet.getString("token"));
				
				
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}


        return arrayList;


	}

	@Override
	public HashMap generateAllHomeworks(String courseToken) {
		// TODO generate all the homeworks which are concerned with TA

		PreparedStatement ps = null;
		ResultSet resultSet = null;
		 HashMap hm = new HashMap();
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT eid,ename FROM exercise WHERE token= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setString(1, courseToken);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				hm.put(resultSet.getInt("eid"), resultSet.getString("ename"));
			}
					
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
	public Exercise generateHomeworkDetails(int homeworkTokenSelected) {
		// TODO Auto-generated method stub
		/*Used to show all the details related to homework.*/
		/*generate a query for homeworktoken and return excercise type objectS*/
		Exercise ex = new Exercise();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT * FROM exercise WHERE eid= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, homeworkTokenSelected);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				ex.setExerciseId(resultSet.getInt("EID"));
				ex.setExerciseName(resultSet.getString("ename"));
				ex.setEndDate(resultSet.getTimestamp("endDate"));
				ex.setStartDate(resultSet.getTimestamp("startdate"));
				ex.setNoOfQuestions(resultSet.getInt("numberofquestions"));
				ex.setPointsCorrect(resultSet.getFloat("pointscorrect"));
				ex.setPointsInCorrect(resultSet.getFloat("pointsincorrect"));
				ex.setMinDifficulty(resultSet.getInt("mindifficultyLevel"));
				ex.setMaxDifficulty(resultSet.getInt("maxdifficultylevel"));
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		
		
		return ex;
	}

	@Override
	public ArrayList generateQuestionsListOfHomework(int homeworkID) {
		// TODO Auto-generated method stub
		ArrayList al = new ArrayList();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT qid FROM adds_removes WHERE eid= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, homeworkID);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				al.add(resultSet.getInt("qid"));
			}
					
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
	public Questions generateQuestions(int value) {
		// TODO Auto-generated method stub
		Questions ques = new Questions();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT * FROM questions WHERE qid= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, value);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				ques.setQid(resultSet.getInt("qid"));
				ques.setQuestionText(resultSet.getString("question_text"));
				ques.setHint(resultSet.getString("hint"));
				
				ques.setTopicid(resultSet.getInt("topicid"));
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		
		
		return ques;
	}

	@Override
	public ArrayList generateFirstQuery(long userID, String courseToken,
			int homeworkID) {
		// TODO Find students who did not take homework
		
		ArrayList ar = new ArrayList();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		String fname = null;
		String lname = null;
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "select s.fname,s.lname from student s, enrolls e where s.sid = e.sid and e.token = ? and s.sid NOT IN (select unique(sid) from attempts a where a.eid = ?)";
			
			ps = connection.prepareStatement(queryInExecution);
			ps.setString(1, courseToken);
			ps.setInt(2, homeworkID);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				/*add first and last name together.*/
				fname = resultSet.getString("fname");
				lname = resultSet.getString("lname");
				ar.add(fname+lname);
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		return ar;
	}

	@Override
	public ArrayList generateSecondQuery(long userID, String courseToken,
			int homeworkID) {
		// TODO Auto-generated method stub
		
		ArrayList ar = new ArrayList();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		String fname = null;
		String lname = null;
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "select lname,fname from student s, attempts a where s.sid = a.sid and a.eid = ? and a.attemptNumber = ? and score = (select Max(a.score) from attempts a where a.eid = ? and a.attemptNumber =1)";
			
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, homeworkID);
			ps.setInt(2, 1);
			ps.setInt(3, homeworkID);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				/*add first and last name together.*/
				fname = resultSet.getString("fname");
				lname = resultSet.getString("lname");
				ar.add(fname+lname);
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		return ar;
		
		
	}

	@Override
	public ArrayList generateThirdQuery(long userID, String courseToken) {
		// TODO Auto-generated method stub
		ArrayList ar = new ArrayList();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		String fname = null;
		String lname = null;
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "select distinct s.sid,s.lname,s.fname from student s, attempts a, exercise e where s.sid = a.sid and a.eid = e.eid and e.token = ? and a.attemptNumber = ? and a.score IN (select Max(a1.score) from attempts a1,exercise e1 where e1.eid = a1.eid and e1.token = ? and a1.attemptNumber=1)";
			
			ps = connection.prepareStatement(queryInExecution);
			ps.setString(1, courseToken);
			ps.setInt(2, 1);
			ps.setString(3, courseToken);
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			
			while(resultSet.next()){
				/*add first and last name together.*/
				fname = resultSet.getString("fname");
				lname = resultSet.getString("lname");
				ar.add(fname+lname);
			}
					
		}catch (SQLException sqlEx) {
			// TODO : handle exception
			System.out.println(sqlEx.toString());
			sqlEx.printStackTrace();
		}finally{
			close(connection);
			close(resultSet);
			close(ps);
		}
		return ar;
	}



	

}

