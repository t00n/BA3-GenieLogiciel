package be.ac.ulb.infof307.g05;

import be.ac.ulb.infof307.g05.MainWindow;

/**
 * The Class Main which instantiate the MainWindow.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainWindow("Projet Génie logiciel 2014-2015");
			}
		});
	}
}
