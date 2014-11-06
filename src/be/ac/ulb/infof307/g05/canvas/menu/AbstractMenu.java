package be.ac.ulb.infof307.g05.canvas.menu;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventControler;

public abstract class AbstractMenu extends JMenu {

	protected EventControler    _eventControler;
	protected Vector<JMenuItem> _items = new Vector<JMenuItem>();

	
	protected void createItem(String command){
		_items.add(new JMenuItem(command));
		_items.lastElement().addActionListener(_eventControler);
	}
	
	protected void appendItems(){
		for(JMenuItem item:_items){
			this.add(item);
		}
	}
}
