package com.example.service;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pojo.RowMapper;
import com.example.repository.IRepository;
import com.example.validator.RegexChecking;
import com.example.validator.Validator;

import ch.qos.logback.core.boolex.Matcher;

@Service
public class TestService implements IService {
	
	
	static Logger logger_ = LoggerFactory.getLogger(TestService.class);	
	Validator validate = new Validator();
	
	@Autowired
	IRepository testRepository;
	
	RowMapper rowMapper = new RowMapper();
	RegexChecking regexCheck = new RegexChecking();
	
	
	/*
	 * #############################################################################
	 */
	@Override
	public String basic_info_registration(String object, String password) throws JSONException {
		/*
		 * VALIDATING ALL INCOMING FIELDS FROM OBJECT THEN PERFORM THE OPERATION
		 */
		String getValidationStatus = validate.validateFormData(object, password);
		JSONObject validField = new JSONObject(getValidationStatus);

		if (validField.getString("field").contentEquals("working fine")) {
			/*
			 * BEFORE INSERTING DATA INTO DATABASE- CHECK EMAIL ID IS ALREADY PRESENT OR NOT
			 */
			JSONObject getEmailForValidating = new JSONObject(object);
			String status = checkIfAlreadyRegistered(getEmailForValidating.getString("email"));

			/* null = EMAIL NOT FOUND IN DATABASE && 1= EMAIL IS FOUND IN DATABASE */
			if (status == null) {
				String response = null;
			/* GET JSON DATA FROM object & SET THESE DATA TO OBJECT MAPPER CLASS */
				JSONObject json = new JSONObject(object);
				rowMapper.setName(json.getString("name"));
				rowMapper.setProfile(json.getString("profile"));
				rowMapper.setCity(json.getString("city"));
				rowMapper.setEmail(json.getString("email"));
				rowMapper.setType(json.getString("type"));
				rowMapper.setPassword(password);

				response = testRepository.basic_info_registration(rowMapper);
				return response;
			} else {
				/* IF EMAIL ALREADY REGISTERED THEN RESPONSE RETURN */
				return "{\"status\": \"Fail\",\"reason\": \"Account With Email Already Registered.\"}";

			}
		} else {
			/* VALIDATION FAILS THEN RESPONSE RETURN */
			return getValidationStatus.toString();
		}

	}

	
	/*
	 * #############################################################################
	 */
	public String checkIfAlreadyRegistered(String emailID) {
		return testRepository.checkIfAlreadyRegistered(emailID);
	}


	@Override
	public String login(String email, String password,String type) {
		Boolean check=regexCheck.validate(email);
		System.out.println("EMAIL VALID - "+check);
		if(check) {
			
		}
		else {
			return "{\"status\": \"Fail\",\"reason\": \"Email Should Not Empty\"}";
		}
		if(password==null || password=="") {
			return "{\"status\": \"Fail\",\"reason\": \"password Should Not Empty\"}";
		}
		if(type==null || type=="") {
			return "{\"status\": \"Fail\",\"reason\": \"Select Type\"}";
		}
		return testRepository.login(email,password,type);
		
	}


	@Override
	public String owner_flat_details(String object) {
		RowMapper rowMapper = new RowMapper();
		JSONObject json;
		try {
			json = new  JSONObject(object);
			rowMapper.setFlat_number(json.getString("flat_number"));
			rowMapper.setBulding_name(json.getString("bulding_name"));
			rowMapper.setPlot_number(json.getString("plot_number"));
			rowMapper.setLandmark(json.getString("landmark"));
			rowMapper.setCity(json.getString("city"));
			rowMapper.setTaluka(json.getString("taluka"));
			rowMapper.setDistrict(json.getString("district"));
			rowMapper.setState(json.getString("state"));
			rowMapper.setId(json.getInt("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return testRepository.owner_flat_details(rowMapper);
		 
	}


	@Override
	public String getLoginDetails(String id) {
return testRepository.getLoginDetails(id);		
	}

}
