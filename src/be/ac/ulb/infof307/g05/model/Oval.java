package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class Oval extends CompositeObject {

	public Oval(Vector<Vertex> vectors){
		this.positions = vectors;
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
