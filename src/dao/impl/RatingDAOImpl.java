package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.ConnectionFactory;
import dao.RatingDAO;

public class RatingDAOImpl implements RatingDAO {

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
	public boolean setHomeworkRating(int uid, int homeworkID,int rating) {
		// TODO Auto-generated method stub
		/*
		 * Succcess: true
		 * faliure: false
		 * */
		
		/*check if the homework attempt is first for the student.*/
		if(checkFirstAttempt(uid,homeworkID)){
			//first attempt has been checked and now we will take user input for report and write that in feedback table
			//System.out.println("Enter your rating between 1-6:");
			if(insertRating(uid,homeworkID,rating))
			{
				return true;
			}
			else 
			{
				System.out.println("Not able to insert");
				return false;
			}
		}
		else{
			System.out.println("Not First Attempt of user"+uid+"in homework: "+homeworkID);
			return false;
		}
		
	}

	private boolean insertRating(int uid, int homeworkID, int rating) {
		// TODO Auto-generated method stub
		System.out.println("Insert Rating called");
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		float ratingRecieved = 0;
		int numberOfStudents = 0;
		float currentRating = 0;
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT Rating,numberOfStudentsAttempted FROM feedback WHERE eid= ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, homeworkID);
					
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			//if(!resultSet.next()){
			//	System.out.println("No data returned of attempt");
			//	return false;
			//}
			//else{
				while(resultSet.next()){
					ratingRecieved = resultSet.getFloat("Rating");
					numberOfStudents = resultSet.getInt("numberOfStudentsAttempted");
					currentRating = ratingRecieved*numberOfStudents;
					numberOfStudents +=1;
					ratingRecieved = (float)(rating+currentRating)/numberOfStudents;
					//update the above rating in feedback.
					
					System.out.println("Rating Recieved"+ratingRecieved+numberOfStudents);
					/*executing the above feedback function*/
					String updateQuery = "UPDATE feedback SET Rating= ?,numberOfStudentsAttempted= ? WHERE eid= ?";
					ps = connection.prepareStatement(updateQuery);
					ps.setFloat(1, ratingRecieved);
					ps.setInt(2, numberOfStudents);
					ps.setInt(3, homeworkID);
					
					
					/*
					 * execute the query. and upadate values for rating
					 * */
					ps.executeQuery();
					
					
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
		
		return true;
	}

	private boolean checkFirstAttempt(int uid, int homeworkID) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT attemptnumber FROM attempts WHERE sid= ? and eid = ? and attemptnumber=?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, uid);
			ps.setInt(2, homeworkID);
			ps.setInt(3, 1);
			
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
					if(resultSet.getInt("attemptNumber")==1)
						return true;
					else 
						return false;
					
					
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
		return false;
	}

	@Override
	public float getHomeworkRating(int eid) {
		// TODO Auto-generated method stub
		float rating=0;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		
		try{
			/*
			 * Creating Connection with query 
			 * using prepared statement.
			 * */
			connection = ConnectionFactory.getInstance().getConnection();
			String queryInExecution = "SELECT Rating FROM feedback WHERE eid = ?";
			ps = connection.prepareStatement(queryInExecution);
			ps.setInt(1, eid);
			
			
			
			/*
			 * execute the query.
			 * */
			resultSet = ps.executeQuery();
			//if(!resultSet.next()){
			//	System.out.println("No data returned");
			//	return 0;
			//}
			//else{
				while(resultSet.next()){
					rating =  resultSet.getFloat("Rating");
					
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
		return rating;
	}
	
}
