package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JButton;
import javax.swing.JComboBox;


public class Tab2 extends AbstractTab {
		
	public Tab2(){
		/** constructor **/
		_name = new String("Tab2");
		
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
