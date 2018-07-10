
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuestionService;
import domain.Question;

@Controller
@RequestMapping("/question")
public class QuestionController extends AbstractController {

	@Autowired
	private QuestionService	questionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {

		ModelAndView result;
		Collection<Question> questions;

		questions = this.questionService.findAllByRendezvous(rendezvousId);

		result = new ModelAndView("question/list");
		result.addObject("questions", questions);
		result.addObject("requestURI", "question/list.do");

		return result;
	}

	@RequestMapping(value = "/answers", method = RequestMethod.GET)
	public ModelAndView answers(@RequestParam final int questionId) {

		ModelAndView result;
		final Question question = this.questionService.findOne(questionId);

		result = new ModelAndView("question/answers");
		result.addObject("answers", question.getAnswers());
		result.addObject("requestURI", "question/answers.do");

		return result;
	}
}
