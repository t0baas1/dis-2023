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
public class Apartment{
    private int id = -1;
    private int estateId;
    private int floor;
    private int rent;
    private int rooms;
    private Boolean balcony;
    private Boolean kitchen;
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

    public int getEstateId() {
        return estateId;
    }

    public void setEstateId(int id) {
        this.estateId = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int id) {
        this.agentId = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public boolean getBalcony() {
        return balcony;
    }

    public void setBooleanBalcony(Boolean b) {
        this.balcony = b;
    }

    public void setBooleanKitchen(Boolean k) {
        this.kitchen = k;
    }

    public void setBalcony(String balcony) {

        if(balcony.equals("y")) this.balcony = true;
        if(balcony.equals("n")) this.balcony = false;
    }

    public boolean getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        if(kitchen.equals("y")) this.kitchen = true;
        if(kitchen.equals("n")) this.kitchen = false;
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
    public static Apartment load(int id) {
        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM apartment WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Apartment ts = new Apartment();
                ts.setId(id);
                ts.setEstateId(rs.getInt("estateid"));
                ts.setFloor(rs.getInt("floor"));
                ts.setRent(rs.getInt("rent"));
                ts.setRooms(rs.getInt("rooms"));
                ts.setBooleanBalcony(rs.getBoolean("balcony"));
                ts.setBooleanKitchen(rs.getBoolean("builtinkitchen"));
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
     * Saves the Apartment in the database. If the Agent has no ID, a new record is
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
                String insertSQL = "INSERT INTO apartment(estateId, floor, rent, rooms, balcony, builtinkitchen, city, postalCode, street, streetnumber, squarearea, agentId) VALUES (?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getEstateId());
                pstmt.setInt(2, getFloor());
                pstmt.setInt(3, getRent());
                pstmt.setInt(4, getRooms());
                pstmt.setBoolean(5, getBalcony());
                pstmt.setBoolean(6, getKitchen());
                pstmt.setString(7, getCity());
                pstmt.setInt(8, getPostalCode());
                pstmt.setString(9, getStreet());
                pstmt.setString(10, getStreetNumber());
                pstmt.setInt(11, getSquare());
                pstmt.setInt(12, getAgentId());

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
                String updateSQL = "UPDATE apartment SET estateId = ?, floor = ?, rent = ?, rooms = ?, balcony = ?, builtinkitchen = ?, city = ?, postalCode = ?, street = ?, streetnumber = ?, squarearea = ?, agentId = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getEstateId());
                pstmt.setInt(2, getFloor());
                pstmt.setInt(3, getRent());
                pstmt.setInt(4, getRooms());
                pstmt.setBoolean(5, getBalcony());
                pstmt.setBoolean(6, getKitchen());
                pstmt.setString(7, getCity());
                pstmt.setInt(8, getPostalCode());
                pstmt.setString(9, getStreet());
                pstmt.setString(10, getStreetNumber());
                pstmt.setInt(11, getSquare());
                pstmt.setInt(12, getAgentId());
                pstmt.setInt(13, getId());
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
            String selectSQL = "DELETE FROM apartment WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

