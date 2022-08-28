package com.oneum20.study.account;

import com.oneum20.study.domain.Account;
import com.oneum20.study.main.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.*;
import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;



    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model){

        model.addAttribute("email", account.getEmail());

        return "account/check-email";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account == null){
            model.addAttribute("error", "worng.email");
            return view;
        }

        if(!account.isValidToken(token)){
            model.addAttribute("error", "wrong.email");
            return view;
        }

        accountService.completeSignUp(account);

        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("nickname", account.getNickname());


        return view;
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model){
        if(!account.canSendConfirmEmail()){
            model.addAttribute("error", "메일은 한 시간에 한 번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());

            return "account/check-email";
        }

        return "redirect:/";
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser Account account){
        Account byNickname = accountRepository.findByNickname(nickname);

        if(byNickname == null){
            throw  new IllegalArgumentException("잘못된 접근입니다.");
        }

        model.addAttribute(byNickname);

        model.addAttribute("isOwner", byNickname.equals(account));

        return "account/profile";

    }
}
