package com.salman.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salman.model.Role;



@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
	 

}