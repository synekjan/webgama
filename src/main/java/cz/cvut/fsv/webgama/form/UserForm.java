package cz.cvut.fsv.webgama.form;

import org.hibernate.validator.constraints.Length;

import cz.cvut.fsv.webgama.domain.User;

public class UserForm {

	private String username;

	@Length(max = 30)
	private String firstName;

	@Length(max = 50)
	private String lastName;

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
	
	public UserForm() {
	}

	public UserForm(User user) {
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.telephone = user.getTelephone();
		this.street = user.getStreet();
		this.number = user.getNumber();
		this.city = user.getCity();
		this.zipCode = user.getZipCode();
		this.state = user.getState();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
