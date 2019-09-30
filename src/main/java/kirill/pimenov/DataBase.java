package kirill.pimenov;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            System.out.println(e);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keys;
    }

    void insert(CodeJobKey key, String description) {
        String query = "INSERT INTO departments(DepCode, DepJob, Description) VALUES ('"
                + key.getCode() + "','" + key.getJob() + "','" + description + "')";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void close() {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void update(CodeJobKey key, String description) {
        String query = "UPDATE departments SET Description = '" + description +
                "' where DepCode = '" + key.getCode() + "' and DepJob = '" + key.getJob() + "'";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void delete(CodeJobKey key) {
        String query = "DELETE from departments " +
                "where DepCode = '" + key.getCode() + "' and DepJob = '" + key.getJob() + "'";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
