
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MoletRepository;
import domain.Administrator;
import domain.Molet;
import domain.Newspaper;

@Service
@Transactional
public class MoletService {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MoletRepository			moletRepository;

	@Autowired
	private NewspaperService		newspaperService;


	public MoletService() {
		super();
	}

	public Molet create() {
		Molet result;
		result = new Molet();
		final Administrator principal = this.administratorService.findByPrincipal();
		result.setAdministrator(principal);
		result.setFinalMode(false);
		result.setTicker(this.createTicker());

		return result;
	}

	public String createTicker() {

		final String datePattern = "yyMMdd";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		final String moment = simpleDateFormat.format(new Date());
		String code = "";
		code += moment.charAt(4) + moment.charAt(5);
		final int random = MoletService.randInt(2, 5);
		for (int i = 0; i < random; i++)
			code += this.randomLetter();
		code += "-" + moment.charAt(2) + moment.charAt(3) + moment.charAt(0) + moment.charAt(1);
		return code;
	}
	public char randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(62));
		return result;
	}

	public static int randInt(final int min, final int max) {

		final Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		final int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public Collection<Molet> findAll() {
		Collection<Molet> res;
		res = this.moletRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Molet findOne(final Integer moletId) {
		final Molet res = this.moletRepository.findOne(moletId);
		Assert.notNull(res);

		return res;
	}

	public Molet findOneToSelectNewspaper(final Integer moletId) {
		final Molet res = this.moletRepository.findOne(moletId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(res.getAdministrator().equals(principal));
		Assert.notNull(res);
		Assert.isTrue(res.getFinalMode());

		return res;
	}

	public Molet save(final Molet molet) {
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();
		if (molet.getFinalMode() == null)
			molet.setFinalMode(false);
		if (molet.getNewspaper() != null || newspapers.isEmpty())
			molet.setFinalMode(true);
		final Molet res = this.moletRepository.save(molet);
		return res;
	}

	public Molet onlySave(final Molet molet) {
		Assert.isTrue(molet.getFinalMode());
		final Molet res = this.moletRepository.save(molet);

		return res;
	}

	public Molet findOneToEdit(final Integer moletId) {
		final Molet res = this.moletRepository.findOne(moletId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(res.getAdministrator().equals(principal));
		Assert.notNull(res);

		Assert.isTrue(!res.getFinalMode());
		return res;
	}
	public void deleteAdmin(final int moletId) {
		final Molet r = this.findOne(moletId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(r.getAdministrator().equals(principal));
		Assert.notNull(r);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.moletRepository.delete(r);
	}

	public void flush() {
		this.moletRepository.flush();
	}

	public Collection<Molet> findMoletsByAdmin(final int adminId) {
		return this.moletRepository.findMoletsByAdmin(adminId);
	}

	public Collection<Molet> findMoletsByNewspaper(final int newspaperId) {
		return this.moletRepository.findMoletsByAdmin(newspaperId);
	}

	public Collection<Molet> findAllPublicated() {
		return this.moletRepository.findAllPublicated();
	}
}
