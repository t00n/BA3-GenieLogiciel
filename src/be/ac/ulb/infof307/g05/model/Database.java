package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Vector;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.TableUtils;

public class Database<T> extends BaseDaoEnabled<T, Integer> {
	
	/**
	 *  static link to database connection source
	 */
	private final static String connectionString = "jdbc:sqlite:HomePlans.db";
	private static JdbcConnectionSource connectionSource = null;
	
	public static JdbcConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			try {
				connectionSource = new JdbcConnectionSource(connectionString);
				// create tables
				TableUtils.dropTable(connectionSource, Vertex.class, true);
				TableUtils.dropTable(connectionSource, Texture.class, true);
				TableUtils.dropTable(connectionSource, CompositeObject.class, true);
				TableUtils.dropTable(connectionSource, Stage.class, true);
				TableUtils.dropTable(connectionSource, Project.class, true);
				TableUtils.dropTable(connectionSource, Order.class, true);
				TableUtils.createTableIfNotExists(connectionSource, Project.class);
				TableUtils.createTableIfNotExists(connectionSource, Texture.class);
				TableUtils.createTableIfNotExists(connectionSource, CompositeObject.class);
				TableUtils.createTableIfNotExists(connectionSource, Stage.class);
				TableUtils.createTableIfNotExists(connectionSource, Vertex.class);
				TableUtils.createTableIfNotExists(connectionSource, Order.class);
				Project project = new Project("test project");
				project.create();
				Stage stage = new Stage(project, 0);
				Vector<Vertex> vertexes = new Vector<Vertex>();
				Vertex vertex1 = new Vertex(0,0,0);
				vertexes.add(vertex1);
				Vertex vertex3 = new Vertex(0,2,0);
				vertexes.add(vertex3);
				Vertex vertex2 = new Vertex(0,2,2);
				vertexes.add(vertex2);
				Vertex vertex4 = new Vertex(0,0,2);
				vertexes.add(vertex4);
				
				Vector<Order> orders = new Vector<Order>();
				Order order1 = new Order(0);
				Order order2 = new Order(1);
				Order order3 = new Order(2);
				Order order4 = new Order(3);
				Order order5 = new Order(2);
				Order order6 = new Order(0);
				orders.add(order1);
				orders.add(order2);
				orders.add(order3);
				orders.add(order4);
				orders.add(order5);
				orders.add(order6);
				stage.floor = new CompositeObject(null, vertexes);
				stage.floor.setMeshOrder(orders);
				vertex1.setReferent(stage.floor);
				vertex2.setReferent(stage.floor);
				vertex3.setReferent(stage.floor);
				vertex4.setReferent(stage.floor);
				order1.referent = stage.floor;
				order2.referent = stage.floor;
				order3.referent = stage.floor;
				order4.referent = stage.floor;
				order5.referent = stage.floor;
				order6.referent = stage.floor;
				try {
					stage.floor.create();
					stage.create();
					order1.create();
					order2.create();
					order3.create();
					order4.create();
					order5.create();
					order6.create();
					vertex1.create();
					vertex2.create();
					vertex3.create();
					vertex4.create();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectionSource;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Dao<T, Integer> getDao(Class<?> klass) {
		try {
			return (Dao<T, Integer>) DaoManager.createDao(Database.getConnectionSource(), klass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Database() {
		try {
			this.setDao((Dao<T, Integer>) DaoManager.createDao(Database.getConnectionSource(), this.getClass()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
