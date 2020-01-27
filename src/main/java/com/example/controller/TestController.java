package com.example.controller;

import org.json.JSONException;
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
	
	
	/*TESTING API FOR CHECKING CONNECTION*/
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		logger_.info("TEST SUCCESS ");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*BASIC REGISTRATION  API FOR TENANT & OWNER*/
	@PostMapping("/basic_info_registration")
	public ResponseEntity<?> insertData(@RequestBody String object,@RequestHeader String password){
		String response = null;
		try {
			response = testService.basic_info_registration(object,password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	/*LOGIN API FOR TENANT & OWNER*/
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email,@RequestHeader String password,@RequestParam String type){
		String status=null;
		logger_.info("email - "+email+",  password - "+password+"  type - "+type);
		status= testService.login(email,password,type);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

	/*
	 * API FOR PUTTING ADVANCE DETAILS OF OWNER. 
	 * THIS API WILL BE CALL AFTER OWNER LOGIN.
	 **/
	@PostMapping("/register_owner_flat_details")
	public ResponseEntity<?> owner_flat_details(@RequestBody String object){
		logger_.info(""+object);
		String response = testService.owner_flat_details(object);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
/*	AFTER OWNER SUCCESSFULL LOGIN, 
 * 	THIS API WILL BE CALLED
 * */
	@GetMapping("/get_login_details")
	public ResponseEntity<?> get_login_details(@RequestParam String id){
		String response  = testService.getLoginDetails(id);
	return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
