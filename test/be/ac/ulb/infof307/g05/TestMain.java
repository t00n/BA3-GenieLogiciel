package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;

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
	public void testORM() throws SQLException {
		JdbcConnectionSource connectionSource = Database.getConnectionSource();
		// create tables
		TableUtils.dropTable(connectionSource, Vertex.class, true);
		TableUtils.dropTable(connectionSource, Mesh.class, true);
		TableUtils.dropTable(connectionSource, Texture.class, true);
		TableUtils.dropTable(connectionSource, SimpleObject.class, true);
		TableUtils.dropTable(connectionSource, CompositeObject.class, true);
		TableUtils.dropTable(connectionSource, Stage.class, true);
		TableUtils.dropTable(connectionSource, Project.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
		TableUtils.createTableIfNotExists(connectionSource, Mesh.class);
		TableUtils.createTableIfNotExists(connectionSource, Texture.class);
		TableUtils.createTableIfNotExists(connectionSource, SimpleObject.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
		TableUtils.createTableIfNotExists(connectionSource, Stage.class);
		TableUtils.createTableIfNotExists(connectionSource, Project.class);

		// create project
		Project project = new Project("new project");
		Stage stage = new Stage(project, 0, 3);
		
		// create a hierarchy of objects
		Vertex vertex = new Vertex(0, 0, 0);
		Mesh mesh = new Mesh("wall", "resources/wall.obj");
		CompositeObject compositeObject1 = new CompositeObject(stage, vertex, vertex, mesh);
		CompositeObject compositeObject2 = new CompositeObject(compositeObject1, vertex, vertex, mesh);
		SimpleObject simpleObject1 = new SimpleObject(compositeObject2, vertex, vertex, mesh);

		// save
		project.create();
		stage.create();
		vertex.create();
		mesh.create();
		compositeObject1.create();
		compositeObject2.create();
		simpleObject1.create();
	}
}
