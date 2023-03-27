package com.example.demo.Service;


import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    @Value("${request.queue.name}")
    private String requestQueueName;

    @Value("${result.queue.name}")
    private String resultQueueName;

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final AmazonSQSAsync amazonSQSAsync;
    public SqsService(QueueMessagingTemplate queueMessagingTemplate, AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.amazonSQSAsync = amazonSQSAsync;
    }

    public String getQueueUrl(String queueName){
        return amazonSQSAsync.getQueueUrl(queueName).getQueueUrl();
    }

    public void purgeQueue(String queueName){
        String queueUrl=getQueueUrl(queueName);
        PurgeQueueRequest request=new PurgeQueueRequest(queueUrl);
        amazonSQSAsync.purgeQueue(request);
    }

    public void sendRequestMessage(String message) {
        queueMessagingTemplate.convertAndSend(requestQueueName, message);
    }

    public void sendResultMessage(String message) {
        queueMessagingTemplate.convertAndSend(resultQueueName, message);
    }
}