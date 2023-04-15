package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PContract {

    private int id = -1;
    private int houseid;
    private int personid;
    private int contractNumber;
    private java.sql.Date date;
    private String place;
    private int numberOfInstallments;
    private int interestrate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
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

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public int getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(int interestrate) {
        this.interestrate = interestrate;
    }



    public static PContract load(int id) {
        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM purchasecontract WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PContract ts = new PContract();
                ts.setId(id);
                ts.setHouseid(rs.getInt("House ID"));
                ts.setPersonid(rs.getInt("Person ID"));
                ts.setContractNumber(rs.getInt("Contract Number"));
                ts.setDate(rs.getDate("Date"));
                ts.setPlace(rs.getString("Place"));
                ts.setNumberOfInstallments(rs.getInt("Number of Installments"));
                ts.setInterestrate(rs.getInt("Interestrate"));

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
                String insertSQL = "INSERT INTO purchasecontract(houseid, personid, contractnumber, date, place, noofinstallments, intrestrate) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set SQL-Parameters and execute
                pstmt.setInt(1, getHouseid());
                pstmt.setInt(2, getPersonid());
                pstmt.setInt(3, getContractNumber());
                pstmt.setDate(4, getDate());
                pstmt.setString(5, getPlace());
                pstmt.setInt(6, getNumberOfInstallments());
                pstmt.setInt(7, getInterestrate());
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
