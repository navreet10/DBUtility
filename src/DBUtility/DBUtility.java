package DBUtility;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DBUtility {
	public static Connection con = null;
	public static ResultSet rs = null;
	public static PreparedStatement sql = null;
	
	
	
	public static Map<Integer,List<String>> selectData(String query, List<String> params, List<String> types) {
		Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
		try {
			Driver myDriver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(myDriver);
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			sql = con.prepareStatement(query);
			for (int i = 0; i< params.size(); i++) {
				if (types.get(i).equals("int"))
					sql.setInt(i+1, Integer.parseInt(params.get(i)));
				else if (types.get(i).equals("String")) 
					sql.setString(i+1, params.get(i));
				else if (types.get(i).equals("double")) 
					sql.setDouble(i+1, Double.parseDouble(params.get(i)));
				else if (types.get(i).equals("long")) 
					sql.setLong(i+1, Long.parseLong(params.get(i)));
				else if (types.get(i).equals("date"))  {
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
					java.util.Date date = format.parse(params.get(i));
					Date sqldate = new Date(date.getTime());
					sql.setDate(i+1, sqldate);
				} else {
					throw new SQLException("Type unavailable");
				}
			}
			rs = sql.executeQuery();
			ResultSetMetaData rsm  = rs.getMetaData();
			int cols = rsm.getColumnCount();
			int count =0;
			while (rs.next()) {
				List<String> newCol = new ArrayList<String>();
				for (int i=1;i<=cols;i++) {
					newCol.add(rs.getString(i));
				}
				map.put(count, newCol);
				count++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				sql.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return map;
	}
	
	public static int updateData(String query, List<String> params, List<String> types) {
		int res = -1;
		try {
			Driver myDriver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(myDriver);
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			sql = con.prepareStatement(query);
			for (int i = 0; i< params.size(); i++) {
				if (types.get(i).equals("int"))
					sql.setInt(i+1, Integer.parseInt(params.get(i)));
				else if (types.get(i).equals("String")) 
					sql.setString(i+1, params.get(i));
				else if (types.get(i).equals("double")) 
					sql.setDouble(i+1, Double.parseDouble(params.get(i)));
				else if (types.get(i).equals("long")) 
					sql.setLong(i+1, Long.parseLong(params.get(i)));
				else if (types.get(i).equals("date"))  {
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
					java.util.Date date = format.parse(params.get(i));
					Date sqldate = new Date(date.getTime());
					sql.setDate(i+1, sqldate);
				} else {
					throw new SQLException("Type unavailable");
				}
					
			}
			res = sql.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				sql.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return res;
	}

}
