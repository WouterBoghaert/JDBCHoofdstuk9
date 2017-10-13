package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Failliet2Main {
    private static final String URL = "jdbc:mysql://localhost/bieren?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String OVERNAME_BROUWER2 = 
        "update bieren set brouwerid = 2 where brouwerid = 1 and alcohol >= 8.5";
    private static final String OVERNAME_BROUWER3 =
        "update bieren set brouwerid = 3 where brouwerid = 1";
    private static final String DELETE_BROUWER1 = 
        "delete from brouwers where id = 1";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.addBatch(OVERNAME_BROUWER2);
            statement.addBatch(OVERNAME_BROUWER3);
            statement.addBatch(DELETE_BROUWER1);
            int [] aantalUitgevoerdeUpdates = statement.executeBatch();
            connection.commit();
            System.out.println("Aantal uitgevoerde updates:");
            System.out.println(Arrays.stream(aantalUitgevoerdeUpdates).sum());
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
