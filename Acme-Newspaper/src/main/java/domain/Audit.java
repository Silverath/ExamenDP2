
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	private Date	moment;
	private String	title;
	private String	ticker;
	private String	description;
	private Integer	gauge;
	private Boolean	finalMode;


	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Future
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Length(min = 1, max = 250)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Range(min = 1, max = 3)
	public Integer getGauge() {
		return this.gauge;
	}

	public void setGauge(final Integer gauge) {
		this.gauge = gauge;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Length(min = 1, max = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	private Administrator	administrator;
	private Newspaper		newspaper;


	//	private Follower	follower;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	@ManyToOne(optional = true)
	@Valid
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
