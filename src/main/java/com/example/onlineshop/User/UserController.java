package com.example.onlineshop.User;

import com.example.onlineshop.Employee.EmployeeUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper mapper;

    @GetMapping("/registration/add")
    public String registrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/registration-form";
    }

    @PostMapping("/registration/submit")
    public String postRegister(@Valid UserDto userDto, BindingResult bindingResult, Model model) {

        User userForSeeIfUsernameExist = userRepository.getUserByUsername(userDto.getUsername());
        User userForSeeIfEmailExist = userRepository.getUserByEmail(userDto.getEmail());
        if (bindingResult.hasErrors()) {
            return "user/registration-form";
        }
        if (userForSeeIfUsernameExist != null) {
            model.addAttribute("userExistMessage", "this username already exist !");
            return "user/registration-form";
        }
        if (userForSeeIfEmailExist != null) {
            model.addAttribute("emailExistMessage", "this email already exist !");
            return "user/registration-form";
        }
        if (!userService.ifTwoPasswordsMatch(userDto.getPassword(), userDto.getRepeatPassword())) {
            model.addAttribute("passwordsDoNotMatch", "two passwords do not match !");
            return "user/registration-form";
        }

        User user = mapper.toEntity(userDto);
        userRepository.save(user);
        return "user/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/employee/registration")
    public String addEmployee(Model model) {
        model.addAttribute("employeeUserDto", new EmployeeUserDto());
        return "user/registration-employee";
    }

    @PostMapping("/employee/registration")
    public String postEmployee(@Valid EmployeeUserDto employeeUserDto, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            return "user/registration-employee";
        }

        if (!userService.ifTwoPasswordsMatch(employeeUserDto.getPassword(), employeeUserDto.getCheckPassword())) {
            model.addAttribute("passwordsDoNotMatch", "two passwords do not match !");
            return "user/registration-employee";
        }

        User user = mapper.toEmployeeEntity(employeeUserDto);
        userRepository.save(user);
        return "redirect:/";
    }
}
