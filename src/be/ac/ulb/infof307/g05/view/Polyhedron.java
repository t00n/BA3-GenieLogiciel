package be.ac.ulb.infof307.g05.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import be.ac.ulb.infof307.g05.model.Vertex;

import com.jme3.math.Vector3f;

public class Polyhedron {
	Collection<Vertex> vertices;
	Vector3f[] vectors;
	int[] meshOrder;
	
	
	public Polyhedron(Collection<Vertex> vertices) {
		this.vertices = vertices;
		this.build();
	}
	
	public Vector3f[] getVectors() { return this.vectors; }
	public int[] getMeshOrder() { return this.meshOrder; }
	
	protected void build() {
		// build vectors
		this.vectors = new Vector3f[this.vertices.size()];
		int i = 0;
		for (Vertex vertex : this.vertices) {
			this.vectors[i] = vertex.toVector3f();
			++i;
		}
		ArrayList<Integer> rest = new ArrayList<Integer>();
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (i = 0; i < this.vectors.length; ++i) {
			order.add(0);
			for (int j = 1; j < 3; ++j) {
				order.add((i+j) % this.vectors.length);
			}
			rest.add(i % this.vectors.length);
		}
		i = 0;
		this.meshOrder = new int[order.size()];
		for (Integer ord : order) {
			this.meshOrder[i] = ord;
			++i;
		}
	}
}
