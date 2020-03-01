package com.busyqa.crm.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByfirstName(String firstName);

	Optional<User> findByid(Long id);
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByTeams(String teams);

	List<User> findAll();

	List<User> findByTeams(String teams);

	User save(User user);

	void deleteById(int id);
	//  User update(User user);
}
