
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FollowerRepository;
import security.Authority;
import security.UserAccount;
import domain.Follower;
import domain.User;

@Service
@Transactional
public class FollowerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FollowerRepository	followerRepository;

	// Supporting services ----------------------------------------------------	

	@Autowired
	private UserService			userService;


	// Constructors -----------------------------------------------------------

	public FollowerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Follower create() {

		final Follower res = new Follower();
		final User a = this.userService.findByPrincipal();
		Assert.notNull(a);
		Assert.isTrue(this.isUser(a.getUserAccount()));
		res.setFollow(a);

		return res;
	}

	public Follower save(final Follower follower) {
		Assert.notNull(follower);
		Follower res = null;
		final User a = this.userService.findOne(follower.getFollow().getId());
		final User b = this.userService.findOne(follower.getFollowed().getId());
		Assert.notNull(b);
		Assert.notNull(a);
		Assert.isTrue(!a.equals(b));
		Assert.isTrue(this.isUser(a.getUserAccount()));
		Assert.isTrue(this.isUser(follower.getFollowed().getUserAccount()));
		Assert.isTrue(a == this.userService.findByPrincipal());
		Assert.isTrue(!this.existsFollower(a, b));
		res = this.followerRepository.save(follower);

		return res;
	}

	public void delete(final Follower follower) {
		Assert.notNull(follower);
		final User a = this.userService.findOne(follower.getFollow().getId());
		final User b = this.userService.findOne(follower.getFollowed().getId());
		Assert.notNull(b);
		Assert.notNull(a);
		Assert.isTrue(this.isUser(a.getUserAccount()));
		Assert.isTrue(this.isUser(follower.getFollowed().getUserAccount()));
		Assert.isTrue(a == this.userService.findByPrincipal());
		Assert.isTrue(this.existsFollower(a, b));
		this.followerRepository.delete(follower);
	}

	public Collection<Follower> findAll() {
		final Collection<Follower> res = this.followerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Follower findOne(final Integer followerId) {
		final Follower res = this.followerRepository.findOne(followerId);
		Assert.notNull(res);
		return res;
	}

	// Other business methods -------------------------------------------------

	public Boolean isUser(final UserAccount account) {
		final Collection<Authority> authorities = account.getAuthorities();
		Boolean res = false;
		for (final Authority a : authorities)
			if (a.getAuthority().equals("USER"))
				res = true;
		return res;
	}

	public Boolean existsFollower(final User a, final User b) {
		return this.followerRepository.existsFollower(a, b) != null;
	}

	public Follower findByFollowAndFollower(final User a, final User e) {
		final Follower x = this.followerRepository.existsFollower(a, e);
		return x;
	}
	public Collection<Follower> findByFollow(final User a) {
		final Collection<Follower> x = this.followerRepository.findByFollow(a);
		return x;
	}
	public Collection<Follower> findByFollower(final User a) {
		final Collection<Follower> x = this.followerRepository.findByFollower(a);
		return x;
	}

}
