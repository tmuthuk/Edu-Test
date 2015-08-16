package dao.impl;

import dao.ConnectionFactory;
import dao.LoginDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAOImpl implements LoginDAO {

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

	/*
	 * This method authenticates the user while logging in Checks if the user
	 * entered a valid username and password and returns the role of the user If
	 * more than one role exists for a student i.e student and TA, send back TA
	 * as the role
	 */
	@Override
	public long getUserRole(String uname, String password,String role) {
		long userId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT user_id FROM Credentials WHERE username= ? AND password= ? AND role= ?";
			ps = connection.prepareStatement(query);
			ps.setString(1, uname);
			ps.setString(2, password);
			ps.setString(3, role);
			rs = ps.executeQuery();
			int totalRows = 0;
			
			while(rs.next()){
				userId = rs.getLong("user_id");
			}			

		} catch (SQLException sqlEx) {
			// TODO : handle exception
		}finally{
			close(connection);
			close(rs);
			close(ps);
		}
		return userId;
	}

	@Override
	public long getUserID(String uid, String password) {
		long userID = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			String query = "SELECT user_id FROM credentials WHERE username=? AND password=?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, uid);
			stmt.setString(2, password);
			// TODO : pass query
			rs = stmt.executeQuery();
					
			// Write query to check
			// TODO : update column number
			while (rs.next()) {
				userID = rs.getLong(1);
			}
		} catch (SQLException sqlEx) {
			// TODO : handle exception
		} finally {
			close(connection);
			close(rs);
			close(stmt);
		}
		return userID;
	}

	/*
	 * This method checks whether the univ_id exists in the respective tables and
	 * then calls insertCredentials and the record gets inserted if univ id exists in
	 * either student, TA or instructor tables
	 * 
	 */
	@Override
	public boolean newUser(String uname, String email_id, String password,
			String role_name, long univ_id) {
		boolean status = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			connection = ConnectionFactory.getInstance().getConnection();
			long id = 0;

			// TODO : pass query for different tables
			if (role_name.equals("student")) {
				String query = "SELECT SID FROM STUDENT WHERE sid =?";
				ps = connection.prepareStatement(query);
				ps.setLong(1, univ_id);
				rs = ps.executeQuery();
				while (rs.next()) {
					id = rs.getLong("sid");
					System.out.println("Univ id" + id);
				}

				if (id == univ_id) {
					status = true;
				}
				ps.clearParameters();
			} else if (role_name.equals("professor")) {
				String query = "SELECT instructor_id FROM instructor WHERE instructor_id = ?";
				ps = connection.prepareStatement(query);
				ps.setLong(1, univ_id);
				rs = ps.executeQuery(query);

				while (rs.next()) {
					id = rs.getLong("instructor_id");
				}

				if (id == univ_id) {
					status = true;
				}
			} else if (role_name.equals("ta")) {
				String query = "SELECT SID FROM TA WHERE sid = ?";
				ps = connection.prepareStatement(query);
				ps.setLong(1, univ_id);
				rs = ps.executeQuery();
				while (rs.next()) {
					id = rs.getLong("sid");
				}
				if (id == univ_id) {
					status = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close(connection);
			close(rs);
			close(ps);
		}
		insertCredentials(uname, email_id, password, role_name, univ_id, true);
		return status;
	}

	/*
	 * This method checks whether the username already exists in the Credentials
	 * table Returns true if the username already exists in the table else
	 * returns false
	 */
	public boolean checkUserName(String uname,String roleName) {
		boolean status = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = "SELECT username from CREDENTIALS WHERE username= ? AND role= ?";
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, uname);
			ps.setString(2, roleName);
			rs = ps.executeQuery();
			int rows = rs.getRow();
			String name = null;			
			while (rs.next()) {
				name = rs.getString("username");
				System.out.println("username" + name);
			}
			if (name != null && name.equals(uname)) {
				status = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
			close(ps);
			close(rs);
		}
		return status;
	}

	public void insertCredentials(String uname, String email_id,
			String password, String role_name, long univ_id, boolean status) {
		PreparedStatement ps = null;
		Statement stmt = null;
		if (status) {
			try {
				connection = ConnectionFactory.getInstance().getConnection();
				connection.setAutoCommit(true);
				String query = "insert into credentials values(?,?,?,?)";
				ps = connection.prepareStatement(query);
				ps.setLong(1, univ_id);
				ps.setString(2, uname);
				ps.setString(3, password);
				ps.setString(4, role_name);
				System.out.println("testing");
				ps.executeUpdate();
				System.out.println("testing");
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				close(connection);
				close(ps);
			}

		}
	}

}
