
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;
import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {

	@Query("select s.volume from Subscription s where s.customer.id = ?1")
	Collection<Volume> findAllSubscribed(int id);

	//B1
	@Query("select avg(v.newspapers.size) from Volume v")
	Double avgNewspaperPerVolume();

	//The ratio of subscriptions to volumes versus subscriptions to newspapers.
	@Query("select (select sum(v.subscriptions.size) from Newspaper v)*1.0/ s.volume from Subscription s")
	Double ratioSubscriptionsVolumesNewspapers();

	@Query("select n from Newspaper n where n.isOpen = true and n.publish = true")
	Collection<Newspaper> findAllPublicNewspapers();
}
