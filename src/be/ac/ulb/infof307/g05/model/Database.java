package be.ac.ulb.infof307.g05.model;

import java.util.HashMap;

public class Database {
	private HashMap<Integer, Stage> m_stages;
	
	public Database() {
		m_stages = new HashMap<Integer, Stage>();
	}
	
	public void addStage(int stageNumber, Stage stage) {
		if (m_stages.containsKey(stageNumber)) {
			// ERROR ! TODO
		} else {
			m_stages.put(stageNumber, stage);
		}
	}
	
	public Stage getStage(int stageNumber) {
		return m_stages.get(stageNumber);
	}
	
}
