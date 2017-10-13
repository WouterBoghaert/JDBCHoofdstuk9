package be.vdab;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:mysql://localhost/tuincentrum?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String UPDATE_PRIJS_10_PROCENT = 
        "update planten set verkoopprijs = verkoopprijs*1.1 where verkoopprijs >= 100";
    private static final String UPDATE_PRIJS_5_PROCENT =
        "update planten set verkoopprijs = verkoopprijs*1.05 where verkoopprijs < 100";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement()){
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.addBatch(UPDATE_PRIJS_10_PROCENT);
            statement.addBatch(UPDATE_PRIJS_5_PROCENT);
            int [] aantalGewijzigdeRecordsPerUpdate = statement.executeBatch();
            System.out.println(aantalGewijzigdeRecordsPerUpdate[0] + 
                    " planten met 10% verhoogd.");
            System.out.println(aantalGewijzigdeRecordsPerUpdate[1] + 
                    " planten met 5% verhoogd.");
            connection.commit();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }    
}
