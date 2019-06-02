package com.andreimesina.bankoffuture.controller;

import com.andreimesina.bankoffuture.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    private MemberService service;

    @RequestMapping(value = "/signIn")
    public ModelAndView signIn() {
        ModelAndView mav = new ModelAndView("signIn");
        mav.addObject("pageTitle", "Sign in");

        return mav;
    }

    @RequestMapping(value = "/signIn/verify")
    public ModelAndView verifyLogin(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    HttpServletRequest request) {
        ModelAndView mav;

        service = MemberService.getInstance();
        Integer memberId = service.getMemberIdByCredentials(username, password);

        if (memberId != null) {
            request.getSession().setAttribute("memberId", memberId);
            mav = new ModelAndView("redirect:/member");
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "AF");
        }

        return mav;
    }
}
