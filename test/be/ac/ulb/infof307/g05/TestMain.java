package be.ac.ulb.infof307.g05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;

import be.ac.ulb.infof307.g05.model.*;

public class TestMain {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testORM() throws SQLException {
		// create project
		Project project = new Project("new project");
		Stage stage = new Stage(project, 0, 3);
		
		// create a hierarchy of objects
		Vertex vertex = new Vertex(0, 0, 0);
		Mesh mesh = new Mesh("wall", "resources/wall.obj");
		CompositeObject compositeObject1 = new CompositeObject(stage, vertex, vertex, mesh);
			CompositeObject compositeObject2 = new CompositeObject(compositeObject1, vertex, vertex, mesh);
				SimpleObject simpleObject1 = new SimpleObject(compositeObject2, vertex, vertex, mesh);
				CompositeObject compositeObject5 = new CompositeObject(compositeObject2, vertex, vertex, mesh);
			CompositeObject compositeObject3 = new CompositeObject(compositeObject1, vertex, vertex, mesh);
			SimpleObject simpleObject2 = new SimpleObject(compositeObject1, vertex, vertex, mesh);
		CompositeObject compositeObject4 = new CompositeObject(stage, vertex, vertex, mesh);

		// save
		project.create();
		stage.create();
		vertex.create();
		mesh.create();
		compositeObject1.create();
		compositeObject2.create();
		compositeObject3.create();
		compositeObject4.create();
		compositeObject5.create();
		simpleObject1.create();
		simpleObject2.create();
		
		// load
		Project projectLoad = (Project) Project.getDao(Project.class).queryForId(1);
		ForeignCollection<Stage> stagesLoad = projectLoad.getStages();
		// another way to add a Stage to the Project
		stagesLoad.add(new Stage(projectLoad, 1, 3));
		stagesLoad.updateAll();
		Stage stageLoad = (Stage) stagesLoad.toArray()[0];
	}
}
