package be.ac.ulb.infof307.g05;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Static_ID;
import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Texture;
import be.ac.ulb.infof307.g05.model.Vertex;
import be.ac.ulb.infof307.g05.view.MainWindow;

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
		JdbcConnectionSource connectionSource = Database.getConnectionSource();
		try {
			TableUtils.createTableIfNotExists(connectionSource, Project.class);
			TableUtils.createTableIfNotExists(connectionSource, Texture.class);
			TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
			TableUtils.createTableIfNotExists(connectionSource, Static_ID.class);
			TableUtils.createTableIfNotExists(connectionSource, Stage.class);
			TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainWindow("Projet Génie logiciel 2014-2015");
			}
		});
	}
}
