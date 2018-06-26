
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.User;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;
	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AgentService			agentService;


	public ActorService() {
		super();
	}

	public Collection<Actor> findAll() {
		Collection<Actor> res;

		res = this.actorRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	public Actor findOne(final int actorId) {
		final Actor a = this.actorRepository.findOne(actorId);
		return a;
	}

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountService.findByActor(actor);

		return result;
	}

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Actor findActorByUserAccountName(final String name) {
		final Actor us = this.actorRepository.findActorByUserAccountName(name);
		return us;
	}

	public void updateActor(final Actor a) {

		for (final Authority au : a.getUserAccount().getAuthorities()) {
			System.out.println(au.getAuthority());
			if (au.getAuthority().equals("USER"))
				this.userService.save((User) a);
			if (au.getAuthority().equals("CUSTOMER"))
				this.userService.save((User) a);
			if (au.getAuthority().equals("ADMIN"))
				this.administratorService.save((Administrator) a);

			if (au.getAuthority().equals("AGENT"))
				this.agentService.save((Agent) a);

		}

	}

}
