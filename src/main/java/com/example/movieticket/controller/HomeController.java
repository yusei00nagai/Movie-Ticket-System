package com.example.movieticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * ルートURL ("/") にアクセスされた際にTOPページを表示する
     */
    @GetMapping("/")
    public String showTopPage() {
        return "index"; // index.htmlを呼び出す
    }
}