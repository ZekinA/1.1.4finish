package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {

        Statement statement = null;
        connection.setAutoCommit(false);

        try {
            statement= connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS\n" +
                    "                (\n" +
                    "                    id       BIGINT       DEFAULT NULL,\n" +
                    "                    name     VARCHAR(250) DEFAULT NULL,\n" +
                    "                    lastname VARCHAR(250) DEFAULT NULL,\n" +
                    "                    age      TINYINT      DEFAULT NULL\n" +
                    "                )");
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            if(statement != null){
                statement.close();
            }
            connection.setAutoCommit(true);
        }
    }

    public void dropUsersTable() throws SQLException {

        Statement statement = null;
        connection.setAutoCommit(false);

        try {
            statement = connection.createStatement();
            statement.executeUpdate( "DROP TABLE IF EXISTS USERS");
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            if(statement != null){
                statement.close();
            }
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        try{
            preparedStatement = connection.prepareStatement("INSERT INTO USERS (name, lastname, age) VALUES( ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {

        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        try{
            preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE id= (?)");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() throws SQLException {

        Statement statement = null;
        connection.setAutoCommit(false);
        List<User> userList = new ArrayList<>();

        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                userList.add(user);
                connection.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            if(statement != null){
                statement.close();
            }
            connection.setAutoCommit(true);
        }

        return userList;
    }

    public void cleanUsersTable() throws SQLException {

        Statement statement = null;
        connection.setAutoCommit(false);

        try{
            statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE USERS");
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            if(statement != null){
                statement.close();
            }
            connection.setAutoCommit(true);
        }
    }

}