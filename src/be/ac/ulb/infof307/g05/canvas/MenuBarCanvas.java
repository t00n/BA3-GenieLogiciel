package be.ac.ulb.infof307.g05.canvas;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventController;


public class MenuBarCanvas extends AbstractCanvas<JMenuBar>{
	
	private Vector<JMenu> _menu = new Vector<JMenu>();
	

	public MenuBarCanvas(EventController eventController){
		_panel = new JMenuBar();
		_eventController = eventController;
		
		createFileMenu();
		createEditMenu();
		createViewMenu();
	}
	
	protected JMenuItem createItem(String command){
		JMenuItem item = new JMenuItem(command);
		item.addActionListener(_eventController);
		return item;
	}
	
	private void createFileMenu(){
		JMenu menu = new JMenu("File");
		_menu.add(menu);
		
		menu.add(createItem("New"));
		menu.add(createItem("Open File.."));
		menu.add(createItem("Save"));
		
		_panel.add(menu);
	}
	
	private void createEditMenu(){
		JMenu menu = new JMenu("Edit");
		_menu.add(menu);
		
		menu.add(createItem("Element 1"));
		menu.add(createItem("Element 2"));
		menu.add(createItem("Element 3"));
		
		_panel.add(menu);
	}
	
	private void createViewMenu(){
		JMenu menu = new JMenu("View");
		_menu.add(menu);

		menu.add(createItem("2D view"));
		menu.add(createItem("3D view"));
		menu.add(createItem("2D/3D view"));
		
		_panel.add(menu);
	}
}
