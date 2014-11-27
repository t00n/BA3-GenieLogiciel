package be.ac.ulb.infof307.g05.canvas;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.canvas.tab.AbstractTab;
import be.ac.ulb.infof307.g05.canvas.tab.ToolTab;
import be.ac.ulb.infof307.g05.canvas.tab.LayerTab;

/**
 * The Class TabCanvas. which take care of the differents tabs
 */
public class TabCanvas extends AbstractCanvas<JTabbedPane> {
	
	/** The _tabs. */
	private Vector<AbstractTab> _tabs = new Vector<AbstractTab>();
	private LayerTab _layerTab;
	
	/**
	 * Instantiates a new tab canvas.
	 *
	 * @param eventController the event controller
	 */
	public TabCanvas(EventController eventController){
		/** constructor **/
		_panel = new JTabbedPane();
		_eventController = eventController;
		_layerTab = new LayerTab(_eventController);
		
		_tabs.add(new ToolTab(_eventController));
		_tabs.add(_layerTab);
		
		appendTabs();
	}
	
	/**
	 * Append tabs.
	 */
	private void appendTabs(){
		/** this method add all tabs to the canvas **/
		for(JPanel tab:_tabs){
			_panel.addTab(tab.getName(), tab);
		}
	}
	
	public void update() {
		_layerTab.update();
	}
	
}
