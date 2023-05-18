package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Savepoint;

public class Main {

    public static void main(String[] args) throws SQLException {
        Util.getConnection().setAutoCommit(false);
        Savepoint savepoint = Util.getConnection().setSavepoint();

        try {
            UserDao userDao = new UserDaoJDBCImpl();

            userDao.createUsersTable();

            userDao.saveUser("Borat", "Sagdiyev", (byte)45);
            userDao.saveUser("Azamat", "Bagatov", (byte) 55);
            userDao.saveUser("Natalya", "Sagdiyeva", (byte) 40);
            userDao.saveUser("Pamela", "Anderson", (byte) 55);

            userDao.getAllUsers();
            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        } catch (Exception e) {
            Util.getConnection().rollback(savepoint);
        }

        Util.getConnection().commit();
    }
}
