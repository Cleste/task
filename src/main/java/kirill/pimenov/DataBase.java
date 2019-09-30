package kirill.pimenov;

import kirill.pimenov.Exceptions.EmptyDBException;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DataBase {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    void connect() {
        try {
            Properties property = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/settings.properties");
            property.load(fis);
            connection = DriverManager.getConnection(property.getProperty("db.host"),
                    property.getProperty("db.login"), property.getProperty("db.password"));
            statement = connection.createStatement();
        } catch (Exception e) {
            iisSoftTask.log.error(e);
        }
    }

    ArrayList<CodeJobKey> pullKeys() {
        String query = "SELECT DepCode, DepJob from departments";
        ArrayList<CodeJobKey> keys = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                keys.add(new CodeJobKey(resultSet.getString("DepCode"),
                        resultSet.getString("DepJob")));
            }
            if (keys.isEmpty()) throw new EmptyDBException();
        } catch (SQLException | EmptyDBException e) {
            iisSoftTask.log.error(e);
        }
        return keys;
    }

    HashMap<CodeJobKey, String> pullAll() {
        String query = "SELECT * from departments";
        HashMap<CodeJobKey, String> departments = new HashMap<>();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                departments.put(new CodeJobKey(resultSet.getString("DepCode"),
                        resultSet.getString("DepJob")), resultSet.getString("Description"));
            }

        } catch (SQLException e) {
            iisSoftTask.log.error(e);
        }
        if (departments.isEmpty()) try {
            throw new EmptyDBException();
        } catch (EmptyDBException e) {
            iisSoftTask.log.error(e);
        }
        return departments;
    }

    void insert(CodeJobKey key, String description) {
        String query = "INSERT INTO departments(DepCode, DepJob, Description) VALUES ('"
                + key.getCode() + "','" + key.getJob() + "','" + description + "')";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            iisSoftTask.log.error(e);
        }
    }

    void close() {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            iisSoftTask.log.error(e);
        }

    }

    void update(CodeJobKey key, String description) {
        String query = "UPDATE departments SET Description = '" + description +
                "' where DepCode = '" + key.getCode() + "' and DepJob = '" + key.getJob() + "'";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            iisSoftTask.log.error(e);
        }
    }

    void delete(CodeJobKey key) {
        String query = "DELETE from departments " +
                "where DepCode = '" + key.getCode() + "' and DepJob = '" + key.getJob() + "'";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            iisSoftTask.log.error(e);
        }
    }
}
