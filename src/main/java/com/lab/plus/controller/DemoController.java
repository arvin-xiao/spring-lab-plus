package com.lab.plus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "hello, SpringNative project for Spring Boot";
    }

    @GetMapping("/show")
    public Mono<String> show() {
        return Mono.just("OK");
    }

}
