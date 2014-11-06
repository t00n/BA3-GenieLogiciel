package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TestMain {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testORM() {
		JdbcConnectionSource connectionSource = null;
		try {
			connectionSource = // in order = connector:driver//host/database, user, password
				    new JdbcConnectionSource("jdbc:mysql://localhost/HomePlans", "HomePlans", "HomePlans");
			// auto create tables but you still need to create the database
			TableUtils.createTableIfNotExists(connectionSource, TestORM.class);
			// object that handles database queries
			Dao<TestORM, Integer> daoTest = DaoManager.createDao(connectionSource, TestORM.class);
			TestORM testORM = new TestORM();
			testORM.setSomeString("grosTestDeOuf");
			daoTest.create(testORM);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				connectionSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
