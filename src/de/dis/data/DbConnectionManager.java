package de.dis.data;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * A Singleton to manage the database connection.
 * 
 * @author Michael von Riegen
 * @version April 2009
 */
public class DbConnectionManager {

	// instance of Driver Manager
	private static DbConnectionManager _instance = null;

	// DB connection
	private Connection _con;

	/**
	 * Erzeugt eine Datenbank-Verbindung
	 */
	private DbConnectionManager() {
		try {
			// Get the settings from the db.properties file
			Properties properties = new Properties();
			FileInputStream stream = new FileInputStream(new File("db.properties"));
			properties.load(stream);
			stream.close();

			String jdbcUser = properties.getProperty("jdbc_user");
			String jdbcPass = properties.getProperty("jdbc_pass");
			String jdbcUrl = properties.getProperty("jdbc_url");
			// Establish a connection to the Database
			_con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the instance of the DBConnectionManager
	 * 
	 * @return DBConnectionManager
	 */
	public static DbConnectionManager getInstance() {
		if (_instance == null) {
			_instance = new DbConnectionManager();
		}
		return _instance;
	}

	/**
	 * Returns a connection to the database
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return _con;
	}

}
