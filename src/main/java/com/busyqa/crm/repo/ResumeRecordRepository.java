package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.ResumeRecord;
import com.busyqa.crm.model.User;

public interface ResumeRecordRepository extends JpaRepository<ResumeRecord, Long> {

	Optional<ResumeRecord> findByuser(User user);

	ResumeRecord save(ResumeRecord resumeRecord);

	void deleteById(int id);
}
