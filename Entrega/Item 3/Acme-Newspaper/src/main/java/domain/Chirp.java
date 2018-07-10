
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "moment")
})
public class Chirp extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private Date	moment;
	private String	title;
	private String	description;


	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	//Relationships----------------------------------------------------------------

	private User	user;


	//	private Follower	follower;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	//	@ManyToOne(optional = true)
	//	public Follower getFollower() {
	//		return this.follower;
	//	}
	//
	//	public void setFollower(final Follower follower) {
	//		this.follower = follower;
	//	}

}
