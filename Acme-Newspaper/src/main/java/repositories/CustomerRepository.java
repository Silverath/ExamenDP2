
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select u from Customer u where u.userAccount.username=?1")
	Customer findUserByUserAccountName(String name);

	//A4 
	@Query("select (select avg(n.subscriptions.size)/count(n) from Newspaper n where n.isOpen = FALSE)/count(c) from Customer c")
	Double ratioSubscribersPrivateNewspaperCustomer();
}
