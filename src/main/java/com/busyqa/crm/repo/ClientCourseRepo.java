package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.ClientCourse;

public interface ClientCourseRepo extends JpaRepository<ClientCourse, Long> {

	Optional<ClientCourse> findByname(String name);

	ClientCourse save(ClientCourse clientCourse);

	void deleteById(int id);
}
