package be.ac.ulb.infof307.g05.controller;

import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Stage;

public class Controller {
	
	protected Database m_Database;
	protected Stage m_actualStage;
	
	protected CClosedPoly2D m_CbuttonClosedPoly2D;
	
	public Controller(Database database) {
		m_Database = database;
		
		m_CbuttonClosedPoly2D = new CClosedPoly2D();
	}
	
	public Database getDatabase() {
		return m_Database;
	}
	
	public void setStage(Stage stage) {
		m_actualStage = stage;
		m_CbuttonClosedPoly2D.setStage(stage);
	}
	
	public CClosedPoly2D getCClosedPoly2D() {
		System.out.println("getcclo");
		return m_CbuttonClosedPoly2D;
	}
	
	public Stage getStage() {
		return m_actualStage;
	}
	
}
