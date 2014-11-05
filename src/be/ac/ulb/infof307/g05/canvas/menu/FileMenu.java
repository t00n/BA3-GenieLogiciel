package be.ac.ulb.infof307.g05.canvas.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import be.ac.ulb.infof307.g05.EventControler;


public class FileMenu extends AbstractMenu implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem saveMenu = new JMenuItem("Save");
	private JMenuItem openMenu = new JMenuItem("Open Project..");
	private JMenuItem newMenu = new JMenuItem("New");
	private JFileChooser fc = new JFileChooser();
	
	public FileMenu(EventControler eventControler){
		this.setText("File");
		_eventControler = eventControler;
		createItems();
		appendItems();
	}
	
	protected void createItems(){
		openMenu.addActionListener(this);
		saveMenu.addActionListener(this);
		_items.add(newMenu);
		_items.add(openMenu);
		_items.add(saveMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		//Handle open button action
		if (source == openMenu) {
			int returnVal = fc.showOpenDialog(FileMenu.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
            } else {
                System.out.println("Error opening file.");
            }
		}
		else if (source == saveMenu) {
			int returnVal = fc.showSaveDialog(FileMenu.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
            } else {
                
            }
		}
	}
}
