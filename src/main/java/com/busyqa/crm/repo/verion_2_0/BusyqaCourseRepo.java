package com.busyqa.crm.repo.verion_2_0;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.BusyqaCourse;

@Repository
public interface BusyqaCourseRepo extends JpaRepository<BusyqaCourse, Long> {
	Optional<BusyqaCourse> findBycourseName(String courseName);

	List<BusyqaCourse> findAll();

	List<BusyqaCourse> findBystatus(String status);

	BusyqaCourse save(BusyqaCourse busyqaCourse);

	void deleteById(int id);
}
