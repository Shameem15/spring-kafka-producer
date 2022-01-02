package com.kafkaproducer.springkafkaproducer.controller;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.kafkaproducer.springkafkaproducer.model.Books;
import com.kafkaproducer.springkafkaproducer.service.UrlHealthCheck;

@SpringBootTest
class KafkaControllerTest {

	@InjectMocks
	private KafkaController kafkaController;

	@Mock
	private KafkaTemplate<String, List<Books>> kafkaTemplate;
	
	@Mock
	ListenableFuture<SendResult<String, List<Books>>> future;
	
	@Mock
	private UrlHealthCheck urlHealthCheck;

	
//	@Test
//	public void test_postMessage() {
//
//		
//		List<Books> bookslist = getAllBooks();
//		Mockito.when(kafkaTemplate.send("kafka-spring-producer", bookslist)).thenReturn(future);
//		Mockito.when(urlHealthCheck.getHealth(false)).thenReturn(Health.up().build());
//		Mockito.doNothing().when(future).addCallback(Mockito.any());
//
//		ResponseEntity<String> result = kafkaController.postMessage(bookslist);
//		Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
//		Mockito.verify(urlHealthCheck, times(1)).getHealth(false);
//	}

	private List<Books> getAllBooks() {
		Books book1 = new Books();
		book1.setBookid(1);
		book1.setBookname("Programming with Java");
		book1.setAuthor("E. Balagurusamy");
		book1.setPrice(320);

		Books book2 = new Books();
		book2.setBookid(2);
		book2.setBookname("Data Structures and Algorithms in Java");
		book2.setAuthor("Robert Lafore");
		book2.setPrice(590);

		Books book3 = new Books();
		book3.setBookid(3);
		book3.setBookname("Effective Java");
		book3.setAuthor("Joshua Bloch");
		book3.setPrice(670);

		List<Books> bookslist = new ArrayList<Books>();
		bookslist.add(book1);
		bookslist.add(book2);
		bookslist.add(book3);

		return bookslist;
	}

}
