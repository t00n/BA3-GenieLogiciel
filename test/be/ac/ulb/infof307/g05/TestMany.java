package be.ac.ulb.infof307.g05;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "test_manys")
public class TestMany {
	public TestMany() {
		
	}

	@DatabaseField (generatedId = true)
	private int id_testMany;
	
	public Integer getId_testMany() {
		return id_testMany;
	}

}
