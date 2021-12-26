package com.lsm.test.springboot.web;


import com.lsm.test.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다. , 예전에는 @ResponseBody를 각 메소드 마다 선언해 주었음.
public class HelloController {

    @GetMapping("/hello") // HTTP Get API
    public String hello(){
        return "hello";
    }// json 형식 반환

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name,amount);// json 형식 반환
    }
}

