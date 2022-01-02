package com.kafkaproducer.springkafkaproducer.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BooksTest {
	
	@Test
	public void test_books() {
		Books books;
		books = new Books();
		books.setBookid(1);
		books.setBookname("Programming with Java");
		books.setAuthor("E. Balagurusamy");
		books.setPrice(320);
		Assert.assertEquals(1, books.getBookid());
		Assert.assertEquals("Programming with Java", books.getBookname());
		Assert.assertEquals("E. Balagurusamy", books.getAuthor());
		Assert.assertEquals(320, books.getPrice());
		Assert.assertEquals("Books [bookid=1, bookname=Programming with Java, author=E. Balagurusamy, price=320]", books.toString());
	}
}
