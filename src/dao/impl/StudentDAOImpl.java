package dao.impl;

import dao.ConnectionFactory;
import dao.StudentDAO;
import entities.*;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//TODO : Close rs, ps and connection everywhere

public class StudentDAOImpl implements StudentDAO {
	Connection connection = null;
	private PreparedStatement ps;

	static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Throwable whatever) {
			}
		}

	}

	static void close(Statement s) {
		if (s != null) {
			try {
				s.close();
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
    public HashMap<Exercise, Integer> getCurrentHomeworks1(long studentID, String token) {
        PreparedStatement ps =null;
        ResultSet rs= null;
        PreparedStatement ps1 =null;
        ResultSet rs1= null;

        HashMap<Exercise, Integer> hash = new HashMap<Exercise, Integer>();
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            java.sql.Timestamp todaysDate = new java.sql.Timestamp(date.getTime());
            System.out.println("Todays Date:"+ todaysDate);

            Timestamp start=null;
            Timestamp end=null;

            String query = "SELECT eid,ename,isunlimited, noofretries,pointscorrect, pointsincorrect,startdate,enddate FROM Exercise  WHERE token=?";
           ps=connection.prepareStatement(query);
            ps.setString(1,token);
            rs=ps.executeQuery();

            while (rs.next())
            {

                start=rs.getTimestamp(7);
                System.out.println("Start Date:"+ start);
                end=rs.getTimestamp(8);
                System.out.println("End Date:"+ end);
                if (todaysDate.after(start) && todaysDate.before(end))
                {
                    Exercise ex=new Exercise();
                    ex.setExerciseId(rs.getInt(1));
                    ex.setExerciseName(rs.getString(2));
                    ex.setIsUnlimited(rs.getInt(3));
                    ex.setNoOfRetries(rs.getInt(4));
                    ex.setPointsCorrect(rs.getFloat(5));
                    ex.setPointsInCorrect(rs.getFloat(6));

                    String query2="select attid from attempts where eid=? AND sid=?";
                    ps1=connection.prepareStatement(query2);
                    ps1.setInt(1, ex.getExerciseId());
                    ps1.setLong(2,studentID);
                    rs1=ps1.executeQuery();
                    int count=0;

                    while (rs1.next())
                    {
                        count=count+1;
                    }

                    hash.put(ex,count);

                }

            }



        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            close(connection);
            close(rs);
            close(ps);
        }
        return hash;
    }


	@Override
	public HashMap<Exercise, Attempts> getCurrentHomeworks(long student_id,
			String token) {

		HashMap<Exercise, Attempts> hash = new HashMap<Exercise, Attempts>();
		ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        PreparedStatement ps4=null;
        PreparedStatement ps3=null;
        PreparedStatement ps2=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        int ex_id=0,count_attempts=0;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			java.sql.Timestamp todaysDate = new java.sql.Timestamp(date.getTime());
            System.out.println("Todays Date:"+ todaysDate);

            Timestamp start=null;
            Timestamp end=null;

            //TODO: 2 cases, no attempts/some attempts


            String query2="SELECT A.attid from ATTEMPTS A,EXERCISE E where A.eid=E.eid AND E.token=? AND A.sid=?";
            ps2=connection.prepareStatement(query2); //In case there are no attempts, rs will have no records
            ps2.setString(1,token);
            ps2.setLong(2, student_id);
            rs2=ps2.executeQuery();
            if (!rs2.next())
            {
                System.out.println("Entering here if no attempts are done");
                String query = "SELECT E.eid,E.ename, E.isUnlimited, E.noofretries,E.pointscorrect, E.pointsincorrect,E.startdate,E.enddate FROM Exercise E WHERE E.token=?";

                ps = connection.prepareStatement(query);
                ps.setString(1, token);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    Exercise ex = new Exercise();
                    Attempts at = new Attempts();
                    start=rs.getTimestamp(7);
                    System.out.println("Start Date:"+ start);
                    end=rs.getTimestamp(8);
                    System.out.println("End Date:"+ end);
                    if (todaysDate.after(start) && todaysDate.before(end))
                    {
                        ex.setExerciseId(rs.getInt(1));
                        ex.setExerciseName(rs.getString(2));
                        ex.setIsUnlimited(rs.getInt(3));
                        ex.setNoOfRetries(rs.getInt(4));
                        ex.setPointsCorrect(rs.getFloat(5));
                        ex.setPointsInCorrect(rs.getFloat(6));
                        at.setCountOfAttempts(0);
                        hash.put(ex, at);
                    }
                }
            }



            else
            {
                System.out.println("Entering here if atleast one attempt is done");
             //TODO :Change the query
			String query3 = "SELECT E.eid,COUNT(A.attid) FROM Exercise E, Attempts A WHERE A.eid=E.eid AND E.token=? GROUP BY E.eid";

			ps3 = connection.prepareStatement(query3);
			ps3.setString(1, token);
			rs3 = ps3.executeQuery();

                while(rs3.next())
                {
                 ex_id=rs3.getInt(1);
                 count_attempts=rs3.getInt(2);
                 String query1="SELECT eid,ename,isUnlimited,noofretries,pointscorrect,pointsincorrect,startdate,enddate from Exercise where eid=?";
                    ps1=connection.prepareStatement(query1);
                    ps1.setInt(1,ex_id);
                    rs1=ps1.executeQuery();


				while (rs1.next()) {
					Exercise ex = new Exercise();
					Attempts at = new Attempts();
                    start=rs1.getTimestamp(7);
                    System.out.println("Start Date:"+ start);
                    end=rs1.getTimestamp(8);
                    System.out.println("End Date:"+ end);
                    if (todaysDate.after(start) && todaysDate.before(end))
                    {
					ex.setExerciseId(ex_id);
					ex.setExerciseName(rs1.getString(2));
					ex.setIsUnlimited(rs1.getInt(3));
					ex.setNoOfRetries(rs1.getInt(4));
					ex.setPointsCorrect(rs1.getFloat(5));
					ex.setPointsInCorrect(rs1.getFloat(6));
					at.setCountOfAttempts(count_attempts);
					hash.put(ex, at);
                    }
				}
                }

		}
        } catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(rs);
			close(ps);
            close(rs1);
            close(ps1);
            close(rs2);
            close(ps2);
            close(rs3);
            close(ps3);
		}

		return hash;

	}

	@Override
	public HashMap<Integer, String> selectEnrolled(long student_id) {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		int i;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT token FROM enrolls WHERE sid=?";
			ps = connection.prepareStatement(query);
			ps.setLong(1, student_id);
			rs = ps.executeQuery();

				i = 1;
				while (rs.next()) {
                    System.out.println("Token"+rs.getString(1));
					result.put(i, rs.getString(1));
					i++;
				}

		} catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(rs);
			close(ps);
		}

		return result; 
	}

	@Override
	public int addCourseToEnroll(long student_id, String course_token) {

		int max = 0, no = 0, flag = 3;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        String result = null;
		try {
           	connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT token FROM course_token WHERE token=? ";
			ps = connection.prepareStatement(query);
			ps.setString(1, course_token);
			rs = ps.executeQuery();

				while (rs.next())
                {
					result = rs.getString("token");// course_token validated
					System.out.println("Token found is:" + result);
				}

				// For checking maximum enrollment condition
				String query1 = "SELECT max_enrollment,no_students_enrolled FROM course_token WHERE token=?";
				ps1 = connection.prepareStatement(query1);
				ps1.setString(1, course_token);
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					max = rs1.getInt(1);
					no = rs1.getInt(2);
				}
                System.out.println("Max value, Number of students enrolled:"+ max+" | "+ no );
				// For checking if course is over due date
				Calendar cal = Calendar.getInstance();
				java.util.Date date = cal.getTime();
				java.sql.Date todaysDate = new java.sql.Date(date.getTime());
                System.out.println("TodaysDate:"+ todaysDate +"    date:"+date);
				String query2 = "SELECT end_date FROM course_token WHERE token=?";
				ps2 = connection.prepareStatement(query2);
				ps2.setString(1, course_token);
				rs2 = ps2.executeQuery();
                 Date d=null;
                while (rs2.next())
                {
                     d= rs2.getDate(1);
                    System.out.println("Date got from the database:"+d);
                }

				if (no >= max) // check condition of maximum enrollment
				{
					System.out.println("No>max part..Went in here??\n");
					flag = 0;// different displays for different numbers
				}
				else if (todaysDate.after(d)) // check condition of due date
				{
					System.out.println("Due date part..Went in here??\n");
					flag = 1; // different displays for different numbers
				}
				    else {
					String query4 = "INSERT INTO ENROLLS (SID,TOKEN)VALUES(?,?)";
					ps3 = connection.prepareStatement(query4);
					ps3.setLong(1, student_id);
					ps3.setString(2, course_token);
					ps3.executeUpdate();
					flag = 3;
				}

		} catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(rs);
			close(rs1);
			close(rs2);
			close(rs3);
			close(ps);
            close(ps1);
            close(ps2);
            close(ps3);

		}

		return flag; 
	}

	@Override
	public HashMap<Exercise, ArrayList<Attempts>> getHomeworksAttemptsInfo(
			long student_id, String token) {

		HashMap<Exercise, ArrayList<Attempts>> hash = new HashMap<Exercise, ArrayList<Attempts>>();
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
            System.out.println("The token sent is:"+ token);
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT DISTINCT E.eid,E.ename FROM Exercise E, Attempts A WHERE E.eid=A.eid and E.token=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, token);
			rs = ps.executeQuery();
         	while (rs.next()) {

					Exercise ex = new Exercise();
					ex.setExerciseId(rs.getInt(1));
					ex.setExerciseName(rs.getString(2));
                    System.out.println("The exercise ID is:"+ex.getExerciseId()+ "and the exercise name is"+ ex.getExerciseName());
					String query1 = "SELECT A.attid, A.attemptnumber, A.score FROM Attempts A WHERE A.eid=? AND A.sid=?";
					ps1 = connection.prepareStatement(query1);
					ps1.setInt(1, ex.getExerciseId());
					ps1.setLong(2, student_id);
					rs1 = ps1.executeQuery();

					ArrayList<Attempts> at = new ArrayList<Attempts>();

					while (rs1.next()) {
						Attempts att = new Attempts();
						att.setAttemptId(rs1.getInt(1));
						att.setAttemptNumber(rs1.getInt(2));
						att.setScore(rs1.getInt(3));
                        System.out.println("The attempt Id is:" +att.getAttemptId()+ "The attempt number is"+ att.getAttemptNumber()+ "The score is:" + att.getScore());
						at.add(att);
					}
					hash.put(ex, at);

				}


		} catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(rs);
            close(rs1);
			close(ps);
            close(ps1);
		}

		return hash; 
	}

	/*
	 * This method creates a new record in the Attempts table whenever a user
	 * attempts a homework sid, eid, attid NUMBER(10), score NUMBER(4),
	 * timeofsubmission TIME, attemptnumber NUMBER(5), FOREIGN KEY(sid)
	 * REFERENCES student(sid) on delete cascade, FOREIGN KEY(eid) REFERENCES
	 * exercise(eid) on delete cascade, PRIMARY KEY(attid));
	 */

	@Override
	public int createNewAttempt(Exercise ex, long studentID) {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int attid = 0;
		boolean status = false;

		try {
			connection = ConnectionFactory.getInstance().getConnection();

			// Get the next attemptnumber from Attempts table
			String query = "SELECT count(*) from attempts where eid=? AND sid=? ";
			ps = connection.prepareStatement(query);
			ps.setInt(1, ex.getExerciseId());
			ps.setLong(2, studentID);
			rs = ps.executeQuery();
			int attemptCount = 0;
			while (rs.next()) {
				attemptCount = rs.getInt(1);
				System.out.println("Atempt Count"+attemptCount);
			}
			String query1 = "INSERT INTO attempts(sid,eid,attemptnumber) VALUES(?,?,?)";
			ps1 = connection.prepareStatement(query1);
			ps1.setLong(1, studentID);
			ps1.setInt(2, ex.getExerciseId());
			ps1.setInt(3, attemptCount + 1);
			int rowCount = ps1.executeUpdate();
			System.out.println("Row count"+rowCount);
			if (rowCount > 0) {
				status = true;
			}
			// Get the attid of the newly created record
			String query2 = "SELECT MAX(ATTID) FROM ATTEMPTS";
			ps2 = connection.prepareStatement(query2);
			rs1 = ps2.executeQuery();
			while (rs1.next()) {
				attid = rs1.getInt(1);
				System.out.println("Att id"+attid);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(ps1);
			close(rs1);
			close(rs);
		}
		return attid;
	}

	/*
	 * This method fetches the list of questions for a Homework that a student
	 * is going to attempt
	 */
	@Override
	public ArrayList<Questions> fetchQuestions(Exercise e) {
		ArrayList<Questions> questions = new ArrayList<Questions>();

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT Q.qid,Q.question_text,Q.hint,Q.isParameterized FROM questions Q,adds_removes A where eid=?"
					+ "AND Q.qid=A.qid";
			ps = connection.prepareStatement(query);
			ps.setInt(1, e.getExerciseId());
			rs = ps.executeQuery();

			while (rs.next()) {
				Questions qs = new Questions();
				qs.setQid(rs.getInt("qid"));
				qs.setQuestionText(rs.getString("question_text"));
				qs.setHint(rs.getString("hint"));
				qs.setIsParameterized(rs.getInt("isParameterized"));
				questions.add(qs);

			}

		} catch (Exception e2) {
			// TODO: handle exception
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}

		return questions;
	}

	/*
	 * This method fetches the groupids for a particular question if it is
	 * parameterized
	 */
	@Override
	public ArrayList<Integer> fetchGroupId(Questions qs) {

		ArrayList<Integer> qs_grp = new ArrayList<Integer>();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT groupid from qs_paramgroup where qid=?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, qs.getQid());
			rs = ps.executeQuery();
			while (rs.next()) {
				qs_grp.add(rs.getInt("groupid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}

		return qs_grp;

	}

	@Override
	public ArrayList<Parameters> fetchParameters(Questions qs) {
		ArrayList<Parameters> params = new ArrayList<Parameters>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT pid,pname,pvalue from parameter where qid=? AND groupid=? ";
			ps = connection.prepareStatement(query);
			ps.setInt(1, qs.getQid());
			ps.setInt(2, qs.getGroupID());
			rs = ps.executeQuery();
			while (rs.next()) {
				Parameters p = new Parameters();
				p.setPid(rs.getInt("pid"));
				p.setpName(rs.getString("pname"));
				p.setValue(rs.getInt("pvalue"));
				params.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}

		return params;
	}

	@Override
	public ArrayList<Answers> fetchAnswerList(Questions qs) {
		PreparedStatement ps = null;
		ResultSet rs = null;
        String query=null;
		ArrayList<Answers> ans = new ArrayList<Answers>();
		Answers answer;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
            if(qs.getGroupID()==0)
            {
             query = "SELECT aid, explanation, valid, answer_text FROM Answers WHERE qid=?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, qs.getQid());
            }
            else
            {
			 query = "SELECT aid, explanation, valid, answer_text FROM Answers WHERE qid=? AND groupid=?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, qs.getQid());
                ps.setInt(2, qs.getGroupID());
            }

            System.out.println("Question Id"+ qs.getQid());
            System.out.println("Group Id"+ qs.getGroupID());
			rs = ps.executeQuery();


			while (rs.next()) {
				answer = new Answers();
				answer.setAnswerID(rs.getInt(1));
				answer.setExplanation(rs.getString(2));
				answer.setValid(rs.getString(3));
				answer.setAnswerText(rs.getString(4));
				ans.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} finally {
			close(connection);
			close(rs);
			close(ps);
		}

		return ans;
	}

	@Override
	public void updateAttempt(int attid, ArrayList<Questions> questions) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
            dateFormat.format(date);
            java.sql.Timestamp todaysDate = new java.sql.Timestamp(date.getTime());
			// TODO: Add submission time into the attempt table

			connection = ConnectionFactory.getInstance().getConnection();
			Iterator<Questions> iterator = questions.iterator();
			while (iterator.hasNext()) {
				Questions qs = new Questions();
				qs = iterator.next();
				Iterator<Answers> it = qs.getAns().iterator();
				while (it.hasNext()) {
					Answers ans = new Answers();
					ans = it.next();
					System.out.println("Question id"+qs.getQid());
                    System.out.println("Answer id :" +ans.getAnswerID());
                    System.out.println("Attid  :" +attid);
                    System.out.println("Answer id :" +ans.getIsChosen());
					String query = "INSERT INTO ATTEMPT_HISTORY(qid,aid,attid,ischosen) VALUES(?,?,?,?)";
					ps = connection.prepareStatement(query);
					ps.setInt(1, qs.getQid());
					ps.setInt(2, ans.getAnswerID());
					ps.setInt(3, attid);
					ps.setInt(4, ans.getIsChosen());
                    ps.executeUpdate();
				}
			}

            String query1="UPDATE ATTEMPTS SET TIMEOFSUBMISSION=? WHERE ATTID=?";
            ps=connection.prepareStatement(query1);
            ps.setTimestamp(1,todaysDate);
            ps.setInt(2,attid);
            ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} finally {
			close(connection);
			close(ps);
		}
	}

    @Override
    public ArrayList<Questions> fetchAttempt(Exercise e, Attempts a) {
       PreparedStatement ps=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        PreparedStatement ps4=null;
        ResultSet rs=null;
        ResultSet rs1=null;
        ResultSet rs2=null;
        ResultSet rs3=null;
        ResultSet rs4=null;
        int groupID=0;
        ArrayList<Questions> questions=new ArrayList<Questions>();
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String query="SELECT DISTINCT qid FROM ATTEMPT_HISTORY WHERE attid=? ";
            ps=connection.prepareStatement(query);
            ps.setInt(1,a.getAttemptId());
            rs=ps.executeQuery();
            while (rs.next())
            {
                Questions qs1=new Questions();
                qs1.setQid(rs.getInt(1));
                questions.add(qs1);
            }
            Iterator<Questions> it=questions.iterator();

            while (it.hasNext())
            {
                ArrayList<Answers> ansList=new ArrayList<Answers>();
                Questions qs=new Questions();
                qs=it.next();

                //todo:fetch remaining questions attributes : DONE
                String query3="SELECT question_text,isParameterized,ans_explanation FROM QUESTIONS WHERE qid=? ";
                ps3=connection.prepareStatement(query3);
                ps3.setInt(1,qs.getQid());
                rs3= ps3.executeQuery();
                while (rs3.next())
                {
                    qs.setQuestionText(rs3.getString(1));
                    qs.setIsParameterized(rs3.getInt(2));
                    qs.setAnsExplanation(rs3.getString(3));
                }


                String query1="SELECT aid,isChosen FROM Attempt_history WHERE attid=? AND qid=? ";
                ps1=connection.prepareStatement(query1);
                ps1.setInt(1,a.getAttemptId());
                ps1.setInt(2,qs.getQid());
                rs1=ps1.executeQuery();

                while (rs1.next())
                {
                    Answers ans=new Answers();
                    ans.setAnswerID(rs1.getInt(1));
                    ans.setIsChosen(rs1.getInt(2));
                    String query2="SELECT answer_text,valid,explanation,groupid FROM answers WHERE aid=?";
                    ps2=connection.prepareStatement(query2);
                    ps2.setInt(1,ans.getAnswerID());
                    rs2=ps2.executeQuery();

                    while (rs2.next())
                    {

                        ans.setAnswerText(rs2.getString(1));
                        ans.setValid(rs2.getString(2));
                        ans.setExplanation(rs2.getString(3));
                        if (qs.getIsParameterized()==1)
                        {
                        ans.setGroupID(rs2.getInt(4));
                        }
                    }
                    if (qs.getIsParameterized()==1)
                    {
                        groupID=ans.getGroupID();
                    }
                    ansList.add(ans);
                }
                qs.setAns(ansList);


                 //TODO:fetch parameter attributes now  if the question is parametrized
                if (qs.getIsParameterized()==1)
                {
                String query4="SELECT pid, pname, pvalue FROM parameter WHERE qid=? AND groupid=? ";
                    ps4=connection.prepareStatement(query4);
                    ps4.setInt(1,qs.getQid());
                    ps4.setInt(2,groupID);
                    rs4=ps4.executeQuery();
                    ArrayList<Parameters> params=new ArrayList<Parameters>();
                    while (rs4.next())
                    {
                        Parameters p= new Parameters();
                        p.setPid(rs4.getInt(1));
                        p.setpName(rs4.getString(2));
                        p.setValue(rs4.getInt(3));
                        params.add(p);
                    }
                    qs.setParams(params);
                }
                else
                {
                }


            }

        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            close(connection);
            close(rs);
            close(rs1);
            close(rs2);
            close(rs3);
            close(rs4);
            close(ps);
            close(ps1);
            close(ps2);
            close(ps3);
            close(ps4);
        }
        return questions;
    }

    @Override
    public float fetchScore(Attempts a) {
        ResultSet rs=null;
        PreparedStatement ps=null;
        float score=0;
        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String query="SELECT score FROM ATTEMPTS WHERE attid=?";
            ps=connection.prepareStatement(query);
            ps.setInt(1,a.getAttemptId());
            rs=ps.executeQuery();

            while (rs.next())
            {
               score=rs.getFloat(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            close(connection);
            close(ps);
            close(rs);
        }
               return score;
    }



    @Override
	public boolean saveScore(Exercise e, float totalScore, int attId,
			long studentID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean status = false;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "UPDATE ATTEMPTS SET SCORE=? WHERE sid=? AND eid=? AND attid=?";
			ps = connection.prepareStatement(query);
			ps.setFloat(1, totalScore);
			ps.setLong(2, studentID);
			ps.setInt(3, e.getExerciseId());
			ps.setInt(4, attId);
			int up = ps.executeUpdate();
			if (up > 0) {
				status = true;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			close(connection);
			close(rs);
			close(ps);
		}
		return status;
	}

	/*
	 * This method returns a list of Attempts for every homework
	 * past the due date 
	 * 
	 */
	@Override
	public HashMap<Exercise,ArrayList<Attempts>> viewAttemptsPastDate(long sid,String token) {
		// TODO Auto-generated method stub
		HashMap<Exercise,ArrayList <Attempts>> exListPast = new HashMap<Exercise, ArrayList<Attempts>>();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			//Retrieve the Exercises pat due date
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            java.sql.Timestamp currentDate = new java.sql.Timestamp(date.getTime());

			//Date d = new Date();
			//java.sql.Timestamp currentDate = new java.sql.Timestamp(d.getTime());
			String query = "SELECT eid,ename,startdate,enddate from exercise where token=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, token);
			//ps.setTimestamp(2, currentDate);
			rs = ps.executeQuery();
			
			//Iterate through the RS to fetch the attempts for each exercise
			while(rs.next()){
				java.sql.Timestamp endDate = rs.getTimestamp(4);
				
				if(currentDate.after(endDate)){
				Exercise ex = new Exercise();
				ex.setExerciseId(rs.getInt("eid"));
				ex.setExerciseName(rs.getString("ename"));
				ex.setStartDate(rs.getTimestamp("startdate"));
				ex.setEndDate(rs.getTimestamp("enddate"));
				ArrayList<Attempts> attListPast = new ArrayList<Attempts>();
				String query2 = "SELECT attid,attemptnumber FROM ATTEMPTS WHERE eid=? AND sid=?";
				ps1 = connection.prepareStatement(query2);
				ps1.setInt(1, ex.getExerciseId());
				ps1.setLong(2, sid);
				rs1 = ps1.executeQuery();
				while(rs1.next()){
					Attempts at = new Attempts();
					at.setAttemptId(rs1.getInt(1));
					at.setAttemptNumber(rs1.getInt(2));
					attListPast.add(at);

				}
                    exListPast.put(ex, attListPast);
				}
            }
								
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(connection);
			close(rs);
			close(ps);
		}
		return exListPast;
	}

	/*
	 *This method fetches the list of homework attempts within the due date 
	 *
	 */
	@Override
	public HashMap<Exercise,ArrayList<Attempts>> viewAttemptsWithinDate(long sid,String token) {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		HashMap<Exercise, ArrayList<Attempts>> exListPast = new HashMap<Exercise, ArrayList<Attempts>>();
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			//Retrieve the Exercises within the due date
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            java.sql.Timestamp currentDate = new java.sql.Timestamp(date.getTime());


			//Date d = new Date();
			//java.sql.Timestamp currentDate = new java.sql.Timestamp(d.getTime());
			String query = "SELECT eid,ename,startdate,enddate from exercise where token=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, token);
			//ps.setTimestamp(2, currentDate);
			rs = ps.executeQuery();
			
			//Iterate through the RS to fetch the attempts for each exercise
			while(rs.next()){
				java.sql.Timestamp startDate = rs.getTimestamp(3);
				java.sql.Timestamp endDate = rs.getTimestamp(4);
				if(currentDate.after(startDate) && currentDate.before(endDate)){
				Exercise ex = new Exercise();
				ex.setExerciseId(rs.getInt("eid"));
				ex.setExerciseName(rs.getString("ename"));
				ex.setStartDate(rs.getTimestamp("startdate"));
				ex.setEndDate(rs.getTimestamp("enddate"));
				ArrayList<Attempts> attListPast = new ArrayList<Attempts>();
				String query2 = "SELECT attid,attemptnumber FROM ATTEMPTS WHERE eid=? AND sid=?";
				ps1 = connection.prepareStatement(query2);
				ps1.setInt(1, ex.getExerciseId());
				ps1.setLong(2, sid);
				rs1 = ps1.executeQuery();
				while(rs1.next()){
					Attempts at = new Attempts();
                    at.setAttemptId(rs1.getInt(1));
					at.setAttemptNumber(rs1.getInt(2));

					attListPast.add(at);
                    //System.out.println("Size of attempt list:"+exListPast.size());
				}
                    exListPast.put(ex,attListPast);
                }

         //       System.out.println("Size of hash map:"+exListPast.size());

		}
         //   for (Map.Entry<Exercise,ArrayList<Attempts>> kv:exListPast.entrySet())
           // {
            //System.out.println("Key:"+kv.getKey()+"| Value:" + kv.getValue());
            //}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(connection);
			close(rs);
			close(ps);
		}
		return exListPast;
	}

	
	/*
	 * This method fetches the notifications for the Professor who has logged in	 * 
	 */
	@Override
	public HashMap<Integer, String> fetchNotifications() {
		HashMap<Integer, String> notifications = new HashMap<Integer, String>();
		String query = "Select notification_id,message from student_notifications where sid=? where read_status = 'unread'";
		 PreparedStatement ps = null;
		 ResultSet rs=null;
		 PreparedStatement ps1=null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			//ps.setLong(1,Instructor.id);
			rs = ps.executeQuery();
			int i=1;
			while(rs.next()){
				notifications.put(i, rs.getString("message"));
				String query1 = "UPDATE student_notifications SET read_status='read' where notification_id=?";
				ps1 = connection.prepareStatement(query1);
				ps1.setInt(1, rs.getInt("notification_id"));
				ps1.executeUpdate();
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
            close(connection);
            close(ps1);
            close(rs);
        }
		
		//Once the notifications has been retrieved, update in the table as 'read'
		
		return notifications;
	}
	
	
	

}
