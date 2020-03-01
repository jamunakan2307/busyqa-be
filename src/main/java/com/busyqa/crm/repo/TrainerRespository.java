package com.busyqa.crm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.Trainer;

public interface TrainerRespository extends JpaRepository<Trainer, Long> {

	Optional<Trainer> findBycourseName(String courseName);

}
