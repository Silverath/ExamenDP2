
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private String	title;
	private String	description;
	private String	year;


	@SafeHtml
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@SafeHtml
	@NotBlank
	public String getYear() {
		return this.year;
	}

	public void setYear(final String year) {
		this.year = year;
	}


	// --------------- Relationships -------------

	private User					publisher;
	private Collection<Newspaper>	newspapers;


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public User getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final User publisher) {
		this.publisher = publisher;
	}

	@ManyToMany(mappedBy = "volumes")
	@Valid
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

}
