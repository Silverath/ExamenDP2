
package controllers.administrator;

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

import services.AdministratorService;
import services.BenefitService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Benefit;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services ----------------------------------------------------------
	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private BenefitService			benefitService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category c;
		c = this.categoryService.create();
		final Collection<Category> cats = this.categoryService.findAll();
		final Collection<Benefit> benefits = this.benefitService.findAll();
		result = new ModelAndView("category/edit");
		result.addObject("category", c);
		result.addObject("parent", cats);
		result.addObject("benefits", benefits);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		Assert.notNull(categoryId);
		ModelAndView result;
		Category c;
		c = this.categoryService.findOne(categoryId);
		final Collection<Category> cat = this.categoryService.findAll();
		final Collection<Benefit> benefits = this.benefitService.findAll();
		cat.remove(c);
		result = new ModelAndView("category/edit");
		result.addObject("category", c);
		result.addObject("parent", cat);
		result.addObject("benefits", benefits);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("category/edit");
			result.addObject(category);
		} else
			try {
				final Category res = this.categoryService.save(category);
				if (res.getParent() != null)
					this.categoryService.saveCategoryOnParent(res);
				result = new ModelAndView("redirect:../../category/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("message", "category.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("category/edit");
		else
			try {
				this.categoryService.delete(category);
				result = new ModelAndView("redirect:../../category/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("message", "category.commit.error");
			}

		return result;
	}

	// Ancillary methods -----------------------------------------
	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;
		result = this.createEditModelAndView(category, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");
		result.addObject("category", category);
		result.addObject("message", message);
		return result;
	}
}
