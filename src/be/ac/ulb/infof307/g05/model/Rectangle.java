package be.ac.ulb.infof307.g05.model;

import java.util.Vector;
import java.lang.Math;

import com.jme3.math.Vector3f;


public class Rectangle extends Polygon {

	public Rectangle(Vector<Vertex> vectors){
		super(vectors);
	}
	
	@Override
	protected void init(Vector<Vertex> vectors){
		this.positions = vectors; //FIXME construct vector
	}
}
