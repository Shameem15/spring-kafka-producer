package com.kafkaproducer.springkafkaproducer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafkaproducer.springkafkaproducer.model.Books;
import com.kafkaproducer.springkafkaproducer.service.UrlHealthCheck;

@RestController
@RequestMapping("v1")
public class KafkaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

	@Autowired
	private KafkaTemplate<String, List<Books>> kafkaTemplate;

	@Value("${kafka.books.topic}")
	private String topic;

	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServer;

	@Autowired
	private UrlHealthCheck urlHealthCheck;
	
	ListenableFuture<SendResult<String, List<Books>>> sendFuture;

	private ResponseEntity<String> response;

	private static final String HTTP = "http://";

	// @GetMapping("/publish/{name}")
	// public String postMessage(@PathVariable("name") final String name){
	// User user = new User();
	// user.setName(name);
	// user.setDepartment("Technology");
	// user.setSalary(4000000L);
	// kafkaTemplate.send(TOPIC, user);
	//
	// return "Message Published Successfully";
	// }

	// @PostMapping("/publish")
	// public HttpStatus postMessage(@RequestBody List<Books> listbooks) {
	// kafkaTemplate.send(topic, listbooks);
	// return HttpStatus.OK;
	// }


	@PostMapping("/publish")
	public ResponseEntity<String> postMessage(@RequestBody List<Books> listbooks) {

		urlHealthCheck.setUrl(HTTP + bootstrapServer);
		Health health = urlHealthCheck.getHealth(false);

		if (health.getStatus().getCode().equals("UP")) {

			sendFuture = kafkaTemplate.send(topic, listbooks);
		} else {
			LOGGER.error("Kafka Server is not UP. So, we can't post message");
			return new ResponseEntity<String>("Kafka Server is not UP. So, we can't post message",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		sendFuture.addCallback(new ListenableFutureCallback<SendResult<String, List<Books>>>() {

			public void onSuccess(SendResult<String, List<Books>> result) {
				LOGGER.info(
						"Message has been posted to " + result.getRecordMetadata().topic() + " message: " + listbooks);

				response = new ResponseEntity<String>("List of books Message Published Successfully",
						HttpStatus.CREATED);
			};

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("Exception in posting message to " + topic + ": " + ex);
				response = new ResponseEntity<String>("Error while processing the request : \n" + ex.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});

		return response;
	}
}
