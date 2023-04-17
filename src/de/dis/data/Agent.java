package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Agent-Bean
 * 
 * Example-Table:
 * CREATE TABLE Agent (
 * name varchar(255), 
 * address varchar(255), 
 * login varchar(40) UNIQUE, 
 * password varchar(40), 
 * id serial primary key);
 */
public class Agent {
	private int id = -1;
	private String name;
	private String address;
	private String login;
	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Loads an Agent from the database.
	 * @param id ID of the Agent
	 * @return Agent object or null if not found
	 */
	public static Agent load(int id) {
		try {
			// Get Connection
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Create SQL-Statement
			String selectSQL = "SELECT * FROM estate_agent WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// run SQL-Statement
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Agent ts = new Agent();
				ts.setId(id);
				ts.setName(rs.getString("name"));
				ts.setAddress(rs.getString("address"));
				ts.setLogin(rs.getString("login"));
				ts.setPassword(rs.getString("password"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Agent check(String username, String password) {
		try {
			// Get Connection
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Create SQL-Statement
			String selectSQL = "SELECT * FROM estate_agent WHERE login = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			// run SQL-Statement
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Agent ts = new Agent();
				ts.setId(rs.getInt("id"));
				ts.setName(rs.getString("name"));
				ts.setAddress(rs.getString("address"));
				ts.setLogin(rs.getString("login"));
				ts.setPassword(rs.getString("password"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	};
	
	/**
	 * Saves the Agent in the database. If the Agent has no ID, a new record is
	 * created. Otherwise, the existing record is updated.
	 */
	public void save() {
		// Get Connection
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			// add new record, if no ID is set yet (ID = -1) to the database
			if (getId() == -1) {
				// Warning, here we give an extra parameter to the prepareStatement
				// so that we can get the generated ID back.
				String insertSQL = "INSERT INTO estate_agent(name, address, login, password) VALUES (?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Set SQL-Parameters and execute
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.executeUpdate();

				// get the generated ID
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// if ID is set, update existing record
				String updateSQL = "UPDATE estate_agent SET name = ?, address = ?, login = ?, password = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Set SQL-Parameters and execute
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.setInt(5, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int id) {

		try {
			// add new record, if no ID is set yet (ID = -1) to the database
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Create SQL-Statement
			String selectSQL = "DELETE FROM estate_agent WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
