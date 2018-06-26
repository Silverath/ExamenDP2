
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import services.VolumeService;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;
import domain.Volume;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController extends AbstractController {

	@Autowired
	private SubscriptionService	subscriptionService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private VolumeService		volumeService;


	public SubscriptionController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Subscription> subscription;
		subscription = this.subscriptionService.findAll();

		result = new ModelAndView("subscription/list");
		result.addObject("subscription", subscription);
		result.addObject("requestURI", "subscription/list.do");

		return result;
	}

	// Creating ----------------------------------------------------------------	
	@RequestMapping(value = "/subscribeVolume", method = RequestMethod.GET)
	public ModelAndView createVolume() {
		ModelAndView result;
		Subscription subscription;
		subscription = this.subscriptionService.create();
		//		CreditCard creditCard;
		//		creditCard = new CreditCard();
		//		result = this.createCardModelAndView(creditCard);
		final Customer principal = this.customerService.findByPrincipal();
		final Collection<Volume> volumes = this.volumeService.findAllNotSubscribed(principal);
		result = new ModelAndView("subscription/edit2");
		result.addObject("subscription", subscription);
		result.addObject("volume", volumes);
		//result.addObject("creditCard", creditCard);
		return result;
	}

	@RequestMapping(value = "/editSubscribeVolume", method = RequestMethod.POST, params = "save")
	public ModelAndView saveVolume(@Valid final Subscription subscription, final BindingResult binding) {
		ModelAndView result;
		System.out.println(binding.getAllErrors());
		final Customer principal = this.customerService.findByPrincipal();
		final Collection<Volume> volumes = this.volumeService.findAllNotSubscribed(principal);
		if (binding.hasErrors()) {
			result = new ModelAndView("subscription/edit2");
			result.addObject(subscription);
			result.addObject("volume", volumes);
			result.addObject("message", "subscription.commit.error");
		} else
			try {
				this.subscriptionService.saveNewspaper(subscription);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("subscription/edit2");
				result.addObject("subscription", subscription);
				result.addObject("volume", volumes);
				result.addObject("message", "subscription.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndViewVolume(final Subscription subscription) {
		ModelAndView result;
		result = this.createEditModelAndView(subscription, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewVolume(final Subscription subscription, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("subscription/edit2");
		result.addObject("subscription", subscription);
		result.addObject("message", messageCode);
		final Customer principal = this.customerService.findByPrincipal();
		final Collection<Volume> volumes = this.volumeService.findAllNotSubscribed(principal);
		result.addObject("volume", volumes);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Subscription subscription;
		subscription = this.subscriptionService.create();
		//		CreditCard creditCard;
		//		creditCard = new CreditCard();
		//		result = this.createCardModelAndView(creditCard);
		final Collection<Newspaper> newspapers1 = this.newspaperService.findAllPrivate();
		final Collection<Newspaper> newspapers2 = this.newspaperService.filter(newspapers1);
		result = new ModelAndView("subscription/edit");
		result.addObject("subscription", subscription);
		result.addObject("newspaper", newspapers2);
		//result.addObject("creditCard", creditCard);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Subscription subscription, final BindingResult binding) {
		ModelAndView result;
		System.out.println(binding.getAllErrors());
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();
		if (binding.hasErrors()) {
			result = new ModelAndView("subscription/edit");
			result.addObject(subscription);
			result.addObject("newspaper", newspapers);
			result.addObject("message", "subscription.commit.error");
		} else
			try {
				this.subscriptionService.saveNewspaper(subscription);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("subscription/edit");
				result.addObject("subscription", subscription);
				result.addObject("newspaper", newspapers);
				result.addObject("message", "subscription.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Subscription subscription) {
		ModelAndView result;
		result = this.createEditModelAndView(subscription, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Subscription subscription, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("subscription/edit");
		result.addObject("subscription", subscription);
		result.addObject("message", messageCode);
		result.addObject("newspaper", this.newspaperService.findAll());
		return result;
	}
}
