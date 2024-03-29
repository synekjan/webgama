package cz.cvut.fsv.webgama.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserRegistrationForm {

	@NotBlank
	@Length(min = 3, max = 30)
	@Pattern(regexp = "[^\\s]+", message="Pattern.username")
	private String username;

	@NotBlank
	@Length(min = 6, message = "at least 6 characters")
	private String password;

	private String confirmPassword;

	@Length(max = 30)
	private String firstName;

	@Length(max = 50)
	private String lastName;

	@NotBlank
	@Email
	@Length(max = 50)
	private String email;

	@Length(max = 15)
	private String telephone;

	@Length(max = 50)
	private String street;

	@Length(max = 20)
	private String number;

	@Length(max = 50)
	private String city;

	@Length(max = 30)
	private String zipCode;

	@Length(max = 50)
	private String state;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
