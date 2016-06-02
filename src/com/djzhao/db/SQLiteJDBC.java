package com.djzhao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBC {

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\toolsZ\\printeradjustment.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return connection;
	}

	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:C:\\toolsZ\\schindler.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		try {
			stmt = c.createStatement();
			// 创建一个数据库的表
			String sql = "CREATE TABLE printRecord " + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " productName TEXT NOT NULL, " + " release TEXT NOT NULL, " + " revision TEXT NOT NULL, "
					+ " identificationNo TEXT NOT NULL, " + " serialNo TEXT NOT NULL, "
					+ " batchNo TEXT DEFAULT '---' NOT NULL, "
					+ " manufacture TEXT DEFAULT 'WITTUR Elevator Components(Suzhou)Co.,LTD.215214SuzhouCN' NOT NULL, "
					+ " importer TEXT, " + " status INTEGER DEFAULT 1)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Table created successfully");
	}
}