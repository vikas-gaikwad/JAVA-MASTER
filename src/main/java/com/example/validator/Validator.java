package com.example.validator;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.pojo.RowMapper;

public class Validator<T,U> {
	RowMapper rowMapper = new RowMapper();

	public  String validateFormData( T entity,U password){
		String validationStatus="{\"status\":\"200\",\"field\": \"working fine\",\"reason\": \"done successfully\"}";
		
	JSONObject json = null;
	try {
		json = new JSONObject(entity.toString());
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	try {
		json.getString("email");
	} catch (Exception e) {
		validationStatus="{\"status\":\"400\",\"field\": \"email should not empty.\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";	
	}
	
	try {
		json.getString("name");	
	} catch (Exception e) {
		validationStatus="{\"status\":\"400\",\"field\": \"name should not empty.\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";	
	}
	
	try {
		json.getString("profile");
	} catch (Exception e) {
		validationStatus="{\"status\":\"400\",\"field\": \"profile should not empty.\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";	
	}
	try {
		json.getString("city");
	} catch (Exception e) {
		validationStatus="{\"status\":\"400\",\"field\": \"city should not empty.\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";	
	}
	try {
		json.getString("type");
	} catch (Exception e) {
		validationStatus="{\"status\":\"400\",\"field\": \"type should not empty.\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";	
	}
	

		
		
	return validationStatus;	
	}

}
