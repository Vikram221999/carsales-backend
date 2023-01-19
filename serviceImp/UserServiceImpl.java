package com.stg.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.dto.UserAddressDto;
import com.stg.dto.UserDto1;
import com.stg.entity.Address;
import com.stg.entity.Car;
import com.stg.entity.CarAddress;
import com.stg.entity.User;
import com.stg.exception.UserException;
import com.stg.repository.AddressRepo;
import com.stg.repository.CarRepo;
import com.stg.repository.UserRepo;
import com.stg.service.UserServiceInterface;

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CarRepo carRepo;
	
	public List<User> showUser() {
		return userRepo.findAll();
	}
	
	
	
	
	@Override
	public  UserAddressDto userAddressDetails(String email) {
		User user=userRepo.findByEmail(email);
		Address address=user.getAddress();
		
		UserAddressDto dto=mapper.map(user, UserAddressDto.class);
		
		dto.setCity(address.getCity());
		dto.setPincode(address.getPincode());
		dto.setState(address.getState());
		dto.setDoorNo(address.getDoorNo());
		dto.setStreetName(address.getStreetName());
		
		
		return dto;
	}
	
	
	
	@Override
	public String updateUserDetails(UserAddressDto dto) throws UserException {
	
		System.out.println(dto.toString());
		
		
			User user=userRepo.findById(dto.getUserId()).get();
			
			Address address=addressRepo.findById(user.getAddress().getAddressId()).get();
			
				if (!user.getUserPassword().matches("[a-zA-Z0-9]{8,20}")) {
					throw new UserException("Invalid Password Pattern");
				}
				else {
					user.setUserPassword(dto.getUserPassword());
				}

				if (user.getMobileNumber()==dto.getMobileNumber()) {
					user.setMobileNumber(dto.getMobileNumber());
				}
				else {
					if (userRepo.existsByMobileNumber(user.getMobileNumber())) {
						throw new UserException("\"Mobile Number Already Exist\"");
					}
					else {
						user.setMobileNumber(dto.getMobileNumber());
					}
				}
				
				
			
		user.setUserName(dto.getUserName());
		address.setCity(dto.getCity());
		address.setPincode(dto.getPincode());
		address.setState(dto.getState());
		address.setDoorNo(dto.getDoorNo());
		address.setStreetName(dto.getStreetName());
		
		userRepo.save(user);
		addressRepo.save(address);	
		return "\"Update\"";
	}
	
	
	@Override
	public Address addressReadById(int id) throws UserException {
		
		List<Address> addresses=addressRepo.findAll();

		Address address1=new Address();
		for (Address address : addresses) {
			if(address.getUser().getUserId()==id) {
				address1=address;
			}
		}
		if (address1.getAddressId()>0) {
			
			return address1;
		} else {
			throw new UserException("Incorrect Id");
		}
	}
	
	@Override
	public User readById(int id) throws UserException {

		if (userRepo.existsById(id)) {
			User user = userRepo.findById(id).get();
			return user;
		} else {
			throw new UserException("Incorrect Id");
		}
	}

	@Override
	public List<User> readByName(String name) throws UserException {
		List<User> list = null;
		if (userRepo.existsByUserName(name)) {
			list = userRepo.findByUserName(name);
			return list;
		} else {
			throw new UserException("The Given Name is not avilable.");
		}

	}

	@Override
	public User readByEmail(String email) throws UserException {

		if (userRepo.existsByEmail(email)) {
			User user = userRepo.findByEmail(email);

			return user;
		} else {
			throw new UserException("Incorrect Email Id");
		}
	}

	@Override
	public User readByMobileNumber(long mobile) throws UserException {
		if (userRepo.existsByMobileNumber(mobile)) {

			return userRepo.findByMobileNumber(mobile);
		} else {
			throw new UserException("Incorrect Email Id.");
		}
	}

	@Override
	public User updateName(String email, String name) throws UserException {

		if (userRepo.existsByEmail(email)) {
			User user = userRepo.findByEmail(email);
			user.setUserName(name);
			userRepo.save(user);
			return user;
		} else {
			throw new UserException("Incorrect Email Id.");
		}
	}

	@Override
	public User updatePassword(String email, String password) throws UserException {
		if (userRepo.existsByEmail(email)) {
			User user = userRepo.findByEmail(email);
			user.setUserPassword(password);
			userRepo.save(user);
			return user;
		} else {
			throw new UserException("Incorrect Email Id.");
		}
	}

	@Override
	public User updateMobileNumber(String email, long mobileNumber) throws UserException {
		if (userRepo.existsByEmail(email)) {
			User user = userRepo.findByEmail(email);
			user.setMobileNumber(mobileNumber);
			userRepo.save(user);
			return user;
		} else {
			throw new UserException("Incorrect Email Id.");
		}
	}

	@Override
	public User updateEmail(String email, String changeEmail) throws UserException {
		if (userRepo.existsByEmail(email)) {
			User user = userRepo.findByEmail(email);
			user.setEmail(changeEmail);
			userRepo.save(user);
			return user;
		} else {
			throw new UserException("Incorrect Email Id.");
		}
	}

	@Override
	public String deleteUser(int id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return "User Successfully Deleted";
		} else {
			return "Enter the Correct User Id";
		}
	}

	@Override
	public List<Car> carReadById(int userId) {
		
		List<Car> list=carRepo.findAll();
		
		List<Car> cars=new ArrayList<>();
		
		for (Car car : list) {
			if(car.getAdvertisement().getUser().getUserId()==userId)
			 {
			cars.add(car);	
			}
		}
		
		return cars;
		
		
	}




	
	


}
