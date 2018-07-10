
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.RendezvousService;
import domain.Category;
import domain.Rendezvous;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	//Services ----------------------------------------------------------
	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private AdministratorService	administratorService;


	//Listing ----------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;
		categories = this.categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");
		return result;
	}

	@RequestMapping(value = "/listChildren", method = RequestMethod.GET)
	public ModelAndView listChildren(@RequestParam final int categoryId) {
		ModelAndView result;
		Collection<Category> categories;
		categories = this.categoryService.findChildrenCategoriesByCategoryId(categoryId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");
		return result;
	}
	@RequestMapping(value = "/listRendezvousesByCategory", method = RequestMethod.GET)
	public ModelAndView listRendezvouses(@RequestParam final int categoryId) {
		final ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		Collection<Rendezvous> all;
		all = this.rendezvousService.findAll();
		rendezvouses = this.rendezvousService.sortedByCategory(categoryId);
		result = new ModelAndView("rendezvous/list");
		rendezvouses = this.rendezvousService.findNotAdult();
		all.retainAll(rendezvouses);
		final Collection<Rendezvous> filtered = all;
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("filtered", filtered);
		result.addObject("requestURI", "rendezvous/list.do");
		return result;
	}
}
