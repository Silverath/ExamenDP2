
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository	folderRepository;

	@Autowired
	private ActorService		actorService;


	public FolderService() {
		super();
	}

	public Folder create() {
		final Collection<Message> message = new ArrayList<Message>();
		final Folder res = new Folder();
		final Actor a = this.actorService.findByPrincipal();
		res.setActor(a);
		res.setMessage(message);

		return res;
	}

	public Folder create2() {

		final Folder res = new Folder();

		return res;
	}

	public Folder findOne(final int elementoId) {
		final Folder result = this.folderRepository.findOne(elementoId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Folder> findAll() {
		final Collection<Folder> result = this.folderRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		final Folder res = this.folderRepository.save(folder);
		return res;

	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!this.defaultFolder(folder));
		this.folderRepository.delete(folder);
	}

	public void createFolderforRegisterActor(final Actor r) {

		final Folder inBox = this.create2();
		inBox.setActor(r);
		inBox.setName("InBox");
		inBox.setModify(false);
		r.addFolder(inBox);

		final Folder outBox = this.create2();
		outBox.setActor(r);
		outBox.setName("OutBox");
		outBox.setModify(false);
		r.addFolder(outBox);

		final Folder trashBox = this.create2();
		trashBox.setActor(r);
		trashBox.setName("TrashBox");
		trashBox.setModify(false);
		r.addFolder(trashBox);

		final Folder spamBox = this.create2();
		spamBox.setActor(r);
		spamBox.setName("SpamBox");
		spamBox.setModify(false);
		r.addFolder(spamBox);

		final Folder notificationBox = this.create2();
		spamBox.setActor(r);
		spamBox.setName("NotificationBox");
		notificationBox.setModify(false);
		r.addFolder(notificationBox);
	}
	public void saveFolderForActor(final Actor re) {
		final Folder inBox = this.create2();
		inBox.setActor(re);
		inBox.setName("InBox");
		inBox.setModify(false);

		final Folder outBox = this.create2();
		outBox.setActor(re);
		outBox.setName("OutBox");
		outBox.setModify(false);

		final Folder trashBox = this.create2();
		trashBox.setActor(re);
		trashBox.setName("TrashBox");
		trashBox.setModify(false);

		final Folder spamBox = this.create2();
		spamBox.setActor(re);
		spamBox.setName("SpamBox");
		spamBox.setModify(false);

		final Folder notificationBox = this.create2();
		notificationBox.setActor(re);
		notificationBox.setName("NotificationBox");
		notificationBox.setModify(false);

		this.save(inBox);
		this.save(outBox);
		this.save(trashBox);
		this.save(spamBox);
		this.save(notificationBox);

	}
	public Boolean defaultFolder(final Folder f) {
		Boolean res = false;
		if (f.getName() == "TrashBox" || f.getName() == "InBox" || f.getName() == "OutBox" || f.getName() == "SpamBox" || f.getName() == "NotificationBox")
			res = true;
		return res;
	}

	//Other Methods bussiness-------------------------------------------------------------------------------

	public Collection<Folder> getMyFolders() {
		final Actor a = this.actorService.findByPrincipal();
		Assert.notNull(a);
		final Collection<Folder> result = this.folderRepository.findFolderByActor(a);
		return result;
	}

	public Folder getInbox(final Actor actor) {
		final Collection<Folder> folders = actor.getFolder();
		Folder result = null;
		for (final Folder f : folders)
			if (f.getName().contains("InBox"))
				result = f;
		return result;

	}
	public Folder getOutbox(final Actor actor) {
		final Collection<Folder> folders = actor.getFolder();
		Folder result = null;
		for (final Folder f : folders)
			if (f.getName().contains("OutBox"))
				result = f;
		return result;

	}
	public Folder getTrashbox(final Actor actor) {
		final Collection<Folder> folders = actor.getFolder();
		Folder result = null;
		for (final Folder f : folders)
			if (f.getName().contains("TrashBox"))
				result = f;
		return result;

	}
	public Folder getSpambox(final Actor actor) {
		final Collection<Folder> folders = actor.getFolder();
		Folder result = null;
		for (final Folder f : folders)
			if (f.getName().contains("SpamBox"))
				result = f;
		return result;

	}
	public Folder getNotificationbox(final Actor actor) {
		final Collection<Folder> folders = actor.getFolder();
		Folder result = null;
		for (final Folder f : folders)
			if (f.getName().contains("NotificationBox"))
				result = f;
		return result;

	}

}
