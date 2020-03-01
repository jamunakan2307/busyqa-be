package com.busyqa.crm.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.ClientCourse;

@Repository
public interface CourseRepository extends JpaRepository<ClientCourse, Long> {
	Optional<ClientCourse> findByname(String name);

	List<ClientCourse> findAll();
}
