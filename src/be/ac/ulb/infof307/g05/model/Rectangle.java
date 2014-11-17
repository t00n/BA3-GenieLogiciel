package be.ac.ulb.infof307.g05.model;

import java.util.Vector;
import java.lang.Math;

import com.jme3.math.Vector3f;


public class Rectangle extends Polygon {

	public Rectangle(CompositeObject parent, Vector<Vertex> vectors){
		super(parent, vectors);
	}
	
	@Override
	protected void init(Vector<Vertex> vectors){
		this.setPositions(vectors); //FIXME construct vector
	}
}
