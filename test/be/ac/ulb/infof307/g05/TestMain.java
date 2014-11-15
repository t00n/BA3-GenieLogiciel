package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import be.ac.ulb.infof307.g05.model.*;

public class TestMain {

	private final String connectionString = "jdbc:sqlite:test.db";
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testORM() {
		JdbcConnectionSource connectionSource = Database.getConnectionSource();
		// create tables
		TableUtils.dropTable(connectionSource, SimpleObject.class, true);
		TableUtils.dropTable(connectionSource, CompositeObject.class, true);
		TableUtils.dropTable(connectionSource, Stage.class, true);
		TableUtils.dropTable(connectionSource, Project.class, true);
		TableUtils.createTableIfNotExists(connectionSource, SimpleObject.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
		TableUtils.createTableIfNotExists(connectionSource, Stage.class);
		TableUtils.createTableIfNotExists(connectionSource, Project.class);
		// instantiate DAOs
		Dao<SimpleObject, Integer> daoSimpleObject = DaoManager.createDao(connectionSource, SimpleObject.class);
		Dao<CompositeObject, Integer> daoCompositeObject = DaoManager.createDao(connectionSource, CompositeObject.class);
		Dao<Stage, Integer> daoStage = DaoManager.createDao(connectionSource, Stage.class);
		Dao<Project, Integer> daoProject = DaoManager.createDao(connectionSource, Project.class);
		// create a hierarchy of objects
		SimpleObject simpleObject1 = new SimpleObject();
		CompositeObject compositeObject1 = new CompositeObject();
		CompositeObject compositeObject2 = new CompositeObject();
		simpleObject1.parent = compositeObject2;
		compositeObject2.parent = compositeObject1;
		
		// create project
		Stage stage1 = new Stage();
		compositeObject1.stage = stage1;
		Project project = new Project();
		stage1.project = project;
		daoSimpleObject.create(simpleObject1);
		daoCompositeObject.create(compositeObject2);
		daoCompositeObject.create(compositeObject1);
		daoStage.create(stage1);
		daoProject.create(project);
	}
}
