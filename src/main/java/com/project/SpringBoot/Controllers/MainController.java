package com.project.SpringBoot.Controllers;

import com.project.SpringBoot.entites.Roles;
import com.project.SpringBoot.entites.Users;
import com.project.SpringBoot.repositories.RolesRepository;
import com.project.SpringBoot.repositories.UserRepository;
import com.project.SpringBoot.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    UserServiceImpl userService;

    @GetMapping(path = "/")
    public String index(Model model){

        return "main";
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        return "annonymous/login";
    }


    @GetMapping(path = "/register")
    public String registration(){return "annonymous/register";}

    @PostMapping(path = "/register")
    public String addUser(
            @RequestParam(name = "user_email") String user_email,
            @RequestParam(name = "user_password") String user_password,
            @RequestParam(name = "password_req") String rpassword,
            @RequestParam(name = "lname") String lname,
            @RequestParam(name = "fname") String fname){

        String redirect = "redirect:/register?error";

        Users user = userRepository.findByEmail(user_email);

        if (user == null){

            if (user_password.equals(rpassword)){
                Set<Roles> roles = new HashSet<>();
                Roles userRole = rolesRepository.getOne(1L);
                roles.add(userRole);

                user = new Users(user_email, fname, lname,user_password, roles);
                userService.registerUser(user);
                redirect ="redirect:/register?success";
            }

        }
        return redirect;
    }

    @GetMapping(path = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){

        model.addAttribute("user", getUserData());

        return "profile";
    }
    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername());
        }
        return userData;
    }

}
