package com.example.demo;

import java.sql.*;

import org.springframework.beans.factory.annotation.Value;
public class Db {
	@Value("${javax.persistence.jdbc.url}")
	private static String url;
	@Value("${javax.persistence.jdbc.user}")
	private static String user;
	@Value("${javax.persistence.jdbc.password}")
	private static String password;
	public static void executeSql (String sql) throws Exception {
    	

        Connection conn = DriverManager.getConnection(url, user, password);
        // add application code here
        Statement stmt = conn.createStatement();
        stmt.executeQuery(sql);
        System.out.println(stmt.getUpdateCount());
        conn.close();
    }
}
