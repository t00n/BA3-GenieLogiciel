package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.math.Vector3f;


public class Polygon extends JmeCompositeObject {

	public Polygon(Vector<Vertex> vectors) {
		super(vectors);
		buildMeshOrder();
	}

	@Override
	protected void buildMeshOrder(){
		int offset = this.getPositions().size()/2;
		int idx = 0;
		this.setMeshOrder(new Vector<Order>(2*(offset-2)*3 + offset*2*3)); //2 horizontal face each made by (offset-2) triangles + offset vertical face each made by 2 triangles
		
		for(int h_face=0; h_face<2; ++h_face){
			for(int triangle=0; triangle<(offset-2); ++triangle){
				this.getMeshOrder().add(new Order(offset*h_face));
				this.getMeshOrder().add(new Order(offset*h_face + triangle+1));
				this.getMeshOrder().add(new Order(offset*h_face + triangle+2));
			}
		}
		for(int v_face=0; v_face<offset; ++v_face){
			for(int triangle=0; triangle<2; ++triangle){
				this.getMeshOrder().add(new Order(v_face));
				this.getMeshOrder().add(new Order(offset+v_face+1));
				this.getMeshOrder().add(new Order((int) (v_face+Math.pow(offset,triangle))));
			}
		}
		
	}

}
