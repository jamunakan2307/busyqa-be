package com.busyqa.crm.repo.verion_2_0;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.BusyqaBatch;
import com.busyqa.crm.model.BusyqaCourse;
import com.busyqa.crm.model.User;

@Repository
public interface BusyqaBatchRepo extends JpaRepository<BusyqaBatch, Long> {
	List<BusyqaBatch> findBybusyqaCourse(BusyqaCourse busyqaCourse);

	Optional<BusyqaBatch> findByuser(User user);

	List<BusyqaBatch> findBybatchStatus(String batchStatus);

	List<BusyqaBatch> findAll();

	BusyqaBatch save(BusyqaBatch busyqaBatch);

	void deleteById(int id);
}
