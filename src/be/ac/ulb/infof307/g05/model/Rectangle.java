package be.ac.ulb.infof307.g05.model;

import java.util.Vector;
import java.lang.Math;

import com.jme3.math.Vector3f;


public class Rectangle extends Polygon {

	public Rectangle(Vector<Vector3f> vectors, String id){
		super(vectors, id);
	}
	
	@Override
	protected void init(Vector<Vector3f> vectors, String id){
		_vectors = vectors; //FIXME construct vector
		_id = id;
	}
}
