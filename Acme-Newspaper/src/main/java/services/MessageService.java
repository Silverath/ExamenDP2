
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FolderService			folderService;

	//	@Autowired
	//	private ManagerService			managerService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Message create() {

		final Message result = new Message();

		final UserAccount senderAccount = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccount(senderAccount);
		final Date moment = new Date(System.currentTimeMillis() - 1);
		result.setFolder(this.folderService.getOutbox(sender));
		result.setMoment(moment);
		result.setActorSender(sender);

		return result;
	}

	public Message findOne(final int messageId) {
		final Message result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findAll() {

		final Collection<Message> result = this.messageRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void save(final Message message) { // send message
		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		Assert.notNull(message);
		final Actor recipient = message.getActorReceiver();
		final Message copy = this.create();
		copy.setBody(message.getBody());
		copy.setMoment(message.getMoment());
		copy.setActorSender(message.getActorSender());
		copy.setPriority(message.getPriority());
		copy.setSubject(message.getSubject());
		copy.setActorReceiver(recipient);
		Folder folder = new Folder();
		if (this.spamFilter(copy))
			folder = this.folderService.getSpambox(recipient);
		else
			folder = this.folderService.getInbox(recipient);
		copy.setFolder(folder);
		this.messageRepository.save(copy);
		this.messageRepository.save(message);
		final Collection<Message> mes = folder.getMessage();
		mes.add(copy);
		folder.setMessage(mes);
		this.folderService.save(folder);
		final Collection<Message> mes2 = message.getFolder().getMessage();
		mes2.add(message);
		message.getFolder().setMessage(mes2);
		this.folderService.save(message.getFolder());

	}

	public void delete(final Message message) {
		Assert.notNull(message);
		if (message.getFolder().getName().contains("TrashBox"))
			this.messageRepository.delete(message);
		else {
			Folder trashBox = null;
			final Collection<Folder> folders = this.folderService.getMyFolders();
			for (final Folder f : folders)
				if (f.getName().contains("TrashBox")) {
					trashBox = f;
					break;
				}
			Assert.notNull(trashBox);
			message.setFolder(trashBox);
			this.messageRepository.save(message);
		}
	}

	// Other Business methods -------------------------------------------------

	public void move(final Message message) { // move message
		Assert.notNull(message);
		this.messageRepository.delete(message);
		this.messageRepository.save(message);
		this.folderService.save(message.getFolder());

	}
	public void moveToTrash(final Message message) { // move to trash
		message.setFolder(this.folderService.getTrashbox(this.actorService.findByUserAccount(LoginService.getPrincipal())));
		this.messageRepository.save(message);
		this.folderService.save(message.getFolder());
	}

	public Collection<Message> getMyInbox2() {

		final UserAccount loginNow = LoginService.getPrincipal();
		Collection<Message> result = new ArrayList<Message>();
		final Actor actor = this.actorService.findByUserAccount(loginNow);

		result = actor.getMessageReceived();

		return result;

	}
	public Collection<Message> getMyInbox() {

		final UserAccount loginNow = LoginService.getPrincipal();
		Collection<Message> result = new ArrayList<Message>();
		final Actor actor = this.actorService.findByUserAccount(loginNow);
		final Folder folder = this.folderService.getInbox(actor);

		result = folder.getMessage();

		return result;

	}

	public Collection<Message> getMyOutbox() {

		final UserAccount loginNow = LoginService.getPrincipal();
		Collection<Message> result = new ArrayList<Message>();
		final Actor actor = this.actorService.findByUserAccount(loginNow);

		result = actor.getMessageSend();

		return result;

	}

	public void moveMessage(final Message message, final int folderId) {
		Assert.notNull(message);
		final Folder folder = this.folderService.findOne(folderId);
		message.setFolder(folder);
		this.messageRepository.save(message);
	}

	//Other bussines methods----------------------------------------------

	private boolean spamFilter(final Message m) {
		boolean res = false;
		if (m.getBody().contains("viagra") || m.getBody().contains("cialis") || m.getBody().contains("sex") || m.getBody().contains("jes extender") || m.getSubject().contains("viagra") || m.getSubject().contains("cialis") || m.getSubject().contains("sex")
			|| m.getSubject().contains("jes extender"))
			res = true;

		return res;
	}

	public void saveB(final Message message) {
		for (final Actor a : this.actorService.findAll()) {
			message.setActorReceiver(a);
			this.save(message);
		}
	}

	public void saveBroadcast(final Message message) { // send message
		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		Assert.notNull(message);
		final Message copy = this.create();
		copy.setBody(message.getBody());
		copy.setMoment(message.getMoment());
		copy.setActorSender(message.getActorSender());
		copy.setPriority(message.getPriority());
		copy.setSubject(message.getSubject());
		//final Actor recipient = message.getActorReceiver();
		//copy.setActorReceiver(a);
		Folder folder = new Folder();
		for (final Actor a : this.actorService.findAll()) {
			if (this.spamFilter(copy))
				folder = this.folderService.getSpambox(a);
			else
				folder = this.folderService.getNotificationbox(a);

			copy.setFolder(folder);
			this.messageRepository.save(copy);

		}

		//this.messageRepository.save(message);
		final Collection<Message> mes = folder.getMessage();
		mes.add(copy);
		folder.setMessage(mes);
		this.folderService.save(folder);
		final Collection<Message> mes2 = message.getFolder().getMessage();
		mes2.add(message);
		message.getFolder().setMessage(mes2);
		this.folderService.save(message.getFolder());

	}

	//	public void createMessageOnApplicationManager(final Application ap) {
	//
	//		final Message res = new Message();
	//		final UserAccount receivedAccount = LoginService.getPrincipal();
	//		final Manager received = this.managerService.findByUserAccount(receivedAccount);
	//		final Collection<Administrator> aa = this.administratorService.findAll();
	//		for (final Administrator a : aa) {
	//			res.setActorSender(a);
	//			final Date moment = new Date(System.currentTimeMillis() - 1);
	//			res.setMoment(moment);
	//			res.setFolder(this.folderService.getOutbox(a));
	//			res.setActorReceiver(received);
	//			res.setBody("Se ha cambiado el status");
	//			res.setPriority("LOW");
	//			res.setSubject("Broadcast Status");
	//
	//			break;
	//		}
	//
	//		final Date moment = new Date(System.currentTimeMillis() - 1);
	//		res.setMoment(moment);
	//		Assert.notNull(res);
	//		final Actor recipient = res.getActorReceiver();
	//		final Message copy = this.create();
	//		copy.setBody(res.getBody());
	//		copy.setMoment(res.getMoment());
	//		copy.setActorSender(res.getActorSender());
	//		copy.setPriority(res.getPriority());
	//		copy.setSubject(res.getSubject());
	//		copy.setActorReceiver(recipient);
	//		Folder folder = new Folder();
	//		if (this.spamFilter(copy))
	//			folder = this.folderService.getSpambox(recipient);
	//		else
	//			folder = this.folderService.getNotificationbox(recipient);
	//		copy.setFolder(folder);
	//		this.messageRepository.save(copy);
	//		this.messageRepository.save(res);
	//		final Collection<Message> mes = folder.getMessage();
	//		mes.add(copy);
	//		folder.setMessage(mes);
	//		this.folderService.save(folder);
	//		final Collection<Message> mes2 = res.getFolder().getMessage();
	//		mes2.add(res);
	//		res.getFolder().setMessage(mes2);
	//		this.folderService.save(res.getFolder());
	//
	//	}
	//
	//	public void createMessageOnApplicationExplorer(final Application ap) {
	//
	//		final Message res = new Message();
	//		final Collection<Administrator> aa1 = this.administratorService.findAll();
	//		final Explorer e = ap.getExplorer();
	//		for (final Administrator a : aa1) {
	//			res.setActorSender(a);
	//			final Date moment = new Date(System.currentTimeMillis() - 1);
	//			res.setMoment(moment);
	//			res.setFolder(this.folderService.getOutbox(a));
	//			res.setActorReceiver(e);
	//			res.setBody("Se ha cambiado el status");
	//			res.setPriority("LOW");
	//			res.setSubject("Broadcast status");
	//			break;
	//		}
	//
	//		final Date moment = new Date(System.currentTimeMillis() - 1);
	//		res.setMoment(moment);
	//		Assert.notNull(res);
	//		final Actor recipient = res.getActorReceiver();
	//		final Message copy = this.create();
	//		copy.setBody(res.getBody());
	//		copy.setMoment(res.getMoment());
	//		copy.setActorSender(res.getActorSender());
	//		copy.setPriority(res.getPriority());
	//		copy.setSubject(res.getSubject());
	//		copy.setActorReceiver(recipient);
	//		Folder folder = new Folder();
	//		if (this.spamFilter(copy))
	//			folder = this.folderService.getSpambox(recipient);
	//		else
	//			folder = this.folderService.getNotificationbox(recipient);
	//		copy.setFolder(folder);
	//		this.messageRepository.save(copy);
	//		this.messageRepository.save(res);
	//		final Collection<Message> mes = folder.getMessage();
	//		mes.add(copy);
	//		folder.setMessage(mes);
	//		this.folderService.save(folder);
	//		final Collection<Message> mes2 = res.getFolder().getMessage();
	//		mes2.add(res);
	//		res.getFolder().setMessage(mes2);
	//		this.folderService.save(res.getFolder());
	//
	//	}

}
