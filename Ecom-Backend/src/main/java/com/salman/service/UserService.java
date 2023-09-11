package com.salman.service;

import java.util.List;

import com.salman.payload.UserDto;

public interface UserService {

	public UserDto createUser(UserDto userDto);
	
	public UserDto getUserById(Integer id);
	
	public List<UserDto> findAllUsers();
	
	public void deleteUser(Integer id);
	
	public UserDto updateUserById(UserDto userDto, Integer id);
}
