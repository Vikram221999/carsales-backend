package com.stg.dto;

import com.stg.entity.Bookings.Choose;
import com.stg.entity.Bookings.MyBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class offerDto {
	
	
	private int userId;
	
	private String city;
	
	private String userName;

	private long mobileNumber;
	
	private int offerPrice;
	private int yearOfManufacture;
	
	private int bookingId;
	
	private MyBooking myBooking;
	
	private String carModelName;
	
	private String carName;
	
	private Choose choose;
	private int price;

}
