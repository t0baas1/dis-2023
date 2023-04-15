package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Overview {

    public void load() {
        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM purchasecontract";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);


            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                Integer id = rs.getInt(1);
                Integer houseid = rs.getInt(2);
                Integer personid = rs.getInt(3);
                Integer contractnumber = rs.getInt(4);
                java.sql.Date date = rs.getDate(5);
                Integer place = rs.getInt(6);
                Integer noofinstallments = rs.getInt(7);
                Integer intrestrate = rs.getInt(8);
                System.out.println("Purchase Contracts:");
                System.out.println("Contract ID: " + id);
                System.out.println("House ID: " + houseid);
                System.out.println("Person ID: " + personid);
                System.out.println("Contract Number :" + contractnumber);
                System.out.println("Date: " + date);
                System.out.println("Place: " + place);
                System.out.println("Number of Installments: " + noofinstallments);
                System.out.println("Intrestrate: " + intrestrate);
                System.out.println("");
                }
                rs.close();
                pstmt.close();
            }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM tenancycontract";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);


            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                Integer apartmentid = rs.getInt(2);
                Integer personid = rs.getInt(3);
                Integer contractnumber = rs.getInt(4);
                java.sql.Date date = rs.getDate(5);
                Integer place = rs.getInt(6);
                java.sql.Date startdate = rs.getDate(7);
                Integer duration =  rs.getInt(8);
                Integer additionalcost =  rs.getInt(9);

                System.out.println("Tenancy Contracts");
                System.out.println("Contract ID: " + id);
                System.out.println("Apartment ID: " + apartmentid);
                System.out.println("Person ID: " + personid);
                System.out.println("Contract Number: " + contractnumber);
                System.out.println("Date: " + date);
                System.out.println("Place:" + place);
                System.out.println("Start Date: " + startdate);
                System.out.println("Duration: " + duration);
                System.out.println("Additional Costs: " + additionalcost);
                System.out.println("");
                }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
