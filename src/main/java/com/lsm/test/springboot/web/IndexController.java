package com.lsm.test.springboot.web;

import com.lsm.test.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());
        return "index"; // gradle의 mustache-starter 덕분에 경로설정 및 머스테치 확장자가 자동으로 설정됨
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
