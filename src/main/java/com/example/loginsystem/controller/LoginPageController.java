package com.example.loginsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginPageController {

    @GetMapping("/form")
    public String showForm() {
        return "form"; // 返回表單頁面
    }


    @PostMapping("/process-form")
    public String processForm(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        // 處理表單提交邏輯
        return "form"; // 返回處理結果頁面模板
    }
}
