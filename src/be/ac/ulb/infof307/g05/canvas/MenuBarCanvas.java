package be.ac.ulb.infof307.g05.canvas;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventController;

/**
 * The Class MenuBarCanvas. Which take care of the menus
 */
public class MenuBarCanvas extends AbstractCanvas<JMenuBar>{	

	/**
	 * Instantiates a new menu bar canvas.
	 *
	 * @param eventController the event controller
	 */
	public MenuBarCanvas(EventController eventController){
		_panel = new JMenuBar();
		_eventController = eventController;
		
		createMenu("File", new String[] {"New Project","Open project..","Save", "Quit"});
		createMenu("Edit", new String[] {"Element 1", "Element 2", "Element 3"});
		createMenu("View", new String[] {"2D view","3D view","2D/3D view"});
	}
	
	/**
	 * Creates the menu.
	 *
	 * @param name the name
	 * @param items the items
	 */
	private void createMenu(String name, String[] items){
		JMenu menu = new JMenu(name);
		
		for(String item_name:items){
			JMenuItem item = new JMenuItem(item_name);
			item.addActionListener(_eventController);
			menu.add(item);
		}
		_panel.add(menu);
	}
}
