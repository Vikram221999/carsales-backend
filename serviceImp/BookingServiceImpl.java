package com.stg.serviceImp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.dto.BookingDto;
import com.stg.dto.MybookingDto;
import com.stg.dto.UserAddressDto;
import com.stg.dto.UserDto;
import com.stg.dto.UserDto1;
import com.stg.dto.offerDto;
import com.stg.entity.Advertisement;
import com.stg.entity.Bookings;

import com.stg.entity.Bookings.Choose;
import com.stg.entity.Bookings.MyBooking;
import com.stg.entity.Car;
import com.stg.entity.User;
import com.stg.exception.UserException;
import com.stg.repository.AdvertisementRepo;
import com.stg.repository.BookingRepo;
import com.stg.repository.UserRepo;
import com.stg.service.BookingServiceInterface;
import com.stg.service.UserServiceInterface;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class BookingServiceImpl implements BookingServiceInterface {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private AdvertisementRepo advertisementRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserServiceInterface serviceInterface;

	@Override
	public String bookCar(int userId, int adId, int offerPrice) throws UserException {
		System.out.println(userId);
		if (userRepo.existsById(userId)) {
			List<Bookings> bookingss = bookingRepo.findAll();
			boolean check = false;
			for (Bookings bookings2 : bookingss) {
				if (bookings2.getAdvertisementId() == adId && bookings2.getUser().getUserId() == userId) {
					check = true;
					break;
				}
			}
			if (check == false) {
				Advertisement advertisement = advertisementRepo.findById(adId).get();

				User user = serviceInterface.readById(userId);
				if(!(offerPrice>0)) {
					throw new UserException("Offer price shouldn't be negative or zero");
				}
				Bookings bookings = new Bookings();
				
				bookings.setOfferPrice(offerPrice);
				
				bookings.setSellerId(advertisement.getUser().getUserId());
				bookings.setAdvertisement(advertisement);
				bookings.setUser(user);
				bookings.setAdvertisementId(advertisement.getAdId());
				bookings.setBuyerDetail(advertisement.getUser().getUserId() + " Seller");
				bookings.setSellerDetail(userId + " Buyer");
				bookings.setBuyerId(userId);
				bookings.setChoose(Choose.AVAILABLE);
				bookings.setMyBooking(MyBooking.AVAILABLE);

				bookingRepo.save(bookings);

				return "\"Your profile has been sended.\"";
			} else {
				throw new UserException("\"You are already booked this car\"");
			}
		} else {
			throw new UserException("Id is Not found");
		}
	}

	@Override
	public List<offerDto> offers(int userId) throws UserException {
		List<offerDto> dtos = new ArrayList<offerDto>();
		if (userRepo.existsById(userId)) {

			List<Bookings> bookings = bookingRepo.findAll();

			for (Bookings booking : bookings) {
				if (booking.getSellerId() == userId) {
					if (booking.getChoose() != (Choose.ACCEPT)) {
						if (booking.getMyBooking() == MyBooking.AVAILABLE && booking.getChoose() == Choose.AVAILABLE) {
						offerDto dto = mapper.map(booking.getAdvertisement().getCar().get(0), offerDto.class);
						dto.setUserId(booking.getSellerId());
						dto.setCity(booking.getUser().getAddress().getCity());
						dto.setUserName(booking.getUser().getUserName());
						dto.setMobileNumber(booking.getUser().getMobileNumber());
						dto.setOfferPrice(booking.getOfferPrice());
						dto.setBookingId(booking.getBookingId());
						dto.setChoose(booking.getChoose());
						dto.setMyBooking(booking.getMyBooking());
						dtos.add(dto);
					} 
					}else {

					}
				}
			}
		} else {
			throw new UserException("Id is Not found");
		}
		if (dtos.size() > 0) {
			return dtos;

		} else {
			throw new UserException("No data Found");
		}
	}

	@Override
	public List<MybookingDto> myBooking(int userId) throws UserException {

		List<MybookingDto> dtos = new ArrayList<MybookingDto>();
		if (userRepo.existsById(userId)) {

			List<Bookings> bookings = bookingRepo.findAll();

			for (Bookings booking : bookings) {
				if (booking.getBuyerId() == userId) {
					if (booking.getChoose() == Choose.AVAILABLE) {

						MybookingDto dto = mapper.map(booking.getAdvertisement().getCar().get(0), MybookingDto.class);
						dto.setUserId(booking.getSellerId());
						dto.setUserName(booking.getUser().getUserName());
						dto.setMobileNumber(booking.getUser().getMobileNumber());
						dto.setOfferPrice(booking.getOfferPrice());
						dto.setBookingId(booking.getBookingId());
						dto.setChoose(booking.getChoose());
						dto.setMyBooking(booking.getMyBooking());
						dtos.add(dto);
					}
				}
			}

		} else {
			throw new UserException("Id is Not found");
		}
		if (dtos.size() > 0) {
			return dtos;

		} else {
			throw new UserException("No data Found");
		}
	}

	@Override
	public List<MybookingDto> myBookingHistory(int userId) throws UserException {
		List<MybookingDto> dtos = new ArrayList<MybookingDto>();
		if (userRepo.existsById(userId)) {

			List<Bookings> bookings = bookingRepo.findAll();

			for (Bookings booking : bookings) {
				if (booking.getBuyerId() == userId) {
					if (booking.getChoose() != Choose.AVAILABLE || booking.getChoose()==Choose.REJECT  || booking.getChoose() == Choose.SOLD_TO_YOU || booking.getChoose() == Choose.SOLD || booking.getChoose()==Choose.ACCEPT) {
						MybookingDto dto = mapper.map(booking.getAdvertisement().getCar().get(0), MybookingDto.class);
						dto.setUserId(booking.getSellerId());
						dto.setCity(booking.getUser().getAddress().getCity());
						dto.setUserName(booking.getUser().getUserName());
						dto.setMobileNumber(booking.getUser().getMobileNumber());
						dto.setOfferPrice(booking.getOfferPrice());
						dto.setBookingId(booking.getBookingId());
						dto.setChoose(booking.getChoose());
						dto.setMyBooking(booking.getMyBooking());
						dtos.add(dto);
					}
				}
			}
		} else {
			throw new UserException("Id is Not found");
		}
		if (dtos.size() > 0) {
			return dtos;

		} else {
			throw new UserException("No data Found");
		}
	}

	@Override
	public List<offerDto> offerHistory(int userId) throws UserException {
		List<offerDto> dtos = new ArrayList<offerDto>();
		if (userRepo.existsById(userId)) {

			List<Bookings> bookings = bookingRepo.findAll();

			for (Bookings booking : bookings) {
				if (booking.getSellerId() == userId) {

					if ( booking.getChoose() != Choose.AVAILABLE) {
						offerDto dto = mapper.map(booking.getAdvertisement().getCar().get(0), offerDto.class);
						dto.setUserId(booking.getSellerId());
						dto.setUserName(booking.getUser().getUserName());
						
						dto.setMobileNumber(booking.getUser().getMobileNumber());
						dto.setOfferPrice(booking.getOfferPrice());
						dto.setBookingId(booking.getBookingId());
						dto.setChoose(booking.getChoose());
						dto.setMyBooking(booking.getMyBooking());
						dtos.add(dto);
					}
				}
			}
		} else {
			throw new UserException("Id is Not found");
		}
		if (dtos.size() > 0) {
			return dtos;

		} else {
			throw new UserException("No data Found");
		}
	}

	@Override
	public Bookings readByBookingId(int bookingId) {
		Bookings bookings = bookingRepo.findById(bookingId).get();
		System.out.println(bookings.getUser().getUserId());
		System.out.println(bookings.getUser().getUserName());
		return bookings;
	}

	@Override
	public String accept(int userId, int bookingId) throws UserException {
		String ans = "";
		int adId=0;
		if (userRepo.existsById(userId)) {

			Bookings bookings = bookingRepo.findById(bookingId).get();
			if (bookings.getChoose() != Choose.ACCEPT) {
				bookings.setChoose(Choose.ACCEPT);
				System.out.println("///////// Accepted");
				adId = bookings.getAdvertisementId();
				bookingRepo.save(bookings);
				System.out.println(adId);
				 
				List<Bookings> list = bookingRepo.findAll();
				for (Bookings bookings2 : list) {
					System.out.println("////??????????? "+bookings2.getAdvertisementId()+"  ???????//////////////");
					if (bookings2.getAdvertisementId() == adId) {
						System.out.println("///23////// "+bookings2.getAdvertisementId()+"  ///////////////23////");
						Bookings bookinganss = bookingRepo.findById(bookingId).get();
						
						if(bookings2.getBookingId()!=bookingId) {
							System.out.println("(#$%&//////////"+bookings.getUser().getUserId());
							
							Bookings bookingans = bookingRepo.findById(bookings2.getBookingId()).get();
							System.out.println("Yessssssss");
							bookingans.setChoose(Choose.SOLD);
							System.out.println("///@#$#//// "+bookinganss.getAdvertisement().getCarId()+"  ////////@#%&///////");
							bookingRepo.save(bookingans);
							
						} 
					}

				}

				ans = "\"Accepted\"";
			} else {
				ans = "\"Already accept this offer\"";
			}
		} else {
			throw new UserException("Id is Not found");
		}
		if (ans.length() > 0) {
			return ans;

		} else {
			throw new UserException("Miss");
		}
	}

	@Override
	public String reject(int userId, int bookingId) throws UserException {
		String ans = "";

		if (userRepo.existsById(userId)) {

			Bookings bookings = bookingRepo.findById(bookingId).get();
			bookings.setChoose(Choose.REJECT);
			System.out.println("///////// rejected");
			bookingRepo.save(bookings);
			ans = "\"Reject\"";
		} else {
			throw new UserException("Id is Not found");
		}
		if (ans.length() > 0) {
			return ans;

		} else {
			throw new UserException("Miss");
		}
	}

	@Override
	public String cancel(int userId, int bookingId) throws UserException {
		String ans = "";

		if (userRepo.existsById(userId)) {
			Bookings bookings = bookingRepo.findById(bookingId).get();
			bookings.setMyBooking(MyBooking.CANCEL);
			bookings.setChoose(Choose.CANCEL);
			System.out.println("///////// cancel");
			bookingRepo.save(bookings);
			ans = "\"cancel\"";
		} else {
			throw new UserException("Id is Not found");
		}
		if (ans.length() > 0) {
			return ans;

		} else {
			throw new UserException("Miss");
		}
	}

	@Override
	public UserAddressDto buyerDetails(int buyerId) {
		
		Bookings bookings= bookingRepo.findById(buyerId).get();
		System.out.println(bookings.getSellerId());
		
		User user= userRepo.findById(bookings.getBuyerId()).get();
		UserAddressDto booking = mapper.map(user, UserAddressDto.class);
		booking.setCity(user.getAddress().getCity());
		booking.setDoorNo(user.getAddress().getDoorNo()); 
		booking.setState(user.getAddress().getCity()); 
		booking.setStreetName(user.getAddress().getStreetName());
		booking.setPincode(user.getAddress().getPincode());
		
		
		return booking;
	}

}
