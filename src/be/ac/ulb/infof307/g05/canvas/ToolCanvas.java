package be.ac.ulb.infof307.g05.canvas;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.canvas.tab.AbstractTab;
import be.ac.ulb.infof307.g05.canvas.tab.Tab1;
import be.ac.ulb.infof307.g05.canvas.tab.Tab2;


public class ToolCanvas extends AbstractCanvas<JTabbedPane> {
	
	private Vector<AbstractTab> _tabs = new Vector<AbstractTab>();
	
	public ToolCanvas(EventController eventController){
		/** constructor **/
		_panel = new JTabbedPane();
		_eventController = eventController;
		
		_tabs.add(new Tab1(_eventController));
		_tabs.add(new Tab2());
		
		appendTabs();
	}
	
	private void appendTabs(){
		/** this method add all tabs to the canvas **/
		for(JPanel tab:_tabs){
			_panel.addTab(tab.getName(), tab);
		}
	}
	
}
