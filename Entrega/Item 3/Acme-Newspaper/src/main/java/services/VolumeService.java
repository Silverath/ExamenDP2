
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.VolumeRepository;
import domain.Customer;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class VolumeService {

	@Autowired
	private Validator			validator;

	@Autowired
	private VolumeRepository	volumeRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	public VolumeService() {
		super();
		// TODO Auto-generated constructor stub
	}

	//---------------------------------------------- Create ----------------------------------------------

	public Volume create() {
		final Volume result = new Volume();
		final User userPrincipal = this.userService.findByPrincipal();
		result.setPublisher(userPrincipal);
		result.setNewspapers(new HashSet<Newspaper>());

		return result;
	}

	public Collection<Volume> findAll() {
		Collection<Volume> res;
		res = this.volumeRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Volume save(final Volume v) {
		Assert.notNull(v);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(v.getPublisher()));
		final Volume res = this.volumeRepository.save(v);
		for (final Newspaper n : res.getNewspapers()) {
			n.getVolumes().add(res);
			this.newspaperService.save2(n);
		}
		u.getVolumes().add(res);
		this.userService.save(u);
		return res;
	}
	public Volume save2(final Volume v) {
		Assert.notNull(v);
		final Volume res = this.volumeRepository.save(v);
		return res;
	}

	public Volume findOne(final int volumeId) {

		Assert.isTrue(volumeId != 0);
		Volume result;
		result = this.volumeRepository.findOne(volumeId);

		return result;

	}

	public void flush() {
		this.volumeRepository.flush();
	}

	public Collection<Volume> findAllNotSubscribed(final Customer principal) {
		final Collection<Volume> all = this.findAll();
		final Collection<Volume> subscribed = this.volumeRepository.findAllSubscribed(principal.getId());
		all.removeAll(subscribed);
		return all;
	}

	public Collection<Newspaper> filter(final int volumeId) {
		final Volume v = this.volumeRepository.findOne(volumeId);
		final Collection<Newspaper> res = this.newspaperService.findAll();
		res.removeAll(v.getNewspapers());
		return res;
	}

	public void addNews(final Volume volume, final Collection<Newspaper> aux) {
		Assert.notNull(volume);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(volume.getPublisher()));
		aux.addAll(volume.getNewspapers());
		for (final Newspaper n : volume.getNewspapers()) {
			n.getVolumes().add(volume);
			this.newspaperService.save2(n);
		}
		volume.setNewspapers(aux);
		this.volumeRepository.save(volume);
	}

	public void deleteNews(final Volume volume, final Collection<Newspaper> aux) {
		Assert.notNull(volume);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(volume.getPublisher()));
		aux.removeAll(volume.getNewspapers());
		for (final Newspaper n : volume.getNewspapers()) {
			n.getVolumes().remove(volume);
			this.newspaperService.save2(n);
		}
		volume.setNewspapers(aux);
		this.volumeRepository.save(volume);

	}

	public Double avgNewspaperPerVolume() {
		final Double res = this.volumeRepository.avgNewspaperPerVolume();
		return res;
	}

	public Double ratioSubscriptionsVolumesNewspapers() {
		final Double res = this.volumeRepository.ratioSubscriptionsVolumesNewspapers();
		return res;
	}

	public void delete(final Volume volume) {
		Assert.notNull(volume);

		//Assertion that the user deleting this volume has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == volume.getPublisher().getId());

		this.volumeRepository.delete(volume);
	}

	public Collection<Newspaper> findAllPublicNewspapers() {
		return this.volumeRepository.findAllPublicNewspapers();
	}
}
