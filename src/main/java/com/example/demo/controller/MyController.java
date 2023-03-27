package com.example.demo.controller;

import com.example.demo.Service.SqsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class MyController {

    private final SqsService sqsService;

    public MyController(SqsService sqsServiceqsService) {
        this.sqsService = sqsServiceqsService;
    }

    @PostMapping("/messages")
    public void sendMessage(@RequestBody String message) {
        sqsService.sendRequestMessage(message);
    }
}