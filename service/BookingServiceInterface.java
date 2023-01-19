package com.stg.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.stg.dto.BookingDto;
import com.stg.dto.MybookingDto;
import com.stg.dto.UserAddressDto;
import com.stg.dto.UserDto1;
import com.stg.dto.offerDto;
import com.stg.entity.Bookings;
import com.stg.entity.User;
import com.stg.entity.Bookings.Choose;
import com.stg.exception.UserException;

public interface BookingServiceInterface {
	public String bookCar(int userId,int adId,int offerPrice)throws UserException;
	
	public UserAddressDto buyerDetails( int buyerId);
	
	public List<MybookingDto>  myBooking(int userId)throws UserException;
	
	public List<MybookingDto>  myBookingHistory(int userId)throws UserException;
	
	public List<offerDto>  offerHistory(int userId)throws UserException;
	
	public List<offerDto> offers(int userId)throws UserException;
	
	public Bookings readByBookingId(int bookingId);
	
	public String accept(int userId,int bookingId ) throws UserException;
	
	public String reject(int userId ,int bookingId) throws UserException;
	
	public String cancel(int userId ,int bookingId) throws UserException;
	

}
