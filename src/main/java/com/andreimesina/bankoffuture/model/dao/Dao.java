package com.andreimesina.bankoffuture.model.dao;

public abstract class Dao {

    protected Dao() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
