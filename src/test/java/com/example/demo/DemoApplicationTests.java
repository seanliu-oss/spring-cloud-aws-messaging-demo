package com.example.demo;

import com.example.demo.Service.SqsService;
import com.example.demo.listener.MessageListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;

@SpringBootTest
class DemoApplicationTests {

	@Value("${request.queue.name}")
	private String requestQueueName;

	@Value("${result.queue.name}")
	private String resultQueueName;

	@Autowired
	private SqsService sqsService;

	@SpyBean
	private MessageListener messageListener;

	@BeforeEach
	void purgeQueues(){
		sqsService.purgeQueue(requestQueueName);
		sqsService.purgeQueue(resultQueueName);
	}
	@Test
	void when_send_to_request_queue_there_should_be_receives() throws InterruptedException {
		//given
		String testMessage="testMessage";
		ArgumentCaptor<String> requestCaptor= ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> resultCaptor= ArgumentCaptor.forClass(String.class);

		//when
		sqsService.sendRequestMessage(testMessage);

		//then
		TimeUnit.SECONDS.sleep(1);
		Mockito.verify(messageListener,times(1)).requestQueueReceive(requestCaptor.capture());
		Mockito.verify(messageListener,times(1)).resultQueueReceive(resultCaptor.capture());
		Assertions.assertEquals(testMessage,requestCaptor.getValue());
		Assertions.assertEquals(testMessage.toUpperCase(),resultCaptor.getValue());
	}

}
