package com.revature.repos;

import com.revature.models.AppUser;
import com.revature.models.Reimb;
import com.revature.models.ReimbTypes;
import com.revature.models.Role;
import com.revature.util.ConnectionFactory;
import com.revature.util.SessionFact;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class that accesses the Reimb Repository and contains methods to easily access users.
 */

public class ReimbRepo {


    public Set<Reimb> findReimbByType(String reimb_type) {

        Set<Reimb> _reimb = new HashSet<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            // select the user matching the username and password provided.
            String sql = "SELECT * FROM project1.ers_reimbursments WHERE reimb_type_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int reimbTypeId = ReimbTypes.getIDFromName(reimb_type);

            pstmt.setInt(1, reimbTypeId);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _reimb = mapResultSet(rs).stream().collect(Collectors.toSet());

            return _reimb;


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _reimb;
    }


    public Set<Reimb> findReimbByStatus(Integer reimb_status_id) {

        Set<Reimb> _reimb = new HashSet<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments WHERE reimb_status_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reimb_status_id);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _reimb = mapResultSet(rs).stream().collect(Collectors.toSet());

            return _reimb;


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _reimb;
    }

    public Set<Reimb> findReimbsByUser(int appUser_id) {

        Set<Reimb> _reimbs = new HashSet<>();
        Optional<Reimb> _reimb = Optional.empty();

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments WHERE author_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appUser_id);
            //if the returned user set contains a user that matches the username, return that user.
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                _reimb = mapResultSet(rs).stream().findAny();
                if (_reimb.isPresent()) {
                    _reimbs.add(_reimb.get());
                }
            }

        } catch (SQLException sqle) {
            System.err.println("Database Error!");
        }
        return _reimbs;

    }
    //TODO update to make them for reimbs

    /**
     * Save an Appuser in the repository after registration.
     *
     * @param newUser
     */
    public void save(AppUser newUser) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "INSERT INTO project1.ers_users (username, password, first_name, last_name, email, user_role_id) " +
                    "VALUES (?, ? , ? , ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"user_role_id"}); //retrieve autogenerated values


            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRole().ordinal() + 1);

            int rowsInserted = pstmt.executeUpdate();
            //add the autogenerated values into the new user so they match the serial/generated one from the repository.

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }
    }

    /**
     * Gather the returned Users from the result set in a Java-readable format.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Set<Reimb> mapResultSet(ResultSet rs) throws SQLException {

        Set<Reimb> reimbs = new HashSet<>();

        //Add the returned users to a hashset so the program can interpret it.
        while (rs.next()) {
            Reimb temp = new Reimb();
            temp.setReimb_id(rs.getInt("reimb_id"));
            temp.setAmount(rs.getInt("amount"));
            temp.setSubmitted(rs.getTime("submitted"));
            temp.setResolved(rs.getTime("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setAuthor_id(rs.getInt("author_id"));
            temp.setResolver_id(rs.getInt("resolver_id"));
            temp.setReimb_status_id(rs.getInt("author_id"));

            int role_int = rs.getInt("user_role_id");
            temp.setReimb_type(ReimbTypes.getByID(role_int));
            reimbs.add(temp);
        }

        return reimbs;

    }


    public String returnAllReimbs() {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List<Reimb> reimbs = session.createQuery("FROM Reimb", Reimb.class).list();
            for (Reimb r : reimbs) {
                System.out.println("Reimbursement: " + r.toString());
            }


            tx.commit();

            return reimbs.toString();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public void selectReimbursement(int reimb_id) {

        //TODO implement
    }


    public void createNewReimb(AppUser emp, String firstName, String lastName) {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            //TODO ADD ALL FIELDS
            session.save(emp);
            tx.commit();


        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

    public void updateReimb(String firstNameNew, String lastNameNew, int empUpdateId) {

        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session.createQuery("Update Employee set firstName = :firstNameNew, lastName = :lastNameNew" +
                    "WHERE id = :empUpdateId");

            query.executeUpdate();
            tx.commit();


        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

    public void deleteReimb(int empDeleteId) {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("Delete from Employee " +
                    "WHERE id = :empDeleteId").setParameter("empDeleteId", empDeleteId);

            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

}

