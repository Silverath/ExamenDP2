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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AgentService;
import domain.Agent;

@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController {

	@Autowired
	private AgentService	agentService;


	//	@Autowired
	//	private ArticleService	articleService;
	//
	//	@Autowired
	//	private FollowerService	followerService;

	// Constructors -----------------------------------------------------------

	public AgentController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int agentId) {
		Assert.notNull(agentId);
		ModelAndView result;
		Agent r;
		r = this.agentService.findOne(agentId);
		result = new ModelAndView("user/display");
		result.addObject("agent", r);
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Agent> agents;
		agents = this.agentService.findAll();

		result = new ModelAndView("agent/list");
		result.addObject("agents", agents);
		result.addObject("requestURI", "agent/list.do");
		return result;
	}

}
