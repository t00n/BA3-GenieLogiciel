package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.misc.BaseDaoEnabled;


// TODO: Auto-generated Javadoc
/**
 * The Class Database.
 *
 * @param <T> the generic type
 */
public abstract class Database<T> extends BaseDaoEnabled<T, Integer> {
	
	/**  static link to database connection source. */
	private final static String connectionString = "jdbc:sqlite:HomePlans.db";
	
	/** The connection source. */
	private static JdbcConnectionSource connectionSource = null;
	
	//logg.debug("built statement{}", statement);
	public static JdbcConnectionSource getConnectionSource() {
//		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY , "ERROR");
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
	 * Gets the dao.
	 *
	 * @param <T> the generic type
	 * @param klass the klass
	 * @return the dao
	 */
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
	
	/**
	 * Instantiates a new database.
	 */
	@SuppressWarnings("unchecked")
	public Database() {
		try {
			this.setDao((Dao<T, Integer>) DaoManager.createDao(Database.getConnectionSource(), this.getClass()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * Save.
     */
    public void save() {
		try {
			if (this.isNew) {
				this.create();
				this.isNew = false;
			}
			else {
				this.update();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
    
    /** The is new. */
    public Boolean isNew = false;
}
