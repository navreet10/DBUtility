package DBUtilityTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import DBUtility.DBUtility;

public class DBTest {

	@Test
	public void testSelect() {
		String query = "SELECT firstname FROM CUSTOMERS where lastname = ?";
		List<String> params = new ArrayList<String>();
		params.add(0, "Kaur");
		List<String> types = new ArrayList<String>();
		types.add(0, "String");
		Map<Integer,List<String>> map = DBUtility.selectData(query, params, types);
		assertTrue(map.get(0).get(0).equals("Navreet"));
	}
	
	@Test
	public void testUpdate() {
		String query = "Update CUSTOMERS set streetaddress = ?  where lastname = ?";
		List<String> params = new ArrayList<String>();
		params.add(0, "Hullu lullu street");
		params.add(1, "Kaur");
		List<String> types = new ArrayList<String>();
		types.add(0, "String");
		types.add(1, "String");
		int rs = DBUtility.updateData(query, params, types);
		assertTrue(rs ==1);
		
	}

}
