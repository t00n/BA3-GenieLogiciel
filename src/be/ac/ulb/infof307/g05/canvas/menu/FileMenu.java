package be.ac.ulb.infof307.g05.canvas.menu;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventControler;


public class FileMenu extends AbstractMenu {
	
	public FileMenu(EventControler eventControler){
		this.setText("File");
		_eventControler = eventControler;
		createItems();
		appendItems();
	}
	
	protected void createItems(){
		_items.add(new JMenuItem("New"));
		_items.add(new JMenuItem("Open File.."));
		_items.add(new JMenuItem("Save"));
	}
}
