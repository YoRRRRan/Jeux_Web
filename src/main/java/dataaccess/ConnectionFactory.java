package dataaccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	private static String dbUrl;
	private static String userName;
	private static String password;
	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		URL url = getClass().getResource("/dbConnection.txt");
		try (InputStream inputStream = url.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			int index = 0;
			while (reader.ready()) {
				String line = reader.readLine();
				switch (index++) {
				case 0 -> dbUrl = line;
				case 1 -> userName = line;
				case 2 -> password = line;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// registering the jdbc driver here, your string to use
		// here depends on what driver you are using.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, userName, password);
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}