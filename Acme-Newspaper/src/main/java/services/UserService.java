
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.User;
import forms.ActorAccountForm;

@Service
@Transactional
public class UserService {

	//Managed repository -----------------------------------------------------
	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	//Supporting services ----------------------------------------------------

	//Constructor ------------------------------------------------------------
	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		final User result;
		result = new User();
		return result;
	}

	public User create(final String name, final String surname, final String email, final String phone, final String address) {
		final User res = new User();
		res.setName(name);
		res.setSurname(surname);
		res.setEmail(email);
		res.setPhone(phone);
		res.setAddress(address);

		return res;
	}

	public Collection<User> findAll() {
		final Collection<User> res = this.userRepository.findAll();
		Assert.notNull(res);
		return res;

	}
	public User findOne(final int userId) {
		final User t = this.userRepository.findOne(userId);
		return t;

	}

	public User save(final User user) {
		Assert.notNull(user);
		final User res = this.userRepository.save(user);
		return res;
	}

	public void delete(final User user) {
		Assert.notNull(user);
		Assert.isTrue(user.getId() != 0);

		this.userRepository.delete(user);
	}

	public User findByPrincipal() {
		User user;
		UserAccount result;
		result = LoginService.getPrincipal();
		Assert.notNull(result);
		user = this.findByUserAccount(result);
		Assert.notNull(user);
		return user;
	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public User findActorByUserAccountName(final String name) {
		final User us = this.userRepository.findUserByUserAccountName(name);
		return us;
	}

	public User registerUser(final UserAccount userAccount, final User r) {
		final Authority au = new Authority();
		au.setAuthority("USER");
		userAccount.addAuthority(au);
		r.setUserAccount(userAccount);
		//this.save(r);

		this.folderService.createFolderforRegisterActor(r);
		this.save(r);

		final Actor re = this.actorService.findActorByUserAccountName(userAccount.getUsername());
		this.folderService.createFolderforRegisterActor(re);
		this.folderService.saveFolderForActor(re);
		return r;
	}

	//
	//	public Collection<User> usersByRendezvous(final int rendezvousId) {
	//		final Collection<User> res = this.userRepository.usersByRendezvous(rendezvousId);
	//		return res;
	//	}
	//
	//	public Collection<Announcement> findAnnouncementsByUserId2(final int userId) {
	//		final Collection<Announcement> announcements = new HashSet<Announcement>();
	//		Collection<Rendezvous> rendezvouses;
	//		rendezvouses = this.userRepository.findOne(userId).getRsvp();
	//		for (final Rendezvous r : rendezvouses)
	//			for (final Announcement a : r.getAnnouncement())
	//				announcements.add(a);
	//
	//		return announcements;
	//	}

	public void flush() {
		this.userRepository.flush();
	}

	public Actor reconstruct(final ActorAccountForm actorAccountForm, final BindingResult binding) {
		Actor result;

		if (actorAccountForm.getId() == 0) {

			result = this.create();
			result.getUserAccount().setUsername(actorAccountForm.getUsername());
			result.getUserAccount().setPassword(actorAccountForm.getPassword());
			result.setPhone(actorAccountForm.getPhone());
			result.setName(actorAccountForm.getName());
			result.setSurname(actorAccountForm.getSurname());

			result.setAddress(actorAccountForm.getAddress());

		} else {
			result = this.userRepository.findOne(actorAccountForm.getId());

			result.setName(actorAccountForm.getName());
			result.setSurname(actorAccountForm.getSurname());
			result.setAddress(actorAccountForm.getAddress());

			result.setPhone(actorAccountForm.getPhone());
			result.setEmail(actorAccountForm.getEmail());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public Double avgNewspapersPerUser() {
		final Double res = this.userRepository.avgNewspapersPerUser();
		return res;
	}

	public Double stddevNewspapersPerUser() {
		final Double res = this.userRepository.stddevNewspapersPerUser();
		return res;
	}

	public Double avgArticlesPerUser() {
		final Double res = this.userRepository.avgArticlesPerUser();
		return res;
	}

	public Double stddevArticlesPerUser() {
		final Double res = this.userRepository.stddevArticlesPerUser();
		return res;
	}

	public Double ratioNewspaperCreatedPerUser() {
		final Double res = this.userRepository.ratioNewspaperCreatedPerUser();
		return res;
	}

	public Double ratioArticlesCreatedPerUser() {
		final Double res = this.userRepository.ratioArticlesCreatedPerUser();
		return res;
	}
	public Double avgChirpsPerUser() {
		final Double res = this.userRepository.avgChirpsPerUser();
		return res;
	}

	public Double stddevChirpsPerUser() {
		final Double res = this.userRepository.stddevChirpsPerUser();
		return res;
	}

	public Double usersWithMoreAvgChirps() {
		final Double res = this.userRepository.usersWithMoreAvgChirps();
		return res;
	}

	public User onlySave(final User user) {
		User res;
		res = this.userRepository.save(user);
		return res;
	}

	public User findUserByNewspaper(final int newspaperId) {
		User result;
		Assert.isTrue(newspaperId != 0);

		result = this.userRepository.findUserByNewspaper(newspaperId);

		return result;
	}

	public User findUserByArticle(final int articleId) {
		User result;
		Assert.isTrue(articleId != 0);

		result = this.userRepository.findUserByArticle(articleId);

		return result;
	}

	public User findUserByVolume(final int volumeId) {
		User result;
		Assert.isTrue(volumeId != 0);

		result = this.userRepository.findUserByVolume(volumeId);

		return result;
	}

}
