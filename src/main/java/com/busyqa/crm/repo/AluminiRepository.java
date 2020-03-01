package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.AluminiRecord;
import com.busyqa.crm.model.User;

public interface AluminiRepository extends JpaRepository<AluminiRecord, Long> {

	Optional<AluminiRecord> findByuser(User user);

	AluminiRecord save(AluminiRecord aluminiRecord);

	void deleteById(int id);
}
