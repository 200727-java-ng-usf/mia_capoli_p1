package com.revature.repos;

import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.AppUser;
import com.revature.models.Reimb;
import com.revature.models.ReimbStatusTypes;
import com.revature.models.ReimbTypes;
import com.revature.util.ConnectionFactory;
import com.revature.util.SessionFact;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class that accesses the Reimb Repository and contains methods to easily access users.
 */

public class ReimbRepo {


    public Set<Reimb> findReimbByType(ReimbTypes reimb_type) {

        Set<Reimb> _reimb = new HashSet<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            // select the user matching the username and password provided.
            String sql = "SELECT * FROM project1.ers_reimbursments WHERE reimb_type_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int reimbTypeId = ReimbTypes.getIDFromName(reimb_type.toString());

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

    public void save(Reimb reimb) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "INSERT INTO project1.ers_reimbursments (reimb_id, amount, submitted, resolved, description, " +
                    "author_id, resolver_id, reimb_status_id, reimb_type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"reimb_id"}); //retrieve autogenerated values

            pstmt.setInt(1, reimb.getReimb_id());
            pstmt.setDouble(2, reimb.getAmount());
            pstmt.setTimestamp(3, reimb.getSubmitted());
            pstmt.setTimestamp(4, reimb.getResolved());
            pstmt.setString(5, reimb.getDescription());
            pstmt.setDouble(6, reimb.getAuthor_id());
            pstmt.setInt(7, reimb.getResolver_id());
            pstmt.setInt(8, reimb.getReimb_status().ordinal() + 1);
            pstmt.setInt(9, reimb.getReimb_type().ordinal() + 1);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }
    }
    

    private Set<Reimb> mapResultSet(ResultSet rs) throws SQLException {

        Set<Reimb> reimbs = new HashSet<>();

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


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return  _reimb.get();

    }

//    public void createNewReimb(int amount, Time submitted, Time resolved, String description, int author_id, int resolver_id, int reimb_status_id, String reimbType) {
//
//    Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();
//
//    Transaction tx = null;
//
//        try
//
//    {
//        tx = session.beginTransaction();
//        Reimb reimb = new Reimb();
//
//
//        reimb.setAmount(amount);
//        reimb.setSubmitted(submitted);
//        reimb.setResolved(resolved);
//        reimb.setDescription(description);
//        reimb.setAuthor_id(author_id);
//        reimb.setResolver_id(resolver_id);
//        reimb.setReimb_status_id(reimb_status_id);
//        reimb.setReimb_type(ReimbTypes.getByName(reimbType));
//
//        session.save(reimb);
//        tx.commit();
//
//
//    } catch(Exception e) {
//        if (tx != null) tx.rollback();
//        e.printStackTrace();
//    }
//
//}

    public void updateReimb(Reimb reimb) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "update project1.ers_reimbursments set amount = ?, submitted = ?, resolved = ?, description = ?, author_id = ?, " +
                    "resolver_id = ?, reimb_status_id = ?, reimb_type_id = ? where reimb_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setDouble(1, reimb.getAmount());
            pstmt.setTimestamp(2, reimb.getSubmitted());
            pstmt.setTimestamp(3, reimb.getResolved());
            pstmt.setString(4, reimb.getDescription());
            pstmt.setInt(5, reimb.getAuthor_id());
            pstmt.setInt(6, reimb.getResolver_id());
            pstmt.setInt(7, ReimbStatusTypes.getIDFromName(reimb.getReimb_status().toString()));
            pstmt.setInt(8, ReimbTypes.getIDFromName(reimb.getReimb_type().toString()));
            pstmt.setInt(9, reimb.getReimb_id());

            pstmt.executeUpdate();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }
    }

    public void deleteReimb(int reimbDeleteId) {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("Delete from Reimb " +
                    "WHERE id = :reimbDeleteId").setParameter("empDeleteId", reimbDeleteId);

            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }


    public Set<Reimb> findAllReimbs() {
        Set<Reimb> reimbs = new HashSet<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id";
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

