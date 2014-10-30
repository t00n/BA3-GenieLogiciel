package be.ac.ulb.infof307.g05.canvas;

import java.awt.Color;

import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventControler;

//import com.jme3.system.JmeCanvasContext;
//import com.jme3.system.AppSettings;


public class CanvasJme extends AbstractCanvas<JPanel> {
	
	public CanvasJme(EventControler eventControler){
		/** constructor **/
		_panel = new JPanel();
		_panel.setBackground(Color.black);
	}
	
	
	/*	private void appendJmeCanvas(){
	AppSettings settings = new AppSettings(true);
	settings.setWidth(640);
	settings.setHeight(480);
	_eventManager.setSettings(settings);
	
	_eventManager.createCanvas();
	JmeCanvasContext ctx = (JmeCanvasContext) _eventManager.getContext();
	ctx.setSystemListener(_eventManager);
	_panel.add(ctx.getCanvas());
	_eventManager.startCanvas();
}
*/
}
