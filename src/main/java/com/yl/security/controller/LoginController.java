package com.yl.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @Author yang
 * @Description //TODO $
 * @Date $ $
 **/
@Controller
public class LoginController {
    @RequestMapping("/toLogin")
    public String tologin(){
        return "views/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "index";
    }
    @RequestMapping("/level1/{id}")
    public String level1(@PathVariable("id") String id){
        return "views/level1/"+id;
    }
    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable("id") String id){
        return "views/level2/"+id;
    }
    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id") String id){
        return "views/level3/"+id;
    }
}
