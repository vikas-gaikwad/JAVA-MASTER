package com.example.repository;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.pojo.RowMapper;
import com.example.service.TestService;

@Repository
public class TestRepository implements IRepository{
	@Autowired
	JdbcTemplate jdbcTemplate;
	static Logger logger_=LoggerFactory.getLogger(TestService.class);
	
	
	/*
	 * #############################################################################
	 */

	@Override
	public String basic_info_registration(RowMapper rowMapper) {
		int count=0;
		String INSERT_QUERY="insert into basic_info (name , city , profile , email , password , type) "
							+ " values(?,?,?,?,?,?)";
		try {
			count = jdbcTemplate.update(INSERT_QUERY, 
					rowMapper.getName(),
					rowMapper.getCity(),
					rowMapper.getProfile(),
					rowMapper.getEmail(),
					rowMapper.getPassword(),
					rowMapper.getType()
					
					);
		} catch (DataAccessException e) {
			count=0;
			e.printStackTrace();
		}
		
		/*
		 * INSTEAD OF IF..ELSE BLOCK I USED HERE TERNARY OPERATOR
		 */	
		String status = 
				count==1   	?	"{\"status\": \"Success\",\"reason\": \"Registration Success\"}"
							:	"{\"status\": \"Fail\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";
		
		return status;
	}


	/*
	 * #############################################################################
	 */
	@Override
	public String checkIfAlreadyRegistered(String emailID) {
		String  check=null;
		String validateEmailID="select 1 from basic_info where email = ? order by createdAt desc limit 1";
		try {
			check=jdbcTemplate.queryForObject(validateEmailID, new Object[] {emailID},String.class);
		} catch (Exception e) {
			check=null;
		}
		return check;
	}


	@Override
	public String login(String email, String password,String type) {
		List<Map<String, Object>> userData=null;
		JSONObject json = new JSONObject();
		String CHECK_IF_ACCOUNT_AVAILABLE="select * from basic_info where email=? and password=? and type=? order by createdAt desc limit 1";
		try {
			userData=jdbcTemplate.queryForList(CHECK_IF_ACCOUNT_AVAILABLE,email,password,type);
			System.out.println(userData);
		} catch (Exception e) {
			userData=null;
		}
		 if(userData.size()>0) {
			 try {
				json.put("status", "Success");
				json.put("message", "Login Success");
				 json.put("loginData", userData);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }else {
			 try {
				json.put("status", "Fail");
				 json.put("message", "Login Fail, Chack Email & Password & Type");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					 }
		  return json.toString();
	}


	@Override
	public String owner_flat_details(RowMapper rowMapper) {
		System.out.println(rowMapper.toString());
		int count=0;
		String INSERT_OWNER_ADV_DETAILS="insert into adv_details "
				+ " (flat_number , bulding_name , plot_number , landmark , "
				+ " city , taluka , district , state , id )"
				+ " values (?,?,?,?,?,?,?,?,?)";
		try {
			count=jdbcTemplate.update(INSERT_OWNER_ADV_DETAILS,
					rowMapper.getBulding_name(),
					rowMapper.getFlat_number(),
					rowMapper.getPlot_number(),
					rowMapper.getLandmark(),
					rowMapper.getCity(),
					rowMapper.getTaluka(),
					rowMapper.getDistrict(),
					rowMapper.getState(),
					rowMapper.getId());
			System.out.println(">>>>>"+count);
		} catch (Exception e) {
			e.printStackTrace();
			count=0;		
		}
		JSONObject j=new JSONObject();
		String status = 
				count==1   	?   "{\"status\": \"Success\",\"reason\": \"Registration Success\"}"
							:	"{\"status\": \"Fail\",\"reason\": \"Registration Fail, Something Went Wrong.\"}";
		
		try {
			j.put("response", status);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return j.toString();
	}


	@Override
	public String getLoginDetails(String id) {
		List<Map<String,Object>> getDetails=null;
		JSONObject j=new JSONObject();
		String GET_ALL_DETAILS_OF_LOGIN_USER = 
				"select "
				+ "b.name,b.type,b.createdAt,"
				+ "a.flat_id,a.flat_number,a.bulding_name,a.plot_number,a.landmark,a.city,a.taluka,a.district,a.state,a.createdAt,a.updatedAt "
				+ "from basic_info b inner join adv_details a on b.id=a.id where b.id=?";
		try {
			getDetails = jdbcTemplate.queryForList(GET_ALL_DETAILS_OF_LOGIN_USER, id);
		} catch (Exception e) {
			getDetails=null;		
		}
		if(getDetails.size()>0) {
			try {
				j.put("data", getDetails);
				j.put("message", "Success");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		else {
			try {
				j.put("message", "No Data Found For This User");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return j.toString();
		
	}

}
