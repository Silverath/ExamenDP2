/*
 * UserController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.NewspaperService;
import domain.Advertisement;
import domain.Newspaper;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController extends AbstractController {

	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private NewspaperService		newspaperService;


	// Constructors -----------------------------------------------------------

	public AdvertisementController() {
		super();
	}

	// Listing ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Advertisement> advertisements;

		advertisements = this.advertisementService.findAll();

		result = new ModelAndView("advertisement/list");
		result.addObject("advertisements", advertisements);
		result.addObject("requestURI", "advertisement/list.do");

		return result;
	}

	@RequestMapping(value = "/listTaboo", method = RequestMethod.GET)
	public ModelAndView listTaboo() {
		ModelAndView result;
		final Collection<Advertisement> advertisements;

		advertisements = this.advertisementService.adsWithTaboo();

		result = new ModelAndView("advertisement/listTaboo");
		result.addObject("advertisements", advertisements);
		result.addObject("requestURI", "advertisement/listTaboo.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Collection<Newspaper> newspapers;
		final Advertisement a = this.advertisementService.create();
		newspapers = this.newspaperService.findAll();
		result = new ModelAndView("advertisement/edit");
		result.addObject("newspapers", newspapers);
		result.addObject("advertisement", a);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Advertisement advertisement, final BindingResult binding) {
		ModelAndView result;
		advertisement = this.advertisementService.reconstruct(advertisement, binding);
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();
		if (binding.hasErrors()) {
			result = new ModelAndView("advertisement/edit");
			result.addObject("advertisement", advertisement);
			result.addObject("newspapers", newspapers);
			//result.addObject("message", "advertisement.commit.error");
		} else
			try {
				this.advertisementService.validMonthCreditCard(advertisement);
				this.advertisementService.save(advertisement);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = new ModelAndView("advertisement/edit");
				result.addObject("advertisement", advertisement);
				result.addObject("newspapers", newspapers);
				result.addObject("message", "advertisement.commit.error");
			}

		return result;
	}
	//	@RequestMapping(value = "/listTabu", method = RequestMethod.GET)
	//	public ModelAndView listTabu() {
	//		final ModelAndView result;
	//		Collection<Article> articles;
	//		articles = this.articleService.articlePublishedByUser(userId);
	//		result = new ModelAndView("user/listArticles");
	//		result.addObject("articles", articles);
	//		result.addObject("requestURI", "user/listArticles.do");
	//		return result;
	//	}

	// Deleting ---------------------------------------------------------------	
	@RequestMapping(value = "/deleteAdminTaboo", method = RequestMethod.GET)
	public ModelAndView deleteAdminTaboo(@RequestParam final int advertisementId) {
		ModelAndView result;
		try {
			this.advertisementService.deleteAdmin(advertisementId);
			result = new ModelAndView("redirect:listTaboo.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("advertisement/listTaboo");
			result.addObject("article", advertisementId);
		}

		return result;
	}

	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.GET)
	public ModelAndView deleteAdmin(@RequestParam final int advertisementId) {
		ModelAndView result;
		try {
			this.advertisementService.deleteAdmin(advertisementId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("advertisement/list");
			result.addObject("article", advertisementId);
		}

		return result;
	}
}
