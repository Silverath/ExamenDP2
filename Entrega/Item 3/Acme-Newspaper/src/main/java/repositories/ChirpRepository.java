
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	//	@Query("select c from User u join u.follow f join f.chirps c where u.id = ?1")
	//	Collection<Chirp> chirpsPostedBymyFollows(int userId);

}
