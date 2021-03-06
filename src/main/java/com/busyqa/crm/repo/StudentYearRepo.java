package com.busyqa.crm.repo;
/* 5:07 PM
created by: ajaypalsingh , 2018-12-07
Project: crm
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.student.StudentYear;

@Repository
public interface StudentYearRepo extends JpaRepository<StudentYear, Long> {

}
