package be.ac.ulb.infof307.g05;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "test_onetoones")
public class TestOneToOne {
	public TestOneToOne() {
		
	}
	
	public TestORM getTestORM() {
		return testORM;
	}

	public void setTestORM(TestORM testORM) {
		this.testORM = testORM;
	}

	@DatabaseField (generatedId = true)
	private int id_testOneToOne;
	
	@DatabaseField (foreign = true, unique = true)
	private TestORM testORM;
}
