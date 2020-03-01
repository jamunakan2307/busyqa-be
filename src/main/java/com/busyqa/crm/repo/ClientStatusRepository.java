package com.busyqa.crm.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.ClientStatus;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatus, Long> {
	Optional<ClientStatus> findByname(String name);

	List<ClientStatus> findAll();
}
