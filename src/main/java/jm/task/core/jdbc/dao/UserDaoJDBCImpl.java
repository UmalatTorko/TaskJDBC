package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS user " +
                "(`id` BIGINT NOT NULL AUTO_INCREMENT," +
                " `name` VARCHAR(45) NULL," +
                " `lastName` VARCHAR(45) NULL," +
                " `age` TINYINT NULL," +
                " PRIMARY KEY (`id`), UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";

        try(Statement statement = util.getConnection().createStatement()) {
                statement.executeUpdate(SQL);
        } catch (SQLException ignore) {
        }
    }

    public void dropUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException ignore) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertQuery = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";

        try(PreparedStatement statement = util.getConnection().prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException ignore) {
        }
    }

    public void removeUserById(long id) {
        String deleteQuery = "DELETE FROM user WHERE id = ?";
        try(PreparedStatement statement = util.getConnection().prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ignore) {
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));

                userList.add(user);
            }
        } catch (SQLException ignore) {
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException ignore) {
        }
    }
}
