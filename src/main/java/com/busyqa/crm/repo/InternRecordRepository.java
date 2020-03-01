package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.InternRecord;
import com.busyqa.crm.model.User;

public interface InternRecordRepository extends JpaRepository<InternRecord, Long> {

	Optional<InternRecord> findByuser(User user);

	InternRecord save(InternRecord internRecord);

	void deleteById(int id);
}
