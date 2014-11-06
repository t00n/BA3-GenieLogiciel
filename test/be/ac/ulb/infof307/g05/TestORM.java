package be.ac.ulb.infof307.g05;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "test_orms")
public class TestORM {
	public TestORM() { // ORMlite needs a default constructor
		
	}
	
	@DatabaseField (generatedId = true) // id and auto_increment
	private int id_test;
	
	@DatabaseField (canBeNull = false)
	private String someString;

	public int getId_test() {
		return id_test;
	}

	public String getSomeString() {
		return someString;
	}

	public void setSomeString(String someString) {
		this.someString = someString;
	}
}
