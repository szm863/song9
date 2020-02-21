package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        return "hellow world hasAuthority('add')";
    }

    @RequestMapping("/bbb")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String dd(){
        return "hellow bbbb ROLE_ADMIN";
    }
}
