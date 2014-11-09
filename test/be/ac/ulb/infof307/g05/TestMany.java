package be.ac.ulb.infof307.g05;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "test_manys")
public class TestMany {
	public TestMany() {
		
	}

	@DatabaseField (generatedId = true)
	private int id_testMany;
	
	@ForeignCollectionField(eager = true)
	private
    ForeignCollection<TestORM> testOrms;
	
	public Integer getId_testMany() {
		return id_testMany;
	}

	public ForeignCollection<TestORM> getTestOrms() {
		return testOrms;
	}

}
