package cz.cvut.fsv.webgama.form;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordRecoveryForm {

	@NotBlank
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
