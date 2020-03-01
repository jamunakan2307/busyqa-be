package com.busyqa.crm.repo;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.student.StudentProfile;

@Repository
@Transactional
public interface StudentProfileRep extends PagingAndSortingRepository<StudentProfile, Long> {

	@Query("select s.id,s.firstName,s.lastName,s.email,s.phone,s.status from StudentProfile s where s.firstName = :terms")
	List<StudentProfile> SearchStudentsByTerm(@Param(value = "terms") String terms);

	//    @Query("select '*' from StudentProfile s where s.id = :id")
	//    List<StudentProfile> updateStudent(@Param(value = "id") long id);
	//void updateStudent(StudentProfile student);
}


