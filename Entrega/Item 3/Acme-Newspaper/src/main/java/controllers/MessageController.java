
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services ---------------------------------------------------------
	@Autowired
	private MessageService	messageService;
	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	public MessageController() {
		super();
	}

	// Listing --------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Message> messages;
		Collection<Folder> folders;

		//messages = messageService.getMyInbox();
		messages = null;
		folders = this.folderService.getMyFolders();
		final Collection<Folder> folder2 = new ArrayList<Folder>();
		folder2.addAll(folders);
		final Collection<Folder> reservFolders = new ArrayList<Folder>();
		for (final Folder f : folder2)
			if (f.getName().contains("InBox") || f.getName().contains("OutBox") || f.getName().contains("TrashBox") || f.getName().contains("SpamBox") || f.getName().contains("NotificationBox")) {
				folders.remove(f);
				reservFolders.add(f);
			}
		result = new ModelAndView("message/list");
		result.addObject("reservFolders", reservFolders);
		result.addObject("folders", folders);
		result.addObject("all", true);
		result.addObject("folder", null);

		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");
		result.addObject("requestURI2", "message/folder/create.do");

		return result;
	}

	@RequestMapping(value = "/listByFolder", method = RequestMethod.GET)
	public ModelAndView listByFolder(@RequestParam final Integer folderId) {

		Collection<Folder> folders;
		ModelAndView result;
		Collection<Message> messages;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		folders = this.folderService.getMyFolders();
		final Collection<Folder> folder2 = new ArrayList<Folder>();
		folder2.addAll(folders);
		final Collection<Folder> reservFolders = new ArrayList<Folder>();
		for (final Folder f : folder2)
			if (f.getName().contains("InBox") || f.getName().contains("OutBox") || f.getName().contains("TrashBox") || f.getName().contains("SpamBox") || f.getName().contains("NotificationBox")) {
				folders.remove(f);
				reservFolders.add(f);
			}
		messages = folder.getMessage();
		final Collection<String> rec = new ArrayList<String>();
		rec.add("");
		final Collection<String> send = new ArrayList<String>();
		send.add("");
		for (final Message m : messages) {
			rec.add(m.getActorReceiver().getName());
			send.add(m.getActorSender().getName());
		}
		result = new ModelAndView("message/listByFolder");
		result.addObject("all", false);
		result.addObject("reservFolders", reservFolders);
		result.addObject("rec", rec);
		result.addObject("send", send);
		result.addObject("folders", folders);
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/listByFolder.do?folderId=" + folderId);
		result.addObject("requestURI2", "message/folder/create.do");

		return result;
	}

	// Create ----------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		Assert.notNull(message);

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/folder/create", method = RequestMethod.GET)
	public ModelAndView createFolder() {
		ModelAndView result;
		Folder folder;
		;
		folder = this.folderService.create();
		result = this.createEditModelAndView(folder);
		result.addObject("create", true);
		return result;
	}

	// Edit ----------------------------------------

	@RequestMapping(value = "/folder/edit", method = RequestMethod.GET)
	public ModelAndView editFolder(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		result = this.createEditModelAndView(folder);
		result.addObject("create", false);
		return result;
	}

	// Save--------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				final Folder f = this.folderService.getOutbox(this.actorService.findByUserAccount(LoginService.getPrincipal()));

				message.setFolder(f);

				this.messageService.save(message);

				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/folder/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveFolder(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result = null;
		boolean pass = true;
		boolean b = true;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(folder);
			if (folder.getId() == 0)
				result.addObject("create", true);
			else
				result.addObject("create", false);
		} else
			try {
				for (final Folder s : this.folderService.getMyFolders())
					if (s.getName().contains(folder.getName())) {
						result = this.createEditModelAndView(folder, "folder.commit.error.reserved");
						if (folder.getId() == 0)
							result.addObject("create", true);
						else
							result.addObject("create", false);
						b = false;
						break;
					}
				if (b) {
					this.folderService.save(folder);
					pass = false;
					result = new ModelAndView("redirect:../list.do");
				}
			} catch (final Throwable oops) {
				try {
					if (pass)
						this.folderService.save(folder);
					result = new ModelAndView("redirect:../list.do");

				} catch (final Throwable oops2) {
					result = this.createEditModelAndView(folder, "folder.commit.error");
				}
			}
		return result;
	}

	// Delete ----------------------------------------

	@RequestMapping(value = "/folder/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteFolder(final Folder folder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "message.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/delete")
	public ModelAndView deleteMessage(@RequestParam final int mId) {
		final Message m = this.messageService.findOne(mId);
		Assert.isTrue(m.getFolder().getActor().getUserAccount().equals(LoginService.getPrincipal()));
		ModelAndView result;
		try {
			if (m.getFolder().getName().contains("TrashBox"))
				this.messageService.delete(m);
			else
				this.messageService.moveToTrash(m);
			result = new ModelAndView("redirect:../message/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(m, "message.commit.error");
		}
		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message messa, final String message) {
		ModelAndView result;
		Actor sender;
		Folder folder;
		Date moment;
		final Collection<String> priorityList = new ArrayList<String>();
		priorityList.add("HIGH");
		priorityList.add("NEUTRAL");
		priorityList.add("LOW");
		final Collection<Actor> recipientsActor = this.actorService.findAll();

		if (messa.getActorSender() == null)
			sender = null;
		else
			sender = messa.getActorSender();

		if (messa.getFolder() == null)
			folder = null;
		else
			folder = messa.getFolder();
		if (messa.getMoment() == null)
			moment = null;
		else
			moment = messa.getMoment();

		messa.setFolder(folder);
		messa.setMoment(moment);
		messa.setActorSender(sender);

		result = new ModelAndView("message/edit");
		result.addObject("message", messa);
		result.addObject("recipientsConsumers", recipientsActor);
		result.addObject("priorityList", priorityList);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String au) {
		ModelAndView result;

		result = new ModelAndView("message/folder/edit");
		result.addObject("error", au);
		result.addObject("create", true);
		result.addObject("folder", folder);
		return result;
	}

	// ///////////////////////////////////////////////////////

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView editMessageFolder(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = this.createEditFolderModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMessageFolder(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditFolderModelAndView(message);
		else
			try {
				this.messageService.move(message);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditFolderModelAndView(message, "message.commit.error");
			}

		return result;
	}

	private ModelAndView createEditFolderModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditFolderModelAndView(message, null);

		return result;
	}

	private ModelAndView createEditFolderModelAndView(final Message message, final String string) {
		ModelAndView result;
		Collection<Folder> avaiableFolders;
		avaiableFolders = this.folderService.getMyFolders();
		avaiableFolders.remove(message.getFolder());
		result = new ModelAndView("message/move");
		result.addObject("avaiableFolders", avaiableFolders);
		result.addObject("messa", message);

		return result;
	}

	@RequestMapping(value = "/createBroadcast", method = RequestMethod.GET)
	public ModelAndView createBroadcast() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		Assert.notNull(message);

		result = this.createEditModelAndViewB(message);

		return result;
	}

	protected ModelAndView createEditModelAndViewB(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndViewB(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewB(final Message messa, final String message) {
		ModelAndView result;
		Actor sender;
		Folder folder;
		Date moment;
		final Collection<String> priorityList = new ArrayList<String>();
		priorityList.add("HIGH");
		priorityList.add("NEUTRAL");
		priorityList.add("LOW");

		if (messa.getActorSender() == null)
			sender = null;
		else
			sender = messa.getActorSender();

		if (messa.getFolder() == null)
			folder = null;
		else
			folder = messa.getFolder();
		if (messa.getMoment() == null)
			moment = null;
		else
			moment = messa.getMoment();

		messa.setFolder(folder);
		messa.setMoment(moment);
		messa.setActorSender(sender);

		result = new ModelAndView("message/editBroadcast");
		result.addObject("message", messa);
		result.addObject("priorityList", priorityList);

		return result;
	}

	@RequestMapping(value = "/editBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBroadcast(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndViewB(message);
		else
			try {
				final Folder f = this.folderService.getOutbox(this.actorService.findByUserAccount(LoginService.getPrincipal()));

				message.setFolder(f);

				this.messageService.saveB(message);

				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndViewB(message, "message.commit.error");
			}
		return result;
	}
}
