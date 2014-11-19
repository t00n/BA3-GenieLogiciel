package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
@DatabaseTable (tableName = "orders")
public class Order extends Database<Order> {
	
	/**
	 * Instantiates a new order.
	 */
	protected Order() {
		
	}
	
	/**
	 * Instantiates a new order.
	 *
	 * @param referent the referent
	 * @param order the order
	 */
	public Order(CompositeObject referent, Integer order) {
		this.referent = referent;
		this.order = order;
		this.isNew = true;
	}

	public Integer getOrder() { return this.order; }

	/** The id_order. */
	@DatabaseField (generatedId = true)
	protected int id_order;
	
	/** The order. */
	@DatabaseField (canBeNull = false)
	protected Integer order;
	
	/** The referent. */
	@DatabaseField (canBeNull = false, foreign = true)
	private CompositeObject referent;
}
