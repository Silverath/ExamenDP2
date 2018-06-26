
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
import services.ChirpService;
import services.UserService;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private UserService		userService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ChirpController() {
		super();
	}

	// Listing ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Chirp> chirps = this.chirpService.findAll();
		result = new ModelAndView("chirp/list");
		result.addObject("requestURI", "chirp/list.do");
		result.addObject("chirps", chirps);
		return result;
	}

	@RequestMapping(value = "/listChirpsPostedByMyFollows", method = RequestMethod.GET)
	public ModelAndView listChirpsPostedByMyFollows() {
		ModelAndView result;
		final User u = this.userService.findByPrincipal();
		final Collection<Chirp> chirps = this.chirpService.prueba();
		result = new ModelAndView("chirp/listChirpsPostedByMyFollows");
		result.addObject("requestURI", "chirp/listChirpsPostedByMyFollows.do");
		result.addObject("chirps", chirps);
		return result;
	}

	// Creating ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Chirp chirp = this.chirpService.create();
		result = new ModelAndView("chirp/edit");
		result.addObject("chirp", chirp);
		return result;
	}

	// Editing ---------------------------------------------------------------	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chirpId) {
		Assert.notNull(chirpId);
		ModelAndView result;
		final Chirp chirp = this.chirpService.findOne(chirpId);
		result = new ModelAndView("chirp/edit");
		result.addObject("chirp", chirp);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Chirp chirp, final BindingResult binding) {
		ModelAndView result;
		chirp = this.chirpService.reconstruct(chirp, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("chirp/edit");
			result.addObject(chirp);
		} else
			try {
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("chirp/edit");
				result.addObject("chirp", chirp);
				result.addObject("message", "chirp.commit.error");
			}

		return result;
	}

	// Deleting ---------------------------------------------------------------	

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteAdmin(final Chirp chirp, final BindingResult binding) {
		ModelAndView result;

		try {
			this.chirpService.deleteAdmin(chirp);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("chirp/edit");
			result.addObject("chirp", chirp);
			result.addObject("message", "chirp.commit.error");
		}

		return result;
	}

}
