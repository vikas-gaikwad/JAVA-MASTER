package com.example.repository;

import com.example.pojo.RowMapper;

public interface IRepository {

	String basic_info_registration(RowMapper rowMapper);

	String checkIfAlreadyRegistered(String emailID);

	String login(String email, String password,String type);

	String owner_flat_details(RowMapper rowMapper);

}
