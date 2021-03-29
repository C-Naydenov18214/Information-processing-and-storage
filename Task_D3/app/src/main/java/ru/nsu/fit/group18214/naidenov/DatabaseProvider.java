package ru.nsu.fit.group18214.naidenov;

import java.sql.*;
import java.util.List;

public class DatabaseProvider {
    // Блок объявления констант
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/Task_D2";
    private static PreparedStatement stmt;
    private static Connection connection;
    private static String insertQuery = "INSERT INTO persons(person_id,first_name,surname,gender) VALUES (?,?,?,?)";


    public static void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, "postgres", "123123");//соединениесБД
            try {
                stmt = connection.prepareStatement(insertQuery);
                System.out.println("Соединение с СУБД выполнено.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("connection failure, try again");
        }

    }

    public static void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            connection.close();
            System.out.println("Отключение от СУБД выполнено.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void insertValues(List<Person> persons) {
        for (Person person : persons) {
            try {
                stmt.setString(1, person.getId());
                stmt.setString(2, person.getFirstName());
                stmt.setString(3, person.getLastName());
                stmt.setString(4, person.getGender());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }


    }

    public static void badInsert() {
        try {
            stmt.setString(1, "0");
            stmt.setString(2, null);
            stmt.setString(3, null);
            stmt.setString(4, null);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


}
