package com.revature.repos;

import com.revature.models.AppUser;
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

/**
 * The class that accesses the AppUser Repository and contains methods to easily access users.
 */
public class AppUserRepo {
    /**
     * Find a user in the Repo based on the Username and Password provided.
     *
     * @param username
     * @param password
     * @return
     */
    public Optional<AppUser> findUser(String username, String password) {

        Optional<AppUser> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            // select the user matching the username and password provided.
            String sql = "SELECT * FROM project1.ers_users WHERE username = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            //find the first user that matches the results
            _user = mapResultSet(rs).stream().findFirst();

            return _user;


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

        return _user;
    }

    /**
     * Find the AppUser in the repository by their username only.
     *
     * @param username
     * @return
     */
    public Optional<AppUser> findUserByUsername(String username) {

        Optional<AppUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = "SELECT * FROM project1.ers_users";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //if the returned user set contains a user that matches the username, return that user.
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AppUser temp = new AppUser(rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"));
                if (temp.getUsername().equals(username)) {
                    _user = Optional.of(temp);
                    return _user;
                }
            }

        } catch (SQLException sqle) {
            System.err.println("Database Error!");
        }
        return _user;

    }

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
    private Set<AppUser> mapResultSet(ResultSet rs) throws SQLException {

        Set<AppUser> users = new HashSet<>();

        //Add the returned users to a hashset so the program can interpret it.
        while (rs.next()) {
            AppUser temp = new AppUser();
            temp.setId(rs.getInt("ers_user_id"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setLastName(rs.getString("email"));
            int role_int = rs.getInt("user_role_id");
            temp.setRole(Role.getByID(role_int));
            users.add(temp);
        }

        return users;

    }


    public String returnAllAppUsers() {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List<AppUser> users = session.createQuery("FROM AppUser", AppUser.class).list();
            for (AppUser u : users) {
                System.out.println("Entry: " + u.getFirstName() + " " +
                        u.getLastName() + ", " + u.getEmail());
            }


            tx.commit();

            return users.toString();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return null;
    }


    public void createNewUser(AppUser emp, String firstName, String lastName, String username, String password, String email, int userRole) {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            emp.setUsername(username);
            emp.setPassword(password);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setEmail(email);
            emp.setRole(Role.getByID(userRole));
            session.save(emp);
            tx.commit();


        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

    public void updateAppUser(String firstNameNew, String lastNameNew, int empUpdateId) {

        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session.createQuery("Update Employee set firstName = :firstNameNew, lastName = :lastNameNew" +
                    "WHERE id = :empUpdateId");
            //todo fix
            query.executeUpdate();
            tx.commit();


        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

    public void deleteEmployee(int empDeleteId) {
        Session session = SessionFact.getSessionFactoryProgrammaticConfig().openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("Delete from AppUser " +
                    "WHERE id = :empDeleteId").setParameter("empDeleteId", empDeleteId);

            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

}
