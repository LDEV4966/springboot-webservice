package com.lsm.test.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index(){
        return "index"; // gradle의 mustache-starter 덕분에 경로설정 및 머스테치 확장자가 자동으로 설정됨
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
