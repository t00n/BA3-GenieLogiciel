package be.ac.ulb.infof307.g05.view;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;

public class Polyhedron {
	List<Vector3f> downSide;
	List<Vector3f> upSide;
	ArrayList<ArrayList<Vector3f>> sides;
	
	Vector3f[] vectors;
	int[] meshOrder;
	
	public Polyhedron(ArrayList<Vector3f> vectors) {
		// create all polygons
		this.downSide = vectors.subList(0, (vectors.size()/2)-1);
		this.upSide = vectors.subList(vectors.size()/2, vectors.size()-1);
		this.sides = new ArrayList<ArrayList<Vector3f>>();
		for (int i = 0; i < this.downSide.size(); ++i) {
			this.sides.add(new ArrayList<Vector3f>());
			this.sides.get(i).add(this.downSide.get(i));
			this.sides.get(i).add(this.downSide.get((i+1) % this.downSide.size()));
			this.sides.get(i).add(this.upSide.get((i+1) % this.downSide.size()));
			this.sides.get(i).add(this.upSide.get(i));
		}
		// copy in Vector3f[]
		this.vectors = new Vector3f[vectors.size()];
		int index = 0;
		for (Vector3f vector : vectors) {
			this.vectors[index++] = vector;
		}
		// build mesh order
		int[] orderDown = new Polygon(this.downSide).getMeshOrder();
		int[] orderUp = new Polygon(this.upSide).getMeshOrder();
		for (int i = 0; i < orderUp.length; ++i) {
			orderUp[i] += this.downSide.size();
		}
		int[][] orderSides = new int[this.sides.size()][];
		for (int i = 0; i < this.sides.size(); ++i) {
			orderSides[i] = new Polygon(this.sides.get(i)).getMeshOrder();
			orderSides[i][0] += i;
			orderSides[i][1] = (orderSides[i][1] + i) % this.downSide.size();
			orderSides[i][2] = ((orderSides[i][2] + (this.downSide.size()-1) + i) % this.downSide.size()) + this.downSide.size();
			orderSides[i][3] += i;
			orderSides[i][4] = ((orderSides[i][4] + (this.downSide.size()-1) + i) % this.downSide.size()) + this.downSide.size();
			orderSides[i][5] += i+this.downSide.size()-3;
		}
		this.meshOrder = new int[orderDown.length + orderUp.length + orderSides.length*orderSides[0].length];
		index = 0;
		for (int i = 0; i < orderDown.length; ++i) {
			this.meshOrder[index++] = orderDown[i];
		}
		for (int i = 0; i < orderUp.length; ++i) {
			this.meshOrder[index++] = orderUp[i];
		}
		for (int i = 0; i < orderSides.length; ++i) {
			for (int j = 0; j < orderSides[i].length; ++j) {
				this.meshOrder[index++] = orderSides[i][j];
			}
		}
	}
	
	public Vector3f[] getVectors() {
		return this.vectors;
	}
	
	public int[] getMeshOrder() {
		return this.meshOrder;
	}
}
