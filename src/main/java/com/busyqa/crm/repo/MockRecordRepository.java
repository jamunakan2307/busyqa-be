package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.MockRecord;
import com.busyqa.crm.model.User;

public interface MockRecordRepository extends JpaRepository<MockRecord, Long> {

	Optional<MockRecord> findByuser(User user);

	MockRecord save(MockRecord mockRecord);

	void deleteById(int id);
}
