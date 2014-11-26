package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Floor;
import be.ac.ulb.infof307.g05.model.Resource;
import be.ac.ulb.infof307.g05.model.Room;
import be.ac.ulb.infof307.g05.model.Static_ID;
import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Vertex;
import be.ac.ulb.infof307.g05.model.Wall;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TestMain {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public void createTestProject(String name) {
        Project project = new Project(name, 50, 50);
        project.addStage(0);
        Stage stage =  project.getStage(0);
        
        Collection<Vector3f> vectors = new ArrayList<Vector3f>();
        vectors.add(new Vector3f(0,0,0));
        vectors.add(new Vector3f(2,0,0));
        vectors.add(new Vector3f(3, 0, 1));
        vectors.add(new Vector3f(2,0,2));
        vectors.add(new Vector3f(0,0,2));
        
        stage.addRoom(name, vectors);
        Collection<Vector3f> wall1 = new ArrayList<Vector3f>();
        wall1.add(new Vector3f(0,0,0));
        wall1.add(new Vector3f(2,0,0));
        Collection<Vector3f> wall2 = new ArrayList<Vector3f>();
        wall2.add(new Vector3f(2,0,0));
        wall2.add(new Vector3f(3,0,1));
        Collection<Vector3f> wall3 = new ArrayList<Vector3f>();
        wall3.add(new Vector3f(3,0,1));
        wall3.add(new Vector3f(2,0,2));
        Collection<Vector3f> wall4 = new ArrayList<Vector3f>();
        wall4.add(new Vector3f(2,0,2));
        wall4.add(new Vector3f(0,0,2));
        Collection<Vector3f> wall5 = new ArrayList<Vector3f>();
        wall5.add(new Vector3f(0,0,2));
        wall5.add(new Vector3f(0,0,0));
        stage.getRoom(name).addWall(wall1);
        stage.getRoom(name).addWall(wall2);
        stage.getRoom(name).addWall(wall3);
        stage.getRoom(name).addWall(wall4);
        stage.getRoom(name).addWall(wall5);
        
        project.save();
	}
	
	@Test
	public void createTestDB() throws SQLException {
		JdbcConnectionSource connectionSource = Database.getConnectionSource();
		// create tables
		TableUtils.dropTable(connectionSource, Vertex.class, true);
		TableUtils.dropTable(connectionSource, Resource.class, true);
		TableUtils.dropTable(connectionSource, CompositeObject.class, true);
		TableUtils.dropTable(connectionSource, Floor.class, true);
		TableUtils.dropTable(connectionSource, Room.class, true);
		TableUtils.dropTable(connectionSource, Wall.class, true);
		TableUtils.dropTable(connectionSource, Stage.class, true);
		TableUtils.dropTable(connectionSource, Project.class, true);
		TableUtils.dropTable(connectionSource, Static_ID.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Project.class);
		TableUtils.createTableIfNotExists(connectionSource, Resource.class);
		TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
		TableUtils.createTableIfNotExists(connectionSource, Static_ID.class);
		TableUtils.createTableIfNotExists(connectionSource, Stage.class);
		TableUtils.createTableIfNotExists(connectionSource, Floor.class);
		TableUtils.createTableIfNotExists(connectionSource, Room.class);
		TableUtils.createTableIfNotExists(connectionSource, Wall.class);
		TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
		this.createTestProject("test project 1");
	}
	
	@Test
	public void testStaticID() {
		try {
			TableUtils.dropTable(Database.getConnectionSource(), Static_ID.class, true);
			TableUtils.createTableIfNotExists(Database.getConnectionSource(), Static_ID.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert(Static_ID.getObjectID() == 1);
		assert(Static_ID.getObjectID() == 2);
	}
}
