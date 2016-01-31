package com.technosoft.auto.tests;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.technosoft.auto.framework.chunks.DatabaseUtil;



public class SampleDataBaseTest {
	public static void main(String[] args) throws SQLException {
		DatabaseUtil.getMySqlConnection();
		DatabaseUtil.executeSQLquerry("SELECT * FROM `Database`.EmployeeInfo;");
		DatabaseUtil.getAllValuesBasedOnColumnLabel();
	//	System.out.println(DatabaseUtil.getValueFromColumn("surname"));
		DatabaseUtil.closeSqlConnection();
	}

}
