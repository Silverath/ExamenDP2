
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
import services.AdvertisementService;
import services.ArticleService;
import services.UserService;
import domain.Actor;
import domain.Advertisement;
import domain.Article;
import domain.User;
import forms.SearchForm;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdvertisementService	advertisementService;


	public ArticleController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Article> articles = this.articleService.findAll();
		result = new ModelAndView("article/list");
		result.addObject("requestURI", "article/list.do");
		result.addObject("articles", articles);
		return result;
	}

	@RequestMapping(value = "/listSearch", method = RequestMethod.GET)
	public ModelAndView listSearch(@RequestParam final String keyword) {
		ModelAndView result;
		final Collection<Article> articles = this.articleService.searchArticle(keyword);
		result = new ModelAndView("article/listSearch");
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("articles", articles);
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final SearchForm searchForm = new SearchForm();
		ModelAndView result;
		result = new ModelAndView("article/search");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid final SearchForm searchForm) {
		ModelAndView result;
		result = new ModelAndView("redirect:listSearch.do?keyword=" + searchForm.getKeyword());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		Assert.notNull(newspaperId);
		ModelAndView result;
		final Article article = this.articleService.create(newspaperId);
		result = this.createEditModelAndView(article);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		Assert.notNull(articleId);
		ModelAndView result;
		final Article article = this.articleService.findOneToEdit(articleId);
		result = this.createEditModelAndView(article);
		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Article article, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		User publisher;
		if (binding.hasErrors())
			result = this.createEditModelAndView(article);
		else
			try {
				actor = this.actorService.findByPrincipal();
				publisher = this.userService.findUserByNewspaper(article.getNewspaper().getId());
				Assert.isTrue(actor.equals(publisher));
				this.articleService.save(article);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("article/edit");
				result.addObject("article", article);
				result.addObject("message", "article.commit.error");
			}

		return result;
	}

	// Displaying ---------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		Assert.notNull(articleId);
		ModelAndView result;
		Advertisement adv = null;
		final Article a = this.articleService.findOne(articleId);
		if (!a.getNewspaper().getAdvertisements().isEmpty())
			adv = this.advertisementService.getRandomAdvertisement(a.getNewspaper().getAdvertisements());
		result = new ModelAndView("article/display");
		result.addObject("advertisement", adv);
		result.addObject("article", a);
		return result;
	}

	@RequestMapping(value = "/displayUser", method = RequestMethod.GET)
	public ModelAndView displayUser(@RequestParam final int articleId) {
		Assert.notNull(articleId);
		ModelAndView result;
		User r = this.articleService.findOne(articleId).getUser();
		r = this.userService.findOne(r.getId());
		result = new ModelAndView("user/display");
		result.addObject("user", r);
		return result;

	}

	// Deleting ---------------------------------------------------------------	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int articleId) {
		ModelAndView result;
		try {
			this.articleService.deleteAdmin(articleId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("article/list");
			result.addObject("article", articleId);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;

		result = this.createEditModelAndView(article, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Article article, final String message) {
		ModelAndView result;

		result = new ModelAndView("article/edit");
		result.addObject("article", article);
		result.addObject("message", message);
		result.addObject("requestURI", "article/edit.do");

		return result;
	}
}
