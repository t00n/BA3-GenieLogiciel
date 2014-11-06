package be.ac.ulb.infof307.g05;

import be.ac.ulb.infof307.g05.MainWindow;

public class Main {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainWindow("Projet G�nie logiciel 2014-2015");
			}
		});
	}
}
