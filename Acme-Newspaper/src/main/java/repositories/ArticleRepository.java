
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where a.title like %?1% or a.summary like %?1% or a.body like %?1%")
	Collection<Article> searchArticle(String keyword);

	@Query("select a from User u join u.newspapers n join n.articles a where n.publication!= null and u.id=?1")
	Collection<Article> articlePublishedByUser(int userId);

	//B1
	@Query("select avg(a.followUps.size) from Article a")
	Double avgFollowsUpPerArticle();
}
