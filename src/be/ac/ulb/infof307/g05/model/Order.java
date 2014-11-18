package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "orders")
public class Order extends Database<Order> {
	protected Order() {
		
	}
	
	public Order(Integer order) {
		this.order = order;
	}

    @Override
    public void save() {
        try {
        	this.order = 0;
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@DatabaseField (generatedId = true)
	protected int id_order;
	
	@DatabaseField (canBeNull = false)
	protected Integer order;
	
	public Integer getOrder() { return this.order; }
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject referent;

	public void setReferent(CompositeObject referent) {
		this.referent = referent;
	}
}
