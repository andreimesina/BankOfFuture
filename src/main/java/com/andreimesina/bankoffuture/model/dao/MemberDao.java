package com.andreimesina.bankoffuture.model.dao;

import com.andreimesina.bankoffuture.model.MemberDetails;
import com.andreimesina.bankoffuture.model.database.DatabaseManager;

import java.sql.*;

public class MemberDao extends Dao {
    private static MemberDao instance;

    private MemberDao() {
        super();
    }

    public static synchronized MemberDao getInstance() {
        if (instance == null) {
            instance = new MemberDao();
        }

        return instance;
    }

    public Integer insertNewMember(String username, String password) {
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO Member(username, password) ");
        query.append("VALUES(?, ?) ");

        ResultSet rs = null;
        Integer memberId = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                memberId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return memberId;
    }

    public void insertMemberDetails(MemberDetails memberDetails) {
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO MemberDetails(memberId, firstName, lastName, cnp, email, phone, address) ");
        query.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, memberDetails.getMemberId());
            ps.setString(2, memberDetails.getFirstName());
            ps.setString(3, memberDetails.getLastName());
            ps.setString(4, memberDetails.getCnp());
            ps.setString(5, memberDetails.getEmail());
            ps.setString(6, memberDetails.getPhone());
            ps.setString(7, memberDetails.getAddress());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getMemberIdByCredentials(String username, String password) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT m.id ");
        query.append("FROM Member m ");
        query.append("WHERE m.username = ? ");
        query.append("AND m.password = ?");

        ResultSet rs = null;
        Integer memberId = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                memberId = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return memberId;
    }

    public MemberDetails getMemberDetails(int memberId) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * ");
        query.append("FROM MemberDetails md ");
        query.append("WHERE md.memberId = ?");

        ResultSet rs = null;
        MemberDetails md = null;

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, memberId);

            rs = ps.executeQuery();
            if (rs.next()) {
                md = new MemberDetails();

                md.setId(rs.getInt("id"));
                md.setMemberId(rs.getInt("memberId"));
                md.setFirstName(rs.getNString("firstName"));
                md.setLastName(rs.getNString("lastName"));
                md.setCnp(rs.getNString("cnp"));
                md.setEmail(rs.getNString("email"));
                md.setPhone(rs.getNString("phone"));
                md.setAddress(rs.getNString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.close(rs);
        }

        return md;
    }

    public void updateMemberDetails(MemberDetails memberDetails) {
        StringBuffer query = new StringBuffer();
        query.append("UPDATE MemberDetails ");
        query.append("SET firstName = ?, ");
        query.append("lastName = ?, ");
        query.append("cnp = ?, ");
        query.append("email = ?, ");
        query.append("phone = ?, ");
        query.append("address = ? ");
        query.append("WHERE memberId = ?");

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setString(1, memberDetails.getFirstName());
            ps.setString(2, memberDetails.getLastName());
            ps.setString(3, memberDetails.getCnp());
            ps.setString(4, memberDetails.getEmail());
            ps.setString(5, memberDetails.getPhone());
            ps.setString(6, memberDetails.getAddress());
            ps.setInt(7, memberDetails.getMemberId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMember(int memberId) {
        StringBuffer query = new StringBuffer();
        query.append("DELETE ");
        query.append("FROM Member ");
        query.append("WHERE id = ?");

        try(Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
            PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, memberId);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
