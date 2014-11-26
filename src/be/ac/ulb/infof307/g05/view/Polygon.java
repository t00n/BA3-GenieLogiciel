package be.ac.ulb.infof307.g05.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import be.ac.ulb.infof307.g05.model.Vertex;

import com.jme3.math.Vector3f;

public class Polygon {
	ArrayList<Vector3f> vertices;
	Vector3f[] vectors;
	int[] meshOrder;
	
	
	public Polygon(ArrayList<Vector3f> vertices) {
		this.vertices = vertices;
		this.buildMeshOrder();
	}
	
	public Vector3f[] getVectors() { return this.vectors; }
	public int[] getMeshOrder() { return this.meshOrder; }
	
	protected void buildMeshOrder() {
		// build vectors
		this.vectors = new Vector3f[vertices.size()];
		int i = 0;
		for (Vector3f vec : vertices) {
			this.vectors[i] = vec;
			++i;
		}
		int orderIndex = 0;
		this.meshOrder = new int[(this.vectors.length-2) * 3];
		for (i = 0; i < this.vectors.length - 2; ++i) {
			this.meshOrder[orderIndex++] = 0;
			for (int j = 1; j < 3; ++j) {
				this.meshOrder[orderIndex++] = (i+j);
			}
		}
	}
}
