package paramedic_assistant_version_3_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/paramedicassistantappdb";
        String username = "root";
        String password = "123PA";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}