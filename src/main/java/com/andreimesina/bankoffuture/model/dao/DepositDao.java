package com.andreimesina.bankoffuture.model.dao;

import com.andreimesina.bankoffuture.model.Deposit;
import com.andreimesina.bankoffuture.model.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepositDao extends Dao {

    private static DepositDao instance;

    private DepositDao() {
        super();
    }

    public static synchronized DepositDao getInstance() {
        if(instance == null) {
            instance = new DepositDao();
        }

        return instance;
    }

    public boolean insertDeposit(Deposit deposit) {
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO Deposit(memberId, currencyId, amount) ");
        query.append(" VALUES (?, (SELECT id FROM Currency WHERE currency = ?), ?)");

        ResultSet rs = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, deposit.getMemberId());
            ps.setString(2, deposit.getCurrency());
            ps.setFloat(3, deposit.getAmount());

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

    public Deposit getDeposit(int depositId) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM Deposit d ");
        query.append("WHERE d.id = ?");

        ResultSet rs = null;
        Deposit deposit = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, depositId);

            rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");

                int memberId = rs.getInt("memberId");

                int currencyId = rs.getInt("currencyId");
                String currency = getCurrency(currencyId);
                currency = (currency == null) ? "NULL" : currency;

                float amount = rs.getFloat("amount");

                deposit = new Deposit(id, memberId, currency, amount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return deposit;
    }

    public List<Deposit> getDepositsForMember(int memberId) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM Deposit d ");
        query.append("WHERE d.memberId = ?");

        ResultSet rs = null;
        List<Deposit> deposits = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, memberId);

            rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");

                int currencyId = rs.getInt("currencyId");
                String currency = getCurrency(currencyId);
                currency = (currency == null) ? "NULL" : currency;

                float amount = rs.getFloat("amount");

                deposits.add(new Deposit(id, memberId, currency, amount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return deposits;
    }

    public String getCurrency(int currencyCd) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM Currency c ");
        query.append("WHERE c.id = ?");

        ResultSet rs = null;
        String currency = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, currencyCd);
            rs = ps.executeQuery();

            if(rs.next()) {
                currency = rs.getNString("currency");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return currency;
    }

    public int getCurrencyId(String currency) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT c.id ");
        query.append("FROM Currency c ");
        query.append("WHERE c.currency = ?");

        ResultSet rs = null;
        int currencyId = 0;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setString(1, currency);
            rs = ps.executeQuery();

            if(rs.next()) {
                currencyId = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return currencyId;
    }

}
