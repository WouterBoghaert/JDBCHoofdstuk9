package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main2 {
    private static final String URL = 
        "jdbc:mysql://localhost/tuincentrum?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String INSERT_SOORT =
        "insert into soorten(naam) values(?)";
    public static void main(String[] args) {
        Set<String> namen = new LinkedHashSet<>();
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Tik soortnamen, tik stop na de laatste naam");
            for(String naam; !"stop".equalsIgnoreCase(naam = scanner.nextLine());
                    namen.add(naam));
        }
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT_SOORT)){
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            for(String naam: namen){
                statement.setString(1,naam);
                statement.addBatch();
            }
            int [] aantalToegevoegdeRecordsPerInsert = statement.executeBatch();
            connection.commit();
            System.out.print("Aantal toegevoegde soorten: ");
            System.out.println(Arrays.stream(aantalToegevoegdeRecordsPerInsert).sum());
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
