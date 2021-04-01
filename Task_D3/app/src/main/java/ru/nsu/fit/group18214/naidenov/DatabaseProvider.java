package ru.nsu.fit.group18214.naidenov;

import java.sql.*;
import java.util.List;

public class DatabaseProvider {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/Task_D3";
    private PreparedStatement stmt;
    private Connection connection;
    private static String insertPersons = "INSERT INTO persons(person_id,first_name,surname,gender) VALUES (?,?,?,?)";
    private static final String insertRelations = "INSERT INTO tableName (person_id, personType) VALUES (?,?)";
    private static String deleteAll = "TRUNCATE persons CASCADE";
    public static final int BATCH_SIZE = 3000;


    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, "postgres", "123123");//соединениесБД

            System.out.println("Соединение с СУБД выполнено.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("connection failure, try again");
        }

    }

    public void clearTable() {
        try {
            stmt = connection.prepareStatement(deleteAll);
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
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public void insertRelations(String table, String personType, String person, List<String> args) {
        String query = insertRelations.replaceFirst("tableName", table);
        query = query.replaceFirst("personType", personType);
        try {
            stmt = connection.prepareStatement(query);
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
        } catch (SQLException ex) {
            System.out.println(table + " " + person);

        }


    }

    public void insertRelations(String table, String personType, String person, String arg) {
        String query = insertRelations.replaceFirst("tableName", table);
        query = query.replaceFirst("personType", personType);
        try {
            stmt = connection.prepareStatement(query);
            try {
                stmt.setString(1, person);
                stmt.setString(2, arg);
                stmt.addBatch();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            stmt.executeBatch();
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
