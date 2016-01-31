package com.technosoft.auto.framework.chunks;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.technosoft.auto.framework.chunks.Settings.DbLoginCredintials;

/**
 * This class holds all Database Realated Util
 * 
 * @author mmuntakim
 *
 */

public class DatabaseUtil {

	static Connection conn = null;
	static ResultSet result = null;

	/**
	 * Description This method gets sql Connection with URL, Database Name,
	 * username and password
	 * 
	 * @author mmuntakim
	 */
	public static void getMySqlConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(
					DbLoginCredintials.URL + DbLoginCredintials.DATABASE_NAME,
					DbLoginCredintials.USERNAME, DbLoginCredintials.PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Description: This method is used to executes any specific query
	 * 
	 * @author mmuntakim
	 * @param enterQuerry
	 * @throws SQLException
	 */
	public static void executeSQLquerry(String enterQuerry) throws SQLException {

		try {
			String sqlQuerry = enterQuerry;
			Statement statement = (Statement) conn.createStatement();
			result = statement.executeQuery(sqlQuerry);
		} catch (SQLException e) {
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();
		}
//		while(result.next()){
//			int idEmployeeInfo = result.getInt("idEmployeeInfo");
//			int age = result.getInt("age");
//			String name = result.getString("name");
//			String surname = result.getString("surname");
//			
//			 //Display values
//	         System.out.print("ID: " + idEmployeeInfo);
//	         System.out.print(", Age: " + age);
//	         System.out.print(", First: " + name);
//	         System.out.println(", Last: " + surname);
		
		
	}

	/**
	 * Description: Returns the values from specified column
	 * 
	 * @author mmuntakim
	 * @param enterColumnName
	 * @return
	 * @throws SQLException
	 */
	public static String getValueFromColumn(String enterColumnName)
			throws SQLException {
		result.next();
		String value = result.getString(enterColumnName);
		return value;

	}
	
	/**
	 * Description: This method return full table based on column
	 * @throws SQLException 
	 */
	
	public static void getAllValuesBasedOnColumnLabel() throws SQLException {
		while(result.next()){
		int idEmployeeInfo = result.getInt("idEmployeeInfo");
		int age = result.getInt("age");
		String name = result.getString("name");
		String surname = result.getString("surname");
		
		 //Display values
         System.out.print("ID: " + idEmployeeInfo);
         System.out.print(", Age: " + age);
         System.out.print(", name: " + name);
         System.out.println(", surname: " + surname);
		} 
	}

	/**
	 * This method closes the sql connection
	 */

	public static void closeSqlConnection() {
		if (conn != null) {
			conn = null;
		}
	}

}
