/**
 * 
 */
package be.ac.ulb.infof307.g05.controller;

import java.util.Vector;

import be.ac.ulb.infof307.g05.model.Ground;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Wall;

import com.jme3.math.Vector2f;

/**
 * @author segolene
 *
 */
public class CClosedPoly2D {
	
	private Stage m_actualStage;
	private Vector<Vector2f> m_coord;
	private String m_type;

	
	public void setStage(Stage stage) {
		m_coord = new Vector<Vector2f>();
		m_actualStage = stage;
	}

	public void setType(String type) {
		m_type = type;
	}
	
	public void addCoord(Vector2f coord) {
		System.out.println("clic " + coord);
		m_coord.add(coord);
		System.out.println("lalalaalalalalala");
	}
	
	public void makePoly() {
		System.out.println("hello");
		if (m_type == "wall") {
			for (int i = 0; i<m_coord.size(); i++) {
					m_actualStage.add(new Wall(m_coord.get(i), m_coord.get(i+1)));
			}
			if (m_coord.size() != 2) // si on mets que 2 points et on valide : un seul mur créé.
				m_actualStage.add(new Wall(m_coord.get(m_coord.size()-1), m_coord.get(0)));
		} else if (m_type == "ground") {
			m_actualStage.add(new Ground(m_coord));
		}
	}
}
