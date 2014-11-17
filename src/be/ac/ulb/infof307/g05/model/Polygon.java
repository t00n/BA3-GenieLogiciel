package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.math.Vector3f;


public class Polygon extends SceneObject {
	
	public Polygon(Vector<Vector3f> vectors, String id){
		_vectors = vectors;
		_id = id;
		buildMeshOrder();
	}

	@Override
	protected void buildMeshOrder() {
		
	}

}
