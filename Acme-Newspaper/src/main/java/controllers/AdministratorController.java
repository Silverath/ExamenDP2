/*
 * AdministratorController.java
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AdvertisementService;
import services.ArticleService;
import services.ChirpService;
import services.ConfigurationService;
import services.CustomerService;
import services.FollowUpService;
import services.NewspaperService;
import services.UserService;
import services.VolumeService;
import domain.Article;
import domain.Chirp;
import domain.Configuration;
import domain.Newspaper;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	@Autowired
	private UserService				userService;
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private FollowUpService			followUpService;

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private VolumeService			volumeService;


	// Dashboads ---------------------------------------------------------------		

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");

		return result;
	}

	@RequestMapping(value = "/dashboard1", method = RequestMethod.GET)
	public ModelAndView dashboard1() {
		ModelAndView result;
		final Double avg = this.userService.avgNewspapersPerUser();
		final Double stddev = this.userService.stddevNewspapersPerUser();
		result = new ModelAndView("administrator/dashboard1");
		result.addObject("avg", avg);
		result.addObject("stddev", stddev);
		result.addObject("requestURI", "administrator/dashboard1.do");
		return result;
	}

	@RequestMapping(value = "/dashboard2", method = RequestMethod.GET)
	public ModelAndView dashboard2() {
		ModelAndView result;
		final Double avg = this.userService.avgArticlesPerUser();
		final Double stddev = this.userService.stddevArticlesPerUser();
		result = new ModelAndView("administrator/dashboard2");
		result.addObject("avg", avg);
		result.addObject("stddev", stddev);
		result.addObject("requestURI", "administrator/dashboard2.do");
		return result;
	}

	@RequestMapping(value = "/dashboard3", method = RequestMethod.GET)
	public ModelAndView dashboard3() {
		ModelAndView result;
		final Double avg = this.newspaperService.avgArticlesPerNewspaper();
		final Double stddev = this.newspaperService.stddevArticlesPerNewspaper();
		result = new ModelAndView("administrator/dashboard3");
		result.addObject("avg", avg);
		result.addObject("stddev", stddev);
		result.addObject("requestURI", "administrator/dashboard3.do");
		return result;
	}

	@RequestMapping(value = "/dashboard4", method = RequestMethod.GET)
	public ModelAndView dashboard4() {
		ModelAndView result;
		final Collection<Newspaper> rv = this.newspaperService.moreArticlesThanAverage();
		result = new ModelAndView("administrator/dashboard4");
		result.addObject("rv", rv);
		result.addObject("requestURI", "administrator/dashboard4.do");
		return result;
	}

	@RequestMapping(value = "/dashboard5", method = RequestMethod.GET)
	public ModelAndView dashboard5() {
		ModelAndView result;
		final Collection<Newspaper> rv = this.newspaperService.lessArticlesThanAverage();
		result = new ModelAndView("administrator/dashboard5");
		result.addObject("rv", rv);
		result.addObject("requestURI", "administrator/dashboard5.do");
		return result;
	}

	@RequestMapping(value = "/dashboard6", method = RequestMethod.GET)
	public ModelAndView dashboard6() {
		ModelAndView result;
		final Double ratio = this.userService.ratioNewspaperCreatedPerUser();
		result = new ModelAndView("administrator/dashboard6");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard6.do");
		return result;
	}

	@RequestMapping(value = "/dashboard7", method = RequestMethod.GET)
	public ModelAndView dashboard7() {
		ModelAndView result;
		final Double ratio = this.userService.ratioArticlesCreatedPerUser();
		result = new ModelAndView("administrator/dashboard7");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard7.do");
		return result;
	}

	@RequestMapping(value = "/dashboard8", method = RequestMethod.GET)
	public ModelAndView dashboard8() {
		ModelAndView result;
		final Double avg = this.articleService.avgFollowsUpPerArticle();
		result = new ModelAndView("administrator/dashboard8");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard8.do");
		return result;
	}

	@RequestMapping(value = "/dashboard9", method = RequestMethod.GET)
	public ModelAndView dashboard9() {
		final ModelAndView result;
		final Double avg = this.followUpService.getAverageFollowUpPerArticleOneWeek();
		result = new ModelAndView("administrator/dashboard9");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard9.do");
		return result;
	}
	@RequestMapping(value = "/dashboard10", method = RequestMethod.GET)
	public ModelAndView dashboard10() {
		final ModelAndView result;
		final Double avg = this.followUpService.getAverageFollowUpPerArticleTwoWeek();
		result = new ModelAndView("administrator/dashboard10");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard10.do");
		return result;
	}
	@RequestMapping(value = "/dashboard11", method = RequestMethod.GET)
	public ModelAndView dashboard11() {
		ModelAndView result;
		final Double avg = this.userService.avgChirpsPerUser();
		result = new ModelAndView("administrator/dashboard11");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard11.do");
		return result;
	}

	@RequestMapping(value = "/dashboard12", method = RequestMethod.GET)
	public ModelAndView dashboard12() {
		ModelAndView result;
		final Double ratio = this.userService.usersWithMoreAvgChirps();
		result = new ModelAndView("administrator/dashboard12");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard12.do");
		return result;
	}

	@RequestMapping(value = "/dashboard13", method = RequestMethod.GET)
	public ModelAndView dashboard13() {
		ModelAndView result;
		final Double ratio = this.newspaperService.ratioPublicVersusPrivate();
		result = new ModelAndView("administrator/dashboard13");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard13.do");
		return result;
	}

	@RequestMapping(value = "/dashboard14", method = RequestMethod.GET)
	public ModelAndView dashboard14() {
		ModelAndView result;
		final Double avg = this.newspaperService.avgArticlesPerPrivateNewspaper();
		result = new ModelAndView("administrator/dashboard14");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard14.do");
		return result;
	}

	@RequestMapping(value = "/dashboard15", method = RequestMethod.GET)
	public ModelAndView dashboard15() {
		ModelAndView result;
		final Double avg = this.newspaperService.avgArticlesPerPublicNewspaper();
		result = new ModelAndView("administrator/dashboard15");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard15.do");
		return result;
	}

	@RequestMapping(value = "/dashboard16", method = RequestMethod.GET)
	public ModelAndView dashboard16() {
		ModelAndView result;
		final Double ratio = this.customerService.ratioSubscribersPrivateNewspaperCustomer();
		result = new ModelAndView("administrator/dashboard16");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard16.do");
		return result;
	}

	@RequestMapping(value = "/dashboard17", method = RequestMethod.GET)
	public ModelAndView dashboard17() {
		ModelAndView result;
		final Double avg = this.newspaperService.avgArticlesPerPublicNewspaper();
		result = new ModelAndView("administrator/dashboard17");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard17.do");
		return result;
	}

	@RequestMapping(value = "/dashboard18", method = RequestMethod.GET)
	public ModelAndView dashboard18() {
		final ModelAndView result;
		final Double ratio = this.newspaperService.ratioWithAtLeastOneAdvertisiment();
		result = new ModelAndView("administrator/dashboard18");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard18.do");
		return result;
	}

	@RequestMapping(value = "/dashboard19", method = RequestMethod.GET)
	public ModelAndView dashboard19() {
		final ModelAndView result;
		final Double ratio = this.advertisementService.ratioAdvertisementsWithTaboo();
		result = new ModelAndView("administrator/dashboard19");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard19.do");
		return result;
	}

	@RequestMapping(value = "/dashboard20", method = RequestMethod.GET)
	public ModelAndView dashboard20() {
		final ModelAndView result;
		final Double avg = this.volumeService.avgNewspaperPerVolume();
		result = new ModelAndView("administrator/dashboard20");
		result.addObject("avg", avg);
		result.addObject("requestURI", "administrator/dashboard20.do");
		return result;
	}

	@RequestMapping(value = "/dashboard21", method = RequestMethod.GET)
	public ModelAndView dashboard21() {
		final ModelAndView result;
		final Double ratio = this.volumeService.ratioSubscriptionsVolumesNewspapers();
		result = new ModelAndView("administrator/dashboard21");
		result.addObject("ratio", ratio);
		result.addObject("requestURI", "administrator/dashboard21.do");
		return result;
	}
	@RequestMapping(value = "/listTabooWord", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		result = new ModelAndView("administrator/listTabooWord");
		result.addObject("tabooWords", tabooWords);
		result.addObject("requestURI", "administrator/listTabooWord.do");
		return result;
	}

	@RequestMapping(value = "/addTabooWord", method = RequestMethod.GET)
	public ModelAndView add() {
		final ModelAndView result = new ModelAndView("administrator/addTabooWord");
		final String s = "";
		result.addObject("tabooWord", s);
		return result;
	}

	@RequestMapping(value = "/addTabooWord", method = RequestMethod.POST, params = "save")
	public ModelAndView saveWord(@RequestParam("tabooWord") final String word) {
		ModelAndView result;
		if (word == "") {
			result = new ModelAndView("administrator/addTabooWord");
			result.addObject("message", "administrator.empty.taboo");
		} else
			try {
				final Configuration c = this.configurationService.findOne();
				c.getTaboo().add(word);
				this.configurationService.save(c);
				final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
				result = new ModelAndView("administrator/listTabooWord");
				result.addObject("tabooWords", tabooWords);
				result.addObject("requestURI", "administrator/listTabooWord.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/addTabooWord");
				result.addObject("message", "configuration.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listTabooArticles", method = RequestMethod.GET)
	public ModelAndView listTabooArticles() {
		ModelAndView result;
		Collection<Article> articles = this.articleService.findAll();
		articles = this.administratorService.ArticlesWithTabooWords(articles);
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/list.do");
		return result;
	}

	@RequestMapping(value = "/listTabooNewspapers", method = RequestMethod.GET)
	public ModelAndView listTabooNewspapers() {
		ModelAndView result;
		Collection<Newspaper> newspapers = this.newspaperService.findAll();
		newspapers = this.administratorService.newspapersWithTabooWords(newspapers);
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		return result;
	}

	@RequestMapping(value = "/listTabooChirps", method = RequestMethod.GET)
	public ModelAndView listTabooChirps() {
		ModelAndView result;
		Collection<Chirp> chirps = this.chirpService.findAll();
		chirps = this.administratorService.chirpsWithTabooWords(chirps);
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");
		return result;
	}
}
