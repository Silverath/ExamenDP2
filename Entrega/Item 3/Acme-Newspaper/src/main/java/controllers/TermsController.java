
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/terms")
public class TermsController extends AbstractController {

	@RequestMapping("/terms")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("terms/terms");

		return result;
	}

	@RequestMapping("/privacy")
	public ModelAndView privacy() {
		ModelAndView result;

		result = new ModelAndView("terms/privacy");

		return result;
	}

	@RequestMapping("/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("terms/cookies");

		return result;
	}

}
