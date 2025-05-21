package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.dto.BookingDetailedDTO;
import org.josandlin.javabackend1group.dto.CustomerDetailedDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper2 {

//    CustomerDao customerDao;
//    BookingMapper2 bookingMapper2;
//
//    @Autowired
//    public CustomerMapper2(CustomerDao customerDao, BookingMapper2 bookingMapper2){
//        this.customerDao = customerDao;
//        this.bookingMapper2 = bookingMapper2;
//    }
//
//    public CustomerDetailedDTO toDTO(Customer customer){
//        List<BookingDetailedDTO> bookings = customerDao.getBookingsByCustomerId(customer.getId()).stream().map(bookingMapper2::toDTO).collect(Collectors.toList());
//        return new CustomerDetailedDTO(customer.getId(), customer.getName(), bookings);
//    }
}
