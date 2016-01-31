package com.technosoft.auto.framework.chunks;

import java.sql.*;

public class DbUtils {

	/*
	 * This method will connect to QA Database
	 * 
	 * SELECT * FROM pg_catalog.pg_tables - returns all tables
	 * 
	 * 
	 * Author: Michael Ovchinnikov
	 * Last modified: 12/15/2015
	 */

	    public static Connection connectToDb() {
	    	
	    	String dbURL = "jdbc:postgresql://qa.cxcvsj3v4moe.us-east-1.rds.amazonaws.com:5432/rsrvqa";
	    	String dbUsername = "reserve";
	    	String dbPassw = "Src9MTMesCXBvjGL";
	    	Connection c = null;
	    	
	    	System.out.println("-------- Testing PostgreSQL JDBC Connection ------------");
	    	
	    	// Registering JDBC driver
			try {
				Class.forName("org.postgresql.Driver");
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Unable to load PostgreSQL JDBC Driver. Include in your library path!");
				e.printStackTrace();
				return c;
			}

			System.out.println("-------- PostgreSQL JDBC Driver Registered! ------------");
			
			// Connecting to the database
			try {
				c = DriverManager.getConnection(dbURL, dbUsername, dbPassw);
			} 
			catch (SQLException e) {
				System.out.println("-------- Connection Failed! Check output console ------------");
				// e.printStackTrace();
				System.out.println("SQLException: " + e.getMessage());
				return c;
			}
			
			// Check the connection
			if (c != null) {
				System.out.println("-------- Successfully connected to the Database --------");
			
			} else {
				System.out.println("-------- Failed to connect! --------");
			}
			
			return c;
	    }

	/*
	 * Below method will get the phone number value from the DB
	 * 
	 * Statement:
	 * SELECT PHONE FROM phone_counter WHERE id = 1;
	 * 
	 * Author: Michael Ovchinnikov
	 * Last modified: 12/15/2015
	 */
	    public static String getCounter (String counterName) {
	    	String counter = null;
			Connection c = connectToDb();
	    	Statement stmt = null;
			
	    	try {
				// Execute statement
	    		stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT VALUE FROM counters_table WHERE name = '" + counterName + "';" );
				
				// Loop throught the rows (There shoulb be just one)
				while ( rs.next() ) {
					counter = rs.getString("value");
					System.out.printf("Extracted counter value is: %s%n", counter);
				}
				
				// Closing connections
				rs.close();
		        stmt.close();
		        c.close();
			
	    	} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());		
			}
	    	
	    	return counter;
	    }
	    
	    
	/*
	 * Below method will set the phone number value and save it to the DB
	 * New value is passed as an argument
	 * 
	 * Statement:
	 * UPDATE counters_table SET value = '0075' WHERE name = 'phone';
	 * 
	 * Author: Michael Ovchinnikov
	 * Last modified: 12/15/2015
	 */
	    public static void setCounter (String counterName, String newValue) {

	    	Connection c = connectToDb();
	    	Statement stmt = null;
	    	String sql = "UPDATE counters_table SET value = '" + newValue + "' WHERE name = '" + counterName + "';";
	    	System.out.println("------------Setting up new counter value in the remote DB:");
	    	
	    	try {
				c.setAutoCommit(false);
				stmt = c.createStatement();
				stmt.executeUpdate(sql);
				c.commit();
				
				// Closing connections
				stmt.close();
		        c.close();
			
	    	} catch (SQLException e) {
	    		System.out.println("SQLException: " + e.getMessage());	
	    	}

	    }
	
}
