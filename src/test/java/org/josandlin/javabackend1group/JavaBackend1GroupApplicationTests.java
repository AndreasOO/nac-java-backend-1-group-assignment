package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaBackend1GroupApplicationTests {

    @LocalServerPort
    private Integer port;


    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.4")

            .withDatabaseName("backend_db")
            .withUsername("backend_app")
            .withPassword("test1234")
            .withInitScript("db/init.sql");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/backend_db");
        registry.add("spring.datasource.username", () -> "backend_app");
        registry.add("spring.datasource.password", () -> "test1234");
    }

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }


    @Autowired
    BookingService bookingService;

    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldRegisterCustomer() {
        Customer customer = new Customer(1L,"Test1");
        Customer registeredCustomer = customerService.registerCustomer(customer);
        assertThat(registeredCustomer.getId()).isEqualTo(1L);

    }

}
