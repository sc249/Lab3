package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	static private final String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root" ;
	static private Connection connection = null ;
	

	public ConnectDB() {
		// TODO Auto-generated constructor stub
	}
	
	public static Connection getConnection(){
		
		try{
			if( connection == null )
				connection = DriverManager.getConnection(jdbcURL) ;
			return connection ;		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot get connection " + jdbcURL, e) ;
		}
	}
	
}
