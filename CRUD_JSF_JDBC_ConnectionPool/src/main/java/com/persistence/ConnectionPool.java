package com.persistence;



import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class ConnectionPool {
	
	// Attributes that will provide the required information to connect to the DB
	
	private static final String DB = "student_tracker";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	// The object dataSource will read the info required to access the DB 
	
	private static ConnectionPool dataSource = null;
	private BasicDataSource basicDataSource = null;
	
	
	// Private constructor, as the intention with this design pattern (Singleton) 
	// is to force our app to have a single instance of this object
	
	private ConnectionPool() {
		
		initDataSource();
	}
	
	// We can omit the method and do this directly on the constructor
	// This method will retrieve the data into the pool to generate the connection
	
	private void initDataSource() {
		
		basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		basicDataSource.setUsername(USER);
		basicDataSource.setPassword(PASS);
		basicDataSource.setUrl(URL);
		
		// This can be omitted as the system can handle it automatically.
		basicDataSource.setMinIdle(5);
		basicDataSource.setMaxIdle(20);
		basicDataSource.setMaxTotal(50);
		basicDataSource.setMaxWaitMillis(10000);
		
	}
	
	// Verifies if there's an instance of this object, if not it generates one. Otherwise It will use the existing one.
	
	public static ConnectionPool getInstance() {
		
		if(dataSource == null) {
			dataSource = new ConnectionPool();
			return dataSource;
		} else {
			return dataSource;
		}
	}
	
	// Finally the method that initializes the connection.
	
	public Connection getConnection() throws SQLException {
		
		return this.basicDataSource.getConnection();
		
	}
	
	// We can't forget we need to close the connection as well
	
	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

}
