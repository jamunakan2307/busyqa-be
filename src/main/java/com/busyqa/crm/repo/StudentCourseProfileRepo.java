package com.busyqa.crm.repo;

import javax.transaction.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.student.LearningProfile;

@Repository
@Transactional
public interface StudentCourseProfileRepo extends PagingAndSortingRepository<LearningProfile, Long> {

}

