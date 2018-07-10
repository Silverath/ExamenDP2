
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.Customer;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private Validator				validator;

	@Autowired
	private SubscriptionRepository	subscriptionRepository;

	@Autowired
	private CustomerService			customerService;


	public SubscriptionService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Subscription create() {
		Subscription result;
		result = new Subscription();
		final Customer e = this.customerService.findByPrincipal();
		Assert.notNull(e);
		result.setCustomer(e);

		return result;
	}

	public Collection<Subscription> findAll() {
		final Collection<Subscription> res = this.subscriptionRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public Subscription findOne(final int subscriptionId) {
		//	Explorer b = explorerService.findByPrincipal();
		//	Assert.notNull(b);
		final Subscription t = this.subscriptionRepository.findOne(subscriptionId);
		//	Assert.isTrue(b.getApplication().contains(t));
		return t;

	}
	public Subscription saveNewspaper(final Subscription s) {
		final Customer m = this.customerService.findByPrincipal();
		Assert.notNull(m);
		Assert.notNull(s);
		final Subscription res = this.subscriptionRepository.save(s);
		Assert.notNull(res.getVolume());
		return res;
	}

	public Subscription saveVolume(final Subscription s) {
		final Customer m = this.customerService.findByPrincipal();
		Assert.notNull(m);
		Assert.notNull(s);
		final Subscription res = this.subscriptionRepository.save(s);
		Assert.notNull(res.getVolume());
		return res;
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Subscription reconstruct(final Subscription subscription, final BindingResult binding) {
		Subscription result;

		if (subscription.getId() == 0)
			result = subscription;
		else {
			result = this.subscriptionRepository.findOne(subscription.getId());

			result.setId(subscription.getId());
			result.setVersion(subscription.getVersion());
			result.setCreditCard(subscription.getCreditCard());
			result.setCustomer(subscription.getCustomer());
			result.setNewspaper(subscription.getNewspaper());
			result.setVolume(subscription.getVolume());
		}

		this.validator.validate(result, binding);

		return result;
	}

}
