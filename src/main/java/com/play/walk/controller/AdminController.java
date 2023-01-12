package com.play.walk.controller;

import com.play.walk.mapper.AdminMapper;
import com.play.walk.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/test/print", method = {RequestMethod.GET})
    public RedirectView test(){
        System.out.println("test print123!");
        return new RedirectView("/main.html");
        //adminService.test();
    }

}
