package com.andreimesina.bankoffuture.controller;

import com.andreimesina.bankoffuture.model.Deposit;
import com.andreimesina.bankoffuture.model.DepositTransaction;
import com.andreimesina.bankoffuture.service.DepositService;
import com.andreimesina.bankoffuture.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DepositTransactionController {

    private TransactionService service;

    @RequestMapping(value = "/member/transactions")
    public ModelAndView transactionsView(HttpServletRequest request,
                                         HttpServletResponse response) {
        ModelAndView mav;

        int memberId;
        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("transactions");
            memberId = (int) request.getSession().getAttribute("memberId");

            service = TransactionService.getInstance();

            List<DepositTransaction> senderTransactionsList = service.getSenderTransactions(memberId);
            List<DepositTransaction> receiverTransactionsList = service.getReceiverTransactions(memberId);

            mav.addObject("senderTransactionsList", senderTransactionsList);
            mav.addObject("receiverTransactionsList", receiverTransactionsList);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/transactions/newTransaction")
    public ModelAndView newTransaction(HttpServletRequest request,
                                       HttpServletResponse response) {
        ModelAndView mav;
        int memberId = 0;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            memberId = (int) request.getSession().getAttribute("memberId");
            List<Deposit> deposits = DepositService.getInstance().getDepositsList(memberId);

            mav = new ModelAndView("newTransaction");
            mav.addObject("deposits", deposits);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/transactions/newTransaction/create", method = RequestMethod.POST)
    public ModelAndView createTransaction(@RequestParam(value = "senderDepositId") int senderDepositId,
                                          @RequestParam(value = "receiverDepositId") int receiverDepositId,
                                          @RequestParam(value = "receiverFullName") String receiverFullName,
                                          @RequestParam(value = "amount") float amount,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        ModelAndView mav;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("redirect:/member/transactions");

            service = TransactionService.getInstance();
            if(service.verifyTransactionReceiverData(receiverDepositId, receiverFullName)) {
                DepositTransaction depositTransaction =
                        new DepositTransaction(senderDepositId, receiverDepositId, amount, false, true);
                service.createNewTransaction(depositTransaction);
                mav.addObject("success", "TS");
            } else {
                mav.addObject("error", "TF");
            }
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/transactions/acceptTransaction", method = RequestMethod.POST)
    public ModelAndView acceptTransaction(@ModelAttribute("DepositTransaction") DepositTransaction depositTransaction,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        ModelAndView mav;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("redirect:/member/transactions");

            service = TransactionService.getInstance();
            service.acceptTransaction(depositTransaction);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/transactions/denyTransaction", method = RequestMethod.POST)
    public ModelAndView denyTransaction(@ModelAttribute("DepositTransaction") DepositTransaction depositTransaction,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        ModelAndView mav;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("redirect:/member/transactions");

            service = TransactionService.getInstance();
            service.acceptTransaction(depositTransaction);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }
}
