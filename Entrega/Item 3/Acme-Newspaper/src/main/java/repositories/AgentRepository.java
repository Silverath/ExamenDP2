
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {

	@Query("select c from Agent c where c.userAccount.id = ?1")
	Agent findByUserAccountId(int userAccountId);

	@Query("select u from Agent u where u.userAccount.username=?1")
	Agent findAgentByUserAccountName(String name);

}
