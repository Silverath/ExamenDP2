
package forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.Authority;

public class ActorAccountForm {

	private Integer		id;
	private String		username;
	private String		password;
	private Authority	authorities;
	private String		password2;

	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		address;
	private Boolean		terms;


	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}
	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public Authority getAuthorities() {
		return this.authorities;
	}
	public void setAuthorities(final Authority authorities) {
		this.authorities = authorities;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPassword2() {
		return this.password2;
	}
	public void setPassword2(final String password2) {
		this.password2 = password2;
	}

	public Boolean getTerms() {
		return this.terms;
	}

	public void setTerms(final Boolean terms) {
		this.terms = terms;
	}

}
