
package controllers;

import java.util.ArrayList;
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
import services.AdministratorService;
import services.MoletService;
import services.NewspaperService;
import domain.Administrator;
import domain.Molet;
import domain.Newspaper;

@Controller
@RequestMapping("/molet")
public class MoletController extends AbstractController {

	@Autowired
	private MoletService			moletService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private NewspaperService		newspaperService;


	public MoletController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Molet> molets = new ArrayList<Molet>();
		molets = this.moletService.findAllPublicated();
		result = new ModelAndView("molet/list");
		result.addObject("requestURI", "molet/list.do");
		result.addObject("molets", molets);
		return result;
	}

	@RequestMapping(value = "/listMine", method = RequestMethod.GET)
	public ModelAndView listMine() {
		ModelAndView result;
		Collection<Molet> molets = new ArrayList<Molet>();
		final Administrator principal = this.adminService.findByPrincipal();
		molets = this.moletService.findMoletsByAdmin(principal.getId());
		result = new ModelAndView("molet/list");
		result.addObject("requestURI", "molet/listMine.do");
		result.addObject("molets", molets);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Molet molet = this.moletService.create();
		result = this.createEditModelAndView(molet);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int moletId) {
		Assert.notNull(moletId);
		ModelAndView result;
		final Molet molet = this.moletService.findOneToEdit(moletId);
		result = this.createEditModelAndView(molet);
		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/selectNewspaper", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSelectNewspaper(@Valid final Molet molet, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.selectNewspaperModelAndView(molet);
		else
			try {
				this.moletService.onlySave(molet);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("molet/selectNewspaper");
				result.addObject("molet", molet);
				result.addObject("message", "molet.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Molet molet, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(molet);
		else
			try {
				this.moletService.save(molet);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("molet/edit");
				result.addObject("molet", molet);
				result.addObject("moletId", molet.getId());
				result.addObject("message", "molet.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/selectNewspaper", method = RequestMethod.GET)
	public ModelAndView selectNewspaper(@RequestParam final int moletId) {
		Assert.notNull(moletId);
		ModelAndView result;
		final Molet molet = this.moletService.findOneToSelectNewspaper(moletId);
		Assert.isTrue(molet.getFinalMode());
		result = this.selectNewspaperModelAndView(molet);
		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int moletId) {
		ModelAndView result;
		try {
			this.moletService.deleteAdmin(moletId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("molet/list");
			result.addObject("article", moletId);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Molet molet) {
		ModelAndView result;

		result = this.createEditModelAndView(molet, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Molet molet, final String message) {
		ModelAndView result;

		result = new ModelAndView("molet/edit");
		final Collection<Newspaper> select = this.newspaperService.findAll();
		result.addObject("molet", molet);
		result.addObject("select", select);
		result.addObject("message", message);
		result.addObject("requestURI", "molet/edit.do");

		return result;
	}

	protected ModelAndView selectNewspaperModelAndView(final Molet molet) {
		ModelAndView result;

		result = this.selectNewspaperModelAndView(molet, null);

		return result;
	}
	protected ModelAndView selectNewspaperModelAndView(final Molet molet, final String message) {
		ModelAndView result;
		final Collection<Newspaper> select = this.newspaperService.findAll();
		result = new ModelAndView("molet/selectNewspaper");
		result.addObject("molet", molet);
		result.addObject("select", select);
		result.addObject("message", message);
		result.addObject("requestURI", "molet/selectNewspaper.do");

		return result;
	}
}
