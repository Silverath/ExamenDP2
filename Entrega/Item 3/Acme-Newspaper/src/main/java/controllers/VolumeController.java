
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
import services.NewspaperService;
import services.UserService;
import services.VolumeService;
import domain.Actor;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Controller
@RequestMapping("/volume")
public class VolumeController extends AbstractController {

	@Autowired
	private VolumeService			volumeService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private NewspaperService		newspaperService;

	private Collection<Newspaper>	aux;


	// Constructors -----------------------------------------------------------

	public VolumeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Volume> volumes = this.volumeService.findAll();
		result = new ModelAndView("volume/list");
		result.addObject("requestURI", "volume/list.do");
		result.addObject("volumes", volumes);
		return result;
	}

	@RequestMapping(value = "/listNewspapers", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int volumeId) {
		ModelAndView result;
		final Volume volume = this.volumeService.findOne(volumeId);
		final Collection<Newspaper> newspapers = this.volumeService.findAllPublicNewspapers();
		final Collection<Newspaper> volumeNewspapers = volume.getNewspapers();
		volumeNewspapers.retainAll(newspapers);
		result = new ModelAndView("newspaper/list");
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("newspapers", volumeNewspapers);
		return result;
	}

	// Creating ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Volume v = this.volumeService.create();
		final Collection<Newspaper> news = this.newspaperService.findAll();
		result = new ModelAndView("volume/edit");
		result.addObject("volume", v);
		result.addObject("news", news);
		return result;
	}

	// Editing ---------------------------------------------------------------	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int volumeId) {
		Assert.notNull(volumeId);
		ModelAndView result;
		final Boolean option = true;
		this.aux = this.volumeService.findOne(volumeId).getNewspapers();
		final Collection<Newspaper> news = this.volumeService.filter(volumeId);
		final Volume v = this.volumeService.findOne(volumeId);
		result = new ModelAndView("volume/addOrRemove");
		result.addObject("volume", v);
		result.addObject("option", option);
		result.addObject("news", news);
		return result;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int volumeId) {
		Assert.notNull(volumeId);
		ModelAndView result;
		final Boolean option = false;
		this.aux = this.volumeService.findOne(volumeId).getNewspapers();
		final Collection<Newspaper> news = this.volumeService.findOne(volumeId).getNewspapers();
		final Volume v = this.volumeService.findOne(volumeId);
		result = new ModelAndView("volume/addOrRemove");
		result.addObject("volume", v);
		result.addObject("option", option);
		result.addObject("news", news);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;
		final User publisher;
		final Actor actor;
		final Collection<Newspaper> news = this.newspaperService.findAll();
		if (binding.hasErrors())
			try {
				actor = this.actorService.findByPrincipal();
				publisher = this.userService.findUserByVolume(volume.getId());
				Assert.isTrue(actor.equals(publisher));
				result = new ModelAndView("volume/edit");
				result.addObject(volume);
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}
		else
			try {
				this.volumeService.save(volume);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/addNews", method = RequestMethod.POST, params = "save")
	public ModelAndView addNews(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;
		final User publisher;
		final Actor actor;
		final Collection<Newspaper> news = this.newspaperService.findAll();
		if (binding.hasErrors())
			try {
				actor = this.actorService.findByPrincipal();
				publisher = this.userService.findUserByVolume(volume.getId());
				Assert.isTrue(actor.equals(publisher));
				result = new ModelAndView("volume/edit");
				result.addObject(volume);
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}
		else
			try {
				this.volumeService.addNews(volume, this.aux);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/removeNews", method = RequestMethod.POST, params = "remove")
	public ModelAndView removeNews(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;
		final User publisher;
		final Actor actor;
		final Collection<Newspaper> news = this.aux;
		if (binding.hasErrors())
			try {
				actor = this.actorService.findByPrincipal();
				publisher = this.userService.findUserByVolume(volume.getId());
				Assert.isTrue(actor.equals(publisher));
				result = new ModelAndView("volume/edit");
				result.addObject(volume);
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}
		else
			try {
				this.volumeService.deleteNews(volume, this.aux);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("volume/edit");
				result.addObject("volume", volume);
				result.addObject("news", news);
				result.addObject("message", "volume.commit.error");
			}

		return result;
	}

}
