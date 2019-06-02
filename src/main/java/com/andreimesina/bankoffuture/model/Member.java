package com.andreimesina.bankoffuture.model;

public class Member {

    private int id;
    private String username;
    private String password;

    private MemberDetails memberDetails;

    public Member() {
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member(String username, String password, MemberDetails memberDetails) {
        this.username = username;
        this.password = password;
        this.memberDetails = memberDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MemberDetails getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
    }
}
