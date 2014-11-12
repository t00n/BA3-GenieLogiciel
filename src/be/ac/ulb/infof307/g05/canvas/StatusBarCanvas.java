package be.ac.ulb.infof307.g05.canvas;

import javax.swing.JLabel;
import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventController;

import com.jme3.math.Vector3f;

public class StatusBarCanvas extends AbstractCanvas<JPanel> {

	private JLabel _label = new JLabel(" ");
	
	public StatusBarCanvas(EventController eventController){
		_panel = new JPanel();
		_panel.add(_label);

		_eventController = eventController;
	}
	
	public void update(){
		Vector3f position = _eventController.getCursor();
		_label.setText(String.format("X(width):%-20.3f Y(height):%-20.3f Z(depth):%-20.3f",position.getX(), position.getY(), position.getZ()));
	}
}