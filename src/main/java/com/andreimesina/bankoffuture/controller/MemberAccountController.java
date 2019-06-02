package com.andreimesina.bankoffuture.controller;

import com.andreimesina.bankoffuture.model.MemberDetails;
import com.andreimesina.bankoffuture.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberAccountController {

    private MemberService service;

    @RequestMapping(value = "/member/account")
    public ModelAndView memberAccount(HttpServletRequest request,
                                      HttpServletResponse response) {
        ModelAndView mav;
        int memberId;

        if (request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("memberAccount");
            memberId = (int) request.getSession().getAttribute("memberId");

            service = MemberService.getInstance();
            MemberDetails memberDetails = service.getMemberDetails(memberId);
            mav.addObject("memberDetails", memberDetails);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/updateAccount", method = RequestMethod.POST)
    public ModelAndView updateAccount(@ModelAttribute("MemberDetails") MemberDetails memberDetails,
                                      HttpServletRequest request) {
        ModelAndView mav;

        int memberId;
        if (request.getSession().getAttribute("memberId") != null) {
            mav = new ModelAndView("redirect:/member/account");
            memberId = (int) request.getSession().getAttribute("memberId");

            service = MemberService.getInstance();
            memberDetails.setMemberId(memberId);
            service.updateMemberDetails(memberDetails);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

    @RequestMapping(value = "/member/removeAccount")
    public ModelAndView removeAccount(HttpServletRequest request) {
        ModelAndView mav;

        int memberId;
        if (request.getSession().getAttribute("memberId") != null) {
            mav = new ModelAndView("redirect:/login");
            memberId = (int) request.getSession().getAttribute("memberId");

            service = MemberService.getInstance();
            service.removeMember(memberId);
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }

}
