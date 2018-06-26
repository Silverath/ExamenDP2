
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private ConfigurationService	configurationService;


	public AdministratorService() {
		super();
	}

	public Administrator create() {
		Administrator result;

		result = new Administrator();

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Administrator findByPrincipal() {
		Administrator administrator;
		UserAccount result;
		result = LoginService.getPrincipal();
		Assert.notNull(result);
		administrator = this.findByUserAccount(result);
		Assert.notNull(administrator);
		return administrator;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res;

		res = this.administratorRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	public Administrator findOne(final Integer administratorId) {
		final Administrator a = this.administratorRepository.findOne(administratorId);
		return a;
	}

	public Administrator save(final Administrator c) {
		Assert.notNull(c);
		final Administrator res = this.administratorRepository.save(c);
		return res;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

	public Collection<Article> ArticlesWithTabooWords(final Collection<Article> articles) {
		final Collection<Article> res = new HashSet<Article>();
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		for (final Article a : articles)
			for (final String s : tabooWords)
				if (a.getBody().contains(s) || a.getSummary().contains(s) || a.getTitle().contains(s)) {
					res.add(a);
					break;
				}
		return res;
	}
	public Collection<Newspaper> newspapersWithTabooWords(final Collection<Newspaper> newspapers) {
		final Collection<Newspaper> res = new HashSet<Newspaper>();
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		for (final Newspaper a : newspapers)
			for (final String s : tabooWords)
				if (a.getDescription().contains(s) || a.getTitle().contains(s)) {
					res.add(a);
					break;
				}
		return res;
	}

	public Collection<Chirp> chirpsWithTabooWords(final Collection<Chirp> chirps) {
		final Collection<Chirp> res = new HashSet<Chirp>();
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		for (final Chirp a : chirps)
			for (final String s : tabooWords)
				if (a.getDescription().contains(s) || a.getTitle().contains(s)) {
					res.add(a);
					break;
				}
		return res;
	}
}
