package com.stg.dto;

import com.stg.entity.Bookings.Choose;
import com.stg.entity.Bookings.MyBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MybookingDto {
	
	private String carNumber;
	private String carName;
	
	private int yearOfManufacture;
	
	private String carModelName;
	
	private int userId;
	private String city;
	
	private String userName;

	private long mobileNumber;
	
	private int offerPrice;
	
	private int bookingId;
	
	private MyBooking myBooking;
	
	private Choose choose;

	

}
