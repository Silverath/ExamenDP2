
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Collection<String>	taboo;


	@ElementCollection
	@NotEmpty
	@Valid
	public Collection<String> getTaboo() {
		return this.taboo;
	}

	public void setTaboo(final Collection<String> taboo) {
		this.taboo = taboo;
	}
}
