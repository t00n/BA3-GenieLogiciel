package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.BaseDaoEnabled;

public class Database extends BaseDaoEnabled {
	
	/**
	 *  static link to database connection source
	 */
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
	
	/** 
	 * provide subclasses a link to their DAOs
	 */
	private Dao<? extends Database, ?> dao;
	
	public Dao<? extends Database, ?> getDao() {
		if (dao == null) {
			try {
				dao = DaoManager.createDao(Database.getConnectionSource(), this.getClass());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dao;
	}
	
	public Database() {
		this.setDao(this.getDao());
	}
}
