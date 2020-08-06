package com.example.reviewMovie.repo;

import com.example.reviewMovie.entity.LoginDetails;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface LoginDetailsRepo extends PagingAndSortingRepository<LoginDetails,Integer>{

	@Query(value="select id,email_id,password from POS.login_details where email_id=?1 and password=?2 and flag=1",nativeQuery=true)
	LoginDetails getLoginDetails(String emailId, String password);
	
	
	@Query(value="select PASSWORD(?1) AS password",nativeQuery = true)
	String getPassword(String password);

}
