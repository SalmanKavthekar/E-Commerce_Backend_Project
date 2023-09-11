package com.salman.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salman.payload.UserDto;
import com.salman.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		Date date=new Date();
		userDto.setDate(date); 
		UserDto createUser=userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable Integer id){
		UserDto userDto=userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable Integer id){
		userService.deleteUser(id);
	}
	
	@GetMapping("/finadAllUsers")
	public ResponseEntity<List<UserDto>> findAllUsers(){
		List<UserDto> allUser =userService.findAllUsers();
		return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateUserBy/{id}")
	public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto, @PathVariable Integer id){
		Date date=new Date();
		userDto.setDate(date);
		UserDto updateUserDto=userService.updateUserById(userDto, id);
		return new ResponseEntity<UserDto>(updateUserDto, HttpStatus.ACCEPTED);
	}
	
	
	
}













