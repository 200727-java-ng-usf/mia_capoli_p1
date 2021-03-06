package com.revature.repos;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The class that accesses the AppUser Repository and contains methods to easily access users.
 */
public class AppUserRepo {

    private String baseQuery = "SELECT * FROM project1.ers_users au " +
            "JOIN project1.ers_user_roles ur " +
            "ON au.user_role_id = ur.role_id Where au.is_active = true";

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
            String sql = baseQuery + " AND USERNAME = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();


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

            String sql = baseQuery;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //if the returned user set contains a user that matches the username, return that user.
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AppUser temp = new AppUser(rs.getInt("ers_user_id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), Role.getByName(rs.getString("role_name")));
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
     * Save an Appuser in the repository after registration and updating.
     *
     * @param newUser
     */
    public void save(AppUser newUser) {

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "INSERT INTO project1.ers_users (username, password, first_name, last_name, email, user_role_id, is_active) " +
                    "VALUES (?, ? , ? , ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"user_role_id"}); //retrieve autogenerated values


            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRole().ordinal() + 1);
            pstmt.setBoolean(7, true);

            pstmt.executeUpdate();
            //add the autogenerated values into the new user so they match the serial/generated one from the repository.

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }
    }

    /**
     * Deleting an employee by employee id.
     * @param empDeleteId
     * @return
     */
    public boolean deleteEmployee(int empDeleteId) {
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "Update project1.ers_users set is_active = false WHERE ers_user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"user_role_id"}); //retrieve autogenerated values


            pstmt.setInt(1, empDeleteId);


            pstmt.executeUpdate();

            return true;
            //add the autogenerated values into the new user so they match the serial/generated one from the repository.

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
    private ArrayList<AppUser> mapResultSet(ResultSet rs) throws SQLException {

        ArrayList<AppUser> users = new ArrayList();

        //Add the returned users to a hashset so the program can interpret it.
        while (rs.next()) {
            AppUser temp = new AppUser();
            temp.setId(rs.getInt("ers_user_id"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setEmail(rs.getString("email"));
            temp.setRole(Role.getByName(rs.getString("role_name")));
            users.add(temp);
        }

        return users;

    }

    /**
     * Getting a user by their id.
     * @param id
     * @return
     */
    public Optional<AppUser> findUserById(int id) {

        Optional<AppUser> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            String sql = (baseQuery + " AND au.ers_user_id = ?");
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ArrayList<AppUser> rs = mapResultSet(pstmt.executeQuery());
            if (!rs.isEmpty()) {
                _user = rs.stream().findFirst();
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        return _user;

    }

    /**
     * Getting a user by their email.
     * @param email
     * @return
     */
    public Optional<AppUser> findUserByEmail(String email) {

        Optional<AppUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = baseQuery + " AND email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    /**
     * Getting all users in the repository.
     * @return
     */
    public ArrayList<AppUser> findAllUsers() {
        ArrayList<AppUser> users = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {

            String sql = baseQuery + " ORDER by au.ers_user_id";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);

            ResultSet rs = stmt.executeQuery(sql);
            users = mapResultSet(rs);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Updating an app user.
     * @param updatedUser
     */
    public void updateAppUser(AppUser updatedUser) {
        Optional<AppUser> _user = Optional.empty();
        try (Connection conn = ConnectionFactory.getConnFactory().getConnection()) {
            //insert the user into the table
            String sql = "update project1.ers_users set password = ?, first_name = ?, last_name = ?, email = ? where username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"user_role_id"}); //retrieve autogenerated values


            pstmt.setString(1, updatedUser.getPassword());
            pstmt.setString(2, updatedUser.getFirstName());
            pstmt.setString(3, updatedUser.getLastName());
            pstmt.setString(4, updatedUser.getEmail());
            pstmt.setString(5, updatedUser.getUsername());

            pstmt.executeUpdate();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Database Error!");
        }

    }




}
