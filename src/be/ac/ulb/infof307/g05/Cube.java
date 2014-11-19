package be.ac.ulb.infof307.g05;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Vector;

import com.jme3.math.Vector3f;

/**
 * The Class Cube to make a cube in the view.
 */
public class Cube {
	
	/** The _vertexes. */
	private Collection<Vector3f> _vertexes = new Vector<Vector3f>();
	
	/** The _orders. */
	private Collection<Integer> _orders = new Vector<Integer>();
	
	public Collection<Vector3f> getVertices() { return _vertexes; }
	public Collection<Integer> getOrder() { return _orders; }
	
	/**
	 * Instantiates a new cube.
	 *
	 * @param firstPoint the first point
	 * @param secondPoint the second point
	 */
	public Cube(Vector3f firstPoint, Vector3f secondPoint) {
		float fromX = firstPoint.x;
		float fromY = firstPoint.y;
		float fromZ = firstPoint.z;
		float toX   = secondPoint.x;
		float toY   = secondPoint.y;
		float toZ   = secondPoint.z;
		
		//Coord du carré du bas du cube
		//ATTENTION: ordre d'ajout qui suit est important, car doit former un "Z" en liant les points entre eux
		_vertexes.add(firstPoint);
		_vertexes.add(new Vector3f(toX, fromY, fromZ));
		_vertexes.add(new Vector3f(fromX, fromY, toZ));
		_vertexes.add(new Vector3f(toX, fromY, toZ));
		//Coord du carré du haut du cube
		_vertexes.add(new Vector3f(fromX, toY, fromZ));
		_vertexes.add(new Vector3f(toX, toY, fromZ));
		_vertexes.add(new Vector3f(fromX, toY, toZ));
		_vertexes.add(secondPoint);
		
		//Ajout de l'ordre des Integers:
		ArrayList<Integer> cubeOrders = cubeOrder(0,1,2,3,4,5,6,7);
		for(int i=0; i<(cubeOrders.size()); i++){_orders.add(cubeOrders.get(i));}
	}
	
	/**
	 * Square order in order to organize the way the user click on screen.
	 *
	 * @param point0 the point0
	 * @param point1 the point1
	 * @param point2 the point2
	 * @param point3 the point3
	 * @return the int[]
	 */
	private int[] squareOrder(int point0, int point1, int point2, int point3){
		//Renvoie l'ordre des points de façon à pouvoir dessiner le mesh d'un carré visible des deux côtés
		int[] squareOrders = { point1,point0,point2, point2,point3,point1, 
						  	   point2,point0,point1, point1,point3,point2 };
		return squareOrders;
	}
	
	/**
	 * Cube order in order to organize the way the user click on screen.
	 *
	 * @param point0 the point0
	 * @param point1 the point1
	 * @param point2 the point2
	 * @param point3 the point3
	 * @param point4 the point4
	 * @param point5 the point5
	 * @param point6 the point6
	 * @param point7 the point7
	 * @return the array list
	 */
	private ArrayList<Integer> cubeOrder(int point0, int point1, int point2, int point3, int point4, int point5, int point6, int point7){
		ArrayList<Integer> cubeOrders = new ArrayList<Integer>();
		
		int[] cubeOrders1 = squareOrder(point0,point1,point2,point3);
		int[] cubeOrders2 = squareOrder(point4,point5,point6,point7);
		int[] cubeOrders3 = squareOrder(point1,point3,point5,point7);
		int[] cubeOrders4 = squareOrder(point3,point2,point7,point6);
		int[] cubeOrders5 = squareOrder(point2,point0,point6,point4);
		int[] cubeOrders6 = squareOrder(point0,point1,point4,point5);
		
		for(int i=0; i<(cubeOrders1.length); i++){cubeOrders.add(cubeOrders1[i]);}
		for(int i=0; i<(cubeOrders2.length); i++){cubeOrders.add(cubeOrders2[i]);}
		for(int i=0; i<(cubeOrders3.length); i++){cubeOrders.add(cubeOrders3[i]);}
		for(int i=0; i<(cubeOrders4.length); i++){cubeOrders.add(cubeOrders4[i]);}
		for(int i=0; i<(cubeOrders5.length); i++){cubeOrders.add(cubeOrders5[i]);}
		for(int i=0; i<(cubeOrders6.length); i++){cubeOrders.add(cubeOrders6[i]);}

		return cubeOrders;
	}

}
