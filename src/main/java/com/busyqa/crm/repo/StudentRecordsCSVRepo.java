package com.busyqa.crm.repo;
/* 4:19 PM 
created by: ajaypalsingh , 2019-01-06
Project: crm 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.student.StudentRecordsCSV;

@Repository
public interface StudentRecordsCSVRepo extends JpaRepository<StudentRecordsCSV, Long> {

	// List<? extends StudentRecordsCSV> save(StudentRecordsCSV studentRecordsCSV);
}
