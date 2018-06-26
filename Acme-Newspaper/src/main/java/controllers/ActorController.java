
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdministratorService;
import services.AgentService;
import services.CustomerService;
import services.UserService;
import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.Customer;
import domain.User;
import forms.ActorAccountForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AgentService			agentService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final ActorAccountForm actorAccountForm = new ActorAccountForm();
		result = this.createEditModelAndViewR(actorAccountForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveregister(@Valid final ActorAccountForm actorAccountForm, final BindingResult binding) {
		ModelAndView result;
		final UserAccount userAccount = this.userAccountService.create();
		userAccount.setUsername(actorAccountForm.getUsername());
		final Md5PasswordEncoder enconder = new Md5PasswordEncoder();
		userAccount.setPassword(enconder.encodePassword(actorAccountForm.getPassword(), null));

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndViewR(actorAccountForm);
		} else
			try {
				Assert.isTrue(actorAccountForm.getTerms() == true);
				Assert.isTrue(actorAccountForm.getPassword().equals(actorAccountForm.getPassword2()));
				if (actorAccountForm.getAuthorities().getAuthority().equals("USER")) {
					final User res = this.userService.create(actorAccountForm.getName(), actorAccountForm.getSurname(), actorAccountForm.getEmail(), actorAccountForm.getPhone(), actorAccountForm.getAddress());
					this.userService.registerUser(userAccount, res);
				} else if (actorAccountForm.getAuthorities().getAuthority().equals("CUSTOMER")) {
					final Customer res = this.customerService.create(actorAccountForm.getName(), actorAccountForm.getSurname(), actorAccountForm.getEmail(), actorAccountForm.getPhone(), actorAccountForm.getAddress());
					this.customerService.registerCustomer(userAccount, res);
				} else if (actorAccountForm.getAuthorities().getAuthority().equals("AGENT")) {
					final Agent res = this.agentService.create(actorAccountForm.getName(), actorAccountForm.getSurname(), actorAccountForm.getEmail(), actorAccountForm.getPhone(), actorAccountForm.getAddress());
					this.agentService.registerAgent(userAccount, res);
				}

				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (actorAccountForm.getTerms() == false)
					result = this.createEditModelAndViewR(actorAccountForm, "actorAccount.termError");
				else if (!actorAccountForm.getPassword().equals(actorAccountForm.getPassword2()))
					result = this.createEditModelAndViewR(actorAccountForm, "actor.passwordError");
				else
					result = this.createEditModelAndViewR(actorAccountForm, "actor.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndViewR(final ActorAccountForm actorAccountForm) {
		ModelAndView result;

		result = this.createEditModelAndViewR(actorAccountForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewR(final ActorAccountForm userAccountForm, final String message) {
		ModelAndView result;

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority u1 = new Authority();
		u1.setAuthority(Authority.USER);
		final Authority u2 = new Authority();
		u2.setAuthority(Authority.CUSTOMER);
		final Authority u3 = new Authority();
		u3.setAuthority(Authority.AGENT);
		authorities.add(u1);
		authorities.add(u2);
		authorities.add(u3);

		result = new ModelAndView("actor/register");
		result.addObject("actorAccountForm", userAccountForm);
		result.addObject("authorities", authorities);
		result.addObject("message", message);

		return result;
	}

	/////////////////edit personal data///////////////////////////////	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView listUser() {
		ModelAndView result;
		ActorAccountForm actorAccountForm = new ActorAccountForm();
		final Actor actor = this.actorService.findByPrincipal();
		if (this.userService.findOne(actor.getId()) != null) {
			final User user = this.userService.findByPrincipal();
			actorAccountForm = this.convertUserToForm(user);
		} else if (this.administratorService.findOne(actor.getId()) != null) {
			final Administrator admin = this.administratorService.findByPrincipal();
			actorAccountForm = this.convertAdministratorToForm(admin);
		} else if (this.customerService.findOne(actor.getId()) != null) {
			final Customer manager = this.customerService.findByPrincipal();
			actorAccountForm = this.convertCustomerToForm(manager);
		}

		result = new ModelAndView("actor/edit");
		result.addObject("actorAccountForm", actorAccountForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorAccountForm actorAccountForm, final BindingResult binding) {
		ModelAndView result;
		Actor actor = this.actorService.findByPrincipal();
		Actor a = null;
		if (this.userService.findOne(actorAccountForm.getId()) != null)
			a = this.convertFormToUser(actorAccountForm);
		else if (this.administratorService.findOne(actor.getId()) != null)
			a = this.convertFormToAdministrator(actorAccountForm);
		else if (this.customerService.findOne(actor.getId()) != null)
			a = this.convertFormToManager(actorAccountForm);

		actor = this.userService.reconstruct(actorAccountForm, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(actorAccountForm);
		} else
			try {
				this.actorService.updateActor(a);
				result = new ModelAndView("actor/edit");
				result.addObject("message", "actor.commit.ok");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorAccountForm, "actor.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorAccountForm actorAccountForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorAccountForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorAccountForm actorAccountForm, final String message) {
		final ModelAndView result = new ModelAndView("actor/edit");
		result.addObject("actorAccountForm", actorAccountForm);
		result.addObject("message", message);

		return result;
	}

	public ActorAccountForm convertUserToForm(final User u) {
		final ActorAccountForm res = new ActorAccountForm();
		res.setId(u.getId());
		res.setName(u.getName());
		res.setSurname(u.getSurname());
		res.setEmail(u.getEmail());
		res.setPhone(u.getPhone());
		res.setAddress(u.getAddress());

		return res;
	}

	public ActorAccountForm convertCustomerToForm(final Customer u) {
		final ActorAccountForm res = new ActorAccountForm();
		res.setId(u.getId());
		res.setName(u.getName());
		res.setSurname(u.getSurname());
		res.setEmail(u.getEmail());
		res.setPhone(u.getPhone());
		res.setAddress(u.getAddress());

		return res;
	}

	public ActorAccountForm convertAdministratorToForm(final Administrator u) {
		final ActorAccountForm res = new ActorAccountForm();
		res.setId(u.getId());
		res.setName(u.getName());
		res.setSurname(u.getSurname());
		res.setEmail(u.getEmail());
		res.setPhone(u.getPhone());
		res.setAddress(u.getAddress());

		return res;
	}

	public User convertFormToUser(final ActorAccountForm u) {
		final User res = this.userService.findOne(u.getId());
		res.setId(u.getId());
		res.setName(u.getName());
		res.setSurname(u.getSurname());
		res.setEmail(u.getEmail());
		res.setPhone(u.getPhone());
		res.setAddress(u.getAddress());

		return res;
	}

	public Administrator convertFormToAdministrator(final ActorAccountForm u) {
		final Administrator res = this.administratorService.findOne(u.getId());
		res.setId(u.getId());
		res.setName(u.getName());
		res.setSurname(u.getSurname());
		res.setEmail(u.getEmail());
		res.setPhone(u.getPhone());
		res.setAddress(u.getAddress());

		return res;
	}

	public Customer convertFormToManager(final ActorAccountForm m) {
		final Customer res = this.customerService.findOne(m.getId());
		res.setId(m.getId());
		res.setName(m.getName());
		res.setSurname(m.getSurname());
		res.setEmail(m.getEmail());
		res.setPhone(m.getPhone());
		res.setAddress(m.getAddress());

		return res;
	}

}
