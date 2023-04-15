package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Person {

    private int id = -1;
    private String firstName;
    private String name;
    private String adress;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


    public static Person load(int id) {
        try {
            // Get Connection
            Connection con = DbConnectionManager.getInstance().getConnection();

            // Create SQL-Statement
            String selectSQL = "SELECT * FROM person WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // run SQL-Statement
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Person ts = new Person();
                ts.setId(id);
                ts.setFirstName(rs.getString("First Name"));
                ts.setName(rs.getString("Name"));
                ts.setAdress(rs.getString("Adress"));

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
                String insertSQL = "INSERT INTO person(firstname, name, adress) VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set SQL-Parameters and execute
                pstmt.setString(1, getFirstName());
                pstmt.setString(2, getName());
                pstmt.setString(3, getAdress());
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
