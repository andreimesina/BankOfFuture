package com.andreimesina.bankoffuture.controller;

import com.andreimesina.bankoffuture.model.Member;
import com.andreimesina.bankoffuture.model.MemberDetails;
import com.andreimesina.bankoffuture.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    private MemberService service;

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("registration");
        mav.addObject("pageTitle", "Registration");

        return mav;
    }

    @RequestMapping(value = "/register/verify", method = RequestMethod.POST)
    public ModelAndView verifyRegistration(@ModelAttribute("Member") Member member,
                                           @ModelAttribute("MemberDetails") MemberDetails memberDetails,
                                           HttpServletRequest request) {
        ModelAndView mav;

        service = MemberService.getInstance();
        Integer memberId = service.registerNewMember(member, memberDetails);

        if (memberId != null) {
            request.getSession().setAttribute("memberId", memberId);
            mav = new ModelAndView("redirect:/member");
        } else {
            mav = new ModelAndView("registration");
            mav.addObject("error", "Registration failed");
        }

        return mav;
    }
}
