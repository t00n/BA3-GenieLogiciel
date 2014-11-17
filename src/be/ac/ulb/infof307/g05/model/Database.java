package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.TableUtils;

public class Database<T> extends BaseDaoEnabled<T, Integer> {
	
	/**
	 *  static link to database connection source
	 */
	private final static String connectionString = "jdbc:sqlite:HomePlans.db";
	private static JdbcConnectionSource connectionSource = null;
	
	public static JdbcConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			try {
				connectionSource = new JdbcConnectionSource(connectionString);
				// create tables
				TableUtils.dropTable(connectionSource, Vertex.class, true);
				TableUtils.dropTable(connectionSource, Texture.class, true);
				TableUtils.dropTable(connectionSource, CompositeObject.class, true);
				TableUtils.dropTable(connectionSource, Stage.class, true);
				TableUtils.dropTable(connectionSource, Project.class, true);
				TableUtils.createTableIfNotExists(connectionSource, Project.class);
				TableUtils.createTableIfNotExists(connectionSource, Texture.class);
				TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
				TableUtils.createTableIfNotExists(connectionSource, Stage.class);
				TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
				TableUtils.createTableIfNotExists(connectionSource, Order.class);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectionSource;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Dao<T, Integer> getDao(Class<?> klass) {
		try {
			return (Dao<T, Integer>) DaoManager.createDao(Database.getConnectionSource(), klass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Database() {
		try {
			this.setDao((Dao<T, Integer>) DaoManager.createDao(Database.getConnectionSource(), this.getClass()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
