package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

//        Util.getConnection();
//        UserDao userDao = new UserDaoJDBCImpl();
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Borat", "Sagdiyev", (byte)45);
        userDao.saveUser("Azamat", "Bagatov", (byte) 55);
        userDao.saveUser("Natalya", "Sagdiyeva", (byte) 40);
        userDao.saveUser("Pamela", "Anderson", (byte) 55);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
