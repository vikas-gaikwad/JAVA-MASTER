package com.example.service;

public interface IService {

	String basic_info_registration(String object, String password);

	String login(String email, String password,String type);

	String owner_flat_details(String object);

}
