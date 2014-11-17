package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.math.Vector3f;


public class Polygon extends CompositeObject {
	
	public Polygon(Vector<Vertex> vectors){
		init(vectors);
		buildMeshOrder();
	}
	
	protected void init(Vector<Vertex> vectors){
		positions = vectors;
	}

	@Override
	protected void buildMeshOrder(){
		int offset = positions.size()/2;
		int idx = 0;
		meshOrder = new Vector<Order>(2*(offset-2)*3 + offset*2*3); //2 horizontal face each made by (offset-2) triangles + offset vertical face each made by 2 triangles
		
		for(int h_face=0; h_face<2; ++h_face){
			for(int triangle=0; triangle<(offset-2); ++triangle){
				meshOrder.add(idx++, new Order(offset*h_face));
				meshOrder.add(idx++, new Order(offset*h_face + triangle+1));
				meshOrder.add(idx++, new Order(offset*h_face + triangle+2));
			}
		}
		for(int v_face=0; v_face<offset; ++v_face){
			for(int triangle=0; triangle<2; ++triangle){
				meshOrder.add(idx++, new Order(v_face));
				meshOrder.add(idx++, new Order(offset+v_face+1));
				meshOrder.add(idx++, new Order((int) (v_face+Math.pow(offset,triangle))));
			}
		}
		
	}

}
