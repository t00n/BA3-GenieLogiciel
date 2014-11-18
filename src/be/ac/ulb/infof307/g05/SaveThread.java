package be.ac.ulb.infof307.g05;

import java.util.Date;

import be.ac.ulb.infof307.g05.model.Project;

public class SaveThread extends Thread {
	private Project _project;
	private final long timeToSleep = 60000; // 1 minute
	
	public SaveThread(Project project) {
		this._project = project;
	}
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				long diff = 0;
				Thread.sleep(timeToSleep - diff);
				Date oldTime = new Date();
				this._project.save();
				Date currentTime = new Date();
				diff = currentTime.getTime() - oldTime.getTime();
			}
		}
		catch (InterruptedException e) {
			//FIXME what if it is interrupted during a save() ?
			Thread.currentThread().interrupt();
		}
	}
}
