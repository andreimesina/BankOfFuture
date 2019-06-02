package com.andreimesina.bankoffuture.service;

import com.andreimesina.bankoffuture.model.Deposit;
import com.andreimesina.bankoffuture.model.DepositTransaction;
import com.andreimesina.bankoffuture.model.dao.TransactionDao;

import java.util.List;

public class TransactionService {

    private static TransactionService instance;

    private DepositService depositService;

    private TransactionDao transactionDao;

    private TransactionService() {
        depositService = DepositService.getInstance();
        transactionDao = TransactionDao.getInstance();
    }

    public static synchronized TransactionService getInstance() {
        if(instance == null) {
            instance = new TransactionService();
        }

        return instance;
    }

    public boolean verifyTransactionReceiverData(int depositId, String fullName) {
        return transactionDao.verifyTransactionReceiverData(depositId, fullName);
    }

    public boolean createNewTransaction(DepositTransaction transaction) {
        Deposit sender = depositService.getDeposit(transaction.getSenderDepositId());
        Deposit receiver = depositService.getDeposit(transaction.getReceiverDepositId());

        if(sender.getAmount() < transaction.getAmount()) {
            return false;
        }

        if(!sender.getCurrency().equals(receiver.getCurrency())) {
            String senderCurrency = sender.getCurrency();
            String receiverCurrency = receiver.getCurrency();

            float conversionRate = transactionDao.getConversionRate(depositService.getCurrencyId(senderCurrency),
                                                                    depositService.getCurrencyId(receiverCurrency));
            transaction.setAmount(transaction.getAmount() * conversionRate);
        }

        return transactionDao.insertTransaction(transaction);
    }

    public List<DepositTransaction> getSenderTransactions(int memberId) {
        return transactionDao.getTransactions(memberId, true);
    }

    public List<DepositTransaction> getReceiverTransactions(int memberId) {
        return transactionDao.getTransactions(memberId, false);
    }

    public boolean acceptTransaction(DepositTransaction transaction) {
        Deposit sender = depositService.getDeposit(transaction.getSenderDepositId());
        Deposit receiver = depositService.getDeposit(transaction.getReceiverDepositId());

        receiver.setAmount(receiver.getAmount() + transaction.getAmount());

        if(!sender.getCurrency().equals(receiver.getCurrency())) {
            String senderCurrency = sender.getCurrency();
            String receiverCurrency = receiver.getCurrency();

            float conversionRate = transactionDao.getConversionRate(depositService.getCurrencyId(receiverCurrency),
                    depositService.getCurrencyId(senderCurrency));

            sender.setAmount(sender.getAmount() - (transaction.getAmount() * conversionRate));
        } else {
            sender.setAmount(sender.getAmount() - transaction.getAmount());
        }

        return transactionDao.acceptTransaction(transaction.getId());
    }

    public boolean denyTransaction(DepositTransaction transaction) {
        return transactionDao.denyTransaction(transaction.getId());
    }

    public float getConversionRate(String fromCurrency, String toCurrency) {
        return transactionDao.getConversionRate(depositService.getCurrencyId(fromCurrency),
                                                depositService.getCurrencyId(toCurrency));
    }
}
