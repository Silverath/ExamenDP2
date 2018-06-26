
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public MessageService		messageService;
	@Autowired
	public FolderService		folderService;
	@Autowired
	public AdministratorService	administratorService;


	@Test
	public void testCreate() {
		super.authenticate("admin");
		Message message;
		message = this.messageService.create();

		Assert.notNull(message);
		Assert.isNull(message.getBody());
		Assert.isNull(message.getPriority());
		Assert.isNull(message.getActorReceiver());
		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneNotExist() {
		super.authenticate("admin");
		this.messageService.findOne(0);
		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("user1");
		final Actor actor;
		final Folder folder;
		final Message message;

		actor = this.actorService.findByPrincipal();

		folder = (Folder) actor.getFolder().toArray()[0];
		message = this.messageService.create();
		message.setBody("Body 1");
		message.setFolder(folder);
		message.setPriority("LOW");
		message.setActorReceiver((Actor) this.actorService.findAll().toArray()[0]);
		message.setMoment(new Date());
		message.setActorSender(actor);
		message.setSubject("Subject 1");

		this.messageService.save(message);
		Assert.isTrue(actor.getFolder().contains(message.getFolder()));
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotLoggedNegative() {
		super.unauthenticate();
		final Actor actor;
		final Folder folder;
		final Message message;

		actor = this.actorService.findByPrincipal();

		folder = (Folder) actor.getFolder().toArray()[0];
		message = this.messageService.create();
		message.setBody("Body 1");
		message.setFolder(folder);
		message.setPriority("LOW");
		message.setActorReceiver((Actor) this.actorService.findAll().toArray()[0]);
		message.setMoment(new Date());
		message.setActorSender(actor);
		message.setSubject("Subject 1");

		this.messageService.save(message);
		Assert.isTrue(this.messageService.findAll().contains(message));
		Assert.isTrue(actor.getFolder().contains(message.getFolder()));
	}

	@Test
	public void deleteMessage() {
		super.authenticate("admin");
		final Message message;

		message = this.messageService.findOne(super.getEntityId("message1"));
		Assert.notNull(message);
		this.messageService.delete(message);
		Assert.isTrue(message.getFolder().getName().equals("trash box") || !this.folderService.findAll().contains(message));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNotLoggedNegative() {
		final Message message;

		message = this.messageService.findOne(super.getEntityId("Message1"));
		Assert.notNull(message);
		this.messageService.delete(message);
		Assert.isTrue(message.getFolder().getName().equals("trash box") || !this.folderService.findAll().contains(message));

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNotExistingMessage() {
		super.authenticate("admin");
		final Message message;

		message = this.messageService.findOne(0);
		Assert.notNull(message);
		this.messageService.delete(message);
		Assert.isTrue(message.getFolder().getName().equals("trash box") || !this.folderService.findAll().contains(message));

		super.unauthenticate();
	}

	@SuppressWarnings({
		"null", "unused"
	})
	@Test(expected = IllegalArgumentException.class)
	public void moveMessageNotLogged() {
		final Actor actor;
		Folder trashBox = null;
		Message message;

		actor = this.actorService.findByPrincipal();
		message = this.messageService.findOne(super.getEntityId("Message1"));
		trashBox = this.folderService.findOne(trashBox.getId());
		Assert.notNull(message);
		this.messageService.moveMessage(message, trashBox.getId());

		Assert.isTrue(message.getFolder().equals(trashBox));
	}

	@SuppressWarnings({
		"unused", "null"
	})
	@Test(expected = IllegalArgumentException.class)
	public void moveMessageNotOwnDestiny() {
		super.authenticate("admin");
		final Actor otherActor;
		Folder trashBox = null;
		Message message;

		otherActor = this.actorService.findOne(super.getEntityId("User1"));
		message = this.messageService.findOne(super.getEntityId("Message1"));
		trashBox = this.folderService.findOne(trashBox.getId());

		Assert.notNull(message);
		this.messageService.moveMessage(message, trashBox.getId());

		Assert.isTrue(message.getFolder().equals(trashBox));
		super.unauthenticate();
	}

	@SuppressWarnings({
		"unused", "null"
	})
	@Test(expected = IllegalArgumentException.class)
	public void moveMessageNotOwn() {
		super.authenticate("admin");
		final Actor actor;
		Folder trashBox = null;
		Message message;

		actor = this.actorService.findByPrincipal();
		message = this.messageService.findOne(super.getEntityId("Message8"));
		trashBox = this.folderService.findOne(trashBox.getId());
		Assert.notNull(message);
		this.messageService.moveMessage(message, trashBox.getId());

		Assert.isTrue(message.getFolder().equals(trashBox));
		super.unauthenticate();
	}

	@Test
	public void testBroadcastNotification() {
		super.authenticate("admin");

		final Actor actor;
		final Message message;
		//		Folder trashBox = null;
		//
		//		trashBox = this.folderService.findOne(trashBox.getId());

		actor = this.actorService.findByPrincipal();

		message = this.messageService.create();
		message.setBody("Body 1");
		message.setPriority("LOW");
		message.setActorReceiver((Actor) this.actorService.findAll().toArray()[0]);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setActorSender(actor);
		message.setSubject("Subject 1");
		this.messageService.saveBroadcast(message);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBroadcastNotificationNotLoggedNegative() {
		super.unauthenticate();
		final Actor actor;
		final Message message;

		actor = this.actorService.findByPrincipal();

		message = this.messageService.create();
		message.setBody("Body 1");
		message.setPriority("LOW");
		message.setActorReceiver((Actor) this.actorService.findAll().toArray()[0]);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setActorSender(actor);
		message.setSubject("Subject 1");
		this.messageService.saveBroadcast(message);

	}

}
