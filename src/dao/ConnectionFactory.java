
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

	// Put your oracle ID and password here

	private static final String user = "tmuthuk";
	private static final String password = "200020815";


	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			//TODO : handle exception
		}
	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		connection = DriverManager.getConnection(jdbcURL, user, password);
		//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		return connection;
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

}

