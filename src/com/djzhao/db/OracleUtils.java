package com.djzhao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class OracleUtils {

	private static final String driverUrl = "oracle.jdbc.driver.OracleDriver";

	// private static final String url =
	// "jdbc:oracle:thin:@192.168.1.106:1521:orcl";
//	private static final String url = "jdbc:oracle:thin:@172.21.1.61:1521:orcl";
	 private static final String url =
	 "jdbc:oracle:thin:@172.21.3.131:1521:BAAN";// orcl

	 private static final String username = "baandb";// system
//	private static final String username = "system";

	 private static final String password = "change_baan_916";// Qwe12345
//	private static final String password = "Qwe12345";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driverUrl);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("Oracle Stop time : " + new Date() + ">>>" + e.getMessage());
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			connection = OracleUtils.getConnection();
			System.out.println("get conn:" + connection);
		}
		return connection;
	}

	// 测试Oracle连接是否成功
	public static void main(String[] args) throws SQLException {
		long start = System.currentTimeMillis();
		Connection connection = OracleUtils.getConnection();
		System.out.println("连接成功：" + connection);
		Statement statement = connection.createStatement();
		String sql = "select ID, BOXID, ITEM, DSCE, ORNO, PONO, CODE, PYNDATE, STATE, STATION, NINECODE, SNO from cbarcode_pendant";
		// 测试读取时间
		// sql = "SELECT ID, BOXID, CPRJ, ORNO, CODE, ITEM, DSCE, T, TTYPE,
		// PYNDATE, CAUSE, STATE, STATION FROM CBARCODE_BOXPRINT WHERE CODE LIKE
		// 'Z%'";
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("SNO"));
		}
		long end = System.currentTimeMillis();
		System.out.println("The total cost of the time is " + ((end - start) / 1000));
	}

}