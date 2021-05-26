package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public LoginModel(){
        connection = DbConnection.Connector();
        if (connection == null){
            System.exit(1);
        }
    }

    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyLogin(String username, String password) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryStr = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {

            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }finally {

            assert preparedStatement != null;
            preparedStatement.close();
            assert resultSet != null;
            resultSet.close();

        }
    }

    public int getUser(String username, String pass) throws SQLException {
        int userType = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryStr = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {

            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userType = resultSet.getInt(4);
            }
            return userType;
        } catch (Exception e) {

            e.printStackTrace();

        }finally {

            assert preparedStatement != null;
            preparedStatement.close();
            assert resultSet != null;
            resultSet.close();

        }
        return userType;
    }
}
