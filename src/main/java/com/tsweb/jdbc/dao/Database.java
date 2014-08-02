package com.tsweb.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.tsweb.config.Configure;

public class Database {
	private static String linkUri;
	private static Connection connection;

	private static Logger log = Logger.getLogger(Database.class);

	static {
		linkUri = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8", Configure.getProperty("db_host"), Configure.getProperty("db_name"));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		do {
			//log.info("connection database...");
			try {
				connection = DriverManager.getConnection(linkUri, Configure.getProperty("db_user"), Configure.getProperty("db_password"));
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e.getMessage() + ", retry after 5 seconds.");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (connection == null);

		return connection;
	}
}
