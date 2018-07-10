
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Follower extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Follower() {
		super();
	}


	// Relationships ----------------------------------------------------------

	private User	follow;
	private User	followed;


	//private Collection<Chirp>	chirps;

	//	@OneToMany(mappedBy = "follower")
	//	public Collection<Chirp> getChirps() {
	//		return this.chirps;
	//	}
	//
	//	public void setChirps(final Collection<Chirp> chirps) {
	//		this.chirps = chirps;
	//	}

	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Actor getFollow() {
		return this.follow;
	}
	public void setFollow(final User follow) {
		this.follow = follow;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Actor getFollowed() {
		return this.followed;
	}
	public void setFollowed(final User followed) {
		this.followed = followed;
	}

}
