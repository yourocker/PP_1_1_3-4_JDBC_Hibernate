package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (PreparedStatement statement = getConnection()
                .prepareStatement("CREATE TABLE IF NOT EXISTS UsersTable(" +
                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "lastName VARCHAR(255) NOT NULL, " +
                        "age INT NOT NULL)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (PreparedStatement statement = getConnection()
                .prepareStatement("DROP TABLE UsersTable")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO UsersTable (name, lastName, age) VAlUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = getConnection()
                .prepareStatement("DELETE FROM UsersTable WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM UsersTable")) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userList.forEach(System.out::println);
        return userList;
    }

    //DONE
    public void cleanUsersTable() {
        try (PreparedStatement statement = getConnection()
                .prepareStatement("TRUNCATE TABLE UsersTable")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
