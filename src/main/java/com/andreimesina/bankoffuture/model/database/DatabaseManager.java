package com.andreimesina.bankoffuture.model.database;

import java.sql.ResultSet;

public class DatabaseManager {

    public static String DB_URL =
            "jdbc:sqlserver://localhost:1433;databaseName=BankOfFuture;user=bankAdmin;password=default123";

    public static void close(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
