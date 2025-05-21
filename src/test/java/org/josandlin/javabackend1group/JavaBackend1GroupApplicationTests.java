package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    RoomService roomService;

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
    //TODO change to DTO pattern
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

    @Test
        //TODO change to DTO pattern
    void shouldEditCustomer() {
        //given
        Customer customerOriginal = new Customer("TestOriginal");
        Customer registeredCustomer = customerService.registerCustomer(customerOriginal);



        //when
        Customer customerWithEdits = new Customer(registeredCustomer.getId(),"TestAfterEdit");
        Customer customerAfterEdits = customerService.editCustomer(customerWithEdits);


        //then
        assertThat(registeredCustomer.getId()).isEqualTo(customerAfterEdits.getId());
        assertThat(registeredCustomer.getName()).isNotEqualTo(customerAfterEdits.getName());

        assertThat(registeredCustomer.getName()).isEqualTo("TestOriginal");
        assertThat(customerAfterEdits.getName()).isEqualTo("TestAfterEdit");

    }

    @Test
        //TODO change to DTO pattern
    void shouldDeleteCustomer() {
        //given
        Customer customerToBeDeleted = new Customer("customerToBeDeleted");
        Customer registeredCustomerToBeDeleted = customerService.registerCustomer(customerToBeDeleted);

        Customer customerToBeKept = new Customer("customerToBeKept");
        Customer registeredCustomerToBeKept = customerService.registerCustomer(customerToBeKept);


        //when
        customerService.deleteCustomer(registeredCustomerToBeDeleted);


        //then
        assertThat(customerService.getAllCustomers().size()).isEqualTo(1);
        assertThat(customerService.getAllCustomers().get(0).getId()).isEqualTo(registeredCustomerToBeKept.getId());
        assertThat(customerService.getAllCustomers().get(0).getName()).isEqualTo("customerToBeKept");
    }

    @Test
    void shouldNotDeleteCustomer() {
        //given
        Customer customerToTryBeDeleted = new Customer("customerToTryBeDeleted");
        Customer registeredCustomerToTryBeDeleted = customerService.registerCustomer(customerToTryBeDeleted);
        BookingDTO booking = bookingService.createBooking(registeredCustomerToTryBeDeleted.getId());


        Customer customerToBeKept = new Customer("customerToBeKept");
        Customer registeredCustomerToBeKept = customerService.registerCustomer(customerToBeKept);


        //when

        //then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(registeredCustomerToTryBeDeleted));
        assertThat(exception.getMessage().equals("Customer has active bookings, cannot delete")).isTrue();
        assertThat(customerService.getAllCustomers().size()).isEqualTo(2);
    }

    @Test
    void shouldHaveRoomsWithExtraBeds() {
        //given
        RoomType singleRoom1 = new RoomType("Single room", 1000);
        RoomType doubleRoom = new RoomType("Double room", 2000);
        RoomType twinRoom = new RoomType("Twin room", 1900);
        RoomType singleRoom2 = new RoomType("Single room", 3200);

        roomTypeDao.save(singleRoom1);
        roomTypeDao.save(doubleRoom);
        roomTypeDao.save(twinRoom);
        roomTypeDao.save(singleRoom2);


        Room roomOne = new Room("Sea view room", 3, 1, doubleRoom); // millys bokning + andreas bokning (lagt till en extra bed)
        Room roomTwo = new Room("Dumpster room", 2, 0, twinRoom); // olas bokning
        Room roomThree = new Room("Honeymoon suite", 4, 2, doubleRoom); // linns bokning
        Room roomFour = new Room("Nice room", 1, 0, singleRoom1); // josefins bokning
        Room roomFive = new Room("Ok room", 1, 0, singleRoom2); //olas bokning
        Room roomSix = new Room("Unbooked room", 1, 1, singleRoom1);

        roomDao.save(roomOne);
        roomDao.save(roomTwo);
        roomDao.save(roomThree);
        roomDao.save(roomFour);
        roomDao.save(roomFive);
        roomDao.save(roomSix);

        //when
        List<RoomDTO> allRooms = roomService.getAllRooms();

        //then
        assertThat(allRooms).hasSize(6);
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Sea view room")).findFirst().get().getExtraBedsAvailable())
                                                                                                                      .isEqualTo(1);
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Dumpster room")).findFirst().get().getExtraBedsAvailable())
                                                                                                                      .isEqualTo(0);
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Honeymoon suite")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(2);

        assertThat(allRooms.stream().filter(room -> room.getName().equals("Nice room")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(0);

        //
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Ok room")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(0);
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Unbooked room")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(1);

    }

}
