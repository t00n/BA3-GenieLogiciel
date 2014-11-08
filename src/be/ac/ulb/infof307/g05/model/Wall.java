package be.ac.ulb.infof307.g05.model;

import com.jme3.math.Vector2f;

/**
 * Class of the house's walls. The wall doesn't have to be on the ground, to give more flexibility to
 * the end user.
 * @author segolene & daniele
 * @version 0.1
 */
public class Wall extends Component {
	
	private Vector2f start;
	private Vector2f end;
	private int thickness;
	private int height;
	
	/**
	 * @param start
	 * 			Coordinates of the beginning of the wall.
	 * @param end
	 * 			Coordinates of the end of the wall.
	 * @param thickness
	 * 			Thickness of the wall.
	 * @param height
	 * 			Height of the wall. 			
	 */
	public Wall(Vector2f start, Vector2f end, int thickness, int height) {

		this.start = start;
		this.end = end;
		this.thickness = thickness;
		this.height = height;
	}

	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public Vector2f getEnd() {
		return end;
	}

	public void setEnd(Vector2f end) {
		this.end = end;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
