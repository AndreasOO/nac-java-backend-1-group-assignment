package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BookedObjectMapperTest {

    BookedObjectMapper bookedObjectMapper = new BookedObjectMapper(new RoomMapper(new RoomTypeMapper()),
                                                                   new AddedExtraMapper(new ExtraTypeMapper()),
                                                                   new BookingMapper(new CustomerMapper()));

    BookedObject bookedObjectEntity = new BookedObject(1L,
                                                        new Room(2L,
                                                                "roomFromEntity",
                                                                3,
                                                                1,
                                                                new RoomType(3L,"single", 200)),
                                                        List.of(),
                                                        new Booking(4L, new Customer(5L, "customerFromEntity")),
                                                        LocalDate.of(2025,5,12),
                                                        LocalDate.of(2025,5,13)
                                                        );


    BookedObjectDTO bookedObjectDto = new BookedObjectDTO(6L,
                                                        new RoomDTO(7L,
                                                                "roomFromDto",
                                                                3,
                                                                1,
                                                                new RoomTypeDTO(8L,"single", 200)),
                                                        List.of(),
                                                        new BookingDTO(9L, new CustomerDTO(10L, "customerFromDto")),
                                                        LocalDate.of(2025,5,12),
                                                        LocalDate.of(2025,5,13)
    );




    @Test
    void testEntityToDto() {
        BookedObjectDTO bookedObjectDTOFromEntity = bookedObjectMapper.toDTO(bookedObjectEntity);

        assertEquals(1L, bookedObjectDTOFromEntity.getId());
        assertEquals(bookedObjectEntity.getId(), bookedObjectDTOFromEntity.getId());


        assertEquals(bookedObjectEntity.getRoom().getId(), bookedObjectDTOFromEntity.getRoom().getId());
        assertEquals(2L, bookedObjectDTOFromEntity.getRoom().getId());

        assertEquals("roomFromEntity", bookedObjectDTOFromEntity.getRoom().getName());
        assertEquals(bookedObjectEntity.getRoom().getName(), bookedObjectDTOFromEntity.getRoom().getName());

        assertEquals(3L, bookedObjectDTOFromEntity.getRoom().getRoomType().getId());
        assertEquals(bookedObjectEntity.getRoom().getRoomType().getId(), bookedObjectDTOFromEntity.getRoom().getRoomType().getId());

        assertEquals("single", bookedObjectDTOFromEntity.getRoom().getRoomType().getName());
        assertEquals(bookedObjectEntity.getRoom().getRoomType().getName(), bookedObjectDTOFromEntity.getRoom().getRoomType().getName());

        assertEquals(4L, bookedObjectDTOFromEntity.getBooking().getId());
        assertEquals(bookedObjectEntity.getBooking().getId(), bookedObjectDTOFromEntity.getBooking().getId());

        assertEquals(5L, bookedObjectDTOFromEntity.getBooking().getCustomer().getId());
        assertEquals(bookedObjectEntity.getBooking().getCustomer().getId(), bookedObjectDTOFromEntity.getBooking().getCustomer().getId());

        assertEquals("customerFromEntity", bookedObjectDTOFromEntity.getBooking().getCustomer().getName());
        assertEquals(bookedObjectEntity.getBooking().getCustomer().getName(), bookedObjectDTOFromEntity.getBooking().getCustomer().getName());

        assertEquals(LocalDate.of(2025,5,12), bookedObjectDTOFromEntity.getStartDate());
        assertEquals(bookedObjectEntity.getStartDate(), bookedObjectDTOFromEntity.getStartDate());

        assertEquals(LocalDate.of(2025,5,13), bookedObjectDTOFromEntity.getEndDate());
        assertEquals(bookedObjectEntity.getEndDate(), bookedObjectDTOFromEntity.getEndDate());

    }

}