package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.math.Vector3f;


public class Polygon extends SceneObject {
	
	public Polygon(Vector<Vector3f> vectors, String id){
		init(vectors, id);
		buildMeshOrder();
	}
	
	protected void init(Vector<Vector3f> vectors, String id){
		_vectors = vectors;
		_id = id;
	}

	@Override
	protected void buildMeshOrder(){
		int offset = _vectors.size()/2;
		int idx = 0;
		_meshOrder = new int[2*(offset-2)*3 + offset*2*3]; //2 horizontal face each made by (offset-2) triangles + offset vertical face each made by 2 triangles
		
		for(int h_face=0; h_face<2; ++h_face){
			for(int triangle=0; triangle<(offset-2); ++triangle){
				_meshOrder[idx++] = offset*h_face;
				_meshOrder[idx++] = offset*h_face + triangle+1;
				_meshOrder[idx++] = offset*h_face + triangle+2;
			}
		}
		for(int v_face=0; v_face<offset; ++v_face){
			for(int triangle=0; triangle<2; ++triangle){
				_meshOrder[idx++] = v_face;
				_meshOrder[idx++] = offset+v_face+1;
				_meshOrder[idx++] = (int) (v_face+Math.pow(offset,triangle));
			}
		}
		
	}

}
