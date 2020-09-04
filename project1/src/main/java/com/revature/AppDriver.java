package com.revature;

import java.util.List;

import com.revature.models.AppUser;
import com.revature.util.SessionFact;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AppDriver {


    public static void main(String[] args) {
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
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
