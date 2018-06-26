
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Service
@Transactional
public class FollowUpService {

	@Autowired
	private Validator			validator;

	@Autowired
	private FollowUpRepository	followUpRepository;


	public FollowUpService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public FollowUp create() {
		FollowUp result;
		result = new FollowUp();

		result.setPictures(new ArrayList<String>());

		return result;
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public FollowUp reconstruct(final FollowUp followUp, final BindingResult binding) {
		FollowUp result;

		if (followUp.getId() == 0)
			result = followUp;
		else {
			result = this.followUpRepository.findOne(followUp.getId());

			result.setId(followUp.getId());
			result.setVersion(followUp.getVersion());
			result.setTitle(followUp.getTitle());
			result.setMoment(followUp.getMoment());
			result.setSummary(followUp.getSummary());
			result.setBody(followUp.getBody());
			result.setPictures(followUp.getPictures());
			result.setArticle(followUp.getArticle());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> res;

		res = this.followUpRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	public FollowUp findOne(final Integer followUpId) {
		final FollowUp a = this.followUpRepository.findOne(followUpId);
		return a;
	}

	public FollowUp save(final FollowUp c) {
		Assert.notNull(c);
		final FollowUp res = this.followUpRepository.save(c);
		return res;
	}

	public void delete(final FollowUp c) {
		Assert.notNull(c);
		this.followUpRepository.delete(c);
	}
	public void flush() {
		this.followUpRepository.flush();
	}

	public Double getAverageFollowUpPerArticleOneWeek() {
		final Double res = this.followUpRepository.avgFollowUpPerArticleOneWeek();
		return res;
	}

	public Double getAverageFollowUpPerArticleTwoWeek() {
		final Double res = this.followUpRepository.avgFollowUpPerArticleOneWeek();
		return res;
	}
}
