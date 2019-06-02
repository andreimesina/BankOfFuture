package com.andreimesina.bankoffuture.service;

import com.andreimesina.bankoffuture.model.Deposit;
import com.andreimesina.bankoffuture.model.dao.DepositDao;

import java.util.List;

public class DepositService {

    private static DepositService instance;

    private DepositDao depositDao;

    private DepositService() {
        depositDao = DepositDao.getInstance();
    }

    public static synchronized DepositService getInstance() {
        if(instance == null) {
            instance = new DepositService();
        }

        return instance;
    }

    public boolean createNewDeposit(Deposit deposit) {
        return depositDao.insertDeposit(deposit);
    }

    public Deposit getDeposit(int depositId) {
        return depositDao.getDeposit(depositId);
    }

    public List<Deposit> getDepositsList(int memberId) {
        return depositDao.getDepositsForMember(memberId);
    }

    public int getCurrencyId(String currency) {
        return depositDao.getCurrencyId(currency);
    }
}
