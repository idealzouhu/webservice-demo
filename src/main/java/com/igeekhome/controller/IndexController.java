package com.igeekhome.controller;

//import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @RequestMapping("/")
    public  String index(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(HttpSession session) {
        return "register";
    }

    @RequestMapping("login")
    public String login(HttpSession session){
        return "login";
    }


    @RequestMapping("/index")
    public String index1(HttpSession session){
//        if(session.getAttribute("cs")==null){
//            session.setAttribute("msg","你要正常登录才能进入");
//            return "login";
//
//        }
        return "index";
    }


    @RequestMapping("/exit")      //到达主页面，只是index的拦截器，其他的页面没有拦截器
    public String exit(HttpSession session, Model model){

//        session.setAttribute("main","非法登录");
        session.setAttribute("cs","null");
        return "login";          // index   这里是到页面
    }
}
