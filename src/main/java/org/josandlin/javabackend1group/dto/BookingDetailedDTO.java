package org.josandlin.javabackend1group.dto;

import java.util.List;

public class BookingDetailedDTO {

    Long id;
    List<BookedObjectSmallDTO> bookedObjects;

    public BookingDetailedDTO(Long id, List<BookedObjectSmallDTO> bookedObjects) {
        this.id = id;
        this.bookedObjects = bookedObjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookedObjectSmallDTO> getBookedObjects() {
        return bookedObjects;
    }

    public void setBookedObjects(List<BookedObjectSmallDTO> bookedObjects) {
        this.bookedObjects = bookedObjects;
    }
}
