package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Order;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Texture;
import be.ac.ulb.infof307.g05.model.Vertex;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.Vector;

public class TestMain {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public void createTestProject(String name) {
        Project project = new Project("test project");
        Stage stage = new Stage(project, 0);
        
        Vector3f vertex1 = new Vector3f(0,0,0);
        Vector3f vertex2 = new Vector3f(2,2,2);

        Cube cube = new Cube(vertex1, vertex2);
        CompositeObject object = new CompositeObject(null, cube.getVertices(), cube.getOrder());
        stage.setFloor(object);
        
        try {
			project.create();
			object.create();
			for (Order order: object.getMeshOrder()) {
				order.setReferent(object);
				order.create();
			}
			for (Vertex vertex: object.getPositions()) {
				vertex.setReferent(object);
				vertex.create();
			}
			stage.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		TableUtils.dropTable(connectionSource, Order.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Project.class);
		TableUtils.createTableIfNotExists(connectionSource, Texture.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
		TableUtils.createTableIfNotExists(connectionSource, Stage.class);
		TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
		TableUtils.createTableIfNotExists(connectionSource, Order.class);
		this.createTestProject("test project 1");
	}
}
