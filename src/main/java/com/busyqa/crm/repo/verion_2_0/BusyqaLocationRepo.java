package com.busyqa.crm.repo.verion_2_0;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.BusyqaLocation;

@Repository
public interface BusyqaLocationRepo extends JpaRepository<BusyqaLocation, Long> {
	Optional<BusyqaLocation> findBylocationName(String locationName);

	List<BusyqaLocation> findAll();

	BusyqaLocation save(BusyqaLocation busyqaLocation);

	void deleteById(int id);
}
