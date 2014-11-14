package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {
	
	private final static String connectionString = "jdbc:sqlite:HomePlans.db";
	private static JdbcConnectionSource connectionSource = null;
	
	public static JdbcConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			try {
				connectionSource = new JdbcConnectionSource(connectionString);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectionSource;
	}
}
