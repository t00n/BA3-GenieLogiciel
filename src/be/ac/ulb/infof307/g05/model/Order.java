package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "orders")
public class Order extends Database<Order> {
	protected Order() {
		
	}
	
	public Order(Integer order) {
		this.order = order;
	}

	@DatabaseField (generatedId = true)
	protected int id_order;
	
	@DatabaseField (canBeNull = false)
	protected Integer order;
	
	public Integer getOrder() { return this.order; }
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject referent;
}
