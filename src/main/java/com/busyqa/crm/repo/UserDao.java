package com.busyqa.crm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	User findByUsername(String username);
}
