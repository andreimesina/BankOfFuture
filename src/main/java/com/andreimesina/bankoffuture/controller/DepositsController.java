package com.andreimesina.bankoffuture.controller;

import com.andreimesina.bankoffuture.model.Deposit;
import com.andreimesina.bankoffuture.service.DepositService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DepositsController {

    private DepositService service;

    @RequestMapping(value = "/member/deposits", method = RequestMethod.GET)
    public ModelAndView depositsView(HttpServletRequest request,
                                     HttpServletResponse response) {
        ModelAndView mav;
        int memberId;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("deposits");
            memberId = (int) request.getSession().getAttribute("memberId");

            service = DepositService.getInstance();

            List<Deposit> depositsList = service.getDepositsList(memberId);
            mav.addObject("depositsList", depositsList);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/deposits/newDeposit")
    public ModelAndView newDeposit(HttpServletRequest request,
                                   HttpServletResponse response) {
        ModelAndView mav;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("newDeposit");
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/deposits/newDeposit/create", method = RequestMethod.POST)
    public ModelAndView createDeposit(@ModelAttribute("Deposit") Deposit deposit,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        ModelAndView mav;
        int memberId;
        boolean isCreated = false;

        if(request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("redirect:/member/deposits");
            mav.addObject("pageTitle", "Deposits");

            memberId = (int) request.getSession().getAttribute("memberId");

            service = DepositService.getInstance();
            deposit.setMemberId(memberId);
            isCreated = service.createNewDeposit(deposit);

            if(isCreated == false) {
                mav.addObject("error", "FCD");
            }
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }
}
