
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Administrator;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private Validator				validator;

	@Autowired
	private ArticleRepository		articleRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FollowUpService			followUpService;

	@Autowired
	private UserService				userService;

	@Autowired
	private NewspaperService		newspaperService;


	public ArticleService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Article create(final int newspaperId) {
		Article result;
		result = new Article();
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		//Assert.isTrue(!newspaper.getPublish());
		final User principal = this.userService.findByPrincipal();
		result.setUser(principal);
		result.setNewspaper(newspaper);
		result.setFollowUps(new ArrayList<FollowUp>());
		final Date moment = new Date(System.currentTimeMillis() - 1);
		result.setMoment(moment);
		result.setDraftMode(false);

		return result;
	}

	public Collection<Article> findAll() {
		Collection<Article> res;
		res = this.articleRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Article findOne(final Integer articleId) {
		final Article res = this.articleRepository.findOne(articleId);
		Assert.notNull(res);
		return res;
	}

	public Article save(final Article article) {
		final Newspaper newspaper = article.getNewspaper();
		article.setNewspaper(newspaper);
		if (article.getDraftMode() == null)
			article.setDraftMode(false);
		final Article res = this.articleRepository.save(article);
		if (article.getId() != 0) {
			final User principal = this.userService.findByPrincipal();
			final Collection<Article> articles = principal.getArticles();
			articles.add(res);
			principal.setArticles(articles);
			final Collection<Article> articlesNewspaper = newspaper.getArticles();
			articlesNewspaper.add(res);
			newspaper.setArticles(articlesNewspaper);
			this.newspaperService.onlySave(newspaper);
			this.userService.onlySave(principal);
		}
		return res;
	}

	public Article findOneToEdit(final Integer articleId) {
		final Article res = this.articleRepository.findOne(articleId);
		Assert.notNull(res);
		Assert.isTrue(res.getDraftMode());
		return res;
	}
	public void deleteAdmin(final int articleId) {
		final Article r = this.findOne(articleId);
		Assert.notNull(r);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		for (final FollowUp a : new HashSet<FollowUp>(r.getFollowUps()))
			this.followUpService.delete(a);

		final User u = r.getUser();
		u.getArticles().remove(r);
		this.userService.save(u);

		final Newspaper n = r.getNewspaper();
		n.getArticles().remove(r);
		this.newspaperService.save2(n);
		this.articleRepository.delete(r);
	}

	public void deleteAdmin1(final Article r) {
		//final Article r = this.findOne(articleId);
		Assert.notNull(r);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		for (final FollowUp a : new HashSet<FollowUp>(r.getFollowUps()))
			this.followUpService.delete(a);

		final User u = r.getUser();
		u.getArticles().remove(r);
		this.userService.save(u);

		final Newspaper n = r.getNewspaper();
		n.getArticles().remove(r);
		this.newspaperService.save2(n);
		this.articleRepository.delete(r);
	}
	public void flush() {
		this.articleRepository.flush();
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Article reconstruct(final Article article, final BindingResult binding) {
		Article result;

		if (article.getId() == 0)
			result = article;
		else {
			result = this.articleRepository.findOne(article.getId());

			result.setId(article.getId());
			result.setVersion(article.getVersion());
			result.setTitle(article.getTitle());
			result.setMoment(article.getMoment());
			result.setSummary(article.getSummary());
			result.setBody(article.getBody());
			result.setPictures(article.getPictures());
			result.setDraftMode(article.getDraftMode());
			result.setNewspaper(article.getNewspaper());
			result.setFollowUps(article.getFollowUps());
			result.setUser(article.getUser());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Article> searchArticle(final String keyword) {
		final Collection<Article> res = this.articleRepository.searchArticle(keyword);
		return res;
	}

	public Double avgFollowsUpPerArticle() {
		final Double res = this.articleRepository.avgFollowsUpPerArticle();
		return res;
	}

	public Collection<Article> articlePublishedByUser(final int userId) {
		final Collection<Article> res = this.articleRepository.articlePublishedByUser(userId);
		return res;
	}

}
