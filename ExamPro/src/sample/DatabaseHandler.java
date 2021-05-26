package sample;

import java.sql.*;

public final class DatabaseHandler {
    private static DatabaseHandler dh = null;
    private static Connection connection = null;
    private static Statement statement = null;

    public static DatabaseHandler getInstance(){
        if (dh == null){
            dh = new DatabaseHandler();
        }
        return dh;
    }

    private DatabaseHandler(){
        createConnection();
    }

    void createConnection() {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:exampro.sqlite");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet exeQuery(String query){
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }

    public boolean exeAction(String str){
        try{
            statement = connection.createStatement();
            statement.execute(str);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean isNumeric(String value){
        boolean numeric  = value.matches("-?\\d+(\\.\\d+)?");
        return numeric;
    }
}
