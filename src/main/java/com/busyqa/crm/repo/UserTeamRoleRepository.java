package com.busyqa.crm.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.model.Team;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;

@Repository
public interface UserTeamRoleRepository extends JpaRepository<UserTeamRole, Long> {
	List<UserTeamRole> findByTeam(Team team);

	List<UserTeamRole> findByUser(User user);

	List<UserTeamRole> findAll();

}
