package com.oneum20.study.settings;

import com.oneum20.study.domain.Account;
import com.oneum20.study.main.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @GetMapping("/settings/profile")
    public String profileUpdatedForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new Profile(account));

        return "settings/profile";
    }
}
