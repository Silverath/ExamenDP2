
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdvertisementRepository;
import domain.Administrator;
import domain.Advertisement;
import domain.Agent;

@Service
@Transactional
public class AdvertisementService {

	@Autowired
	private Validator				validator;

	@Autowired
	private AdvertisementRepository	advertisementRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AgentService			agentService;


	public AdvertisementService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Advertisement create() {
		Advertisement result;
		result = new Advertisement();
		result.setAgent(this.agentService.findByPrincipal());

		return result;
	}

	//------------------------------------------- Reconstruct --------------------------------------------

	public Advertisement reconstruct(final Advertisement advertisement, final BindingResult binding) {
		Advertisement result;

		if (advertisement.getId() == 0)
			result = advertisement;
		else {
			result = this.advertisementRepository.findOne(advertisement.getId());
			result.setId(advertisement.getId());
			result.setVersion(advertisement.getVersion());
			result.setTitle(advertisement.getTitle());
			result.setBanner(advertisement.getBanner());
			result.setTargetPage(advertisement.getTargetPage());
			result.setCreditCard(advertisement.getCreditCard());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Advertisement save(final Advertisement advertisement) {
		Assert.notNull(advertisement);
		final Agent u = this.agentService.findByPrincipal();
		final Advertisement res = this.advertisementRepository.save(advertisement);
		return res;
	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> res;
		res = this.advertisementRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Advertisement findOne(final Integer advertisementId) {
		final Advertisement res = this.advertisementRepository.findOne(advertisementId);
		Assert.notNull(res);
		return res;
	}

	public void deleteAdmin(final int advertisementId) {
		Assert.notNull(advertisementId);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Advertisement a = this.findOne(advertisementId);

		this.advertisementRepository.delete(a);
	}

	public void validMonthCreditCard(final Advertisement advertisement) {
		final Boolean res = false;
		final int year = advertisement.getCreditCard().getExpirationYear();
		final int month = advertisement.getCreditCard().getExpirationMonth();
		final Date now = new Date();
		final Boolean y = year == now.getYear() + 1900;
		final Boolean m = month == now.getMonth() + 1;
		if (y)
			Assert.isTrue(!m, "advertisement.error.creditCard");

	}

	public Collection<Advertisement> adsWithTaboo() {
		final Collection<Advertisement> ads = this.advertisementRepository.findAll();
		final Collection<Advertisement> res = new HashSet<Advertisement>();
		final Collection<String> taboos = this.configurationService.findOne().getTaboo();
		for (final Advertisement a : ads)
			for (final String taboo : taboos)
				if (a.getTitle().contains(taboo))
					res.add(a);
		return res;
	}

	//	public Double ratioAdvertisementsWithTaboo() {
	//		int res = 0;
	//
	//		final Collection<String> taboo = this.configurationService.findOne().getTaboo();
	//		for (final Configuration t : taboo)
	//			for (final Advertisement a : this.advertisementRepository.findAll())
	//				if (a.getTitle().contains(t.getTaboo()) || a.getBanner().contains(t.getTaboo()) || a.getTargetPage().contains(t.getTaboo()))
	//					res += 1;
	//
	//		return this.advertisementRepository.ratioAdsWithTabooWord(res);
	//	}

	public Double ratioAdvertisementsWithTaboo() {
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		Double res = 0.0;
		for (final Advertisement a : this.advertisementRepository.findAll())
			for (final String s : tabooWords)
				if (a.getTitle().contains(s) || a.getBanner().contains(s) || a.getTargetPage().contains(s))
					res += 1;
		return this.advertisementRepository.ratioAdvertisementsWithTaboo(res);
	}

	public Advertisement getRandomAdvertisement(final Collection<Advertisement> advertisements) {
		final Random rnd = new Random();
		final int i = rnd.nextInt(advertisements.size());
		return (Advertisement) advertisements.toArray()[i];
	}

	public void flush() {
		this.advertisementRepository.flush();
	}

}
