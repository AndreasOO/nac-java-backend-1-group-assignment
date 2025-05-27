package org.josandlin.javabackend1group;

import io.restassured.http.ContentType;
import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.dto.BookedObjectDTO;
import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.restassured.RestAssured;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    @LocalServerPort
    private Integer port;


    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.4")

            .withDatabaseName("backend_db")
            .withUsername("backend_app")
            .withPassword("test1234")
            .withInitScript("db/init.sql");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> mysql.getJdbcUrl());
        registry.add("spring.datasource.username", () -> mysql.getUsername());
        registry.add("spring.datasource.password", () -> mysql.getPassword());
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
    void endpointGETRootShouldGenerateCorrectTemplate() {
        setupDataAndReturnBookingId();
        Response response = given()
                .when()
                .get(baseURI+"/")
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Welcome");
    }

    @Test
    void endpointGETCustomersAllShouldGenerateCorrectTemplate() {
        setupDataAndReturnBookingId();
        Response response = given()
                .when()
                .get(baseURI+"/customers/all")
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Customers");
    }

    @Test
    void endpointGETRegisterCustomerShouldGenerateCorrectTemplate() {
        setupDataAndReturnBookingId();
        Response response = given()
                .when()
                .get(baseURI+"/customers/register")
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .extract()
                .response();


        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Registration");
    }

    @Test
    void endpointPOSTRegisterCustomerShouldRedirectToCorrectUrl() {
        setupDataAndReturnCustomerId();

        Response response = given()
                .when()
                .post(baseURI+"/customers/add?name=test")
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/customers/all")).isTrue();


    }

    @Test
    void endpointPOSTEditCustomerShouldRedirectToCorrectUrl() {
        Long customerId = setupDataAndReturnCustomerId();

        Response response = given()
                .when()
                .post(baseURI+"/customers/edit?id="+customerId+"&name=test2")
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/customers/all")).isTrue();


    }

    @Test
    void endpointDELETERemoveCustomerShouldRedirectToCorrectUrl() {
        Long customerId = setupDataAndReturnCustomerId();

        Response response = given()
                .when()
                .delete(baseURI+"/customers/delete?id="+customerId)
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/customers/all")).isTrue();


    }

    @Test
    void endpointGETBookingsAllShouldGenerateCorrectTemplate() {
        setupDataAndReturnBookingId();
        Response response = given()
                .when()
                    .get(baseURI+"/bookings/all")
                .then()
                        .contentType(ContentType.HTML)
                        .statusCode(200)
                .extract()
                .response();

        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Bookings");
    }

    @Test
    void endpointGETShowBookingShouldGenerateCorrectTemplate() {
        Long id = setupDataAndReturnBookingId();

        Response response = given()
                .when()
                .get(baseURI+"/bookings/booking/"+id)
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Booking");
    }

    @Test
    void endpointGETShowBookedRoomShouldGenerateCorrectTemplate() {
        Map<String,Long> idMap = setupDataAndReturnBookingIdAndBookedObjectIdAndExtraTypeId();

        Response response = given()
                .when()
                .get(baseURI+"/bookings/booking/"+idMap.get("BookingId")+"/booked-room/"+idMap.get("BookingObjectId"))
                .then()
                .contentType(ContentType.HTML)
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.getBody().htmlPath().getString("html.head.title")).isEqualTo("Booked Room");
    }

    @Test
    void endpointDELETERoomShouldRedirectToCorrectUrl() {
        Map<String,Long> idMap = setupDataAndReturnBookingIdAndBookedObjectIdAndExtraTypeId();

        Response response = given()
                .when()
                .delete(baseURI+"/bookings/booking/"+idMap.get("BookingId")+"/booked-room/"+idMap.get("BookingObjectId")+"/delete-room")
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/bookings/booking")).isTrue();
    }

    @Test
    void endpointPOSTBookingShouldRedirectToCorrectUrl() {
        Long customerId = setupDataAndReturnCustomerId();

        Response response = given()
                .when()
                .post(baseURI+"/bookings/booking?customerId="+customerId)
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/bookings/booking")).isTrue();
    }

    @Test
    void endpointPOSTAddExtraToBookedRoomShouldRedirectToCorrectUrl() {
        Map<String,Long> idMap = setupDataAndReturnBookingIdAndBookedObjectIdAndExtraTypeId();

        Response response = given()
                .when()
                .post(baseURI+"/bookings/booking/"+idMap.get("BookingId")+"/booked-room/"+idMap.get("BookingObjectId")+"/add-extra?extraTypeId="+idMap.get("ExtraTypeId"))
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/bookings/booking")).isTrue();

    }

    @Test
    void endpointDELETEExtraFromBookedRoomShouldRedirectToCorrectUrl() {
        Map<String,Long> idMap = setupDataAndReturnBookingIdAndBookedObjectIdAndExtraTypeId();
        Long extraId = bookingService.getBookedObjectById(idMap.get("BookingObjectId")).getExtras().get(0).getId();
        Response response = given()
                .when()
                .delete(baseURI+"/bookings/booking/"+idMap.get("BookingId")+"/booked-room/"+idMap.get("BookingObjectId")+"/delete-extra/"+extraId)
                .then()
                .statusCode(302)
                .extract()
                .response();

        assertThat(response.getHeader("Location").startsWith(baseURI+"/bookings/booking")).isTrue();

    }





    @Test
    //TODO change to DTO pattern
    void serviceShouldRegisterCustomer() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Test1");
        CustomerDTO customer2 = new CustomerDTO("Test2");
        CustomerDTO customer3 = new CustomerDTO("Test3");


        //when
        CustomerDTO registeredCustomer1 = customerService.registerCustomer(customer1);
        CustomerDTO registeredCustomer2 = customerService.registerCustomer(customer2);
        CustomerDTO registeredCustomer3 = customerService.registerCustomer(customer3);

        //then

        assertThat(registeredCustomer1.getId()).isNotNull();
        assertThat(registeredCustomer1.getName()).isEqualTo(customer1.getName());

        assertThat(registeredCustomer2.getId()).isNotNull();
        assertThat(registeredCustomer2.getName()).isEqualTo(customer2.getName());

        assertThat(registeredCustomer3.getId()).isNotNull();
        assertThat(registeredCustomer3.getName()).isEqualTo(customer3.getName());

        assertThat(customerService.getAllCustomers().size()).isEqualTo(3);
    }

    @Test
    void serviceShouldEditCustomer() {
        //given
        CustomerDTO customerOriginal = new CustomerDTO();
                    customerOriginal.setName("TestOriginal");
        CustomerDTO registeredCustomer = customerService.registerCustomer(customerOriginal);



        //when
        CustomerDTO customerWithEdits = new CustomerDTO(registeredCustomer.getId(),"TestAfterEdit");
        CustomerDTO customerAfterEdits = customerService.editCustomer(customerWithEdits);


        //then
        assertThat(registeredCustomer.getId()).isEqualTo(customerAfterEdits.getId());
        assertThat(registeredCustomer.getName()).isNotEqualTo(customerAfterEdits.getName());

        assertThat(registeredCustomer.getName()).isEqualTo("TestOriginal");
        assertThat(customerAfterEdits.getName()).isEqualTo("TestAfterEdit");

    }

    @Test
    void serviceShouldDeleteCustomer() {
        //given
        CustomerDTO customerToBeDeleted = new CustomerDTO();
                    customerToBeDeleted.setName("customerToBeDeleted");
        CustomerDTO registeredCustomerToBeDeleted = customerService.registerCustomer(customerToBeDeleted);

        CustomerDTO customerToBeKept = new CustomerDTO();
                    customerToBeKept.setName("customerToBeKept");
        CustomerDTO registeredCustomerToBeKept = customerService.registerCustomer(customerToBeKept);


        //when
        customerService.deleteCustomer(registeredCustomerToBeDeleted);


        //then
        assertThat(customerService.getAllCustomers().size()).isEqualTo(1);
        assertThat(customerService.getAllCustomers().get(0).getId()).isEqualTo(registeredCustomerToBeKept.getId());
        assertThat(customerService.getAllCustomers().get(0).getName()).isEqualTo("customerToBeKept");
    }

    @Test
    void serviceShouldNotDeleteCustomer() {
        //given
        CustomerDTO customerToTryBeDeleted = new CustomerDTO();
                    customerToTryBeDeleted.setName("customerToTryBeDeleted");
        CustomerDTO registeredCustomerToTryBeDeleted = customerService.registerCustomer(customerToTryBeDeleted);
        BookingDTO booking = bookingService.createBooking(registeredCustomerToTryBeDeleted.getId());


        CustomerDTO customerToBeKept = new CustomerDTO();
                    customerToBeKept.setName("customerToBeKept");
        CustomerDTO registeredCustomerToBeKept = customerService.registerCustomer(customerToBeKept);


        //when

        //then
        assertThat(customerService.getAllCustomers().size()).isEqualTo(2);
    }

    @Test
    void serviceShouldShowRoomsWithExtraBeds() {
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

        assertThat(allRooms.stream().filter(room -> room.getName().equals("Ok room")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(0);
        assertThat(allRooms.stream().filter(room -> room.getName().equals("Unbooked room")).findFirst().get().getExtraBedsAvailable())
                .isEqualTo(1);

    }

    @Test
    void serviceShouldHandleBookings() {

        //given
        CustomerDTO ola = customerService.registerCustomer(new CustomerDTO("Ola"));
        CustomerDTO milly = customerService.registerCustomer(new CustomerDTO("Milly"));
        CustomerDTO andreas = customerService.registerCustomer(new CustomerDTO("Andreas"));
        CustomerDTO linn = customerService.registerCustomer(new CustomerDTO("Linn"));
        CustomerDTO josefin = customerService.registerCustomer(new CustomerDTO("Josefin"));
        CustomerDTO Sixten = customerService.registerCustomer(new CustomerDTO("Sixten"));

        BookingDTO olasBooking = bookingService.createBooking(ola.getId());
        BookingDTO millysBooking =bookingService.createBooking(milly.getId());
        BookingDTO andreasBooking =bookingService.createBooking(andreas.getId());
        BookingDTO linnsBooking =bookingService.createBooking(linn.getId());
        BookingDTO josefinsBooking =bookingService.createBooking(josefin.getId());
        BookingDTO sixtensBooking =bookingService.createBooking(Sixten.getId());

        ExtraType extraBed = extraTypeDao.save(new ExtraType("bed", 200));

        Room savedRoom1 = roomDao.save(new Room("Sea view room", 3, 1, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom2 = roomDao.save(new Room("Dumpster room", 2, 0, roomTypeDao.save(new RoomType("Twin room", 1900))));
        Room savedRoom3 = roomDao.save(new Room("Honeymoon suite", 4, 2, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom4 = roomDao.save(new Room("Nice room", 1, 0, roomTypeDao.save(new RoomType("Single room", 1000))));
        Room savedRoom5 = roomDao.save(new Room("Ok room", 1, 0, roomTypeDao.save(new RoomType("Single room", 3200))));
        Room savedRoom6 = roomDao.save(new Room("Unbooked room", 1, 1, roomTypeDao.save(new RoomType("Single room", 1000))));

        RoomDTO savedRoomDTO1 = roomService.getRoomById(savedRoom1.getId());
        RoomDTO savedRoomDTO2 = roomService.getRoomById(savedRoom2.getId());
        RoomDTO savedRoomDTO3 = roomService.getRoomById(savedRoom3.getId());
        RoomDTO savedRoomDTO4 = roomService.getRoomById(savedRoom4.getId());
        RoomDTO savedRoomDTO5 = roomService.getRoomById(savedRoom5.getId());
        RoomDTO savedRoomDTO6 = roomService.getRoomById(savedRoom6.getId());


        //when
        BookedObjectDTO roomTwoBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO2, olasBooking.getId(), LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
        BookedObjectDTO roomFiveBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO5, olasBooking.getId(), LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 17));
        BookedObjectDTO roomOneBookedObj1OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, millysBooking.getId(), LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
        BookedObjectDTO roomOneBookedObj2OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, andreasBooking.getId(), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
        BookedObjectDTO roomThreeBookedObjTwoExtraBeds = bookingService.saveBookedObject(savedRoomDTO3, linnsBooking.getId(), LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
        BookedObjectDTO roomFourBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO4, josefinsBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

        bookingService.addExtraToBookedObject(roomOneBookedObj1OneExtraBed.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());

        BookedObjectDTO bookedObjBeforeEdited = bookingService.saveBookedObject(savedRoomDTO6, sixtensBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));
        BookedObjectDTO bookedObjAfterEdited = bookingService.editBookedObject(bookedObjBeforeEdited.getId(), savedRoomDTO1.getId(), LocalDate.of(2025, 9, 25), LocalDate.of(2025, 9, 28));




        //then
        // test bookings
        assertThat(bookingService.getAllBookings().size()).isEqualTo(6);

        assertThat(bookingService.getBookedRoomsByBookingId(olasBooking.getId()).size()).isEqualTo(2);

        assertThat(bookingService.getBookedRoomsByBookingId(millysBooking.getId()).size()).isEqualTo(1);
        assertThat(bookingService.getBookedRoomsByBookingId(millysBooking.getId()).get(0).getRoom().getName()).isEqualTo("Sea view room");
        assertThat(bookingService.getBookedRoomsByBookingId(millysBooking.getId()).get(0).getStartDate()).isEqualTo(LocalDate.of(2025, 4, 15));
        assertThat(bookingService.getBookedRoomsByBookingId(millysBooking.getId()).get(0).getEndDate()).isEqualTo(LocalDate.of(2025, 4, 22));

        assertThat(bookingService.getBookedRoomsByBookingId(andreasBooking.getId()).size()).isEqualTo(1);
        assertThat(bookingService.getBookedRoomsByBookingId(andreasBooking.getId()).get(0).getRoom().getName()).isEqualTo("Sea view room");
        assertThat(bookingService.getBookedRoomsByBookingId(andreasBooking.getId()).get(0).getStartDate()).isEqualTo(LocalDate.of(2025, 5, 12));
        assertThat(bookingService.getBookedRoomsByBookingId(andreasBooking.getId()).get(0).getEndDate()).isEqualTo(LocalDate.of(2025, 5, 18));


        assertThat(bookingService.getBookedRoomsByBookingId(linnsBooking.getId()).size()).isEqualTo(1);
        assertThat(bookingService.getBookedRoomsByBookingId(linnsBooking.getId()).get(0).getRoom().getName()).isEqualTo("Honeymoon suite");
        assertThat(bookingService.getBookedRoomsByBookingId(linnsBooking.getId()).get(0).getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(bookingService.getBookedRoomsByBookingId(linnsBooking.getId()).get(0).getEndDate()).isEqualTo(LocalDate.of(2025, 6, 8));


        assertThat(bookingService.getBookedRoomsByBookingId(josefinsBooking.getId()).size()).isEqualTo(1);
        assertThat(bookingService.getBookedRoomsByBookingId(josefinsBooking.getId()).get(0).getRoom().getName()).isEqualTo("Nice room");
        assertThat(bookingService.getBookedRoomsByBookingId(josefinsBooking.getId()).get(0).getStartDate()).isEqualTo(LocalDate.of(2025, 5, 29));
        assertThat(bookingService.getBookedRoomsByBookingId(josefinsBooking.getId()).get(0).getEndDate()).isEqualTo(LocalDate.of(2025, 6, 2));


        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> bookingService.saveBookedObject(savedRoomDTO1, sixtensBooking.getId(), LocalDate.of(2025, 4, 16), LocalDate.of(2025, 4, 21)));
        assertThat(exception.getMessage().equals("Room is not available during input dates")).isTrue();

        // test edits
        assertThat(bookedObjBeforeEdited.getRoom().getName()).isEqualTo("Unbooked room");
        assertThat(bookedObjBeforeEdited.getStartDate()).isEqualTo(LocalDate.of(2025, 5, 29));
        assertThat(bookedObjBeforeEdited.getEndDate()).isEqualTo(LocalDate.of(2025, 6, 2));

        assertThat(bookedObjAfterEdited.getRoom().getName()).isEqualTo("Sea view room");
        assertThat(bookedObjAfterEdited.getStartDate()).isEqualTo(LocalDate.of(2025, 9, 25));
        assertThat(bookedObjAfterEdited.getEndDate()).isEqualTo(LocalDate.of(2025, 9, 28));


        // test extras
        assertThat(bookingService.getBookedObjectById(roomOneBookedObj1OneExtraBed.getId()).getExtras().size()).isEqualTo(1);
        assertThat(bookingService.getBookedObjectById(roomOneBookedObj1OneExtraBed.getId()).getExtras().get(0).getExtraType().getName()).isEqualTo("bed");

        assertThat(bookingService.getBookedObjectById(roomOneBookedObj2OneExtraBed.getId()).getExtras().size()).isEqualTo(0);

        assertThat(bookingService.getBookedObjectById(roomThreeBookedObjTwoExtraBeds.getId()).getExtras().size()).isEqualTo(2);
        assertThat(bookingService.getBookedObjectById(roomThreeBookedObjTwoExtraBeds.getId()).getExtras().get(0).getExtraType().getName()).isEqualTo("bed");
        assertThat(bookingService.getBookedObjectById(roomThreeBookedObjTwoExtraBeds.getId()).getExtras().get(1).getExtraType().getName()).isEqualTo("bed");

        Exception exceptionDoubleBookingBeds1 = assertThrows(IllegalArgumentException.class,
                () -> bookingService.addExtraToBookedObject(roomOneBookedObj1OneExtraBed.getId(),extraBed.getId()));
        assertThat(exceptionDoubleBookingBeds1.getMessage().equals("Bed can't be added to this room")).isTrue();

        Exception exceptionDoubleBookingBeds2 = assertThrows(IllegalArgumentException.class,
                () -> bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId()));
        assertThat(exceptionDoubleBookingBeds2.getMessage().equals("Bed can't be added to this room")).isTrue();

        Exception exceptionDoubleBookingBeds3 = assertThrows(IllegalArgumentException.class,
                () -> bookingService.addExtraToBookedObject(roomTwoBookedObjNoExtraBeds.getId(),extraBed.getId()));
        assertThat(exceptionDoubleBookingBeds3.getMessage().equals("Bed can't be added to this room")).isTrue();

        Exception exceptionDoubleBookingBeds4 = assertThrows(IllegalArgumentException.class,
                () -> bookingService.addExtraToBookedObject(roomFourBookedObjNoExtraBeds.getId(),extraBed.getId()));
        assertThat(exceptionDoubleBookingBeds4.getMessage().equals("Bed can't be added to this room")).isTrue();

    }

    @Test
    void serviceShouldShowAvailableRooms() {
        //given
        CustomerDTO ola = customerService.registerCustomer(new CustomerDTO("Ola"));
        CustomerDTO milly = customerService.registerCustomer(new CustomerDTO("Milly"));


        BookingDTO olasBooking = bookingService.createBooking(ola.getId());
        BookingDTO millysBooking =bookingService.createBooking(milly.getId());


        RoomType singleRoom1 = new RoomType("Single room", 1000);
        RoomType doubleRoom = new RoomType("Double room", 2000);

        roomTypeDao.save(singleRoom1);
        roomTypeDao.save(doubleRoom);


        Room roomOne = new Room("Sea view room", 3, 1, doubleRoom);
        Room roomThree = new Room("Honeymoon suite", 4, 2, doubleRoom);
        Room roomFour = new Room("Nice room", 1, 0, singleRoom1);
        Room roomSix = new Room("Unbooked room", 1, 1, singleRoom1);

        Room savedRoom1 = roomDao.save(roomOne);
        Room savedRoom3 = roomDao.save(roomThree);
        Room savedRoom4 = roomDao.save(roomFour);
        Room savedRoom6 = roomDao.save(roomSix);

        RoomDTO savedRoomDTO1 = roomService.getRoomById(savedRoom1.getId());
        RoomDTO savedRoomDTO3 = roomService.getRoomById(savedRoom3.getId());
        RoomDTO savedRoomDTO4 = roomService.getRoomById(savedRoom4.getId());
        RoomDTO savedRoomDTO6 = roomService.getRoomById(savedRoom6.getId());


        //when
        //happy flow
        bookingService.saveBookedObject(savedRoomDTO1, olasBooking.getId(), LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5));
        bookingService.saveBookedObject(savedRoomDTO1, olasBooking.getId(), LocalDate.of(2025, 5, 8), LocalDate.of(2025, 5, 10));

        //check bordering dates
        bookingService.saveBookedObject(savedRoomDTO1, millysBooking.getId(), LocalDate.of(2025, 5, 11), LocalDate.of(2025, 5, 12));
        bookingService.saveBookedObject(savedRoomDTO1, millysBooking.getId(), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));

        //check that other rooms are shown in month 5 dates despite being booked in month 6
        bookingService.saveBookedObject(savedRoomDTO3, millysBooking.getId(), LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
        bookingService.saveBookedObject(savedRoomDTO4, millysBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

        //find all rooms in clear period
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30), 1).size()).isEqualTo(4);

        //find all rooms in clear period with bordering start date
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 4, 29), LocalDate.of(2025, 5, 1), 1).size()).isEqualTo(4);

        //find all rooms in clear period with bordering end date
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 7), 1).size()).isEqualTo(4);

        //find all rooms in clear period with bordering start date and end date
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 8), 1).size()).isEqualTo(4);

