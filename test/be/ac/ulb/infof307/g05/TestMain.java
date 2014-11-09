package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TestMain {

	private final String connectionString = "jdbc:sqlite:test.db";
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testORM() {
		JdbcConnectionSource connectionSource = null;
		try {
			connectionSource = // in order = connector:driver//host/database, user, password
				    new JdbcConnectionSource(this.connectionString);
			// auto create tables but you still need to create the database
			// create testMany before testORM because testORM has a testMany foreign key
			// create testOneToOne after testORM because it has a testORM foreign key
			TableUtils.createTableIfNotExists(connectionSource, TestMany.class);
			TableUtils.createTableIfNotExists(connectionSource, TestORM.class);
			TableUtils.createTableIfNotExists(connectionSource, TestOneToOne.class);
			// object that handles database queries
			Dao<TestORM, Integer> daoORM = DaoManager.createDao(connectionSource, TestORM.class);
			Dao<TestMany, Integer> daoMany = DaoManager.createDao(connectionSource, TestMany.class);
			Dao<TestOneToOne, Integer> daoOne = DaoManager.createDao(connectionSource, TestOneToOne.class);
			TestMany testMany = new TestMany();
			daoMany.create(testMany);
			TestORM testORM = new TestORM();
			testORM.setSomeString("grosTestDeOuf");
			testORM.setTestMany(testMany);
			TestORM testORM2 = new TestORM();
			testORM2.setSomeString("2eTESTDEOUF");
			testORM2.setTestMany(testMany);
			daoORM.create(testORM);
			daoORM.create(testORM2);
			TestOneToOne testOne = new TestOneToOne();
			testOne.setTestORM(testORM);
			TestOneToOne testTwo = new TestOneToOne();
			// testTwo.setTestORM(testORM); // would violate unique constraint on TestOneToOne.testORM => one-to-one relationship
			testTwo.setTestORM(testORM2);
			daoOne.create(testOne);
			daoOne.create(testTwo);
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
	
	@Test
	public void loadDB() {
		JdbcConnectionSource connectionSource = null;
		try {
			connectionSource = new JdbcConnectionSource(this.connectionString);
			Dao<TestORM, Integer> daoORM = DaoManager.createDao(connectionSource, TestORM.class);
			TestORM testORM = daoORM.queryForId(1);
			Dao<TestMany, Integer> daoMany = DaoManager.createDao(connectionSource, TestMany.class);
			TestMany testMany = daoMany.queryForId(1);
//			System.out.println(testORM);
//			for (TestORM t : testMany.getTestOrms()) {
//				System.out.println(t);
//			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connectionSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
