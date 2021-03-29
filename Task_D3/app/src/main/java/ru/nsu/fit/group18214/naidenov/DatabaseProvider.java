package ru.nsu.fit.group18214.naidenov;

import java.sql.*;

public class DatabaseProvider {
    // Блок объявления констант
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/Task_D2";

    public static void run() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, "postgres", "123123");//соединениесБД
            System.out.println("Соединение с СУБД выполнено.");
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("INSERT INTO persons(person_id,first_name,surname,gender) VALUES ('P000000','Zero','Zero','male')");
//                while (rs.next()) {
//                    System.out.println(rs.getString("mother_id") + " " + rs.getString("child_id"));
//                }
                //rs.close();
                stmt.close();
            } finally {
                connection.close();
                System.out.println("Отключение от СУБД выполнено.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }


}
