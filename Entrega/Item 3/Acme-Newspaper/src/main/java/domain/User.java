
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	public User() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Volume>		volumes;
	private Collection<Newspaper>	newspapers;
	private Collection<Article>		articles;
	private Collection<Chirp>		chirps;
	private Collection<Follower>	follower;	//Seguidores que tienen los usuarios
	private Collection<Follower>	follow;	//Personas que siguen los usuarios


	@OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
	public Collection<Volume> getVolumes() {
		return this.volumes;
	}

	public void setVolumes(final Collection<Volume> volumes) {
		this.volumes = volumes;
	}

	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	public void setChirps(final Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	@OneToMany(mappedBy = "follow")
	public Collection<Follower> getFollower() {
		return this.follower;
	}

	public void setFollower(final Collection<Follower> follower) {
		this.follower = follower;
	}

	@OneToMany(mappedBy = "followed")
	public Collection<Follower> getFollow() {
		return this.follow;
	}

	public void setFollow(final Collection<Follower> follow) {
		this.follow = follow;
	}

}
