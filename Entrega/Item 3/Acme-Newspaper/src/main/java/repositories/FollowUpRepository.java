
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FollowUp;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Integer> {

	//B2
	@Query("select avg(a.followUps.size) from Newspaper n join n.articles a join a.followUps f where DATEDIFF(n.publication, f.moment) < 7")
	Double avgFollowUpPerArticleOneWeek();

	//B3
	@Query("select avg(a.followUps.size) from Newspaper n join n.articles a join a.followUps f where DATEDIFF(n.publication, f.moment) < 14")
	Double avgFollowUpPerArticleTwoWeek();
}
