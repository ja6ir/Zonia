package com.zonia.zonia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zonia.zonia.entity.Login;



@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    Login findByUsernameAndPassword(String username, String password);
    
   
}
