package com.zonia.zonia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zonia.zonia.entity.Register;


@Repository
public interface RegisterRepository extends JpaRepository<Register,String>{

	@Query("SELECT r.name FROM Register r WHERE r.username = :username")
    String findNameByUsername(@Param("username") String username);

	
	@Query("SELECT r.age FROM Register r WHERE r.username = :username")
	String findAgeByUsername(@Param("username") String username);

	@Query("SELECT r.gender FROM Register r WHERE r.username = :username")
	String findGenderByUsername(@Param("username") String username);

	@Query("SELECT r.experience FROM Register r WHERE r.username = :username")
	int findExperienceByUsername(@Param("username") String username);

	@Query("SELECT r.skills FROM Register r WHERE r.username = :username")
	String findSkillsByUsername(@Param("username") String username);

	@Query("SELECT r.qualifications FROM Register r WHERE r.username = :username")
	String findQualificationByUsername(@Param("username") String username);

	@Query("SELECT r.location FROM Register r WHERE r.username = :username")
	String findLocationByUsername(@Param("username") String username);

	@Query("SELECT r.otherPreference FROM Register r WHERE r.username = :username")
	String findOtherPreferenceByUsername(@Param("username") String username);

	@Query("SELECT r.filepath FROM Register r WHERE r.username = :username")
	String findFilepathByUsername(@Param("username") String username);
	
	

}
