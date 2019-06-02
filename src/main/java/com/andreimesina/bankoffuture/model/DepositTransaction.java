package com.andreimesina.bankoffuture.model;

public class DepositTransaction {

    private int id;
    private int senderDepositId;
    private int receiverDepositId;
    private float amount;
    private boolean accepted;
    private boolean pending;

    public DepositTransaction() { }

    public DepositTransaction(int senderDepositId, int receiverDepositId,
                              float amount, boolean accepted, boolean pending) {
        this.senderDepositId = senderDepositId;
        this.receiverDepositId = receiverDepositId;
        this.amount = amount;
        this.accepted = accepted;
        this.pending = pending;
    }

    public DepositTransaction(int id, int senderDepositId, int receiverDepositId,
                              float amount, boolean accepted, boolean pending) {
        this.id = id;
        this.senderDepositId = senderDepositId;
        this.receiverDepositId = receiverDepositId;
        this.amount = amount;
        this.accepted = accepted;
        this.pending = pending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderDepositId() {
        return senderDepositId;
    }

    public void setSenderDepositId(int senderDepositId) {
        this.senderDepositId = senderDepositId;
    }

    public int getReceiverDepositId() {
        return receiverDepositId;
    }

    public void setReceiverDepositId(int receiverDepositId) {
        this.receiverDepositId = receiverDepositId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean getIsAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean getIsPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
