package com.hzlei.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 16:18
 */
@Controller
public class SecurityController {

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/403.html")
    public String noPermission() {
        return "403";
    }

}
