package com.example.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnProperty(name={"use.localstack"}, matchIfMissing = false)
@Slf4j
public class AmazonSqsAsyncConfig {
    @Value("${aws.accessKey:test}")
    String awsAccessKey;

    @Value("${aws.secretKey:test}")
    String awsSecretKey;

    @Value("${aws.region:us-east-1}")
    String awsRegion;
    @Value("${LOCALSTACK_NON_PROXY_HOSTS:localhost}")
    String localStackNonProxyHosts;

    @Value("${LOCALSTACK_BASE_URL:http://localhost:4566}")
    String localstackEndpoint;


    @Value("${request.queue.name:sqs-request}")
    String requestQueueName;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localstackEndpoint, awsRegion))
                .build();
    }
}
