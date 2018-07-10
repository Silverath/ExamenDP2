
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	//---------Atributes-------------
	private String	name;
	private Boolean	modify;


	public Folder() {
		super();
	}
	//-----------Getter&Setters------------

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getModify() {
		return this.modify;
	}

	public void setModify(final Boolean modify) {
		this.modify = modify;
	}


	//--------------Relations

	private Collection<Message>	message;
	private Actor				actor;


	//private Folder				folderFather;

	@OneToMany(mappedBy = "folder")
	public Collection<Message> getMessage() {
		return this.message;
	}

	public void setMessage(final Collection<Message> message) {
		this.message = message;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
