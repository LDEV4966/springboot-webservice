package com.lsm.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//SpringBootApplication으로 인해 스프링부트의 자동설정, 스프링 Bean 읽기와 생성 모두 자동으로 설정됨.
// 특히나 @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치해야 함.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args); // 내장 WAS 실행.
    }
}
