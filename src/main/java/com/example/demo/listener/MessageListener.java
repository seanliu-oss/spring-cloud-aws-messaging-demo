package com.example.demo.listener;

import com.example.demo.Service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {
    private final SqsService sqsService;

    public MessageListener(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @SqsListener(value = "${request.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void requestQueueReceive(String message) {
       log.info("Received request message: {}", message);
       sqsService.sendResultMessage(message.toUpperCase());
    }

    @SqsListener(value = "${result.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void resultQueueReceive(String message) {
        log.info("Received result message: {}", message);
    }
}