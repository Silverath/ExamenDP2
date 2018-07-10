
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Molet;

public interface MoletRepository extends JpaRepository<Molet, Integer> {

	@Query("select a from Molet a where a.administrator.id = ?1")
	Collection<Molet> findMoletsByAdmin(int adminId);

	@Query("select a from Molet a where a.newspaper.id = ?1")
	Collection<Molet> findMoletsByNewspaper(int newspaperId);

	@Query("select a from Molet a where current_date >= a.moment or a.moment = null")
	Collection<Molet> findAllPublicated();
}
