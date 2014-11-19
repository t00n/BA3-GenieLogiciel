package be.ac.ulb.infof307.g05;

import java.util.Date;

import be.ac.ulb.infof307.g05.model.Project;

/**
 * The Class SaveThread to make saves all the minutes.
 */
public class SaveThread extends Thread {
	
	/** The _project. */
	private Project _project;
	
	/** The time to sleep. */
	private final long timeToSleep = 60000; // 1 minute
	
	/**
	 * Instantiates a new save thread.
	 *
	 * @param project the project
	 */
	public SaveThread(Project project) {
		this._project = project;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
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
