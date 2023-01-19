package com.stg.controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stg.dto.BookingDto;
import com.stg.dto.MybookingDto;
import com.stg.dto.UserAddressDto;
import com.stg.dto.UserDto;
import com.stg.dto.UserDto1;
import com.stg.dto.offerDto;
import com.stg.entity.Bookings;
import com.stg.entity.Bookings.Choose;
import com.stg.entity.User;
import com.stg.exception.UserException;
import com.stg.service.BookingServiceInterface;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(value = "booking")
public class BookingController {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private BookingServiceInterface bookingServiceInterface;

	@PostMapping(value = "bookCar")
	public String bookCar(@RequestBody BookingDto dto) throws UserException {
		System.out.println(dto);
		return bookingServiceInterface.bookCar(/* id,adId,offerPrice */dto.getUserId(), dto.getAdId(),
				dto.getOfferPrice());

	}
	
	@GetMapping(value = "buyerDetails/{buyerId}")
	public UserAddressDto buyerDetails(@PathVariable("buyerId") int buyerId)
			throws UserException {
		return bookingServiceInterface.buyerDetails(buyerId);
	}

	@GetMapping(value = "accept/{userId}/{bookingId}")
	public String accept(@PathVariable("userId") int userId,@PathVariable("bookingId") int bookingId)
			throws UserException {
		return bookingServiceInterface.accept(userId,bookingId);
	}
	@GetMapping(value = "reject/{userId}/{bookingId}")
	public String reject(@PathVariable("userId") int userId,@PathVariable("bookingId") int bookingId)
			throws UserException {
		return bookingServiceInterface.reject(userId,bookingId);
	}
	@GetMapping(value = "cancel/{userId}/{bookingId}")
	public String cancel(@PathVariable("userId") int userId,@PathVariable("bookingId") int bookingId)
			throws UserException {
		return bookingServiceInterface.cancel(userId,bookingId);
	}

	@GetMapping(value = "myBooking/{userId}")
	public ResponseEntity<List<MybookingDto>> myBooking(@PathVariable int userId) throws UserException {

		List<MybookingDto> user = bookingServiceInterface.myBooking(userId);

		return new ResponseEntity<List<MybookingDto>>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "myBookingHistory/{userId}")
	public ResponseEntity<List<MybookingDto>> myBookingHistory(@PathVariable int userId) throws UserException {

		List<MybookingDto> user = bookingServiceInterface.myBookingHistory(userId);

		return new ResponseEntity<List<MybookingDto>>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "offerHistory/{userId}")
	public ResponseEntity<List<offerDto>> offerHistory(@PathVariable int userId) throws UserException {

		List<offerDto> user = bookingServiceInterface.offerHistory(userId);

		return new ResponseEntity<List<offerDto>>(user, HttpStatus.OK);
	}
	
	
	

	@GetMapping(value = "offers/{userId}")
	public ResponseEntity<List<offerDto>> offers(@PathVariable int userId) throws UserException {
		List<offerDto> user = bookingServiceInterface.offers(userId);
		return new ResponseEntity<List<offerDto>>(user, HttpStatus.OK);

	}

//	@GetMapping(value = "readByBookingId/{userId}")
	public Bookings readByBookingId(@PathVariable int userId) {
		return bookingServiceInterface.readByBookingId(userId);

	}

}