//        //find all rooms but room 1 in period
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 4), 1).size()).isEqualTo(3);
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 4), 1).stream().filter(room -> room.getName().equals("Sea view room")).toList().size()).isEqualTo(0);

//        //find all rooms but room 1 in period with both overlapping bordering dates
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10), 1).size()).isEqualTo(3);
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10), 1).stream().filter(room -> room.getName().equals("Sea view room")).toList().size()).isEqualTo(0);
//
//        //find all rooms but room 1 in period with start overlapping bordering dates
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 11), 1).size()).isEqualTo(3);
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 11), 1).stream().filter(room -> room.getName().equals("Sea view room")).toList().size()).isEqualTo(0);
//
//        //find all rooms but room 1 in period with end overlapping bordering dates
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 4, 29), LocalDate.of(2025, 5, 2), 1).size()).isEqualTo(3);
        assertThat(roomService.getAvailableRoomsBetweenDatesWithinCapacity(LocalDate.of(2025, 4, 29), LocalDate.of(2025, 5, 2), 1).stream().filter(room -> room.getName().equals("Sea view room")).toList().size()).isEqualTo(0);

    }

    // UTIL DATA LOADERS
    private Map<String,Long> setupDataAndReturnBookingIdAndBookedObjectIdAndExtraTypeId() {
        //given
        CustomerDTO ola = customerService.registerCustomer(new CustomerDTO("Ola"));
        CustomerDTO milly = customerService.registerCustomer(new CustomerDTO("Milly"));
        CustomerDTO andreas = customerService.registerCustomer(new CustomerDTO("Andreas"));
        CustomerDTO linn = customerService.registerCustomer(new CustomerDTO("Linn"));
        CustomerDTO josefin = customerService.registerCustomer(new CustomerDTO("Josefin"));
        CustomerDTO Sixten = customerService.registerCustomer(new CustomerDTO("Sixten"));

        BookingDTO olasBooking = bookingService.createBooking(ola.getId());
        BookingDTO millysBooking = bookingService.createBooking(milly.getId());
        BookingDTO andreasBooking = bookingService.createBooking(andreas.getId());
        BookingDTO linnsBooking = bookingService.createBooking(linn.getId());
        BookingDTO josefinsBooking = bookingService.createBooking(josefin.getId());
        BookingDTO sixtensBooking = bookingService.createBooking(Sixten.getId());

        ExtraType extraBed = extraTypeDao.save(new ExtraType("bed", 200));

        Room savedRoom1 = roomDao.save(new Room("Sea view room", 3, 1, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom2 = roomDao.save(new Room("Dumpster room", 2, 0, roomTypeDao.save(new RoomType("Twin room", 1900))));
        Room savedRoom3 = roomDao.save(new Room("Honeymoon suite", 4, 2, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom4 = roomDao.save(new Room("Nice room", 1, 0, roomTypeDao.save(new RoomType("Single room", 1000))));
        Room savedRoom5 = roomDao.save(new Room("Ok room", 1, 0, roomTypeDao.save(new RoomType("Single room", 3200))));
        Room savedRoom6 = roomDao.save(new Room("Unbooked room", 1, 1, roomTypeDao.save(new RoomType("Single room", 1000))));

        RoomDTO savedRoomDTO1 = roomService.getRoomById(savedRoom1.getId());
        RoomDTO savedRoomDTO2 = roomService.getRoomById(savedRoom2.getId());
        RoomDTO savedRoomDTO3 = roomService.getRoomById(savedRoom3.getId());
        RoomDTO savedRoomDTO4 = roomService.getRoomById(savedRoom4.getId());
        RoomDTO savedRoomDTO5 = roomService.getRoomById(savedRoom5.getId());
        RoomDTO savedRoomDTO6 = roomService.getRoomById(savedRoom6.getId());


        //when
        BookedObjectDTO roomTwoBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO2, olasBooking.getId(), LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
        BookedObjectDTO roomFiveBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO5, olasBooking.getId(), LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 17));
        BookedObjectDTO roomThreeBookedObj2TwoExtraBeds = bookingService.saveBookedObject(savedRoomDTO3, millysBooking.getId(), LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
        BookedObjectDTO roomOneBookedObj2OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, andreasBooking.getId(), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
        BookedObjectDTO roomThreeBookedObjTwoExtraBeds = bookingService.saveBookedObject(savedRoomDTO3, linnsBooking.getId(), LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
        BookedObjectDTO roomFourBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO4, josefinsBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

        bookingService.addExtraToBookedObject(roomThreeBookedObj2TwoExtraBeds.getId(), extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(), extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(), extraBed.getId());

        BookedObjectDTO bookedObjBeforeEdited = bookingService.saveBookedObject(savedRoomDTO6, sixtensBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));
        BookedObjectDTO bookedObjAfterEdited = bookingService.editBookedObject(bookedObjBeforeEdited.getId(), savedRoomDTO1.getId(), LocalDate.of(2025, 9, 25), LocalDate.of(2025, 9, 28));

        return Map.of("BookingId", millysBooking.getId(), "BookingObjectId", roomThreeBookedObj2TwoExtraBeds.getId(),"ExtraTypeId", extraBed.getId());
    }

    private Long setupDataAndReturnBookingId() {
        //given
        CustomerDTO ola = customerService.registerCustomer(new CustomerDTO("Ola"));
        CustomerDTO milly = customerService.registerCustomer(new CustomerDTO("Milly"));
        CustomerDTO andreas = customerService.registerCustomer(new CustomerDTO("Andreas"));
        CustomerDTO linn = customerService.registerCustomer(new CustomerDTO("Linn"));
        CustomerDTO josefin = customerService.registerCustomer(new CustomerDTO("Josefin"));
        CustomerDTO Sixten = customerService.registerCustomer(new CustomerDTO("Sixten"));

        BookingDTO olasBooking = bookingService.createBooking(ola.getId());
        BookingDTO millysBooking =bookingService.createBooking(milly.getId());
        BookingDTO andreasBooking =bookingService.createBooking(andreas.getId());
        BookingDTO linnsBooking =bookingService.createBooking(linn.getId());
        BookingDTO josefinsBooking =bookingService.createBooking(josefin.getId());
        BookingDTO sixtensBooking =bookingService.createBooking(Sixten.getId());

        ExtraType extraBed = extraTypeDao.save(new ExtraType("bed", 200));

        Room savedRoom1 = roomDao.save(new Room("Sea view room", 3, 1, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom2 = roomDao.save(new Room("Dumpster room", 2, 0, roomTypeDao.save(new RoomType("Twin room", 1900))));
        Room savedRoom3 = roomDao.save(new Room("Honeymoon suite", 4, 2, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom4 = roomDao.save(new Room("Nice room", 1, 0, roomTypeDao.save(new RoomType("Single room", 1000))));
        Room savedRoom5 = roomDao.save(new Room("Ok room", 1, 0, roomTypeDao.save(new RoomType("Single room", 3200))));
        Room savedRoom6 = roomDao.save(new Room("Unbooked room", 1, 1, roomTypeDao.save(new RoomType("Single room", 1000))));

        RoomDTO savedRoomDTO1 = roomService.getRoomById(savedRoom1.getId());
        RoomDTO savedRoomDTO2 = roomService.getRoomById(savedRoom2.getId());
        RoomDTO savedRoomDTO3 = roomService.getRoomById(savedRoom3.getId());
        RoomDTO savedRoomDTO4 = roomService.getRoomById(savedRoom4.getId());
        RoomDTO savedRoomDTO5 = roomService.getRoomById(savedRoom5.getId());
        RoomDTO savedRoomDTO6 = roomService.getRoomById(savedRoom6.getId());


        //when
        BookedObjectDTO roomTwoBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO2, olasBooking.getId(), LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
        BookedObjectDTO roomFiveBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO5, olasBooking.getId(), LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 17));
        BookedObjectDTO roomOneBookedObj1OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, millysBooking.getId(), LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
        BookedObjectDTO roomOneBookedObj2OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, andreasBooking.getId(), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
        BookedObjectDTO roomThreeBookedObjTwoExtraBeds = bookingService.saveBookedObject(savedRoomDTO3, linnsBooking.getId(), LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
        BookedObjectDTO roomFourBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO4, josefinsBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

        bookingService.addExtraToBookedObject(roomOneBookedObj1OneExtraBed.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());

        BookedObjectDTO bookedObjBeforeEdited = bookingService.saveBookedObject(savedRoomDTO6, sixtensBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));
        BookedObjectDTO bookedObjAfterEdited = bookingService.editBookedObject(bookedObjBeforeEdited.getId(), savedRoomDTO1.getId(), LocalDate.of(2025, 9, 25), LocalDate.of(2025, 9, 28));

        return olasBooking.getId();
    }

    private Long setupDataAndReturnCustomerId() {
        //given
        CustomerDTO ola = customerService.registerCustomer(new CustomerDTO("Ola"));
        CustomerDTO milly = customerService.registerCustomer(new CustomerDTO("Milly"));
        CustomerDTO andreas = customerService.registerCustomer(new CustomerDTO("Andreas"));
        CustomerDTO linn = customerService.registerCustomer(new CustomerDTO("Linn"));
        CustomerDTO josefin = customerService.registerCustomer(new CustomerDTO("Josefin"));
        CustomerDTO Sixten = customerService.registerCustomer(new CustomerDTO("Sixten"));

        BookingDTO olasBooking = bookingService.createBooking(ola.getId());
        BookingDTO millysBooking =bookingService.createBooking(milly.getId());
        BookingDTO andreasBooking =bookingService.createBooking(andreas.getId());
        BookingDTO linnsBooking =bookingService.createBooking(linn.getId());
        BookingDTO josefinsBooking =bookingService.createBooking(josefin.getId());
        BookingDTO sixtensBooking =bookingService.createBooking(Sixten.getId());

        ExtraType extraBed = extraTypeDao.save(new ExtraType("bed", 200));

        Room savedRoom1 = roomDao.save(new Room("Sea view room", 3, 1, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom2 = roomDao.save(new Room("Dumpster room", 2, 0, roomTypeDao.save(new RoomType("Twin room", 1900))));
        Room savedRoom3 = roomDao.save(new Room("Honeymoon suite", 4, 2, roomTypeDao.save(new RoomType("Double room", 2000))));
        Room savedRoom4 = roomDao.save(new Room("Nice room", 1, 0, roomTypeDao.save(new RoomType("Single room", 1000))));
        Room savedRoom5 = roomDao.save(new Room("Ok room", 1, 0, roomTypeDao.save(new RoomType("Single room", 3200))));
        Room savedRoom6 = roomDao.save(new Room("Unbooked room", 1, 1, roomTypeDao.save(new RoomType("Single room", 1000))));

        RoomDTO savedRoomDTO1 = roomService.getRoomById(savedRoom1.getId());
        RoomDTO savedRoomDTO2 = roomService.getRoomById(savedRoom2.getId());
        RoomDTO savedRoomDTO3 = roomService.getRoomById(savedRoom3.getId());
        RoomDTO savedRoomDTO4 = roomService.getRoomById(savedRoom4.getId());
        RoomDTO savedRoomDTO5 = roomService.getRoomById(savedRoom5.getId());
        RoomDTO savedRoomDTO6 = roomService.getRoomById(savedRoom6.getId());


        //when
        BookedObjectDTO roomTwoBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO2, olasBooking.getId(), LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
        BookedObjectDTO roomFiveBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO5, olasBooking.getId(), LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 17));
        BookedObjectDTO roomOneBookedObj1OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, millysBooking.getId(), LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
        BookedObjectDTO roomOneBookedObj2OneExtraBed = bookingService.saveBookedObject(savedRoomDTO1, andreasBooking.getId(), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
        BookedObjectDTO roomThreeBookedObjTwoExtraBeds = bookingService.saveBookedObject(savedRoomDTO3, linnsBooking.getId(), LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
        BookedObjectDTO roomFourBookedObjNoExtraBeds = bookingService.saveBookedObject(savedRoomDTO4, josefinsBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

        bookingService.addExtraToBookedObject(roomOneBookedObj1OneExtraBed.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());
        bookingService.addExtraToBookedObject(roomThreeBookedObjTwoExtraBeds.getId(),extraBed.getId());

        BookedObjectDTO bookedObjBeforeEdited = bookingService.saveBookedObject(savedRoomDTO6, sixtensBooking.getId(), LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));
        BookedObjectDTO bookedObjAfterEdited = bookingService.editBookedObject(bookedObjBeforeEdited.getId(), savedRoomDTO1.getId(), LocalDate.of(2025, 9, 25), LocalDate.of(2025, 9, 28));

        return ola.getId();
    }

}
