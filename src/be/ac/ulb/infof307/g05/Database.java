package be.ac.ulb.infof307.g05;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {
	
	private final static String connectionString = "jdbc:mysql://localhost/HomePlans";
	private final static String connectionUser = "HomePlans";
	private final static String connectionPwd = "HomePlans";
	private static JdbcConnectionSource connectionSource = null;
	
	public static JdbcConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			try {
				connectionSource = new JdbcConnectionSource(connectionString, connectionUser, connectionPwd);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectionSource;
	}
}
