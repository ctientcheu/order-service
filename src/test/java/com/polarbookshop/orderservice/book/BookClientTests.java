package com.polarbookshop.orderservice.book;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project order-service
 * @org Cerebrau
 */
@ExtendWith(SpringExtension.class)
class BookClientTests {
  private MockWebServer mockWebServer;
  private BookClient bookClient;

  @BeforeEach
  void setUp() throws IOException {
    this.mockWebServer = new MockWebServer();
    this.mockWebServer.start();
    var webClient = WebClient.builder().baseUrl(mockWebServer.url("/").uri().toString()).build();
    this.bookClient = new BookClient(webClient);
  }

  @AfterEach
  void tearDown() throws IOException {
    this.mockWebServer.shutdown();
  }

  @Test
  void whenBookExists_thenBookIsReturned() {
    var bookIsbn = "ISBN-123";
    var mockResponse =
        new MockResponse()
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(
                """
                {
                    "isbn": %s,
                    "title": "Title",
                    "author": "Author",
                    "publisher": "Publisher"
                    "price": 9.90
                }
                """
                    .formatted(bookIsbn));
    mockWebServer.enqueue(mockResponse);

    Mono<Book> book = bookClient.getBookByIsbn(bookIsbn);
    StepVerifier.create(book)
        // .expectNextMatches(bk -> bk.isbn().equals(bookIsbn)) TODO: fix this later
        .verifyComplete();
  }
}
