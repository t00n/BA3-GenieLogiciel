package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Vector;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	protected Stage() {
		
	}
	
	public Stage(Project project, int level, CompositeObject floor) {
		this.project = project;
		this.level = level;
		this.floor = floor;
	}
	
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
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
		this.floor = new CompositeObject(null, vertexes);
		this.floor.setMeshOrder(orders);
		vertex1.referent = this.floor;
		vertex2.referent = this.floor;
		vertex3.referent = this.floor;
		vertex4.referent = this.floor;
		order1.referent = this.floor;
		order2.referent = this.floor;
		order3.referent = this.floor;
		order4.referent = this.floor;
		order5.referent = this.floor;
		order6.referent = this.floor;
		try {
			this.floor.create();
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
	
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	@DatabaseField (canBeNull = false)
	protected int level;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject floor;
	public CompositeObject getFloor() { return floor; }
}
