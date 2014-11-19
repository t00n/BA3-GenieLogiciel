package be.ac.ulb.infof307.g05.canvas;

import javax.swing.JLabel;
import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventController;

import com.jme3.math.Vector3f;

/**
 * The Class StatusBarCanvas. which take care of the positions of the cursor
 */
public class StatusBarCanvas extends AbstractCanvas<JPanel> {

	/** The _label. */
	private JLabel _label = new JLabel(" ");
	
	/**
	 * Instantiates a new status bar canvas.
	 *
	 * @param eventController the event controller
	 */
	public StatusBarCanvas(EventController eventController){
		_panel = new JPanel();
		_panel.add(_label);

		_eventController = eventController;
	}
	
	/* (non-Javadoc)
	 * @see be.ac.ulb.infof307.g05.canvas.AbstractCanvas#update()
	 */
	public void update(){
		Vector3f position = _eventController.getCursor();
		if(position.getY() >= 0)
			_label.setText(String.format("X(width):%-20.3f Y(height):%-20.3f Z(depth):%-20.3f",position.getX(), position.getY(), position.getZ()));
		else
			_label.setText(String.format("X(width):%-20s Y(height):%-20s Z(depth):%-20s","N/C", "N/C", "N/C"));

	}
}