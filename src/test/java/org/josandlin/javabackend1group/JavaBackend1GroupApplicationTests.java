package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.dao.*;
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

    @Autowired
    CustomerDao customerDao;

    @Autowired
    BookingDao bookingDao;

    @Autowired
    AddedExtraDao addedExtraDao;

    @Autowired
    BookedObjectDao bookedObjectDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    RoomTypeDao roomTypeDao;

    @Autowired
    ExtraTypeDao extraTypeDao;


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        bookedObjectDao.deleteAll();
        extraTypeDao.deleteAll();
        roomDao.deleteAll();
        roomTypeDao.deleteAll();
        bookingDao.deleteAll();
        addedExtraDao.deleteAll();
        customerDao.deleteAll();

    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldRegisterCustomer() {
        //given
        Customer customer1 = new Customer("Test1");
        Customer customer2 = new Customer("Test2");
        Customer customer3 = new Customer("Test3");

        //when
        Customer registeredCustomer1 = customerService.registerCustomer(customer1);
        Customer registeredCustomer2 = customerService.registerCustomer(customer2);
        Customer registeredCustomer3 = customerService.registerCustomer(customer3);

        //then
        assertThat(registeredCustomer1.getId()).isEqualTo(customer1.getId());
        assertThat(registeredCustomer2.getId()).isEqualTo(customer2.getId());
        assertThat(registeredCustomer3.getId()).isEqualTo(customer3.getId());
        assertThat(customerService.getAllCustomers().size()).isEqualTo(3);
    }

}
