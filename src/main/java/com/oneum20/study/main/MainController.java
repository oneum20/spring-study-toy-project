package com.oneum20.study.main;

import com.oneum20.study.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model){
        if (account != null){
            model.addAttribute(account);
        }

        return "index";
    }

    @GetMapping("/sign-in")
    public String signIn(){
        return "account/sign-in";
    }
}
