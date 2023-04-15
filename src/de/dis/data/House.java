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
public class House{
    private int id = -1;
    private int floors;
    private int price;
    private Boolean garden;
    private String city;
    private int postalCode;
    private String street;
    private String streetNr;
    private int square;
    private int agentId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int id) {
        this.agentId = id;
    }

    public int getFloor() {
        return floors;
    }

    public void setFloor(int floors) {
        this.floors = floors;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getGarden() {
        return garden;
    }

    public void setBooleanGarden(Boolean b) {
        this.garden = b;
    }

    public void setGarden(String garden) {

        if(garden.equals("y")) this.garden = true;
        if(garden.equals("n")) this.garden = false;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNr;
    }

    public void setStreetNumber(String streetNr) {
        this.streetNr = streetNr;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }


    /**
     * Loads an Apartment from the database.
     * @param id ID of the Apartment
     * @return Apartment object or null if not found
    */
    public static House load(int id) {
    try {
    // Get Connection
    Connection con = DbConnectionManager.getInstance().getConnection();

    // Create SQL-Statement
    String selectSQL = "SELECT * FROM House WHERE id = ?";
    PreparedStatement pstmt = con.prepareStatement(selectSQL);
    pstmt.setInt(1, id);

    // run SQL-Statement
    ResultSet rs = pstmt.executeQuery();
    if (rs.next()) {
    House ts = new House();
    ts.setId(id);
        ts.setId(id);
        ts.setFloor(rs.getInt("floors"));
        ts.setPrice(rs.getInt("price"));
        ts.setBooleanGarden(rs.getBoolean("garden"));
        ts.setCity(rs.getString("city"));
        ts.setPostalCode(rs.getInt("postalcode"));
        ts.setStreet(rs.getString("street"));
        ts.setStreetNumber(rs.getString("streetnumber"));
        ts.setSquare(rs.getInt("squarearea"));
        ts.setAgentId(rs.getInt("agentid"));

    rs.close();
    pstmt.close();
    return ts;
    }
    } catch (SQLException e) {
    e.printStackTrace();
    }
    return null;
    }

    /**
     * Saves the Agent in the database. If the Agent has no ID, a new record is
     * created. Otherwise, the existing record is updated.
    */
    public void save() {
        // Get Connection
        Connection con = DbConnectionManager.getInstance().getConnection();

        try {
            // add new record, if no ID is set yet (ID = -1) to the database
            if (this.getId() == -1) {
                // Warning, here we give an extra parameter to the prepareStatement
                // so that we can get the generated ID back.
                String insertSQL = "INSERT INTO house(floors, price, garden, city, postalcode, street, streetnumber, squarearea, agentid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getPrice());
                pstmt.setBoolean(3, getGarden());
                pstmt.setString(4, getCity());
                pstmt.setInt(5, getPostalCode());
                pstmt.setString(6, getStreet());
                pstmt.setString(7, getStreetNumber());
                pstmt.setInt(8, getSquare());
                pstmt.setInt(9, getAgentId());

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
                String updateSQL = "UPDATE house SET floors = ?, price = ?, garden = ?, city = ?, postalcode = ?, street = ?, streetnumber = ?, squarearea = ?, agentid = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getPrice());
                pstmt.setBoolean(3, getGarden());
                pstmt.setString(4, getCity());
                pstmt.setInt(5, getPostalCode());
                pstmt.setString(6, getStreet());
                pstmt.setString(7, getStreetNumber());
                pstmt.setInt(8, getSquare());
                pstmt.setInt(9, getAgentId());
                pstmt.setInt(10, getId());
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
            String selectSQL = "DELETE FROM house WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

