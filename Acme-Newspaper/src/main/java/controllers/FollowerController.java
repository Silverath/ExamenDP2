
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FollowerService;
import services.UserService;
import domain.Follower;
import domain.User;

@Controller
@RequestMapping("/follow")
public class FollowerController extends AbstractController {

	@Autowired
	private UserService		userService;

	@Autowired
	private FollowerService	followerService;


	public FollowerController() {
		super();
	}
	@RequestMapping(value = "/listFollow", method = RequestMethod.GET)
	public ModelAndView listFollow() {
		ModelAndView result;
		final Collection<Follower> follower = this.followerService.findByFollow(this.userService.findByPrincipal());
		final Collection<User> users = new ArrayList<User>();
		for (final Follower aux : follower)
			if (aux.getFollowed().getClass().equals(User.class)) {
				final User n = (User) aux.getFollowed();
				users.add(n);
			}
		result = new ModelAndView("follow/listFollow");
		result.addObject("users", users);

		return result;
	}

	@RequestMapping(value = "/listFollows", method = RequestMethod.GET)
	public ModelAndView regActor(@RequestParam final int actorId) {
		ModelAndView result;
		final User a = this.userService.findByPrincipal();
		try {

			final User e = this.userService.findOne(actorId);
			if (this.followerService.existsFollower(a, e)) {
				final Follower aux = this.followerService.findByFollowAndFollower(a, e);
				this.followerService.delete(aux);
				result = this.listUserFollows();
				result.addObject("message", "user.unfollow");
			} else {
				final Follower aux = this.followerService.create();
				aux.setFollowed(e);
				this.followerService.save(aux);
				result = this.listUserFollows();
				result.addObject("message", "user.follow");
			}
		} catch (final Throwable oops) {
			result = this.listUserFollows();
			result.addObject("message", "user.errorFollow");

		}

		return result;
	}

	@RequestMapping(value = "/listUserFollows", method = RequestMethod.GET)
	public ModelAndView listUserFollows() {
		ModelAndView result;
		final Collection<User> users = new ArrayList<User>();
		final Collection<Follower> follower = this.followerService.findByFollow(this.userService.findByPrincipal());
		for (final Follower aux : follower)
			if (aux.getFollowed().getClass().equals(User.class)) {
				final User n = (User) aux.getFollowed();
				users.add(n);
			}
		final Collection<User> followUser = new ArrayList<User>();

		for (final User aux : users)
			if (this.followerService.existsFollower(this.userService.findByPrincipal(), aux) == true)
				followUser.add(aux);
		result = new ModelAndView("follow/listFollow");
		result.addObject("users", users);
		result.addObject("followUser", followUser);
		result.addObject("loged", this.userService.findByPrincipal().getId());

		return result;
	}

	@RequestMapping(value = "/listUserFollowers", method = RequestMethod.GET)
	public ModelAndView listUserFollowers() {
		ModelAndView result;
		final Collection<User> users = new ArrayList<User>();
		final Collection<Follower> follower = this.followerService.findByFollower(this.userService.findByPrincipal());
		for (final Follower aux : follower)
			if (aux.getFollow().getClass().equals(User.class)) {
				final User n = (User) aux.getFollow();
				users.add(n);
			}
		final Collection<User> followUser = new ArrayList<User>();

		for (final User aux : users)
			if (this.followerService.existsFollower(this.userService.findByPrincipal(), aux) == true)
				followUser.add(aux);
		result = new ModelAndView("follow/listFollower");
		result.addObject("users", users);
		result.addObject("followUser", followUser);
		result.addObject("loged", this.userService.findByPrincipal().getId());

		return result;
	}
	@RequestMapping(value = "/listFollower", method = RequestMethod.GET)
	public ModelAndView listFollower() {
		ModelAndView result;
		final Collection<Follower> follower = this.followerService.findByFollower(this.userService.findByPrincipal());
		final Collection<User> users = new ArrayList<User>();
		for (final Follower aux : follower)
			if (aux.getFollow().getClass().equals(User.class)) {
				final User n = (User) aux.getFollow();
				users.add(n);
			}
		result = new ModelAndView("follow/listFollower");
		result.addObject("users", users);

		return result;
	}

	@RequestMapping(value = "/listFollowers", method = RequestMethod.GET)
	public ModelAndView regActor2(@RequestParam final int userId) {
		ModelAndView result;
		final User a = this.userService.findByPrincipal();
		try {

			final User e = this.userService.findOne(userId);
			if (this.followerService.existsFollower(a, e)) {
				final Follower aux = this.followerService.findByFollowAndFollower(a, e);
				this.followerService.delete(aux);
				result = this.listUserFollowers();
				result.addObject("message", "user.unfollow");
			} else {
				final Follower aux = this.followerService.create();
				aux.setFollowed(e);
				this.followerService.save(aux);
				result = this.listUserFollowers();
				result.addObject("message", "user.follow");
			}
		} catch (final Throwable oops) {
			result = this.listUserFollowers();
			result.addObject("message", "user.errorFollow");

		}
		return result;
	}

}
