
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Administrator;
import domain.Audit;

@Service
@Transactional
public class AuditService {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditRepository			auditRepository;

	@Autowired
	private NewspaperService		newspaperService;


	public AuditService() {
		super();
	}

	public Audit create() {
		Audit result;
		result = new Audit();
		final Administrator principal = this.administratorService.findByPrincipal();
		result.setAdministrator(principal);
		result.setFinalMode(false);
		result.setTicker(this.createTicker());

		return result;
	}

	public String createTicker() {

		String result;
		final String datePattern = "yyMMdd";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		final String moment = simpleDateFormat.format(new Date());
		String code = "";
		code += "-" + this.randomLetter() + this.randomLetter() + this.randomLetter() + this.randomLetter();
		result = moment + code;
		return result;
	}

	public char randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(52));
		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> res;
		res = this.auditRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Audit findOne(final Integer auditId) {
		final Audit res = this.auditRepository.findOne(auditId);
		Assert.notNull(res);

		return res;
	}

	public Audit findOneToSelectNewspaper(final Integer auditId) {
		final Audit res = this.auditRepository.findOne(auditId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(res.getAdministrator().equals(principal));
		Assert.notNull(res);
		Assert.isTrue(res.getFinalMode());

		return res;
	}

	public Audit save(final Audit audit) {
		if (audit.getFinalMode() == null)
			audit.setFinalMode(false);
		final Audit res = this.auditRepository.save(audit);
		return res;
	}

	public Audit onlySave(final Audit audit) {
		Assert.isTrue(audit.getFinalMode());
		final Audit res = this.auditRepository.save(audit);

		return res;
	}

	public Audit findOneToEdit(final Integer auditId) {
		final Audit res = this.auditRepository.findOne(auditId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(res.getAdministrator().equals(principal));
		Assert.notNull(res);

		Assert.isTrue(!res.getFinalMode());
		return res;
	}
	public void deleteAdmin(final int auditId) {
		final Audit r = this.findOne(auditId);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(r.getAdministrator().equals(principal));
		Assert.notNull(r);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.auditRepository.delete(r);
	}

	public void flush() {
		this.auditRepository.flush();
	}

	public Collection<Audit> findAuditsByAdmin(final int adminId) {
		return this.auditRepository.findAuditsByAdmin(adminId);
	}

	public Collection<Audit> findAuditsByNewspaper(final int newspaperId) {
		return this.auditRepository.findAuditsByAdmin(newspaperId);
	}

	public Collection<Audit> findAllPublicated() {
		return this.auditRepository.findAllPublicated();
	}
}
