
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Administrator;
import domain.Chirp;
import domain.Follower;
import domain.User;

@Service
@Transactional
public class ChirpService {

	@Autowired
	private Validator				validator;

	@Autowired
	private ChirpRepository			chirpRepository;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FollowerService			followerService;


	public ChirpService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Chirp create() {
		Chirp result;
		result = new Chirp();
		final User userPrincipal = this.userService.findByPrincipal();
		result.setUser(userPrincipal);
		final Date d = new Date();
		result.setMoment(d);

		return result;
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Chirp reconstruct(final Chirp chirp, final BindingResult binding) {
		Chirp result;

		if (chirp.getId() == 0) {
			result = chirp;
			final Date moment = new Date();
			result.setMoment(moment);
		} else {
			result = this.chirpRepository.findOne(chirp.getId());

			result.setId(chirp.getId());
			result.setVersion(chirp.getVersion());
			result.setMoment(chirp.getMoment());
			result.setTitle(chirp.getTitle());
			result.setDescription(chirp.getDescription());
			result.setUser(chirp.getUser());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(chirp.getUser()));
		final Chirp res = this.chirpRepository.save(chirp);
		u.getChirps().add(res);
		this.userService.save(u);
		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final Integer chirpId) {
		final Chirp res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public void deleteAdmin(final Chirp r) {
		Assert.notNull(r);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final User u = r.getUser();
		u.getArticles().remove(r);
		this.userService.save(u);

		this.chirpRepository.delete(r);
	}

	//	public Collection<Chirp> chirpsPostedBymyFollows(final int userId) {
	//		final Collection<Chirp> res = this.chirpRepository.chirpsPostedBymyFollows(userId);
	//		return res;
	//	}

	public Collection<Chirp> chirpsPostedBymyFollows(final int userId) {
		final User u = this.userService.findOne(userId);
		final Collection<Follower> rr = u.getFollow();
		final Collection<User> coluser = new HashSet<User>();
		final Collection<Chirp> chirp = new HashSet<Chirp>();
		for (final Follower f : rr) {
			final User user = (User) f.getFollow();
			coluser.add(user);
		}
		for (final User uu : coluser) {
			uu.getChirps();
			chirp.addAll(chirp);
		}
		return chirp;
	}

	public Collection<Chirp> prueba() {
		final Collection<Follower> follower = this.followerService.findByFollow(this.userService.findByPrincipal());
		final Collection<User> users = new ArrayList<User>();
		final Collection<Chirp> chirps = new HashSet<Chirp>();
		for (final Follower aux : follower)
			if (aux.getFollowed().getClass().equals(User.class)) {
				final User n = (User) aux.getFollowed();
				users.add(n);
			}
		for (final User u : users)
			chirps.addAll(u.getChirps());

		return chirps;
	}

	public void flush() {
		this.chirpRepository.flush();
	}

}
