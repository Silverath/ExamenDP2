
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
		this.folder = new HashSet<Folder>();

	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	address;


	@SafeHtml
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@SafeHtml
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@SafeHtml
	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}


	// Relationships ----------------------------------------------------------

	private UserAccount			userAccount;
	private Collection<Folder>	folder;
	private Collection<Message>	messageSend;
	private Collection<Message>	messageReceived;


	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "actor")
	public Collection<Folder> getFolder() {
		return this.folder;
	}

	public void setFolder(final Collection<Folder> folder) {
		this.folder = folder;
	}

	public void addFolder(final Folder folder) {
		this.folder.add(folder);
	}

	@Valid
	@OneToMany(mappedBy = "actorSender")
	public Collection<Message> getMessageSend() {
		return this.messageSend;
	}

	public void setMessageSend(final Collection<Message> messageSend) {
		this.messageSend = messageSend;
	}

	@Valid
	@OneToMany(mappedBy = "actorReceiver")
	public Collection<Message> getMessageReceived() {
		return this.messageReceived;
	}

	public void setMessageReceived(final Collection<Message> messageReceived) {
		this.messageReceived = messageReceived;
	}

}
