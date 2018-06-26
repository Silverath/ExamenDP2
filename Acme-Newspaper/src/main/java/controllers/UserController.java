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

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.FollowerService;
import services.UserService;
import domain.Article;
import domain.Follower;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService		userService;

	@Autowired
	private ArticleService	articleService;

	@Autowired
	private FollowerService	followerService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		Assert.notNull(userId);
		ModelAndView result;
		User r;
		r = this.userService.findOne(userId);
		final Collection<Article> articles = r.getArticles();
		result = new ModelAndView("user/display");
		result.addObject("articles", articles);
		result.addObject("user", r);
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;
		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestURI", "user/list.do");
		try {
			result.addObject("loged", this.userService.findByPrincipal().getId());
			final Collection<User> followUser = new ArrayList<User>();
			users = this.userService.findAll();
			for (final User aux : users)
				if (this.followerService.existsFollower(this.userService.findByPrincipal(), aux) == true)
					followUser.add(aux);
			result.addObject("followUser", followUser);
		} catch (final Throwable oops) {

		}
		return result;
	}

	@RequestMapping(value = "/listArticles", method = RequestMethod.GET)
	public ModelAndView listArticles(@RequestParam final int userId) {
		final ModelAndView result;
		Collection<Article> articles;
		articles = this.articleService.articlePublishedByUser(userId);
		result = new ModelAndView("user/listArticles");
		result.addObject("articles", articles);
		result.addObject("requestURI", "user/listArticles.do");
		return result;
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam final int userId) {
		ModelAndView result;
		final User a = this.userService.findByPrincipal();
		try {

			final User e = this.userService.findOne(userId);
			if (this.followerService.existsFollower(a, e)) {
				final Follower aux = this.followerService.findByFollowAndFollower(a, e);
				this.followerService.delete(aux);
				result = this.display(e.getId());
			} else {
				final Follower aux = this.followerService.create();
				aux.setFollowed(e);
				this.followerService.save(aux);
				result = this.list();
			}
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "user.errorFollow");

		}

		return result;
	}

}
