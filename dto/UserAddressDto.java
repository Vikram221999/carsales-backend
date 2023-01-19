package com.stg.dto;

import java.util.List;

import com.stg.entity.Bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDto {
	
	private int userId;
	private String userName;

	private String userPassword;

	private long mobileNumber;

	private String email;
	
	private int doorNo;

	private String streetName;

	private String city;

	private String state;

	private int pincode;

}
