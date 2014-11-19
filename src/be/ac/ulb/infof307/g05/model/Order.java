package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "orders")
public class Order extends Database<Order> {
	protected Order() {
		
	}
	
	public Order(CompositeObject referent, Integer order) {
		this.referent = referent;
		this.order = order;
	}

	public Integer getOrder() { return this.order; }

    @Override
    public void save() {
		try {
			this.update();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
    @Override
    public void createAll() {
		try {
			this.create();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }

	@DatabaseField (generatedId = true)
	protected int id_order;
	
	@DatabaseField (canBeNull = false)
	protected Integer order;
	
	@DatabaseField (canBeNull = false, foreign = true)
	private CompositeObject referent;
}
