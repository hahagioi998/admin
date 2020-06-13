package com.hzlei.admin.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Description: apiController
 * @Author hzlei
 * @Date 2019/12/7 14:14
 */
@Controller
@RequestMapping(value = "${api-url}")
public class ApiController {

    @RequestMapping("/getPage")
    public ModelAndView getPage(ModelAndView modelAndView, String pageName) {
        modelAndView.setViewName(pageName);
        return modelAndView;
    }

}
