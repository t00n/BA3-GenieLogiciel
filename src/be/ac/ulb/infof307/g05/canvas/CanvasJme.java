package be.ac.ulb.infof307.g05.canvas;

import java.awt.Canvas;

import com.jme3.system.JmeCanvasContext;

import be.ac.ulb.infof307.g05.EventControler;
import be.ac.ulb.infof307.g05.canvas.jme.JmeWorld;


public class CanvasJme extends AbstractCanvas<Canvas> {
	
	private JmeWorld _jmeWorld = new JmeWorld();
	
	public CanvasJme(EventControler eventControler){
		/** constructor **/
		_jmeWorld.createCanvas();
		_panel = (Canvas) ((JmeCanvasContext) _jmeWorld.getContext()).getCanvas();
		_jmeWorld.startCanvas();
	}
}
