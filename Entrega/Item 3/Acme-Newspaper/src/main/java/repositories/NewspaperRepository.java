
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	//C3
	@Query("select avg(n.articles.size) from Newspaper n")
	Double avgArticlesPerNewspaper();

	//C3
	@Query("select stddev(n.articles.size) from Newspaper n")
	Double stddevArticlesPerNewspaper();

	//C4
	@Query("select r.articles from Newspaper r where r.articles.size>(select avg(r.articles.size)+avg(r.articles.size)*0.1 from Newspaper r)")
	Collection<Newspaper> moreArticlesThanAverage();

	//C5
	@Query("select r.articles from Newspaper r where r.articles.size<(select avg(r.articles.size)+avg(r.articles.size)*0.1 from Newspaper r)")
	Collection<Newspaper> lessArticlesThanAverage();

	@Query("select n from Newspaper n join n.articles a where a.id = ?1")
	Newspaper findByArticleId(int articleId);

	@Query("select n from Newspaper n where n.author.id=?1")
	Collection<Newspaper> findNewspapersByAuthor(int userId);

	@Query("select a from Newspaper a where a.title like %?1% or a.description like %?1% AND a.publish=TRUE AND a.publication < CURRENT_TIMESTAMP AND a.isOpen=TRUE")
	Collection<Newspaper> searchNewspaper(String keyword);

	@Query("select n from Newspaper n where n.publish=TRUE AND n.publication < CURRENT_TIMESTAMP AND n.isOpen=TRUE")
	Collection<Newspaper> newspaperPublished();

	@Query("select n from Newspaper n where n.publication=null AND n.publish=FALSE")
	Collection<Newspaper> newspaperNotPublished();

	@Query("select n from Newspaper n where n.isOpen = FALSE")
	Collection<Newspaper> findAllPrivate();

	//A1
	@Query("select (select count(n1) from Newspaper n1 where n1.isOpen = TRUE)/(count(n)*1.0) from Newspaper n")
	Double ratioPublicVersusPrivate();

	//A2
	@Query("select avg(n.articles.size) from Newspaper n where n.isOpen = FALSE")
	Double avgArticlesPerPrivateNewspaper();

	//A3
	@Query("select avg(n.articles.size) from Newspaper n where n.isOpen = TRUE")
	Double avgArticlesPerPublicNewspaper();

	//A5
	@Query("select count(n1)/(select count(n) from Newspaper n) from Newspaper n1 where n1.isOpen = FALSE")
	Double ratioPublicVersusPrivatePerPublisher();

	/** Newspaper2.0 */

	//C1
	@Query("select count(n1)/(select count(n) from Newspaper n) from Newspaper n1 where n1.advertisements.size IS NOT NULL")
	Double ratioWithAtLeastOneAdvertisiment();

	//ESTA SON LAS QUERYS
	@Query("select n from Newspaper n where n.advertisements IS NOT EMPTY")
	Collection<Newspaper> allAdvertisements();

	@Query("select n from Newspaper n where n.advertisements IS EMPTY")
	Collection<Newspaper> notAdvertisements();

	@Query("select s.volume.newspapers from Subscription s where s.customer.id = ?1 and s.volume.id = ?2")
	Collection<Newspaper> findAllVolumeNewspapersSubscribed(int id, int idVolume);

}
