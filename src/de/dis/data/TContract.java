package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TContract {

    private int id = -1;
    private int apartmentid;
    private int personid;
    private int contractNumber;
    private java.sql.Date date;
    private String place;
    private java.sql.Date startDate;
    private int duration;
    private int additionalCosts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApartmentid() {
        return apartmentid;
    }

    public void setApartmentid(int apartmentid) {
        this.apartmentid = apartmentid;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(int ContractNumber) {
        this.contractNumber = ContractNumber;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date Date) {
        this.date = Date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String Place) {
        this.place = Place;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(int additionalCosts) {
        this.additionalCosts = additionalCosts;
    }


    public static TContract load(int id) {
        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM tenancycontract WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TContract ts = new TContract();
                ts.setId(id);
                ts.setApartmentid(rs.getInt("Apartment ID"));
                ts.setPersonid(rs.getInt("Person ID"));
                ts.setContractNumber(rs.getInt("Contract Number"));
                ts.setDate(rs.getDate("Date"));
                ts.setPlace(rs.getString("Place"));
                ts.setStartDate(rs.getDate("Start Date"));
                ts.setDuration(rs.getInt("Duration"));
                ts.setAdditionalCosts(rs.getInt("Additional Costs"));

                rs.close();
                pstmt.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void save() {
        // Get Connection
        Connection con = DbConnectionManager.getInstance().getConnection();

        try {
            // add new record, if no ID is set yet (ID = -1) to the database
            if (getId() == -1) {
                // Warning, here we give an extra parameter to the prepareStatement
                // so that we can get the generated ID back.
                String insertSQL = "INSERT INTO tenancycontract(apartmentid, personid, contractnumber, date, place, startdate,duration,additionalcost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getApartmentid());
                pstmt.setInt(2, getPersonid());
                pstmt.setInt(3, getContractNumber());
                pstmt.setDate(4, getDate());
                pstmt.setString(5, getPlace());
                pstmt.setDate(6, getStartDate());
                pstmt.setInt(7, getDuration());
                pstmt.setInt(8, getAdditionalCosts());
                pstmt.executeUpdate();

                // get the generated ID
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
