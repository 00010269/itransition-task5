package com.itransition.task5.controller;

import com.itransition.task5.entity.User;
import com.itransition.task5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/start")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    public String loginPage(HttpSession session, User userDto) {
        User user = userService.findByName(userDto);
        if (user != null) {
            session.setAttribute("authUser", user);
            return "redirect:/";
        }

        return "redirect:/start";
    }


    @GetMapping("/")
    public String homePage(HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user!=null){
            return "window";
        }
        return "redirect:/start";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/start";
    }








//    @GetMapping("/")
//    public String login() {
//        return "index";
//    }
//
//
//    @GetMapping("/start")
//    public String home() {
//        return "window";
//    }


}
