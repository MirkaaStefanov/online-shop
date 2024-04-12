package com.example.onlineshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIndex() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Home")))
                .andExpect(content().string(containsString("Search")))
                .andExpect(content().string(containsString("Filter")))
                .andExpect(content().string(containsString("Log in")))
                .andExpect(content().string(containsString("Sign in")))
                .andExpect(content().string(containsString("Name:")))
                .andExpect(content().string(containsString("Category:")));
    }

    @Test
    void testWhenFilter() throws Exception {
        this.mockMvc.perform(get("/product/filter"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Name:")))
                .andExpect(content().string(containsString("Category:")))
                .andExpect(content().string(containsString("Price Range:")));

    }

    @Test
    void testWhenSignUp() throws Exception {
        this.mockMvc.perform(get("/user/registration/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Registration")))
                .andExpect(content().string(containsString("Username:")))
                .andExpect(content().string(containsString("Email:")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("Repeat password:")))
                .andExpect(content().string(containsString("Register")));


    }

    @Test
    void testWhenSignUpEmployee() throws Exception {
        this.mockMvc.perform(get("/user/employee/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Registration")))
                .andExpect(content().string(containsString("First name:")))
                .andExpect(content().string(containsString("Last name:")))
                .andExpect(content().string(containsString("Age:")))
                .andExpect(content().string(containsString("Salary:")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("Repeat password:")))
                .andExpect(content().string(containsString("Register")));


    }
    @Test
    void testWhenLogin() throws Exception {
        this.mockMvc.perform(get("/user/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login Form")))
                .andExpect(content().string(containsString("Username")))
                .andExpect(content().string(containsString("Email")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("Login")));
    }

}
