
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
import services.AuditService;
import services.NewspaperService;
import domain.Administrator;
import domain.Audit;
import domain.Newspaper;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	private AuditService			auditService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private NewspaperService		newspaperService;


	public AuditController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits = new ArrayList<Audit>();
		audits = this.auditService.findAllPublicated();
		result = new ModelAndView("audit/list");
		result.addObject("requestURI", "audit/list.do");
		result.addObject("audits", audits);
		return result;
	}

	@RequestMapping(value = "/listMine", method = RequestMethod.GET)
	public ModelAndView listMine() {
		ModelAndView result;
		Collection<Audit> audits = new ArrayList<Audit>();
		final Administrator principal = this.adminService.findByPrincipal();
		audits = this.auditService.findAuditsByAdmin(principal.getId());
		result = new ModelAndView("audit/list");
		result.addObject("requestURI", "audit/listMine.do");
		result.addObject("audits", audits);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Audit audit = this.auditService.create();
		result = this.createEditModelAndView(audit);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		Assert.notNull(auditId);
		ModelAndView result;
		final Audit audit = this.auditService.findOneToEdit(auditId);
		result = this.createEditModelAndView(audit);
		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("audit/edit");
				result.addObject("audit", audit);
				result.addObject("auditId", audit.getId());
				result.addObject("message", "audit.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/selectNewspaper", method = RequestMethod.GET)
	public ModelAndView selectNewspaper(@RequestParam final int auditId) {
		Assert.notNull(auditId);
		ModelAndView result;
		final Audit audit = this.auditService.findOneToSelectNewspaper(auditId);
		result = this.selectNewspaperModelAndView(audit);
		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/selectNewspaper", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSelectNewspaper(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.onlySave(audit);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("audit/selectNewspaper");
				result.addObject("audit", audit);
				result.addObject("message", "audit.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int auditId) {
		ModelAndView result;
		try {
			this.auditService.deleteAdmin(auditId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("audit/list");
			result.addObject("article", auditId);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		ModelAndView result;

		result = new ModelAndView("audit/edit");
		final Collection<Newspaper> select = this.newspaperService.findAll();
		result.addObject("audit", audit);
		result.addObject("select", select);
		result.addObject("message", message);
		result.addObject("requestURI", "audit/edit.do");

		return result;
	}

	protected ModelAndView selectNewspaperModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.selectNewspaperModelAndView(audit, null);

		return result;
	}
	protected ModelAndView selectNewspaperModelAndView(final Audit audit, final String message) {
		ModelAndView result;
		final Collection<Newspaper> select = this.newspaperService.findAll();
		result = new ModelAndView("audit/selectNewspaper");
		result.addObject("audit", audit);
		result.addObject("select", select);
		result.addObject("message", message);
		result.addObject("requestURI", "audit/selectNewspaper.do");

		return result;
	}
}
