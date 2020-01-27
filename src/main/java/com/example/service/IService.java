package com.example.service;

import org.json.JSONException;

public interface IService {

	String basic_info_registration(String object, String password) throws JSONException;

	String login(String email, String password,String type);

	String owner_flat_details(String object);

	String  getLoginDetails(String id);

}
