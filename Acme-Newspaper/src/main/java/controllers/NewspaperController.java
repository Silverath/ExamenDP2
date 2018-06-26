
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.CustomerService;
import services.NewspaperService;
import services.UserService;
import services.VolumeService;
import domain.Actor;
import domain.Article;
import domain.Audit;
import domain.Customer;
import domain.Newspaper;
import domain.User;
import domain.Volume;
import forms.SearchForm;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private UserService			userService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private VolumeService		volumeService;

	@Autowired
	private AuditService		auditService;


	// Constructors -----------------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		try {
			this.customerService.findByPrincipal();
			newspapers = this.newspaperService.findAll();
			newspapers = this.newspaperService.getCustomersNewspapers(newspapers);
		} catch (final Throwable oops) {
			newspapers = this.newspaperService.newspaperPublished();
		}
		result = new ModelAndView("newspaper/list");
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("newspapers", newspapers);
		return result;
	}

	@RequestMapping(value = "/associatedAudits", method = RequestMethod.GET)
	public ModelAndView associatedAudits(@RequestParam final int newspaperId) {
		ModelAndView result;
		final Collection<Audit> audits = this.auditService.findAllPublicated();
		result = new ModelAndView("audit/list");
		result.addObject("requestURI", "audit/list.do");
		result.addObject("audits", audits);
		return result;
	}

	@RequestMapping(value = "/listVolumeNewspapersSubscribed", method = RequestMethod.GET)
	public ModelAndView listVolumeNewspapersSubscribed(@RequestParam final int volumeId) {
		ModelAndView result;
		final Volume volume = this.volumeService.findOne(volumeId);
		Collection<Newspaper> newspapers;
		final Customer principal = this.customerService.findByPrincipal();
		newspapers = this.newspaperService.findAllVolumeNewspapersSubscribed(principal, volume);
		result = new ModelAndView("newspaper/list");
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("newspapers", newspapers);
		return result;
	}
	@RequestMapping(value = "/listPublish", method = RequestMethod.GET)
	public ModelAndView listPublish() {
		ModelAndView result;
		final User u = (User) this.actorService.findByPrincipal();
		final Collection<Newspaper> newspapers = u.getNewspapers();
		result = new ModelAndView("newspaper/listPublish");
		result.addObject("requestURI", "newspaper/listPublish.do");
		result.addObject("newspapers", newspapers);
		return result;
	}

	@RequestMapping(value = "/listArticles", method = RequestMethod.GET)
	public ModelAndView listArticles(@RequestParam final int newspaperId) {
		ModelAndView result;
		Collection<Article> articles;
		articles = this.newspaperService.findOne(newspaperId).getArticles();
		result = new ModelAndView("newspaper/listArticles");
		result.addObject("articles", articles);
		result.addObject("requestURI", "newspaper/listArticles.do");
		return result;
	}

	@RequestMapping(value = "/listAdvertisements", method = RequestMethod.GET)
	public ModelAndView listAdvertisements() {
		ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.allAdvertisements();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		return result;
	}

	@RequestMapping(value = "/listWithoutAdvertisements", method = RequestMethod.GET)
	public ModelAndView listWithoutAdvertisements() {
		ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.notAdvertisements();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		return result;
	}

	// Displaying ---------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		Assert.notNull(newspaperId);
		ModelAndView result;
		final Newspaper n = this.newspaperService.findOne(newspaperId);
		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", n);
		result.addObject("articles", n.getArticles());
		return result;
	}

	// Creating ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Newspaper n = this.newspaperService.create();
		result = new ModelAndView("newspaper/edit");
		result.addObject("newspaper", n);
		return result;
	}

	// Editing ---------------------------------------------------------------	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int newspaperId) {
		Assert.notNull(newspaperId);
		ModelAndView result;
		final Newspaper n = this.newspaperService.findOne(newspaperId);
		result = new ModelAndView("newspaper/edit");
		result.addObject("newspaper", n);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Newspaper newspaper, final BindingResult binding) {
		ModelAndView result;
		final User publisher;
		Actor actor;
		if (binding.hasErrors())
			try {
				actor = this.actorService.findByPrincipal();
				publisher = this.userService.findUserByNewspaper(newspaper.getId());
				Assert.isTrue(actor.equals(publisher));
				result = new ModelAndView("newspaper/edit");
				result.addObject(newspaper);
			} catch (final Throwable oops) {
				result = new ModelAndView("newspaper/edit");
				result.addObject("newspaper", newspaper);
				result.addObject("message", "newspaper.commit.error");
			}
		else
			try {
				this.newspaperService.save(newspaper);
				result = new ModelAndView("redirect:listPublish.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("newspaper/edit");
				result.addObject("newspaper", newspaper);
				result.addObject("message", "newspaper.commit.error");
			}

		return result;
	}

	// Deleting ---------------------------------------------------------------	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int newspaperId) {
		ModelAndView result;
		try {
			this.newspaperService.deleteAdmin1(newspaperId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("newspaper/list");
			result.addObject("newspaper", newspaperId);
		}

		return result;
	}

	@RequestMapping(value = "/listSearch", method = RequestMethod.GET)
	public ModelAndView listSearch(@RequestParam final String keyword) {
		ModelAndView result;
		final Collection<Newspaper> newspapers = this.newspaperService.searchNewspaper(keyword);
		result = new ModelAndView("newspaper/list");
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("newspapers", newspapers);
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final SearchForm searchForm = new SearchForm();
		ModelAndView result;
		result = new ModelAndView("newspaper/search");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid final SearchForm searchForm) {
		ModelAndView result;
		result = new ModelAndView("redirect:listSearch.do?keyword=" + searchForm.getKeyword());

		return result;
	}

	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam final int newspaperId) {
		ModelAndView result;
		try {
			this.newspaperService.publish(this.newspaperService.findOne(newspaperId));
			result = this.listPublish();
		} catch (final Throwable oops) {
			result = this.listPublish();
			result.addObject("message", "newspaper.error.publish");
		}
		return result;

	}

	@RequestMapping(value = "/makePublic", method = RequestMethod.GET)
	public ModelAndView makePublic(@RequestParam final int newspaperId) {
		ModelAndView result;
		try {
			this.newspaperService.makePublic(this.newspaperService.findOne(newspaperId));
			result = this.listPublish();
		} catch (final Throwable oops) {
			result = this.listPublish();
			result.addObject("message", "newspaper.error.commit");
		}
		return result;

	}

	@RequestMapping(value = "/makePrivate", method = RequestMethod.GET)
	public ModelAndView makePrivate(@RequestParam final int newspaperId) {
		ModelAndView result;
		try {
			this.newspaperService.makePrivate(this.newspaperService.findOne(newspaperId));
			result = this.listPublish();
		} catch (final Throwable oops) {
			result = this.listPublish();
			result.addObject("message", "newspaper.commit.error");
		}
		return result;

	}

}
