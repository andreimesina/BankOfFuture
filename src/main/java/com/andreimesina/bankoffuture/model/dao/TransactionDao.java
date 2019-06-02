package com.andreimesina.bankoffuture.model.dao;

import com.andreimesina.bankoffuture.model.DepositTransaction;
import com.andreimesina.bankoffuture.model.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao extends Dao {

    private static TransactionDao instance;

    private TransactionDao() {
        super();
    }

    public static synchronized TransactionDao getInstance() {
        if(instance == null) {
            instance = new TransactionDao();
        }

        return instance;
    }

    public boolean verifyTransactionReceiverData(int depositId, String fullName) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM Deposit d ");
        query.append("INNER JOIN MemberDetails md ");
        query.append("ON d.memberId = md.memberId ");
        query.append("WHERE d.id = ? AND (md.firstName + ' ' + md.lastName = ? ");
        query.append("OR md.lastName + ' ' + md.firstName = ?)");

        ResultSet rs = null;
        boolean isDataMatch = false;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, depositId);
            ps.setString(2, fullName);
            ps.setString(3, fullName);

            rs = ps.executeQuery();
            if(rs.next()) {
                isDataMatch = true;
            }

        } catch (Exception e) {

        } finally {
            DatabaseManager.close(rs);
        }

        return isDataMatch;
    }

    public boolean insertTransaction(DepositTransaction transaction) {
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO DepositTransaction(senderDepositId, receiverDepositId, amount, accepted, pending) ");
        query.append(" VALUES (?, ?, ?, ?, ?)");

        ResultSet rs = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transaction.getSenderDepositId());
            ps.setInt(2, transaction.getReceiverDepositId());
            ps.setFloat(3, transaction.getAmount());
            ps.setBoolean(4, transaction.getIsAccepted());
            ps.setBoolean(5, transaction.getIsPending());

            ps.execute();

            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                if(rs.getInt(1) != 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return false;
    }

    public List<DepositTransaction> getTransactions(int memberId, boolean isSender) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM DepositTransaction dt ");
        query.append("INNER JOIN Deposit d ");
        if(isSender == true) {
            query.append("ON dt.senderDepositId = d.id ");
        } else {
            query.append("ON dt.receiverDepositId = d.id ");
        }
        query.append("AND d.memberId = ? ");

        ResultSet rs;
        List<DepositTransaction> transactions = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, memberId);

            rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int senderDepositId = rs.getInt("senderDepositId");
                int receiverDepositId = rs.getInt("receiverDepositId");
                float amount = rs.getFloat("amount");
                boolean accepted = rs.getBoolean("accepted");
                boolean pending = rs.getBoolean("pending");

                transactions.add(
                        new DepositTransaction(id, senderDepositId, receiverDepositId, amount, accepted, pending));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public float getConversionRate(int fromCurrencyId, int toCurrencyId) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT c.conversionRate ");
        query.append("FROM Conversion c ");
        query.append("WHERE c.fromCurrencyId = ? AND c.toCurrencyId = ?");

        ResultSet rs = null;
        float conversionRate = 0.0f;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, fromCurrencyId);
            ps.setInt(2, toCurrencyId);

            rs = ps.executeQuery();
            if(rs.next()) {
                conversionRate = rs.getFloat("conversionRate");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return conversionRate;
    }

    public boolean acceptTransaction(int transactionId) {
        StringBuffer query = new StringBuffer();
        query.append("UPDATE DepositTransaction ");
        query.append("SET accepted = 1, pending = 0 ");
        query.append("WHERE id = ?");

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, transactionId);
            ps.execute();

            if(ps.getUpdateCount() != -1) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean denyTransaction(int transactionId) {
        StringBuffer query = new StringBuffer();
        query.append("UPDATE DepositTransaction ");
        query.append("SET accepted = 0, pending = 0 ");
        query.append("WHERE id = ?");

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, transactionId);
            ps.execute();

            if(ps.getUpdateCount() != -1) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
