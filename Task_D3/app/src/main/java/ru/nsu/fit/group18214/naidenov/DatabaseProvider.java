package ru.nsu.fit.group18214.naidenov;

import java.sql.*;
import java.util.List;

public class DatabaseProvider {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/Task_D3";
    private PreparedStatement stmt;
    private Connection connection;
    private static String insertPersons = "INSERT INTO persons(person_id,first_name,surname,gender) VALUES (?,?,?,?)";
    private static final String insertRelations = "INSERT INTO tableName (person_id, personType) VALUES (?,?)";
    private static String deleteQuery = "DELETE FROM daughters";


    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, "postgres", "123123");//соединениесБД

            System.out.println("Соединение с СУБД выполнено.");
        } catch (SQLException ex) {
            ex.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("connection failure, try again");
        }

    }

    public void clearTable(String tableName) {
        try {
            stmt = connection.prepareStatement(deleteQuery);
            //stmt.setString(1, tableName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection() {
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

    public void insertPersons(List<Person> persons) {

        try {
            stmt = connection.prepareStatement(insertPersons);
            //connection.setAutoCommit(false);
            for (Person person : persons) {
                try {
                    stmt.setString(1, person.getId());
                    stmt.setString(2, person.getFirstName());
                    stmt.setString(3, person.getLastName());
                    stmt.setString(4, person.getGender());
                    stmt.addBatch();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            stmt.executeBatch();
            //connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public void insertRelations(String table, String personType, String person, List<String> args) {
        String query = insertRelations.replaceFirst("tableName", table);
        query = query.replaceFirst("personType", personType);
        try {
            stmt = connection.prepareStatement(query);
            //connection.setAutoCommit(false);
            for (String id : args) {
                try {
                    stmt.setString(1, person);
                    stmt.setString(2, id);
                    stmt.addBatch();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            stmt.executeBatch();
            //connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }


    }

    public void insertRelations(String table, String personType, String person, String arg) {
        String query = insertRelations.replaceFirst("tableName", table);
        query = query.replaceFirst("personType", personType);
        try {
            stmt = connection.prepareStatement(query);
            //connection.setAutoCommit(false);
            try {
                stmt.setString(1, person);
                stmt.setString(2, arg);
                stmt.addBatch();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            stmt.executeBatch();
            //connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }


    }

    public void badInsert() {
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
