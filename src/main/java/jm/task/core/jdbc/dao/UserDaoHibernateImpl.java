package jm.task.core.jdbc.dao;

import javax.persistence.PersistenceException;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private Transaction transaction;

//    Session SessionSingleton.getInstance().getSession() = SessionSingleton.getInstance().getSession();

    public UserDaoHibernateImpl() {
    }

    void transaction(String query, String message) {
        try (Session session = SessionSingleton.getInstance().getSession()) {
            session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.err.println(message);
        }
    }

    @Override
    public void createUsersTable() {
        transaction("CREATE TABLE if not exists UsersTable " +
                        "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255),lastname VARCHAR(255),age INT)",
                "Table is already exist");
    }

    @Override
    public void dropUsersTable() {
        transaction("DROP TABLE UsersTable", "Table is not deleted");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = SessionSingleton.getInstance().getSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.println("User " + name + " добавлен");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = SessionSingleton.getInstance().getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List userList = new ArrayList<>();

        try (Session session = SessionSingleton.getInstance().getSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User").getResultList();
            userList.forEach(System.out::println);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = SessionSingleton.getInstance().getSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.err.println("Table clean exception");
        }
    }
}
