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

import java.sql.SQLException;
import java.util.Vector;

public class TestMain {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public void createTestProject(String name) {
		Project project = new Project(name);
		Stage stage = new Stage(project, 0);
		Vector<Vertex> vertexes = new Vector<Vertex>();
		Vertex vertex1 = new Vertex(0,0,0);
		vertexes.add(vertex1);
		Vertex vertex3 = new Vertex(0,2,0);
		vertexes.add(vertex3);
		Vertex vertex2 = new Vertex(0,2,2);
		vertexes.add(vertex2);
		Vertex vertex4 = new Vertex(0,0,2);
		vertexes.add(vertex4);
		
		Vector<Order> orders = new Vector<Order>();
		Order order1 = new Order(0);
		Order order2 = new Order(1);
		Order order3 = new Order(2);
		Order order4 = new Order(0);
		Order order5 = new Order(2);
		Order order6 = new Order(3);
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		orders.add(order4);
		orders.add(order5);
		orders.add(order6);
		stage.setFloor(new CompositeObject(null, vertexes, orders));
		vertex1.setReferent(stage.getFloor());
		vertex2.setReferent(stage.getFloor());
		vertex3.setReferent(stage.getFloor());
		vertex4.setReferent(stage.getFloor());
		order1.setReferent(stage.getFloor());
		order2.setReferent(stage.getFloor());
		order3.setReferent(stage.getFloor());
		order4.setReferent(stage.getFloor());
		order5.setReferent(stage.getFloor());
		order6.setReferent(stage.getFloor());
		try {
			project.create();
			stage.getFloor().create();
			stage.create();
			order1.create();
			order2.create();
			order3.create();
			order4.create();
			order5.create();
			order6.create();
			vertex1.create();
			vertex2.create();
			vertex3.create();
			vertex4.create();
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
		this.createTestProject("test project 2");
	}
}
