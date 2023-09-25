package com.project.codedeployproject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
class Hello {

    @RequestMapping("/")
    String index() {
        return "Hello world, Testing CodePipeline.";
    }
}