package com.revature.repos;

import com.revature.dtos.Principal;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimb;
import com.revature.models.ReimbStatusTypes;
import com.revature.models.ReimbTypes;
import com.revature.util.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


/**
 * The class that accesses the Reimb Repository and contains methods to easily access reimbursements.
 */

public class ReimbRepo {

    /**
     * Find a reimbursement by it's type.
     * @param reimb_type
     * @return
     */
    public ArrayList<Reimb> findReimbByType(ReimbTypes reimb_type) {

        ArrayList<Reimb> _reimb = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            // select the user matching the username and password provided.
            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE er.reimb_type_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int reimbTypeId = ReimbTypes.getIDFromName(reimb_type.toString());

            pstmt.setInt(1, reimbTypeId);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _reimb = new ArrayList<>(mapResultSet(rs));

            return _reimb;


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _reimb;
    }

    /**
     * Find a reimbursement by status.
     * @param reimb_status_id
     * @return
     */
    public ArrayList<Reimb> findReimbByStatus(Integer reimb_status_id) {

        ArrayList<Reimb> _reimb = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE er.reimb_status_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reimb_status_id);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _reimb = new ArrayList<>(mapResultSet(rs));

            return _reimb;


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _reimb;
    }

    /**
     * find reimbursements by an author's user id.
     * @param appUser_id
     * @return
     */
    public ArrayList<Reimb> findReimbsByUser(int appUser_id) {

        ArrayList<Reimb> _reimbs = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE author_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appUser_id);
            //if the returned user set contains a user that matches the username, return that user.
            ResultSet rs = pstmt.executeQuery();
            _reimbs = new ArrayList<>(mapResultSet(rs));

        } catch (SQLException sqle) {
            System.err.println("Database Error!");
        }
        return _reimbs;

    }

    /**
     * Finding a reimbursement by user and status.
     * @param appUser_id
     * @param status
     * @return
     */
    public ArrayList<Reimb> findReimbsByUserStatus(int appUser_id, String status) {

        ArrayList<Reimb> _reimbs = new ArrayList<>();
        int statusId = ReimbStatusTypes.getIDFromName(status);

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE author_id = ? AND er.reimb_status_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appUser_id);
            pstmt.setInt(1, statusId);
            //if the returned user set contains a user that matches the username, return that user.
            ResultSet rs = pstmt.executeQuery();
            _reimbs = new ArrayList<>(mapResultSet(rs));

        } catch (SQLException sqle) {
            System.err.println("Database Error!");
        }
        return _reimbs;

    }

    /**
     * Saving a reimbursement in the database.
     * @param amount
     * @param description
     * @param type
     * @param id
     * @return
     */
    public boolean save(double amount, String description, int type, int id) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "INSERT INTO project1.ers_reimbursments (amount, submitted, description, " +
                    "author_id, reimb_status_id, reimb_type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql); //retrieve autogenerated values


            pstmt.setDouble(1, amount);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, description);
            pstmt.setDouble(4, id);
            pstmt.setInt(5, 1);
            pstmt.setInt(6, type);

            pstmt.executeUpdate();
            return true;
        } catch (PSQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
            return false;
        }
    }

    /**
     * Mapping a result set received by the API to a Java object.
     * @param rs
     * @return
     * @throws SQLException
     */
    private ArrayList<Reimb> mapResultSet(ResultSet rs) throws SQLException {

        ArrayList<Reimb> reimbs = new ArrayList<>();

        //Add the returned users to a hashset so the program can interpret it.
        while (rs.next()) {
            Reimb temp = new Reimb();
            temp.setReimb_id(rs.getInt("reimb_id"));
            temp.setAmount(rs.getInt("amount"));
            temp.setSubmitted(rs.getTimestamp("submitted"));
            temp.setResolved(rs.getTimestamp("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setAuthor_id(rs.getInt("author_id"));
            temp.setResolver_id(rs.getInt("resolver_id"));
            temp.setReimb_status_id(ReimbStatusTypes.getByName(rs.getString("reimb_status")));
            temp.setReimb_type(ReimbTypes.getByName(rs.getString("reimb_type")));
            reimbs.add(temp);
        }

        return reimbs;

    }

    /**
     * selecting a reimbursement by it's id.
     * @param reimb_id
     * @return
     */
    public Reimb selectReimbursement(int reimb_id) {
        Optional<Reimb> _reimb = Optional.empty();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE reimb_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setInt(1, reimb_id);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _reimb = Optional.ofNullable(mapResultSet(rs).stream().findFirst().orElseThrow(ResourceNotFoundException::new));

            System.out.println(_reimb);
            return _reimb.get();


        } catch (PSQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _reimb.get();

    }

    /**
     * Updating a reimbursement.
     * @param amount
     * @param description
     * @param reimb_type_id
     * @param reimb_id
     */
    public void updateReimb(double amount, String description, int reimb_type_id, int reimb_id) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "update project1.ers_reimbursments set amount = ?, description = ?," +
                    "reimb_type_id = ? where reimb_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setDouble(1, amount);
            pstmt.setString(2, description);
            pstmt.setInt(3, reimb_type_id);
            pstmt.setInt(4, reimb_id);

            pstmt.executeUpdate();


        } catch (PSQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }
    }

    /**
     * Updating a reimbursement's status and filling in auto-generated info.
     * @param reimb
     * @param status
     * @param currentFinMan
     * @return
     */
    public boolean updateReimbStatus(Reimb reimb, String status, Principal currentFinMan) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "update project1.ers_reimbursments set reimb_status_id = ?, resolved = NOW()::timestamp, resolver_id = ? where reimb_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setInt(1, ReimbStatusTypes.getIDFromName(status));
            pstmt.setInt(2, currentFinMan.getId());
            pstmt.setInt(3, reimb.getReimb_id());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
            return false;
        }
    }

    /**
     * Finding all reimbursements in the repository.
     * @return
     */
    public ArrayList<Reimb> findAllReimbs() {
        ArrayList<Reimb> reimbs = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id " +
                    "JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);

            ResultSet rs = stmt.executeQuery(sql);
            reimbs = mapResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reimbs;
    }

}

