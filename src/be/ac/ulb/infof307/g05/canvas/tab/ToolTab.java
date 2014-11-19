package be.ac.ulb.infof307.g05.canvas.tab;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import be.ac.ulb.infof307.g05.EventController;


public class ToolTab extends AbstractTab implements ItemListener {
	
	private Vector<JToggleButton> _buttons = new Vector<JToggleButton>();
	private JPanel _buttonPanel = new JPanel();
	private JPanel _optionPanel = new JPanel();
	
	
	public ToolTab(EventController eventController){
		/** constructor **/
		_eventController = eventController;
		_name = new String("Tools");

		this.setLayout(new GridLayout(3,1));
		this.add(_buttonPanel);
		this.add(new JSeparator(SwingConstants.HORIZONTAL));
		this.add(_optionPanel);

		GridLayout button_layout = new GridLayout(5,2);
		button_layout.setHgap(5);
		button_layout.setVgap(5);
		_buttonPanel.setLayout(button_layout);
		createButton("Draw");
		createButton("Pull-Push");
		createButton("Move");
		createButton("Rotate");
		createButton("Modify");
		createButton("Paint");
		
		_optionPanel.setLayout(new GridLayout(5,2));
	}
	
	private void createButton(String tool_name){
		/** this method create all tools buttons **/
		JToggleButton button = new JToggleButton(tool_name);
		_buttons.add(button);
		button.setFocusable(false);
		button.addActionListener(_eventController);
		button.addItemListener(this);
		_buttonPanel.add(button);
		_eventController.addTool(tool_name);
	}
	
	public void itemStateChanged(ItemEvent event){
		/** this method is used to set _optionPanel when a tool is selected **/
		String command = ((JToggleButton)event.getSource()).getText();
		
		if(event.getStateChange() == ItemEvent.SELECTED){
			for(JToggleButton button:_buttons){
				if(button.getText() != command)
					button.setSelected(false);
			}
			if(command == "Draw"){
				setDrawOption();
			}
		}else{
			_optionPanel.removeAll();
		}
		_optionPanel.updateUI();
	}
	
	public void setDrawOption(){
		/** this method create options for "Draw" tool in option panel **/
		JLabel shape_label = new JLabel("Shape :", SwingConstants.LEFT);
		JComboBox<String> shape_choice = new JComboBox<String>(new String[] {"Rectangle","Polygon","Oval"});
		shape_choice.setFocusable(false);
		shape_choice.addActionListener(_eventController);
		_optionPanel.add(shape_label);
		_optionPanel.add(shape_choice);
		shape_choice.setSelectedIndex(0);
		_eventController.actionPerformed(new ActionEvent(shape_choice ,ActionEvent.ACTION_PERFORMED, "comboBoxChanged"));
	}
}
