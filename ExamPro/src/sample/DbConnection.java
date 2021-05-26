package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    public static Connection Connector() {
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:exampro.sqlite");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
