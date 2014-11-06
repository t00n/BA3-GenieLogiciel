package be.ac.ulb.infof307.g05.canvas;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import be.ac.ulb.infof307.g05.EventControler;
import be.ac.ulb.infof307.g05.canvas.menu.AbstractMenu;
import be.ac.ulb.infof307.g05.canvas.menu.EditMenu;
import be.ac.ulb.infof307.g05.canvas.menu.FileMenu;
import be.ac.ulb.infof307.g05.canvas.menu.ViewMenu;


public class MenuBarCanvas extends AbstractCanvas<JMenuBar>{
	
	private Vector<AbstractMenu> _menu = new Vector<AbstractMenu>();
	
	
	public MenuBarCanvas(EventControler eventControler){
		_panel = new JMenuBar();
		
		_menu.add(new FileMenu(eventControler));
		_menu.add(new EditMenu(eventControler));
		_menu.add(new ViewMenu(eventControler));
		
		appendMenu();
	}

	protected void appendMenu(){
		for(JMenu menu:_menu){
			_panel.add(menu);
		}
	}
}
