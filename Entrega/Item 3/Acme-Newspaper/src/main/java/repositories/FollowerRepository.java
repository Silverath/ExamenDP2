
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Follower;
import domain.User;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {

	@Query("select f from Follower f where f.follow = ?1 and f.followed = ?2")
	public Follower existsFollower(User a, User b);
	@Query("select f from Follower f where f.follow = ?1")
	public Collection<Follower> findByFollow(User a);
	@Query("select f from Follower f where f.followed = ?1")
	public Collection<Follower> findByFollower(User a);

}
