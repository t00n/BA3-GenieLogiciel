package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.math.Vector2f;

/**
 * Class building the ground of the floors in the house.
 * @author segolene & daniele
 * @version 0.1
 */
public class Ground extends Component {
	
	private Vector<Vector2f> m_coords;
	
	public Ground(Vector<Vector2f> coords) {
		m_coords = coords;
	}

}
