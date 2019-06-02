package com.andreimesina.bankoffuture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

    @RequestMapping(value = "/member")
    public ModelAndView memberView(HttpServletRequest request,
                                   HttpServletResponse response) {
        ModelAndView mav;

        if (request.getSession().getAttribute("memberId") != null) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            mav = new ModelAndView("member");
        } else {
            mav = new ModelAndView("redirect:/signIn");
            mav.addObject("error", "NA");
        }

        return mav;
    }
}
