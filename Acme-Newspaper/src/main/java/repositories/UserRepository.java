
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select c from User c where c.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	@Query("select u from User u where u.userAccount.username=?1")
	User findUserByUserAccountName(String name);

	@Query("select u from User u join u.newspapers n where n.id=?1")
	User findUserByNewspaper(int newspaperId);

	@Query("select u from User u join u.newspapers n join n.articles a where a.id=?1")
	User findUserByArticle(int articleId);

	@Query("select u from User u join u.volumes v where v.id=?1")
	User findUserByVolume(int volumeId);

	//C1
	@Query("select avg(u.newspapers.size) from User u")
	Double avgNewspapersPerUser();

	//C1
	@Query("select stddev(u.newspapers.size) from User u")
	Double stddevNewspapersPerUser();

	//C2
	@Query("select avg(u.articles.size) from User u")
	Double avgArticlesPerUser();

	//C2
	@Query("select stddev(u.articles.size) from User u")
	Double stddevArticlesPerUser();

	//C6
	@Query("select (select count(u) from User u join u.newspapers)*1.0/count(r) from User r")
	Double ratioNewspaperCreatedPerUser();

	//C7
	@Query("select (select count(u) from User u join u.articles)*1.0/count(r) from User r")
	Double ratioArticlesCreatedPerUser();

	//B4
	@Query("select avg(u.chirps.size) from User u")
	Double avgChirpsPerUser();

	//B4
	@Query("select stddev(u.chirps.size) from User u")
	Double stddevChirpsPerUser();

	//B5
	@Query("select sum(case when(u.chirps.size>(select (avg(us.chirps.size)*0.75) from User us)) then 1.0 else 0.0 end)/count(u) from User u")
	Double usersWithMoreAvgChirps();

}
