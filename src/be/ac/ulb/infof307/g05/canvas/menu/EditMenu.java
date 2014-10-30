package be.ac.ulb.infof307.g05.canvas.menu;

import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventControler;


public class EditMenu extends AbstractMenu {
	
	public EditMenu(EventControler eventControler){
		this.setText("Edit");
		_eventControler = eventControler;
		createItems();
		appendItems();
	}
	
	protected void createItems(){
		_items.add(new JMenuItem("Element 1"));
		_items.add(new JMenuItem("Element 2"));
		_items.add(new JMenuItem("Element 3"));
	}
}
