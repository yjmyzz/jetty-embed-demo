package yjmyzz.jetty.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjmyzz.jetty.demo.domain.DemoObject;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }


    @RequestMapping("/test")
    @ResponseBody
    public DemoObject test() {
        return new DemoObject(1, "abc");
    }

}
