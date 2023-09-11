package com.salman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salman.exception.ResourceNotFoundException;
import com.salman.model.User;
import com.salman.payload.UserDto;
import com.salman.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		// convert userdto to user
		User user = mapper.map(userDto, User.class);
		
		//encode password
		String pass=user.getPassword();
		String encode=passwordEncoder.encode(pass);
		user.setPassword(encode);
		
		// save
		User saveUser = userRepo.save(user);
		// convert user to userdto
		UserDto saveUserDto = mapper.map(saveUser, UserDto.class);
		return saveUserDto;
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found by this id"));
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = userRepo.findById(id).orElseThrow(() ->new ResourceNotFoundException("UserId not Found"));
		userRepo.delete(user);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> user = userRepo.findAll();
		List<UserDto> userDto = user.stream().map(u -> mapper.map(u, UserDto.class)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public UserDto updateUserById(UserDto userDto, Integer id) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found by this id"));
		
		user.setAbout(userDto.getAbout());
		user.setActive(userDto.isActive());
		user.setAdress(userDto.getAdress());
		user.setDate(userDto.getDate());
		user.setEmail(userDto.getEmail());
		user.setGender(userDto.getGender());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhone(userDto.getPhone());
		
		User saveUser=userRepo.save(user);
		UserDto saveUserDto=mapper.map(saveUser, UserDto.class);
		return saveUserDto;
	}
	
	
	
	
	
	
	
	
	
	
	

}
