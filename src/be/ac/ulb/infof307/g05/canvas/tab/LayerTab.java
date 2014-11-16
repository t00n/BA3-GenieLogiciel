package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JButton;
import javax.swing.JComboBox;


public class LayerTab extends AbstractTab {
		
	public LayerTab(){
		/** constructor **/
		_name = new String("Layers");
		
		JButton button = new JButton("button2");
		this.add(button);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("element 1");
		comboBox.addItem("element 2");
		comboBox.addItem("element 3");
		this.add(comboBox);
	}
	
	public String getName(){
		return _name;
	}
}
