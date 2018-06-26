
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Audit;

public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.administrator.id = ?1")
	Collection<Audit> findAuditsByAdmin(int adminId);

	@Query("select a from Audit a where a.newspaper.id = ?1")
	Collection<Audit> findAuditsByNewspaper(int newspaperId);

	@Query("select a from Audit a where current_date >= a.moment or a.moment = null")
	Collection<Audit> findAllPublicated();
}
