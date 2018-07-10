
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Agent;
import forms.ActorAccountForm;

@Service
@Transactional
public class AgentService {

	//Managed repository -----------------------------------------------------
	@Autowired
	private AgentRepository	agentRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	//Supporting services ----------------------------------------------------

	//Constructor ------------------------------------------------------------
	public AgentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Agent create() {
		final Agent result;
		result = new Agent();
		return result;
	}

	public Agent create(final String name, final String surname, final String email, final String phone, final String address) {
		final Agent res = new Agent();
		res.setName(name);
		res.setSurname(surname);
		res.setEmail(email);
		res.setPhone(phone);
		res.setAddress(address);

		return res;
	}

	public Collection<Agent> findAll() {
		final Collection<Agent> res = this.agentRepository.findAll();
		Assert.notNull(res);
		return res;

	}
	public Agent findOne(final int agentId) {
		final Agent t = this.agentRepository.findOne(agentId);
		return t;

	}

	public Agent save(final Agent agent) {
		Assert.notNull(agent);
		final Agent res = this.agentRepository.save(agent);
		return res;
	}

	public void delete(final Agent agent) {
		Assert.notNull(agent);
		Assert.isTrue(agent.getId() != 0);

		this.agentRepository.delete(agent);
	}

	public Agent findByPrincipal() {
		Agent agent;
		UserAccount result;
		result = LoginService.getPrincipal();
		Assert.notNull(result);
		agent = this.findByUserAccount(result);
		Assert.notNull(agent);
		return agent;
	}

	public Agent findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Agent result;
		result = this.agentRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Agent findActorByUserAccountName(final String name) {
		final Agent us = this.agentRepository.findAgentByUserAccountName(name);
		return us;
	}

	public Agent registerAgent(final UserAccount userAccount, final Agent a) {
		final Authority au = new Authority();
		au.setAuthority("AGENT");
		userAccount.addAuthority(au);
		a.setUserAccount(userAccount);
		//this.save(a);

		this.folderService.createFolderforRegisterActor(a);
		this.save(a);

		final Actor re = this.actorService.findActorByUserAccountName(userAccount.getUsername());
		this.folderService.createFolderforRegisterActor(re);
		this.folderService.saveFolderForActor(re);
		return a;
	}

	public void flush() {
		this.agentRepository.flush();
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
			result = this.agentRepository.findOne(actorAccountForm.getId());

			result.setName(actorAccountForm.getName());
			result.setSurname(actorAccountForm.getSurname());
			result.setAddress(actorAccountForm.getAddress());

			result.setPhone(actorAccountForm.getPhone());
			result.setEmail(actorAccountForm.getEmail());

		}

		this.validator.validate(result, binding);

		return result;
	}

}
