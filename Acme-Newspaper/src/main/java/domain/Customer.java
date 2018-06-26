
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	public Customer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Collection<Subscription>	subscriptions;


	// Relationships ----------------------------------------------------------

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	public Collection<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void setSubscriptions(final Collection<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
