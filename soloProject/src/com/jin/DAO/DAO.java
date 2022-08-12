package com.jin.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAO {
	protected Connection conn = null;
	protected PreparedStatement pstmt = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	protected ResultSet rs2 = null;
	
//	Properties pro = new Properties();

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "ignitepark";
	String pw = "930708";
	public void conn() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void getProperties() {
//		try {
//			FileReader resource = new FileReader("src/config/db.properties");
//			pro.load(resource);
//			driver = pro.getProperty("driver");
//			url = pro.getProperty("url");
//			id = pro.getProperty("id");
//			pw = pro.getProperty("pw");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
