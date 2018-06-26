
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isOpen,publication,publish")
})
public class Newspaper extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private Date	publication;
	private String	description;
	private String	picture;
	private Boolean	isOpen;
	private Boolean	publish;


	@SafeHtml
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	public Date getPublication() {
		return this.publication;
	}

	public void setPublication(final Date publication) {
		this.publication = publication;
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
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	public Boolean getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(final Boolean isOpen) {
		this.isOpen = isOpen;
	}

	@NotNull
	public Boolean getPublish() {
		return this.publish;
	}

	public void setPublish(final Boolean publish) {
		this.publish = publish;
	}


	// Relationships ---------------------------------------------------------------

	private Collection<Volume>			volumes;
	private Collection<Article>			articles;
	private Collection<Subscription>	subscriptions;
	private User						author;
	private Collection<Advertisement>	advertisements;


	@ManyToMany
	@Valid
	public Collection<Volume> getVolumes() {
		return this.volumes;
	}

	public void setVolumes(final Collection<Volume> volumes) {
		this.volumes = volumes;
	}

	@OneToMany(mappedBy = "newspaper", cascade = CascadeType.REMOVE)
	@Valid
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	@OneToMany(mappedBy = "newspaper", cascade = CascadeType.REMOVE)
	@Valid
	public Collection<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public User getAuthor() {
		return this.author;
	}

	public void setAuthor(final User author) {
		this.author = author;
	}

	@OneToMany(mappedBy = "newspaper", cascade = CascadeType.REMOVE)
	@Valid
	public Collection<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void setSubscriptions(final Collection<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
