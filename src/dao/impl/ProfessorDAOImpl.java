package dao.impl;

import dao.ConnectionFactory;
import dao.ProfessorDAO;
import entities.Answers;
import entities.Exercise;
import entities.Parameters;
import entities.Questions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ProfessorDAOImpl implements ProfessorDAO {

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
	public HashMap<Integer, String> selectTaught(long prof_id) {

		HashMap<Integer, String> token = new HashMap<Integer, String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 1;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT token FROM teaches WHERE instructor_id=?";
			ps = connection.prepareStatement(query);
			ps.setLong(1, prof_id);
			// TODO : pass query
			rs = ps.executeQuery();
			// Write query to check
			// TODO : update column number

			while (rs.next()) {

				token.put(i, rs.getString("token"));
				i++;
			}

		} catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}

		return token;
	}

	/**
	 * Returns the list of topics for the given course name
	 */
	@Override
	public HashMap<Integer, String> fetchTopics(String courseName) {
		HashMap<Integer, String> topicList = new HashMap<Integer, String>();
		// To do: check this query again
		String query = "SELECT topicid,topic_name FROM course_topics WHERE cid IN (SELECT cid FROM course_token WHERE token=?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, courseName);
			rs = ps.executeQuery();

			while (rs.next()) {
				topicList.put(rs.getInt("topicid"), rs.getString("topic_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);

		}

		return topicList;
	}

	/**
	 * Adds a new exercise to the Exercise table
	 */
	@Override
	public boolean insertExercise(Exercise ex) {
		boolean result = false;
		// To do: check this query
		String query = "INSERT INTO exercise(eid,ename,token,startdate,enddate,isUnlimited,noofretries,pointscorrect,"
				+ "pointsincorrect,mindifficultylevel,"
				+ "maxdifficultylevel,numberofquestions,score_selection_method) "
				+ "values(exercise_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, ex.getExerciseName());
			ps.setString(2, ex.getToken());
			ps.setTimestamp(3, ex.getStartDate());
			ps.setTimestamp(4, ex.getEndDate());
			ps.setInt(5, ex.getIsUnlimited());
			ps.setInt(6, ex.getNoOfRetries());
			ps.setFloat(7, ex.getPointsCorrect());
			ps.setFloat(8, ex.getPointsInCorrect());
			ps.setInt(9, ex.getMinDifficulty());
			ps.setInt(10, ex.getMaxDifficulty());
			ps.setInt(11, ex.getNoOfQuestions());
			ps.setString(12, ex.getScoreSelectId());

			int count = ps.executeUpdate();

			// Insert the topics for the exercise in exercise_topics table
			/*
			 * 1.Get the Exercise id inserted in the above step 2.Insert the
			 * topic ids for the given exercise id
			 */
			if (count > 0) {
				String query1 = "SELECT MAX(eid) FROM exercise";
				ps = connection.prepareStatement(query1);
				rs = ps.executeQuery();
				while (rs.next()) {
					int eid = rs.getInt("MAX(eid)");
					Iterator<Integer> iter = ex.getTopicId().iterator();
					while (iter.hasNext()) {
						String query2 = "INSERT INTO exercise_topics values(?,?)";
						ps = connection.prepareStatement(query2);
						ps.setInt(1, eid);
						ps.setInt(2, iter.next());
						count = ps.executeUpdate();
					}
				}
				if (count > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}
		return result;
	}

	/**
	 * Returns a list of Homeworks to be populated for Adding/removing questions
	 * from the Homework
	 * 
	 */
	@Override
	public HashMap<Integer, Exercise> getHomeworks(String courseToken) {
		// To do: check the query
		HashMap<Integer, Exercise> exlist = new HashMap<Integer, Exercise>();
		String query = "SELECT EID,ENAME,mindifficultylevel,maxdifficultylevel,startdate,enddate,pointscorrect,pointsincorrect,numberofquestions,noofretries FROM EXERCISE WHERE TOKEN=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, courseToken);
			rs = ps.executeQuery();

			/*
			 * 1.Iterate through the array list2.Initialize new exercise object
			 * 3.Assign the exid and name to the exercise obj4.Assign it to
			 * hashmap
			 */
			int i = 1;
			while (rs.next()) {
				Exercise ex = new Exercise();
				ex.setExerciseId(rs.getInt("eid"));
				ex.setExerciseName(rs.getString("ename"));
				ex.setMinDifficulty(rs.getInt("mindifficultylevel"));
				ex.setMaxDifficulty(rs.getInt("maxdifficultylevel"));
				ex.setStartDate(rs.getTimestamp("startdate"));
				ex.setEndDate(rs.getTimestamp("enddate"));
				ex.setPointsCorrect(rs.getFloat("pointscorrect"));
				ex.setPointsInCorrect(rs.getFloat("pointsincorrect"));
				ex.setNoOfQuestions(rs.getInt("numberofquestions"));
				ex.setNoOfRetries(rs.getInt("noofretries"));
				exlist.put(i, ex);
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}
		return exlist;
	}

	/*
	 * This method fetches the list of questions from the Questions Tablewith
	 * the chosen difficulty range and topics chosen for the exercise
	 */
	@Override
	public HashMap<Integer, Questions> fetchQuestionsList(Exercise ex,
			String token) {

		// First get the topic ids from the Exercise_topics table
		String query1 = "SELECT E.TOPICID FROM exercise_topics E, course_topics C WHERE "
				+ "C.topicid = E.topicid AND E.EID = ?";
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query1);
			ps.setInt(1, ex.getExerciseId());
			rs = ps.executeQuery();
			while (rs.next()) {
				// For each topic, retrieve the questions
				// within the diff level range

				String query2 = "SELECT qid,question_text,isParameterized FROM questions where cid IN (SELECT CID FROM COURSE_TOKEN WHERE TOKEN =?) AND topicid = ? AND difficulty_level BETWEEN ? AND ? AND QID NOT IN (SELECT QID FROM ADDS_REMOVES WHERE EID = ?)";
				ps1 = connection.prepareStatement(query2);
				ps1.setString(1, token);
				ps1.setInt(2, rs.getInt("topicid"));
				ps1.setInt(3, ex.getMinDifficulty());
				ps1.setInt(4, ex.getMaxDifficulty());
				ps1.setInt(5, ex.getExerciseId());
				ResultSet rs1 = ps1.executeQuery();
				int i = 1;
				while (rs1.next()) {
					Questions qs = new Questions();
					qs.setQid(rs1.getInt("qid"));
					qs.setQuestionText(rs1.getString("question_text"));
					qs.setIsParameterized(rs1.getInt("isParameterized"));
					qsList.put(i, qs);
					i++;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(ps1);
			close(rs);
		}
		return qsList;
	}

	/*
	 * This method fetches All the Parameter groups for all the questions
	 * Displayed for the professor to add to the exercise *
	 */
	@Override
	public HashMap<Integer, ArrayList<Parameters>> fetchParams(Questions qs) {
		// Query for fetching Params for the questions.
		// 1.for each entry in the hashmap, check whether isParameterized is set
		// if set to 1, then get the qid from question object
		// 2.Get the group id for the given quid from qs_paramgroup table
		// 3.For each group id, fetch the list of parameters.
		// Integer in HashMap is going to be groupId
		HashMap<Integer, ArrayList<Parameters>> paramsList = new HashMap<Integer, ArrayList<Parameters>>();
		if (qs.getIsParameterized() == 1) {
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			try {
				connection = ConnectionFactory.getInstance().getConnection();
				String query1 = "SELECT groupid from qs_ParamGroup where qid = ?";
				ps1 = connection.prepareStatement(query1);
				ps1.setInt(1, qs.getQid());
				// Retrieve all the groupids
				rs1 = ps1.executeQuery();

				// for each group id, retrive the list of parameters
				while (rs1.next()) {

					// Fetch the parameters for each group id and put it into
					// parameter
					// object and store it in the hashmap against each group id
					String query2 = "SELECT pid,pname,pvalue FROM PARAMETER WHERE groupid=?";
					int groupId = rs1.getInt("groupid");
					System.out.println("Groupid" + groupId);
					ps2 = connection.prepareStatement(query2);
					ps2.setInt(1, groupId);
					rs2 = ps2.executeQuery();
					// int rowCount = rs2.getRow();
					// For each row in the rs2, put it in the param object
					ArrayList<Parameters> params = new ArrayList<Parameters>();

					while (rs2.next()) {
						Parameters p = new Parameters();
						p.setPid(rs2.getInt("pid"));
						p.setpName(rs2.getString("pname"));
						p.setValue(rs2.getInt("pvalue"));
						params.add(p);
					}
					paramsList.put(rs1.getInt("groupid"), params);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(connection);
				close(rs1);
				close(ps1);
				close(rs2);
				close(ps2);

			}
		}

		return paramsList;
	}

	/*
	 * This method adds the questions selected by the professor to the Exercise
	 * Also updates the Exercise table with the number of questions
	 */
	@Override
	public boolean addQuestions(Exercise ex, ArrayList qsList) {
		boolean status = false;
		String query = "INSERT INTO adds_removes values(?,?)";

		int rowCount = 0;
		Iterator<Integer> iter = qsList.iterator();
		while (iter.hasNext()) {
			PreparedStatement ps = null;
			try {
				connection = ConnectionFactory.getInstance().getConnection();
				ps = connection.prepareStatement(query);
				ps.setInt(1, iter.next());
				ps.setInt(2, ex.getExerciseId());
				rowCount += ps.executeUpdate();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				close(connection);
				close(ps);
			}

		}
		if (rowCount == qsList.size()) {
			status = true;
		}
		updateNoOfQuestionsAdd(ex, qsList.size());
		return status;
	}

	/*
	 * This method fetches the list of questions for removing
	 */
	@Override
	public HashMap<Integer, Questions> fetchQuestions(Exercise ex) {
		HashMap<Integer, Questions> qsList = new HashMap<Integer, Questions>();
		String query = "SELECT qid,question_text,isParameterized,hint,difficulty_level,ans_explanation FROM QUESTIONS WHERE qid in (select qid from adds_removes where eid=?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, ex.getExerciseId());
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) {
				Questions qs = new Questions();
				qs.setQid(rs.getInt(1));
				qs.setQuestionText(rs.getString(2));
				qs.setIsParameterized(rs.getInt(3));
				qs.setHint(rs.getString(4));
				qs.setDifficultyLevel(rs.getInt(5));
				
				qsList.put(i, qs);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(rs);
			close(ps);
		}

		return qsList;
	}

	/*
	 * This method removes the list of questions that professor chose to remove
	 * from the qxercise Also updates the Exercise table with the number of
	 * questions
	 */
	@Override
	public boolean removeQuestions(Exercise ex, ArrayList<Integer> qsList) {
		boolean status = false;
		String query = "DELETE FROM adds_removes where eid = ? AND qid = ?";
		Iterator<Integer> iter = qsList.iterator();
		int rowCount = 0;
		while (iter.hasNext()) {
			PreparedStatement ps = null;
			try {
				connection = ConnectionFactory.getInstance().getConnection();
				ps = connection.prepareStatement(query);
				ps.setInt(1, ex.getExerciseId());
				ps.setInt(2, iter.next());
				rowCount += ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(connection);
				close(ps);
			}
		}

		if (rowCount == qsList.size()) {
			status = true;
		}
		updateNoOfQuestionsRemove(ex, qsList.size());
		return status;
	}

	/*
	 * This method updates the number of questions in exercise table for Add
	 * Questions
	 */
	@Override
	public void updateNoOfQuestionsAdd(Exercise ex, int noOfQuestions) {
		String query = "UPDATE exercise SET numberofquestions = ? +(select numberofquestions from exercise where eid = ?) WHERE eid =?";
		PreparedStatement ps = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, noOfQuestions);
			ps.setInt(2, ex.getExerciseId());
			ps.setInt(3, ex.getExerciseId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
		}

	}

	/*
	 * This method updates the number of questions in exercise table for Remove
	 * Questions
	 */
	@Override
	public void updateNoOfQuestionsRemove(Exercise ex, int noOfQuestions) {
		String query = "UPDATE exercise SET numberofquestions = (select numberofquestions from exercise where eid = ?) - ? WHERE eid =?";
		PreparedStatement ps = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, ex.getExerciseId());
			ps.setInt(2, noOfQuestions);
			ps.setInt(3, ex.getExerciseId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
		}

	}

	@Override
	public boolean updateEndDate(Exercise ex) {
		boolean status = false;
		int rowsUpdate = 0;
		String query = "UPDATE EXERCISE SET enddate= ? where eid = ?";
		PreparedStatement ps = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			 ps = connection.prepareStatement(query);
			ps.setTimestamp(1, ex.getEndDate());
			ps.setInt(2, ex.getExerciseId());
			rowsUpdate = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
		}
		if (rowsUpdate > 0) {
			status = true;
		}

		return status;
	}

	/*
	 * This method fetches the notifications for the Student who has logged in *
	 */
	@Override
	public HashMap<Integer, String> fetchNotifications(long professorID) {
		HashMap<Integer, String> notifications = new HashMap<Integer, String>();
		String query = "Select notification_id,message from instructor_notifications where instructor_id=? and read_status =?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			 ps.setLong(1,professorID);
			 ps.setString(2, "unread");
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) {
				int id= rs.getInt(1);
				System.out.println("not id:"+id);
				notifications.put(i, rs.getString("message"));
				String query1 = "UPDATE instructor_notifications SET read_status='read' where notification_id=?";
				ps1 = connection.prepareStatement(query1);
				ps1.setInt(1, rs.getInt("notification_id"));
				ps1.executeUpdate();
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps1);
			close(rs);
		}

		// Once the notifications has been retrieved, update in the table as
		// 'read'

		return notifications;
	}

	/*
	 * This method fetches the answers for a question and for a specific groupid
	 */
	@Override
	public ArrayList<Answers> fetchAnswers(String query) {
		ArrayList<Answers> ansList = new ArrayList<Answers>();
		//String query = "SELECT aid,answer_text,valid,explanation from answers where qid=? and groupid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			//ps.setInt(1, qs.getQid());
			//ps.setInt(2, groupId);
			rs = ps.executeQuery();
			while(rs.next()){
				Answers answers =  new Answers();
				answers.setAnswerID(rs.getInt(1));
				answers.setAnswerText(rs.getString(2));
				answers.setValid(rs.getString(3));
				answers.setExplanation(rs.getString(4));
				ansList.add(answers);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps1);
			close(rs);
		}

		return ansList;
	}

	@Override
	public boolean updateNoOfQuestions(Exercise ex) {

		PreparedStatement ps = null;
		boolean status = false;
		try{
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "UPDATE EXERCISE SET  NUMBEROFQUESTIONS=? where eid=?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ex.getNoOfQuestions());
			ps.setInt(2, ex.getExerciseId());
			int count = ps.executeUpdate();
			if(count>0){
				status=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
		}
		return status;
	}
}
