package be.ac.ulb.infof307.g05.canvas;

import java.awt.Canvas;
import java.util.logging.Level;

import com.jme3.system.JmeCanvasContext;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.canvas.jme.JmeWorld;

/**
 * The Class CanvasJme. This class will take care of the JME canevas
 */
public class CanvasJme extends AbstractCanvas<Canvas> {
	
	/** The _jme world. */
	private JmeWorld _jmeWorld;
	
	/**
	 * Instantiates a new canvas jme.
	 *
	 * @param eventController the event controller
	 */
	public CanvasJme(EventController eventController){
		java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);
		_eventController = eventController;
		_jmeWorld = new JmeWorld(_eventController);
		_jmeWorld.createCanvas();
		_panel = (Canvas) ((JmeCanvasContext) _jmeWorld.getContext()).getCanvas();
		_jmeWorld.startCanvas();
	}
	
	/* (non-Javadoc)
	 * @see be.ac.ulb.infof307.g05.canvas.AbstractCanvas#update()
	 */
	public void update(){
		_jmeWorld.setViews(_eventController.getFlag2D(), _eventController.getFlag3D());
		_jmeWorld.simpleUpdate((float)0.0);
	}
}