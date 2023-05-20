package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Borat", "Sagdiyev", (byte) 45);
        userDao.saveUser("Azamat", "Bagatov", (byte) 55);
        userDao.saveUser("Natalya", "Sagdiyeva", (byte) 40);
        userDao.saveUser("Pamela", "Anderson", (byte) 55);

        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        userDao.close();
    }
}
