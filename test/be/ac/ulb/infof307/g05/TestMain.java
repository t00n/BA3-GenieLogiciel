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
	
	public int[] squareMesh(int point0, int point1, int point2, int point3){
		//Renvoie l'ordre des points de façon à pouvoir dessiner le mesh d'un carré visible des deux côtés
		int[] indexes = {point1,point0,point2, point2,point3,point1, point2,point0,point1, point1,point3,point2};
		return indexes;
	}
	
	public int[] cubeMesh(int point0, int point1, int point2, int point3, int point4, int point5, int point6, int point7){
		int[] cubeSide1 = squareMesh(point0,point1,point2,point3);
		int[] cubeSide2 = squareMesh(point4,point5,point6,point7);
		int[] cubeSide3 = squareMesh(point1,point3,point5,point7);
		int[] cubeSide4 = squareMesh(point3,point2,point7,point6);
		int[] cubeSide5 = squareMesh(point2,point0,point6,point4);
		int[] cubeSide6 = squareMesh(point0,point1,point4,point5);
		
		int[] indexes = {cubeSide1[0],cubeSide1[1],cubeSide1[2],cubeSide1[3],cubeSide1[4],cubeSide1[5],cubeSide1[6],cubeSide1[7],cubeSide1[8],cubeSide1[9],cubeSide1[10],cubeSide1[11],
						 cubeSide2[0],cubeSide2[1],cubeSide2[2],cubeSide2[3],cubeSide2[4],cubeSide2[5],cubeSide2[6],cubeSide2[7],cubeSide2[8],cubeSide2[9],cubeSide2[10],cubeSide2[11],
						 cubeSide3[0],cubeSide3[1],cubeSide3[2],cubeSide3[3],cubeSide3[4],cubeSide3[5],cubeSide3[6],cubeSide3[7],cubeSide3[8],cubeSide3[9],cubeSide3[10],cubeSide3[11],
						 cubeSide4[0],cubeSide4[1],cubeSide4[2],cubeSide4[3],cubeSide4[4],cubeSide4[5],cubeSide4[6],cubeSide4[7],cubeSide4[8],cubeSide4[9],cubeSide4[10],cubeSide4[11],
						 cubeSide5[0],cubeSide5[1],cubeSide5[2],cubeSide5[3],cubeSide5[4],cubeSide5[5],cubeSide5[6],cubeSide5[7],cubeSide5[8],cubeSide5[9],cubeSide5[10],cubeSide5[11],
						 cubeSide6[0],cubeSide6[1],cubeSide6[2],cubeSide6[3],cubeSide6[4],cubeSide6[5],cubeSide6[6],cubeSide6[7],cubeSide6[8],cubeSide6[9],cubeSide6[10],cubeSide6[11],
						};
		return indexes;
	}
	
	public void createTestProject(String name) {
        Project project = new Project("test project");
        Stage stage = new Stage(project, 0);
        Vector<Vertex> vertexes = new Vector<Vertex>();
        
        Vertex vertex1 = new Vertex(0,0,0);
        vertexes.add(vertex1);
        Vertex vertex2 = new Vertex(2,0,0);
        vertexes.add(vertex2);
        Vertex vertex3 = new Vertex(0,0,2);
        vertexes.add(vertex3);
        Vertex vertex4 = new Vertex(2,0,2);
        vertexes.add(vertex4);
        
		Vertex vertex5 = new Vertex(0,2,0);
		vertexes.add(vertex5);
		Vertex vertex6 = new Vertex(2,2,0);
		vertexes.add(vertex6);
		Vertex vertex7 = new Vertex(0,2,2);
		vertexes.add(vertex7);
		Vertex vertex8 = new Vertex(2,2,2);
		vertexes.add(vertex8);
        
		Vector<Order> orders = new Vector<Order>();
		int[] indexes = cubeMesh(0,1,2,3,4,5,6,7);
		Order order;
		for(int i=0; i<(indexes.length); i++){
			order = new Order(indexes[i]);
			orders.add(order);
		}
        stage.setFloor(new CompositeObject(null, vertexes, orders));
        vertex1.setReferent(stage.getFloor());
        vertex2.setReferent(stage.getFloor());
        vertex3.setReferent(stage.getFloor());
        vertex4.setReferent(stage.getFloor());
        
        vertex5.setReferent(stage.getFloor());
        vertex6.setReferent(stage.getFloor());
        vertex7.setReferent(stage.getFloor());
        vertex8.setReferent(stage.getFloor());
        
		for(int i=0; i<(orders.size()); i++){
			orders.get(i).setReferent(stage.getFloor());
		}
		
        try {
				project.create();
                stage.getFloor().create();
                stage.create();
                
        		for(int i=0; i<(orders.size()); i++){
        			orders.get(i).create();
        		}
                vertex1.create();
                vertex2.create();
                vertex3.create();
                vertex4.create();
                
                vertex5.create();
                vertex6.create();
                vertex7.create();
                vertex8.create();
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
