
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

import repositories.NewspaperRepository;
import domain.Administrator;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class NewspaperService {

	@Autowired
	private Validator				validator;

	@Autowired
	private NewspaperRepository		newspaperRepository;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;


	public NewspaperService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Newspaper create() {
		final Newspaper result = new Newspaper();
		final User userPrincipal = this.userService.findByPrincipal();
		result.setPublish(false);
		result.setAuthor(userPrincipal);
		result.setArticles(new HashSet<Article>());
		result.setSubscriptions(new ArrayList<Subscription>());
		result.setVolumes(new HashSet<Volume>());

		return result;
	}

	public Collection<Newspaper> findAll() {
		Collection<Newspaper> res;
		res = this.newspaperRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Newspaper save(final Newspaper n) {
		Assert.notNull(n);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(n.getAuthor()));
		final Newspaper res = this.newspaperRepository.save(n);
		u.getNewspapers().add(res);
		this.userService.save(u);
		return res;
	}

	public Newspaper save2(final Newspaper n) {
		Assert.notNull(n);
		final Newspaper res = this.newspaperRepository.save(n);
		return res;
	}

	public void delete(final Newspaper n) {
		Assert.notNull(n);
		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(n.getIsOpen());
		this.newspaperRepository.delete(n);
	}

	public void deleteAdmin(final Newspaper n) {
		final Administrator a = this.administratorService.findByPrincipal();
		Assert.notNull(a);
		final Collection<Newspaper> all = this.findAll();
		for (final Newspaper comment : all)
			if (comment.getArticles().contains(n)) {
				comment.getArticles().remove(n);
				this.save2(comment);
			}
		final User u = n.getAuthor();
		u.getNewspapers().remove(n);
		this.userService.save(u);
		this.newspaperRepository.delete(n);
	}

	public void deleteAdmin1(final int newspaperId) {
		final Administrator a = this.administratorService.findByPrincipal();
		final Newspaper n = this.newspaperRepository.findOne(newspaperId);
		Assert.notNull(a);
		final Collection<Newspaper> all = this.findAll();
		for (final Newspaper comment : all)
			if (comment.getArticles().contains(n)) {
				comment.getArticles().remove(n);
				this.save2(comment);
			}
		final User u = n.getAuthor();
		u.getNewspapers().remove(n);
		this.userService.save(u);
		this.newspaperRepository.delete(n);
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Newspaper reconstruct(final Newspaper newspaper, final BindingResult binding) {
		Newspaper result;

		if (newspaper.getId() == 0)
			result = newspaper;
		else {
			result = this.newspaperRepository.findOne(newspaper.getId());

			result.setId(newspaper.getId());
			result.setVersion(newspaper.getVersion());
			if (newspaper.getTitle() != "")
				result.setTitle(newspaper.getTitle());
			else
				result.setTitle("");
			result.setPublication(newspaper.getPublication());
			if (newspaper.getDescription() != "")
				result.setDescription(newspaper.getDescription());
			else
				result.setDescription("");
			result.setPicture(newspaper.getPicture());
			if (newspaper.getIsOpen() != null)
				result.setIsOpen(newspaper.getIsOpen());
			result.setArticles(newspaper.getArticles());
			result.setSubscriptions(newspaper.getSubscriptions());
			result.setAuthor(newspaper.getAuthor());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Newspaper findOne(final int newspaperId) {

		Assert.isTrue(newspaperId != 0);
		Newspaper result;
		result = this.newspaperRepository.findOne(newspaperId);

		return result;

	}

	public void flush() {
		this.newspaperRepository.flush();
	}

	// Other business methods -------------------------------------------------

	//PUBLICAR
	public void publish(final Newspaper newspaper) {
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(newspaper.getAuthor().equals(this.userService.findByPrincipal()));
		Assert.isTrue(!newspaper.getArticles().isEmpty());
		Assert.isTrue(newspaper.getArticles().size() >= 2);
		Assert.isTrue(this.articleFinalMode(newspaper));
		newspaper.setPublish(true);
		this.save(newspaper);
	}

	public void makePublic(final Newspaper newspaper) {
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(newspaper.getAuthor().equals(this.userService.findByPrincipal()));
		newspaper.setIsOpen(true);
		this.save(newspaper);
	}

	public void makePrivate(final Newspaper newspaper) {
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(newspaper.getAuthor().equals(this.userService.findByPrincipal()));
		newspaper.setIsOpen(false);
		this.save(newspaper);
	}

	private Boolean articleFinalMode(final Newspaper n) {
		Boolean res = true;

		for (final Article a : n.getArticles())
			if (a.getDraftMode().equals(true))
				res = false;

		return res;
	}

	public Newspaper findByArticleId(final int articleId) {
		Newspaper result;

		result = this.newspaperRepository.findByArticleId(articleId);

		return result;
	}

	public Collection<Newspaper> findNewspapersByAuthor() {
		Collection<Newspaper> result;
		final User userPrincipal = this.userService.findByPrincipal();
		result = this.newspaperRepository.findNewspapersByAuthor(userPrincipal.getId());
		return result;
	}

	public Collection<Newspaper> newspaperPublished() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.newspaperPublished();

		return result;
	}

	public Collection<Newspaper> newspaperNotPublished() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.newspaperNotPublished();

		return result;
	}

	public Collection<Newspaper> findAllPrivate() {
		final Collection<Newspaper> res = this.newspaperRepository.findAllPrivate();
		Assert.notNull(res);
		return res;

	}

	public Collection<Newspaper> filter(final Collection<Newspaper> newspapers) {
		final Customer c = this.customerService.findByPrincipal();
		final Collection<Newspaper> res = new HashSet<Newspaper>();
		final Collection<Newspaper> aux = new HashSet<Newspaper>();

		for (final Subscription s : c.getSubscriptions())
			aux.add(s.getNewspaper());
		for (final Newspaper n : newspapers)
			if (!aux.contains(n))
				res.add(n);
		return res;

	}

	public Double avgArticlesPerNewspaper() {
		final Double res = this.newspaperRepository.avgArticlesPerNewspaper();
		return res;
	}

	public Double stddevArticlesPerNewspaper() {
		final Double res = this.newspaperRepository.stddevArticlesPerNewspaper();
		return res;
	}

	public Collection<Newspaper> moreArticlesThanAverage() {
		final Collection<Newspaper> res = this.newspaperRepository.moreArticlesThanAverage();
		return res;
	}

	public Collection<Newspaper> lessArticlesThanAverage() {
		final Collection<Newspaper> res = this.newspaperRepository.lessArticlesThanAverage();
		return res;
	}
	public Collection<Newspaper> searchNewspaper(final String keyword) {
		final Collection<Newspaper> res = this.newspaperRepository.searchNewspaper(keyword);
		return res;
	}

	public Double ratioPublicVersusPrivate() {
		final Double res = this.newspaperRepository.ratioPublicVersusPrivate();
		return res;
	}

	public Double avgArticlesPerPrivateNewspaper() {
		final Double res = this.newspaperRepository.avgArticlesPerPrivateNewspaper();
		return res;
	}

	public Double avgArticlesPerPublicNewspaper() {
		final Double res = this.newspaperRepository.avgArticlesPerPublicNewspaper();
		return res;
	}

	public Double ratioPublicVersusPrivatePerPublisher() {
		final Double res = this.newspaperRepository.ratioPublicVersusPrivatePerPublisher();
		return res;
	}

	public Double ratioWithAtLeastOneAdvertisiment() {
		final Double res = this.newspaperRepository.ratioWithAtLeastOneAdvertisiment();
		return res;
	}

	public Collection<Newspaper> allAdvertisements() {
		final Collection<Newspaper> res = this.newspaperRepository.allAdvertisements();
		return res;
	}

	public Collection<Newspaper> notAdvertisements() {
		final Collection<Newspaper> res = this.newspaperRepository.notAdvertisements();
		return res;
	}

	public Newspaper onlySave(final Newspaper newspaper) {

		final Newspaper res = this.newspaperRepository.save(newspaper);
		return res;
	}

	public Collection<Newspaper> getCustomersNewspapers(final Collection<Newspaper> newspapers) {
		final Collection<Newspaper> res = this.newspaperPublished();
		for (final Newspaper n : newspapers)
			for (final Subscription s : n.getSubscriptions())
				if (s.getCustomer().equals(this.customerService.findByPrincipal()) && n.getPublish() == true && n.getPublication().before(new Date()))
					res.add(n);
		return res;
	}

	public Collection<Newspaper> findAllVolumeNewspapersSubscribed(final Customer principal, final Volume volume) {

		return this.newspaperRepository.findAllVolumeNewspapersSubscribed(principal.getId(), volume.getId());
	}
}
