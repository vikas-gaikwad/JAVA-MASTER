package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.IService;

@RestController
public class TestController {
	static Logger logger_=LoggerFactory.getLogger(TestController.class);

	@Autowired 
	IService testService;
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		logger_.info("TEST SUCCESS ");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/basic_info_registration")
	public ResponseEntity<?> insertData(@RequestBody String object,@RequestParam String password){
		String response=testService.basic_info_registration(object,password);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email,@RequestHeader String password,@RequestParam String type){
		String status=null;
		logger_.info("email - "+email+",  password - "+password+"  type - "+type);
		status= testService.login(email,password,type);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

	@PostMapping("/owner_flat_details")
	public ResponseEntity<?> owner_flat_details(@RequestBody String object){
		logger_.info(object);
		String response = testService.owner_flat_details(object);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
