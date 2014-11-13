package be.ac.ulb.infof307.g05.canvas.tab;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventController;


public class Tab1 extends AbstractTab {
	
	public Tab1(EventController _eventControler){
		/** constructor **/
		_name = new String("Tab1");
		
		JPanel panel = new JPanel(new GridLayout(10,1,10,10));

		JButton sol = new JButton("Sol");
		sol.addActionListener(_eventControler);
		panel.add(sol);
		JButton murs = new JButton("Mur");
		murs.addActionListener(_eventControler);
		panel.add(murs);
		
		// Simulation de clics sur l'écran
		JButton murs1 = new JButton("Murclic1");
		murs1.addActionListener(_eventControler);
		panel.add(murs1);
		JButton murs2 = new JButton("Murclic2");
		murs2.addActionListener(_eventControler);
		panel.add(murs2);
		JButton murs3 = new JButton("Murclic3");
		murs3.addActionListener(_eventControler);
		panel.add(murs3);
		JButton murs4= new JButton("Murvalider");
		murs4.addActionListener(_eventControler);
		panel.add(murs4);
		//
		
		this.add(panel);
	}
	
}
