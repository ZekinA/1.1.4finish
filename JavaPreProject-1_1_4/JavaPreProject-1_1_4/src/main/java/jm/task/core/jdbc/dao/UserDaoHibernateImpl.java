package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS USERS\n" +
                    "  (\n" +
                    "  id       BIGINT       AUTO_INCREMENT,\n" +
                    "  name     VARCHAR(250) DEFAULT NULL,\n" +
                    "  lastname VARCHAR(250) DEFAULT NULL,\n" +
                    "  age      TINYINT      DEFAULT NULL\n" +
                    ")");
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USERS");
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.createNativeQuery("delete User where id = :id")
                    .setParameter("id",id).executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User order by name").list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createNativeQuery("delete User");
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }
}
