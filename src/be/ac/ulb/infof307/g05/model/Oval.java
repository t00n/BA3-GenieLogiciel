package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class Oval extends SceneObject {

	public Oval(Vector<Vector3f> vectors, String id){
		_vectors = vectors;
		_id = id;
		buildMeshOrder();
	}
	
	@Override
	protected Geometry getJmeGeometry(AssetManager assetManager) {
		return null;
	}

	@Override
	protected void buildMeshOrder() {
		
	}

}
