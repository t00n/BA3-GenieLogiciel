package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JButton;
import javax.swing.JComboBox;

import be.ac.ulb.infof307.g05.EventControler;

public class Tab1 extends AbstractTab {
	
	public Tab1(EventControler eventControler){
		/** constructor **/
		_name = new String("Tab1");
		_eventControler = eventControler;
		
		JButton button = new JButton("button1");
		this.add(button);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("element 1");
		comboBox.addItem("element 2");
		comboBox.addItem("element 3");
		this.add(comboBox);
	}
	
}
