
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private Validator			validator;

	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	public CustomerService() {
		super();
		// TODO Auto-generated constructor stub
	}
	//---------------------------------------------- Create ----------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();

		return result;
	}

	public Customer create(final String name, final String surname, final String email, final String phone, final String address) {
		final Customer res = new Customer();
		res.setName(name);
		res.setSurname(surname);
		res.setEmail(email);
		res.setPhone(phone);
		res.setAddress(address);

		return res;
	}

	public Customer registerCustomer(final UserAccount userAccount, final Customer c) {
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		userAccount.addAuthority(au);
		c.setUserAccount(userAccount);
		//this.save(c);

		this.folderService.createFolderforRegisterActor(c);
		this.save(c);

		final Actor re = this.actorService.findActorByUserAccountName(userAccount.getUsername());
		this.folderService.createFolderforRegisterActor(re);
		this.folderService.saveFolderForActor(re);
		return c;
	}

	public Collection<Customer> findAll() {
		final Collection<Customer> res = this.customerRepository.findAll();
		Assert.notNull(res);
		return res;

	}
	public Customer findOne(final int userId) {
		final Customer t = this.customerRepository.findOne(userId);
		return t;

	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		final Customer res = this.customerRepository.save(customer);
		return res;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		this.customerRepository.delete(customer);
	}

	public Customer findByPrincipal() {
		Customer customer;
		UserAccount result;
		result = LoginService.getPrincipal();
		Assert.notNull(result);
		customer = this.findByUserAccount(result);
		Assert.notNull(customer);
		return customer;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Customer result;
		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Double ratioSubscribersPrivateNewspaperCustomer() {
		final Double res = this.customerRepository.ratioSubscribersPrivateNewspaperCustomer();
		return res;
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Customer reconstruct(final Customer customer, final BindingResult binding) {
		Customer result;

		if (customer.getId() == 0)
			result = customer;
		else {
			result = this.customerRepository.findOne(customer.getId());

			result.setId(customer.getId());
			result.setVersion(customer.getVersion());
			result.setName(customer.getName());
			result.setSurname(customer.getSurname());
			result.setEmail(customer.getEmail());
			result.setPhone(customer.getPhone());
			result.setAddress(customer.getAddress());
			result.setUserAccount(customer.getUserAccount());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.customerRepository.flush();

	}

}
