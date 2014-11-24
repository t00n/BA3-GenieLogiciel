package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.CompositeObject_ID;
import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Texture;
import be.ac.ulb.infof307.g05.model.Vertex;
import be.ac.ulb.infof307.g05.view.Cube;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jme3.math.Vector3f;

import java.sql.SQLException;

public class TestMain {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public void createTestProject(String name) {
        Project project = new Project(name, 50, 50);
        project.addStage(0);
        
        Vector3f vertex1 = new Vector3f(0,0,0);
        Vector3f vertex2 = new Vector3f(2,2,2);

        Cube cube = new Cube(vertex1, vertex2);
        CompositeObject object = new CompositeObject(null, cube.getVertices(), cube.getOrder());
        project.getStage(0).addObject(null, object);
        
        project.save();
	}
	
	@Test
	public void createTestDB() throws SQLException {
		JdbcConnectionSource connectionSource = Database.getConnectionSource();
		// create tables
		TableUtils.dropTable(connectionSource, Vertex.class, true);
		TableUtils.dropTable(connectionSource, Texture.class, true);
		TableUtils.dropTable(connectionSource, CompositeObject.class, true);
		TableUtils.dropTable(connectionSource, Stage.class, true);
		TableUtils.dropTable(connectionSource, Project.class, true);
		TableUtils.dropTable(connectionSource, CompositeObject_ID.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Project.class);
		TableUtils.createTableIfNotExists(connectionSource, Texture.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject_ID.class);
		TableUtils.createTableIfNotExists(connectionSource, Stage.class);
		TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
		this.createTestProject("test project 1");
		this.createTestProject("test project 2");
	}
}
