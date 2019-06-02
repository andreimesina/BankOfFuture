package com.andreimesina.bankoffuture.model;

public class Deposit {

    private int id;

    private int memberId;

    private String currency;

    private float amount;

    public Deposit() {}

    public Deposit(int id, int memberId, String currency, float amount) {
        this.id = id;
        this.memberId = memberId;
        this.currency = currency;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "memberId=" + memberId +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
