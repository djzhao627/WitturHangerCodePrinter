package com.djzhao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLUtil {

//		private static final String URL = "jdbc:mysql://127.0.0.1:3306/schindler";
	private static final String URL = "jdbc:mysql://172.21.2.78:3306/schindler";

	private static final String USER = "root";

	private static final String PASSWORD = "888888";

	private static Connection conn = null;

	public static Connection getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) throws Exception {

		getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select * from user");
		System.out.println(">>>>>>>>>");
		while (rs.next()) {
			System.out.println(rs.getObject(1));
		}

	}

}
